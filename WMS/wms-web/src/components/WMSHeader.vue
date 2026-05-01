<template>
  <div class="header-container">

    <div class="left-icon">
      <i class="el-icon-s-fold"></i>
    </div>

    <div class="center-title">
      <span>Welcome to the System</span>
    </div>

    <span>{{user.name}}</span><el-dropdown>
      <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="toExit">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "WMSHeader",
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  methods: {
    created(){
      this.$router.push("/HomePage");
    },
    toUser() {
      console.log('toUser method called. Current path is:', this.$route.path);
      // 在跳转前，检查当前路径是否已经是目标路径
      if (this.$route.path !== '/HomePage') {
        this.$router.push("/HomePage");
      } else {
        console.log('Already on HomePage, navigation aborted.');
    }},
    toExit(){
      this.$confirm('确认退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sessionStorage.removeItem('CurUser');
        this.$router.replace("/");
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消退出'
        });
      });
    },

  }
}
</script>

<style scoped>
.header-container {
  display: flex;         /* 关键：设置为 Flex 布局，使其子元素横向排列 */
  align-items: center;   /* 关键：让所有子元素在垂直方向上居中 */
  height: 60px;          /* 设置一个高度 */
  padding: 0 20px;       /* 设置左右内边距，避免元素贴边 */

  /* 这就是您想要的“蓝色框框”的背景色，可以换成您自己的颜色 */
  background-color: #A0C4E2;
}

.center-title {
  flex: 1;               /* 关键：让这个元素占据所有可用的剩余空间 */
  text-align: center;    /* 文字居中 */
  font-size: 18px;       /* 调整一下字体大小 */
  color: #fff;           /* 根据背景色调整字体颜色 */
}

.left-icon {
  font-size: 24px;       /* 调整图标大小 */
}

/* 让下拉菜单的文字颜色更清晰 */
.el-dropdown {
  color: #333;
  cursor: pointer;
}
</style>