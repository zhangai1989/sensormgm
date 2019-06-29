import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: () => import(/* webpackChunkName: "login" */ './views/Login.vue')
    },
    {
      path: '/index',
      component: () => import(/* webpackChunkName: "login" */ './views/Index.vue')
    },
    {
      path: '/home',
      component: () => import(/* webpackChunkName: "login" */ './components/Layout.vue'),
      children: [
        {
          path: '/',
          component: resolve => require(['./views/Monitor.vue'], resolve)
        },
        {
          path: '/companyManage',
          component: resolve => require(['./views/CompanyManage.vue'], resolve)
        },
        {
          path: '/userManage',
          component: resolve => require(['./views/UserManage.vue'], resolve)
        },
      ]
    }
  ]
})
