<template>
  <section>
    <el-scrollbar class="jy-left-aside">
      <el-menu class="jy-left-menus"
               background-color="#F8F9FB"
               unique-opened>
        <template v-for="(menu,i) in menus">
          <!--:class="{'jy-menu-item': true, 'menu-selected': currentRoute === menu.menuUrl, 'menu-not-selected': currentRoute !== menu.menuUrl}"-->
          <el-menu-item :class="{'jy-menu-item': true, 'menu-selected': $route.path === menu.menuUrl, 'menu-not-selected': $route.path !== menu.menuUrl}"
                        v-if="!menu.childrens || (menu.childrens instanceof Array && menu.childrens.length < 1) "
                        :key="i"
                        @click="openTab(menu)"
                        :index="i+''">
            <template>
              <i class="menu-icon iconfont"
                 :class="menu.menuIconClass"></i>
              <span>{{menu.menuName}}</span>
            </template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-scrollbar>
  </section>
</template>

<script>
  export default {
    name: 'leftSidebar',
    components: {},
    data () {
      return {
        menus: []
      }
    },
    created () {
      let self = this
      self.getMenus()
    },
    methods: {
      // open  menu
      openTab (menu) {
        if (menu.menuUrl) {
          sessionStorage.removeItem('ACCEPTordersListParams')
          sessionStorage.removeItem('ALLordersListParams')
          sessionStorage.removeItem('HANDLEordersListParams')
          this.$router.push(menu.menuUrl)
        }
      },
      // get userInfo
      getMenus () {
        if (sessionStorage.getItem('menus') && sessionStorage.getItem('menus') !== 'undefined') {
          let self = this
          let allMenu = JSON.parse(sessionStorage.getItem('menus'))
          // 不显示父级菜单
          allMenu.forEach(function (parent) {
            if (parent.childrens && parent.childrens.length > 0) {
              parent.childrens.forEach(function (child) {
                self.menus.push(child)
              })
            }
          })
          debugger
          console.info(self.menus)
        }
      }
    },
    computed: {
    }
  }
</script>
<style lang="less" scope>
  .jy-left-aside {
    height: calc(100vh - 50px);
    background-color: #e7f2ee;
    border-right: 1px solid #e9ebf0;
  }
  .jy-left-menus:not(.el-menu--collapse) {
    width: 180px;
    min-height: 400px;
  }
</style>

<style lang="less">
  .jy-left-menus {
    border-right: 0 !important;
    .el-menu-item.jy-menu-item {
      height: 50px;
      line-height: 50px;
      font-size: 16px;
      color: #6e6e6e;
      border-bottom: 1px solid #f9f9f9;
      padding-left: 30px !important;
      .menu-icon {
        margin-right: 3px;
      }
      &:hover {
        background-color: #f2fdf9 !important;
        .menu-item-name {
          color: #29b87a;
        }
      }
    }
  }

  .menu-selected {
    background-color: #f2fdf9 !important;
    border-left: 5px solid #29b87a;
    .menu-item-name {
      color: #29b87a;
    }
  }

  .menu-not-selected {
    background-color: #e7f2ee !important;
    border-left: 5px solid #e7f2ee;
    .menu-item-name {
      color: #29b87a;
    }
  }
</style>
