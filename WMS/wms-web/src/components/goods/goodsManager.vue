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
      <el-select v-model="storage" placeholder="选择仓库">
        <el-option
          v-for="item in storageData"
          :key="item.id"
          :label="item.name"
          :value="item.id">

        </el-option>


      </el-select>

      <el-select v-model="goodstype" placeholder="选择物品类型">
        <el-option
            v-for="item in goodsTypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">

        </el-option>

      </el-select>



      <el-button style="margin-left: 10px" type="primary" @click="loadPost">查询</el-button>
      <el-button type ="info" @click="resetSearch">重置</el-button>
      <el-button type ="success" @click="addNewMember" v-if="user.roleId!=2">新增</el-button>
      <el-button type ="primary" @click="plusGood" v-if="user.roleId!=2">入库</el-button>
      <el-button type ="primary" @click="outGood" v-if="user.roleId!=2">出库</el-button>



    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#2C3E50', color: '#FFFFFF' }"
              border
              highlight-current-row
              @current-change="handleSelectRow"
    >
      <el-table-column prop="id" label="ID" width="190">
      </el-table-column>
      <el-table-column prop="name" label="物品名" width="240">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="200">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" width="200" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodstype" label="分类" width="200" :formatter="formatGoodsType">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="200">
      </el-table-column>

      <el-table-column prop="oprate" label="操作" width="330" v-if="user.roleId!=2">
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
        <el-form-item label="物品名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <el-input v-model="form.count"></el-input>
        </el-form-item>

        <el-form-item label="仓库" prop="storage">
          <el-select v-model="form.storage" placeholder="选择仓库">
            <el-option
                v-for="item in storageData"
                :key="item.id"
                :label="item.name"
                :value="item.id">

            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="分类" prop="goodstype">

          <el-select v-model="form.goodstype" placeholder="选择物品类型">
            <el-option
                v-for="item in goodsTypeData"
                :key="item.id"
                :label="item.name"
                :value="item.id">

            </el-option>
          </el-select>
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

    <el-dialog
        title="请输入"
        :visible.sync="plusDiaVisible"
        width="20%"
        center
        @close="resetPlusForm">

      <el-dialog
        width="50%"
        title="申请人选择"
        :visible.sync="innerVisible"
        append-to-body>

        <selectUser @doSelectUser="doSelectUser"> </selectUser>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>

      </el-dialog>


      <el-form ref="plusGoodForm" :model="plusGoodForm" :rules="plusGoodRules" label-width="80px">
        <el-form-item label="物品名" prop="goodsname">
          <el-input v-model="plusGoodForm.goodsname" readonly></el-input>
        </el-form-item>

        <el-form-item label="申请人" prop="username">
          <el-input v-model="plusGoodForm.username" readonly @click.native="selectUser"></el-input>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <el-input v-model="plusGoodForm.count"></el-input>
        </el-form-item>



        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="plusGoodForm.remark"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="plusDiaVisible = false">取 消</el-button>
    <el-button type="primary" @click="doPlus">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
import SelectUser from "@/components/user/selectUser.vue";

export default {
  name: "goodsManager",
  components: {SelectUser},
  data() {
    let checkCount= (rule, value, callback) => {
      if (value < 0) {
        callback(new Error('数量不能小于0'));
      } else if (value > 9999) {
        callback(new Error('数量不能大于9999'));
      } else {
        callback();
      }
    };
    return {
      user:JSON.parse(sessionStorage.getItem("CurUser")),
      currenRow:{},
      storage: '',
      goodstype: '',
      goodsTypeData:[],
      storageData:[],
      tableData: [],
      pageSize:10,
      pageNum: 1,
      total: 0,
      name: '',

      centerDialogVisible: false,
      plusDiaVisible: false, // 新增物品入库的对话框
      innerVisible: false, // 选择申请人的对话框
      tempUser:{},
      form:{
        id: '', // 用于编辑时传递用户ID
        name: '',
        storage:'',
        goodstype:'',
        count:'',
        remark:'',

      },
      plusGoodForm:{
        goods:'',
        goodsname:'',
        count:'',
        username:'',
        userid:'1',
        adminId:'',
        remark:'',
        action:true,

      },

      plusGoodRules:{

      },

      rules:{
        name: [
          { required: true, message: '请输入物品名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        goodstype: [
          { required: true, message: '请选择物品类型', trigger: 'change' }
        ],
        storage: [
          { required: true, message: '请选择仓库', trigger: 'change' }
        ],
        count:[
          { required: true, message: '请输入数量', trigger: 'blur' },
          { pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须是正整数', trigger: 'blur' },
          {validator:checkCount, trigger: 'blur'}
        ]

      },

    }
  },
  methods:{
    formatStorage(row){
      let temp = this.storageData.find(item=>{
        return item.id == row.storage;
      })

      return temp && temp.name;
    },
    formatGoodsType(row){
      let temp = this.goodsTypeData.find(item=>{
        return item.id == row.goodstype; // <-- 修改为正确的字段名
      })
      return temp && temp.name;
    },
    modify(row) {
      this.centerDialogVisible = true; // 打开对话框
      this.$nextTick(() => {
        // 确保表单重置在对话框显示后执行
        this.form.id = row.id; // 假设后端返回的用户对象有 id 字段
        this.form.name = row.name;
        this.form.storage = row.storage;
        this.form.goodstype = row.goodstype;
        this.form.count = row.count;
        this.form.remark = row.remark;
      });

    },
    deleteData(id) {
      this.$axios.get(this.$httpUrl + '/Goods/delete?id=' + id).then(result => result.data).then(result => {
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
      this.$axios.post(this.$httpUrl + '/Goods/modify', this.form).then(result => {
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
      this.$axios.post(this.$httpUrl + '/Goods/save', this.form).then(result => {
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
    plusGood(){
      if(!this.currenRow.id){
        alert('请先选择一行数据');
        return
      }
      this.plusDiaVisible = true;
      this.$nextTick(()=>{
         this.resetPlusForm();
      })
      this.plusGoodForm.goodsname=this.currenRow.name;
      this.plusGoodForm.goods=this.currenRow.id;
      this.plusGoodForm.adminId=this.user.id;
      this.plusGoodForm.action=true;

    },
    outGood(){
      if(!this.currenRow.id){
        alert('请先选择一行数据');
        return
      }
      this.plusDiaVisible = true;
      this.$nextTick(()=>{
        this.resetPlusForm();
      })
      this.plusGoodForm.goodsname=this.currenRow.name;
      this.plusGoodForm.goods=this.currenRow.id;
      this.plusGoodForm.adminId=this.user.id;
      this.plusGoodForm.action=false;

    },

    doPlus() {
      // === 开始新增的校验逻辑 ===

      // 1. 判断是否为出库操作
      // this.plusGoodForm.action 在 outGood() 方法中被设置为了 false
      if (this.plusGoodForm.action === false) {

        // 2. 将输入的数量和当前库存都转为数字，以确保比较的准确性
        const requestCount = Number(this.plusGoodForm.count);
        const currentStock = Number(this.currenRow.count);

        // 3. 核心判断：如果要出库的数量 > 当前库存
        if (requestCount > currentStock) {
          this.$message.error('出库失败，数量不能大于当前库存！');
          return; // 阻止程序继续往下执行，不发送请求
        }
      }

      // === 校验逻辑结束 ===


      // 如果校验通过，才执行原来的后端请求
      this.$axios.post(this.$httpUrl + '/record/save', this.plusGoodForm).then(result => {
        // ... 原来的代码 ...
        if (result.data.code == 200) {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.plusDiaVisible = false;
          this.loadPost();
          this.resetPlusForm();
        } else {
          // 接收并显示后端返回的错误信息，比如“库存不足”
          this.$message({
            message: result.data.msg || '操作失败',
            type: 'error'
          });
        }
      });
    },

    resetPlusForm(){
      this.$refs.plusGoodForm.resetFields();
    },
    addNewMember(){
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        // 确保表单重置在对话框显示后执行
        this.resetForm();
        this.form.id = ''; // 清空ID，表示新增
      });
    },

    selectUser(){
      this.innerVisible = true;
    },
    confirmUser(){
      this.plusGoodForm.username = this.tempUser.name;
      this.plusGoodForm.userid=this.tempUser.id;
      this.innerVisible= false;
    },
    doSelectUser(val){
      this.tempUser=val;
    },
    resetSearch(){
      this.name='';
      this.storage = '';
      this.goodstype = '';
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
    handleSelectRow(val){
      this.currenRow=val;
    },

    loadPost() {
      console.log('loadPost called'),

          this.$axios.post(this.$httpUrl + '/Goods/listPage', {
            pageSize : this.pageSize,
            pageNum : this.pageNum,
            param:{
              name:this.name,
              goodstype: this.goodstype+'',
              storage: this.storage+'',
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
    },
    loadStorage(){
      this.$axios.get(this.$httpUrl + '/storage/list').then(result => result.data).then(result => {
        console.log('loadPost called', result)
        if(result.code == 200){
          this.storageData = result.data
        }else{
          alert('获取数据失败')
        }
      })
    },
    loadGoodsType(){
      this.$axios.get(this.$httpUrl + '/goodsType/list').then(result => result.data).then(result => {
        console.log('loadPost called', result)
        if(result.code == 200){
          this.goodsTypeData = result.data
        }else{
          alert('获取数据失败')
        }
      })
    }

  },


  beforeMount() {
    this.loadStorage();
    this.loadGoodsType();
    this.loadPost();
  }
}
</script>

<style scoped>

::v-deep .el-table__row.current-row > td.el-table__cell {
  background-color: #c9e5ff !important;
}

</style>