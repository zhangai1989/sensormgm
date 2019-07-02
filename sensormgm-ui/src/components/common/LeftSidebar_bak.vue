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
                        :index="i+''">
            <template>
              <i class="menu-icon iconfont"
                 :class="menu.menuIconClass"></i>
              <span>{{menu.menuName}}</span>
            </template>
          </el-menu-item>

          <el-submenu v-else
                      popper-class="jy-left-popper"
                      :key="i"
                      :index="i+''">
            <template>
              <template slot="title">
                <i class="menu-icon iconfont"
                   :class="menu.menuIconClass"></i>
                <span>{{menu.menuName}}</span>
              </template>
              <template v-for="(child,k) in menu.childrens">
                <el-menu-item :key="k"
                              :index="`${i}${k}`+''"
                              @click="openTab(child)">
                  <span class="menu-item-name"
                        slot="title">{{child.menuName}}</span>
                </el-menu-item>
              </template>
            </template>
          </el-submenu>
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
        this.menus = JSON.parse(sessionStorage.getItem('menus'))
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
  .el-submenu {
    .el-submenu__title {
      text-align: left;
      height: 40px;
      line-height: 40px;
      background-color: transparent !important;
      .menu-icon {
        margin-right: 3px;
      }
      .el-submenu__icon-arrow::before {
        color: #142e44;
        font-weight: bold;
      }
    }
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
  .el-menu-item.jy-menu-item {
    height: 36px;
    line-height: 36px;
    font-size: 13px;
    border-left: 3px solid #fff;
    padding-left: 17px !important;
    .menu-icon {
      margin-right: 3px;
    }
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
