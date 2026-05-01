<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input
          placeholder="请输入查询内容"
          suffix-icon="el-icon-search"
          style="width: 200px"
          v-model="name"
          @keyup.enter.native="loadPost">
      </el-input>

      <el-select v-model="sex" filterable placeholder="性别" style="margin-left: 10px">
        <el-option
            v-for="item in sexes"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-button style="margin-left: 10px" type="primary" @click="loadPost">查询</el-button>
      <el-button type ="info" @click="resetSearch">重置</el-button>

    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#2C3E50', color: '#FFFFFF' }"
              border
              highlight-current-row
              @current-change="handleSelectRow"
    >
      <el-table-column prop="id" label="ID" width="190">
      </el-table-column>
      <el-table-column prop="no" label="账号" width="240">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="200">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="100">

        <template slot-scope="scope">

          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transition>
            {{ scope.row.sex === 1 ? '男' : '女' }}
          </el-tag>

        </template>

      </el-table-column>
      <el-table-column prop="age" label="年龄" width="100">
      </el-table-column>
      <el-table-column prop="phone" label="电话">
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag v-if="roleMap[scope.row.roleId]"
                  :type="roleMap[scope.row.roleId].type"
                  disable-transition>
            {{ roleMap[scope.row.roleId].text }}
          </el-tag>
        </template>
      </el-table-column>

    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[ 5, 10,20,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="请输入"
        :visible.sync="centerDialogVisible"
        width="20%"
        center>


      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="账号" prop="no">
          <el-input v-model="form.no"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
              v-model="form.password"
              placeholder="请输入密码"
              show-password>
          </el-input>
        </el-form-item>

        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
      </el-form>






      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "selectUser",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value < 1 || value > 120) {
        callback(new Error('年龄必须在1到120之间'));
      } else {
        callback();
      }
    };
    //帐号唯一验证
    let checkNo = (rule,value,callback)=>{
      console.log('checkNo called', value, this.form.id)
      if(this.form.id){
        return callback();
      }else{
        this.$axios.get(this.$httpUrl + '/user/findByNo?no='+this.form.no).then(res=>res.data).then(res=>{
          console.log(res.code)
          if(res.code!=200){
            callback()
          }else{
            callback(new Error('账号已存在，请重新输入'));
          }
        })
      }
    };
    return {
      currentRow:{},
      tableData: [],
      pageSize:10,
      pageNum: 1,
      total: 0,
      name: '',
      sex:'',
      centerDialogVisible: false,
      form:{
        id: '', // 用于编辑时传递用户ID
        name: '',
        no:'',
        password:'',
        age:'',
        phone:'',
        sex:'1',
        roleId:'2',
      },

      rules:{
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur' },
          { validator: checkNo, trigger: 'blur' } // 如果需要验证账号唯一性，可以取消注释
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        age:[
          { required:true,message:'请输入年龄',trigger:'blur'},

          { min:1, max: 3, message: '年龄位数必须是三位', trigger: 'blur' },
          {pattern:/^([1-9][0-9]*){1,3}$/,message:'年龄必须是正整数',trigger:'blur'},
          {validator:checkAge,trigger:'blur'},
        ],
        phone:[
          { required:true,message:'请输入电话号码',trigger:'blur'},
          { pattern:/^1[3-9]\d{9}$/,message:'电话号码格式不正确',trigger:'blur'}
        ]
      },

      sexes: [
        { value: 1, label: '男' },
        { value: 0, label: '女' }
      ],
      roleMap: {
        0: { text: '超级管理员', type: 'danger' },
        1: { text: '管理员', type: 'info' },
        2: { text: '普通用户', type: 'success' }
      }
    }
  },
  methods:{
    handleSelectRow(val){
      this.$emit("doSelectUser",val)
    },
    modify(row) {
      this.centerDialogVisible = true; // 打开对话框
      this.$nextTick(() => {
        // 确保表单重置在对话框显示后执行
        this.form.id = row.id; // 假设后端返回的用户对象有 id 字段
        this.form.name = row.name;
        this.form.no = row.no;
        this.form.age = row.age +'';
        this.form.phone = row.phone;
        this.form.password = ''; // 清空密码字段，避免编辑时误修改密码
        this.form.sex=row.sex+''; // 确保性别是字符串类型
        this.form.roleId = row.roleId; // 如果有角色ID字段
      });

    },
    deleteData(id) {
      this.$axios.get(this.$httpUrl + '/user/delete?id=' + id).then(result => result.data).then(result => {
        if (result.code == 200) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.loadPost(); // 刷新列表
        } else {
          this.$message({
            message: '删除失败',
            type: 'error'
          });
        }
      });

    },
    doModify(){
      this.$axios.post(this.$httpUrl + '/user/modify', this.form).then(result => {
        // 建议使用 result.data.code
        if (result.data.code == 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          // 可能还需要关闭对话框和刷新列表等操作
          this.centerDialogVisible = false;
          this.loadPost();
          this.resetForm();
        } else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      });

    },
    doSave() {
      this.$axios.post(this.$httpUrl + '/user/save', this.form).then(result => {
        // 建议使用 result.data.code
        if (result.data.code == 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          // 可能还需要关闭对话框和刷新列表等操作
          this.centerDialogVisible = false;
          this.loadPost();
          this.resetForm();
        } else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      });
    },

    save() {
      // 先对表单进行验证
      this.$refs.form.validate((valid)=>{
        if(valid){
          if(this.form.id){
            this.doModify();
          }else{
            this.doSave();
          }
          // 直接将 this.form 作为请求体发送，不要再用花括号包裹
        }
        else{
          return false; // 如果验证不通过，直接返回
        }
      })
      // 在发送前，可以先打印一下 this.form，确保它的值都是正确的
      console.log('即将发送给后端的数据:', this.form);


    },
    resetForm(){
      this.$refs.form.resetFields(); // 重置表单
    },
    addNewMember(){
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        // 确保表单重置在对话框显示后执行
        this.resetForm();
        this.form.id = ''; // 清空ID，表示新增

      });

    },
    resetSearch(){
      this.name='';
      this.sex='';
      //this.loadPost();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.pageNum = 1; // 重置页码为1
      this.loadPost();

    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    loadGet(){
      this.$axios.get(this.$httpUrl+ '/user/list').then(result=>result.data).then(result => {
        console.log('loadGet called', result);
        this.tableData = result;

      });
    },
    loadPost() {
      console.log('loadPost called'),

          this.$axios.post(this.$httpUrl + '/user/listPageC1', {
            pageSize : this.pageSize,
            pageNum : this.pageNum,
            param:{
              name:this.name,
              sex:this.sex,
              roleId:'2',
            },
          }).then(result => result.data).then(result => {
            console.log('loadPost called', result)
            if(result.code == 200){
              this.tableData = result.data
              this.total = result.total
            }else{
              alert('获取数据失败')
            }
          });
    }

  },


  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>

</style>