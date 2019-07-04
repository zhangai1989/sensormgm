import Vue from 'vue'
import Router from 'vue-router'
const Layout = resolve => require(['@views/layout'], resolve)

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    redirect: '/login'
  }, {
    path: '/login',
    name: 'login',
    component: () => import('@views/login')
  }, {
    path: '/index',
    name: 'index',
    component: () => import('@views/index')
  }, {
    path: '/monitor',
    component: Layout,
    children: [{
      path: '/monitor/real',
      name: 'real',
      component: () => import('@views/Monitor'),
      meta: {
        title: '实时监控'
      }
    }, {
      path: '/monitor/history',
      name: 'history',
      component: () => import('@views/History'),
      meta: {
        title: '历史记录'
      }
    }, {
      path: '/monitor/analysis',
      name: 'analysis',
      component: () => import('@views/Analysis'),
      meta: {
        title: '数据分析'
      }
    }, {
      path: '/monitor/warning',
      name: 'warning',
      component: () => import('@views/Warning'),
      meta: {
        title: '预警查询'
      }
    }, {
      path: '/monitor/outStandard',
      name: 'outStandard',
      component: () => import('@views/OutStandard'),
      meta: {
        title: '超标查询'
      }
    }, {
      path: '/monitor/realMap',
      name: 'realMap',
      component: () => import('@views/RealMap'),
      meta: {
        title: '实时地图'
      }
    }, {
    path: '/monitor/ranking',
      name: 'ranking',
      component: () => import('@views/ranking'),
      meta: {
      title: '污染排名'
    }
  }]
  },
  {
    path: '/system',
    component: Layout,
    children: [{
      path: '/system/enterprise',
      name: 'enterprise',
      component: () => import('@views/EnterpriseManage'),
      meta: {
        title: '企业管理'
      }
    }, {
      path: '/system/user',
      name: 'user',
      component: () => import('@views/UserManage'),
      meta: {
        title: '用户管理'
      }
    }, {
      path: '/system/area',
      name: 'user',
      component: () => import('@views/Area'),
      meta: {
        title: '区域管理'
      }
    }]
  },
  {
    path: '/error',
    component: Layout,
    name: 'error',
    children: [{
      path: '401',
      name: 'page401',
      component: () => import('@views/error/401'),
      meta: {
        title: 'page401'
      }
    },
    {
      path: '404',
      name: 'page404',
      component: () => import('@views/error/404'),
      meta: {
        title: 'page404'
      }
    }
    ]
  },

  {
    path: '*',
    redirect: {
      name: 'page404'
    }
  }
  ]
})
