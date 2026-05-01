<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user:JSON.parse(sessionStorage.getItem("CurUser")),
    }
  },
  // watch: { ... },  // <--- 删除整个 watch 部分
  name: 'App',
  components: {
  }
}
</script>


<style>
#app {
  height: 100%;

}
</style>
