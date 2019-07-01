import router from './'
// import store from '../store'
import {
  Loading
} from 'element-ui'
import {
  menus
} from '@api/Base'

// 免登陆可进入的页面(白名单)
const whiteList = ['/login', '/401', '/404']

const loadingFun = (text = '数据加载中...') =>
  Loading.service({
    lock: true,
    text: text,
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.5)'
  })

let isReload = true

// 路由前置
router.beforeEach((to, from, next) => {
  if (whiteList.includes(to.fullPath)) {
    next()
  } else {
    if (isReload) {
      let loading = loadingFun()
      menus().then(res => { // 这个接口根据项目需要做修改
        isReload = false
        if (res.items) {
          this.menus = res.items || []
          sessionStorage.setItem('menus', JSON.stringify(this.menus))
        }
        loading.close()
        next()
      }).catch((error) => {
        console.log(error.response)
      })
    } else {
      if (sessionStorage.getItem('menus')) {
        let currentUrl = to.path
        let firstUrl = ''
        let flag = false
        let menus = JSON.parse(sessionStorage.getItem('menus'))
        menus.map((item, itemIndex) => {
          if (item.childrens) {
            let children = item.childrens || []
            children.map((child, childIndex) => {
              if (currentUrl === child.menuUrl) {
                flag = true
              }
              if (itemIndex === 0 && childIndex === 0) {
                firstUrl = child.menuUrl
              }
            })
          }
        })
        if (to.meta.hidden) {
          next()
        } else {
          if (!flag) {
            next({
              path: firstUrl
            })
          } else {
            next()
          }
        }
      }
    }
  }
})
// router.afterEach(() => {
//   console.log('路由配置完成')
// })
