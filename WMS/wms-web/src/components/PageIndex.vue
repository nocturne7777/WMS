<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246);height:100%; margin-left: -1px">
        <ASide></ASide>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px">
        <WMSHeader></WMSHeader>
      </el-header>

      <el-main style="height: 100%">
        <!-- Main content goes here -->
<!--        <WMSMain></WMSMain>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import ASide from "@/components/ASide.vue";
import WMSHeader from "@/components/WMSHeader.vue";
// import WMSMain from "@/components/WMSMain.vue";

export default {
  name: "PageIndex",
  components: { WMSHeader, ASide}
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-main {
  padding: 5px;
}
.el-aside {
  color: #333;
}
</style>