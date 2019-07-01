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
// echarts图表
import ECharts from 'vue-echarts/components/ECharts'
import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/title'
import 'echarts/lib/component/legend'

Vue.config.productionTip = false // 设置为 false 以阻止 vue 在启动时生成生产提示。

Vue.use(ElementUI)
Vue.component('chart', ECharts)

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
  if (response.data.code === 3000) {
    window.location.href = LOGIN_URL
  } else {
    return response
  }
}, (error) => {
  console.log(error)
  if (error.response.code === 3000) {
    window.location.href = LOGIN_URL
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
