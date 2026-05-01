
import 'element-ui/lib/theme-chalk/index.css';

import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from "axios";
import VueRouter from "vue-router";
// 从 router/index.js 中导入 router 实例和我们新导出的 addNewRoute 方法
import router, { addNewRoute } from './router';
import store from './store';

Vue.use(VueRouter);
Vue.prototype.$axios = axios;
import './assets/global.css'
Vue.prototype.$httpUrl = 'http://localhost:8090';

Vue.config.productionTip = false
Vue.use(VueRouter);

//Vue.use(ElementUI);
Vue.use(ElementUI,{size:'small'});

const menu = store.state.menu;
if (menu && menu.length > 0) {
  addNewRoute(menu);
}

new Vue({
  router, // 此时的 router 实例已经包含了动态路由
  store,
  render: h => h(App),
}).$mount('#app');
