<template> <div style="text-align: center; padding: 20px;">
  <h1 style="font-size: 30px; text-align: left">欢迎，{{user.name}}</h1>
  <el-descriptions :title="user.name" :column="2" size="40" border style="margin-top: 40px;">
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-custom"></i>
        账号
      </template>
      {{user.no}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        电话
      </template>
      {{user.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-location-outline"></i>
        性别
      </template>
      <el-tag :type="user.sex === '1' ? 'primary' : 'danger'" disable-transitions>
        <i :class="user.sex === '1' ? 'el-icon-male' : 'el-icon-female'"></i>
        {{user.sex === '1' ? '男' : '女'}}
      </el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        角色
      </template>
      <el-tag type="success" disable-transitions>
        {{user.roleId === 0 ? '超级管理员' : (user.roleId === 1 ? '管理员' : '用户')}}
      </el-tag>
    </el-descriptions-item>
  </el-descriptions>
  <DateUtils  style="margin-top: 20px;"></DateUtils >
</div>
</template>

<script>
import DateUtils from './DateUtils.vue';

export default { // 显示用法
  name: 'HomePage',
  components: {
    DateUtils
  },
  data() {
    return {
      user: {}
    };
  },
  mounted() {
    const userStr = sessionStorage.getItem('CurUser');
    if (userStr) {
      try {
        this.user = JSON.parse(userStr);
      } catch (error) {
        console.error('解析用户数据出错:', error);
      }
    }
  },
};
</script>

<style scoped>
/* 可根据需求自定义颜色等样式 */
h1 {
  color: #322d2d;
}
</style>

