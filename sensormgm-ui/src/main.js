// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
// 导入element-ui组件
import ElementUI from 'element-ui'
// 权限控制
import './router/permission'
import 'element-ui/lib/theme-chalk/index.css'
// 混入(公共方法)
import Mixin from './mixin.js'
// Axios
import axios from 'axios'
// 配置文件
import {LOGIN_URL} from '@configs'

Vue.config.productionTip = false // 设置为 false 以阻止 vue 在启动时生成生产提示。

Vue.use(ElementUI)

// 引入公共样式
require('./assets/styles/base.less')
require('./assets/styles/index.less')

// 请求状态判断
axios.interceptors.request.use(function (config) {
  if (config.method.toUpperCase() === 'GET' && config.url !== undefined) {
    if (config.params === undefined || config.params === 'undefined') {
      config.params = {}
    }
    config.params.__preventCache = new Date().getTime()
  }
  config.withCredentials = true
  return config
},
function (error) {
  return Promise.reject(error)
})
// 返回状态判断
axios.interceptors.response.use((response) => {
  return response
}, (error) => {
  console.log(error)
  // let loading = ElementUI.Loading.service({});
  if (error.response.status * 1 === 401) {
    if (process.env.NODE_ENV === 'local') {
      window.location.href = '/#/login'
    } else {
      window.location.href = LOGIN_URL + '&redirect=' + encodeURIComponent(document.URL)
    }
  } else if (error.response.status * 1 === 400) {
    if (error.response.data.message === 'mobile is exists') {
      ElementUI.Message.error('手机号已存在')
    } else if (error.response.data.message === 'email is exists') {
      ElementUI.Message.error('邮箱已存在')
    } else {
      ElementUI.Message.error(error.response.data.message)
    }
  } else if (error.response.status * 1 === 403) {
    ElementUI.Message.error(error.response.data.message)
    // window.location.href = '/' //跳转到没权限页面
  } else {
    ElementUI.Message.error(error.response.data.message)
  }
  // loading.close();
  // return Promise.reject(error);
})

const render = new Vue({
  el: '#app',
  mixins: [Mixin],
  router,
  store,
  components: {
    App
  },
  data: {
    bus: new Vue(),
    eventHub: new Vue()
  },
  template: '<App/>'
})

Vue.use({
  render
})
