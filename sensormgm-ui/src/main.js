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
// 地图插件
import AMap from 'vue-amap'
// v-charts
import VCharts from 'v-charts'

import moment from 'moment'
Vue.prototype.$moment = moment



Vue.use(AMap)

Vue.config.productionTip = false // 设置为 false 以阻止 vue 在启动时生成生产提示。

Vue.use(ElementUI)
Vue.use(VCharts)

AMap.initAMapApiLoader({
  // 高德key
  key: '55d8e1afb1f07c883a13d0b4520911ba',
  // 插件集合 （插件按需引入）
  plugin: ['AMap.Geolocation']
})

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
    return
  }
  if (response.data.code !== 2000) {
    ElementUI.Message.error(response.data.message)
  }
  return response
}, (error) => {
  ElementUI.Message.error(error.response.data.error)
  Promise.reject(error)
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
