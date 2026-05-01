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

      <el-button style="margin-left: 10px" type="primary" @click="loadPost">查询</el-button>
      <el-button type ="info" @click="resetSearch">重置</el-button>
      <el-button type ="success" @click="addNewMember">新增</el-button>


    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#2C3E50', color: '#FFFFFF' }"
              border
    >
      <el-table-column prop="id" label="ID" width="190">
      </el-table-column>
      <el-table-column prop="name" label="品类名" width="240">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="200">
      </el-table-column>

      <el-table-column prop="oprate" label="操作" width="330">
        <template slot-scope="scope">
          <el-button size ="small" type="primary" @click="modify(scope.row)">编辑</el-button>
          <el-popconfirm
              title="Sure to delete?"
              @confirm="deleteData(scope.row.id)"
          >
            <el-button slot="reference" size ="small" type="danger">删除</el-button>
          </el-popconfirm>
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
        <el-form-item label="品类名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>



        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark"></el-input>
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
  name: "goodsTypeManager",
  data() {
    return {
      tableData: [],
      pageSize:10,
      pageNum: 1,
      total: 0,
      name: '',

      centerDialogVisible: false,
      form:{
        id: '', // 用于编辑时传递用户ID
        name: '',
        remark:'',

      },

      rules:{
        name: [
          { required: true, message: '请输入品类名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
      },

    }
  },
  methods:{
    modify(row) {
      this.centerDialogVisible = true; // 打开对话框
      this.$nextTick(() => {
        // 确保表单重置在对话框显示后执行
        this.form.id = row.id; // 假设后端返回的用户对象有 id 字段
        this.form.name = row.name;
        this.form.remark = row.remark;
      });

    },
    deleteData(id) {
      this.$axios.get(this.$httpUrl + '/goodsType/delete?id=' + id).then(result => result.data).then(result => {
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
      this.$axios.post(this.$httpUrl + '/goodsType/modify', this.form).then(result => {
        // 建议使用 result.data.code
        if (result.data.code == 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          // 可能还需要关闭对话框和刷新列表等操作
          this.centerDialogVisible = false;
          this.loadPost();
        } else {
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      });

    },
    doSave() {
      this.$axios.post(this.$httpUrl + '/goodsType/save', this.form).then(result => {
        // 建议使用 result.data.code
        if (result.data.code == 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          // 可能还需要关闭对话框和刷新列表等操作
          this.centerDialogVisible = false;
          this.loadPost();
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

    loadPost() {
      console.log('loadPost called'),

          this.$axios.post(this.$httpUrl + '/goodsType/listPage', {
            pageSize : this.pageSize,
            pageNum : this.pageNum,
            param:{
              name:this.name,
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