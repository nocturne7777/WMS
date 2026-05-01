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


    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#2C3E50', color: '#FFFFFF' }"
              border
    >
      <el-table-column prop="id" label="ID" width="190">
      </el-table-column>
      <el-table-column prop="goodsname" label="物品名" width="240">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="200">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库" width="200">
      </el-table-column>
      <el-table-column prop="goodstypename" label="分类" width="200">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="200">

      </el-table-column>
      <el-table-column prop="username" label="申请人" width="200">

      </el-table-column>
      <el-table-column prop="createtime" label="时间" width="200">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="200">
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
  </div>
</template>

<script>
export default {
  name: "recordManage",
  data() {
    return {
      user:JSON.parse(sessionStorage.getItem("CurUser")),
      storage: '',
      goodstype: '',
      goodsTypeData:[],
      storageData:[],
      tableData: [],
      pageSize:10,
      pageNum: 1,
      total: 0,
      name: '',
      userId: '',
      roleId: '',

      centerDialogVisible: false,
      form:{
        id: '', // 用于编辑时传递用户ID
        name: '',
        storage:'',
        goodstype:'',
        count:'',
        remark:'',

      },

    }
  },
  methods:{
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
    },
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

    loadPost() {
      console.log('loadPost called'),

          this.$axios.post(this.$httpUrl + '/record/listPage', {
            pageSize : this.pageSize,
            pageNum : this.pageNum,
            param:{
              name:this.name,
              goodstype: this.goodstype+'',
              storage: this.storage+'',
              roleId:this.user.roleId+'',
              userId:this.user.id+'',
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


  },


  beforeMount() {
    this.loadStorage();
    this.loadGoodsType();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>