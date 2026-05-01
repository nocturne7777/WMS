<template>
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="height: 100vh"
      default-active="/Home"
      router
  >
    <el-menu-item index="/HomePage">
      <i class="el-icon-s-home"></i>
      <span>首页</span>
    </el-menu-item>


    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>


  </el-menu>
</template>

<script>
export default {
  name: "ASide",
  data() {
    return {
      // menu:[
      //   {
      //     menuClick:'adminManager',
      //     menuName:'管理员管理',
      //     menuIcon:'el-icon-s-custom',
      //   },
      //   {
      //     menuClick:'userManager',
      //     menuName:'用户管理',
      //     menuIcon:'el-icon-user-solid',
      //   },
      // ]

    }
  },
  computed:{
    "menu":{
      get(){
        return this.$store.state.menu;
      }
    }
  },
}
</script>

<style scoped>

</style>