<template>
  <section>
    <el-scrollbar class="jy-left-aside">
      <el-menu class="jy-left-menus"
               background-color="#F8F9FB"
               :collapse="isCollapse"
               unique-opened>
        <template v-for="(menu,i) in menus">
          <el-menu-item class="jy-menu-item"
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
        isCollapse: false,
        // defaultActive: '00',
        setShowCollapse: true,
        menus: []
      }
    },
    created () {
      this.getMenus()
      this.collapseMenus()
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
      // collapse menus
      collapseMenus () {
        this.$root.eventHub.$on('asideIsCollapse', () => {
          this.isCollapse = !this.isCollapse
          this.setShowCollapse = !this.setShowCollapse
        })
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
    background-color: #f8f9fb;
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
      border-left: 5px solid #fff;
      border-bottom: 1px solid #fff;
      padding-left: 30px !important;
      .menu-icon {
        margin-right: 3px;
      }
      &:hover {
        background-color: #e7f2ee !important;
        .menu-item-name {
          color: #29b87a;
        }
      }
      &.is-active {
        background-color: #e7f2ee !important;
        border-left: 5px solid #29b87a;
        .menu-item-name {
          color: #29b87a;
        }
      }
    }
  }
  .jy-left-popper {
    .el-menu {
      .el-menu-item {
        height: 36px;
        line-height: 36px;
        font-size: 13px;
        border-left: 3px solid #fff;
        &:hover {
          background-color: transparent !important;
          .menu-item-name {
            color: #29b87a;
          }
        }
        &.is-active {
          background-color: #e7f2ee !important;
          border-left: 3px solid #29b87a;
          .menu-item-name {
            color: #29b87a;
          }
        }
      }
    }
  }
</style>
