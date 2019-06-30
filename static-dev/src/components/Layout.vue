<template>
    <div>

        <!--油烟检测系统-->
        <div class="layout-header">
            <div class="layout-logo">
                <div style="width: 256px;display: flex;align-items: center;justify-content: center;color: #fff;">油烟检测系统</div></div>
            <div class="layout-top">

                <el-popover
                        placement="bottom"
                        width="100"
                        trigger="click">
                    <div style="text-align: center">
                        <el-button type="text" @click="clickLogout">退出登录</el-button>
                    </div>
                    <img class="user-img" slot="reference" src="../assets/img/default_user.jpg"/>
                </el-popover>
                <div style="font-size: 12px;color: #888;font-weight: bold;margin-right: 24px;">{{username}}</div>
            </div>
        </div>


        <div class="layout-container">
            <div style="width: 256px;border-top: 1px solid #f1f1f1;">
                <div class="layout-aside">
                    <el-menu :router="true"
                             :default-active="defaultRouter"
                             :unique-opened="true"
                             background-color="#fff"
                             text-color="#666"
                             style="border:none;padding-top: 9px"
                             active-text-color="#1890FF">
                        <template v-for="(item, i) in menu" v-if="item.show">
                            <el-submenu :index=item.index>
                                <template slot="title">
                                    <i :class="item.icon"></i>
                                    <span>{{item.title}}</span>
                                </template>
                                <el-menu-item v-for="(sub,j) in item.subMenu" :key="j" v-if="sub.show"
                                              :index="sub.index">
                                    <span style="margin-left: 10px">{{sub.title}}</span>
                                </el-menu-item>
                            </el-submenu>
                        </template>
                    </el-menu>
                </div>
            </div>

            <div class="layout-main">
                <router-view></router-view>
            </div>
        </div>

    </div>
</template>

<style>
    .el-submenu__title:hover, .el-submenu .el-menu-item:hover {
        background-color: #fff !important;
        color: #1890ff !important;
    }

    .el-menu-item.is-active {
        background-color: #e6f7ff !important;
        border-right: 4px solid #1890ff;
    }

    .el-submenu .el-menu-item{
        height: 46px;
        line-height: 46px;
    }
</style>

<style scoped>
    .layout-header {
        display: flex;
        width: 100%;
        height: 65px;
        /*border-bottom: 1px solid #e1e1e1;*/
        background: linear-gradient(to right,#36C2CF,#B8E986);

        /*#439107  #67C23A*/
    }

    .layout-logo {
        height: 100%;
        width: 256px;
        /*border-right: 1px solid #e1e1e1;*/
        /*background-color: #aaa;*/
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .logo-content {
        width: 256px;
        height: 65px;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .layout-top {
        box-sizing: border-box;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        width: 100%;
        height: 65px;
    }

    .user-img {
        cursor: pointer;
        width: 24px;
        height: 24px;
        border-radius: 50%;
        background-color: #ddd;
        margin-right: 10px;
    }

    .layout-container {
        display: flex;
        width: 100%;
        min-height: calc(100vh - 65px);
    }

    .layout-aside {
        width: 256px;
        height: calc(100vh - 65px);
        background-color: #fff;
        /*box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);*/
    }

    .layout-main {
        box-sizing: border-box;
        /*padding-left: 215px;*/
        width: 100%;
        padding: 24px;
        min-height: calc(100vh - 65px);
        background-color: #f1f1f1;
    }
</style>

<script>
  import {logout} from '../http/modules/index'

  export default {
    data() {
      return {

        defaultRouter: '/home',
        username: 'xysh',

        menu: [
          {
            icon: 'el-icon-document',
            index: '/',
            title: '监控管理',
            show: true,
            subMenu: [
              {
                index: '/home',
                title: '实时监控',
                show: true,
              },
              {
                index: '/history',
                title: '历史记录',
                show: true,
              }
            ]
          },
          {
            icon: 'el-icon-document',
            index: '/companyManage',
            title: '系统管理',
            show: true,
            subMenu: [
              {
                index: '/companyManage',
                title: '企业管理',
                show: true,
              },
              {
                index: '/userManage',
                title: '用户管理',
                show: true,
              }
            ]
          }
        ]

      }
    },
    created() {
      let that = this
      that.username = localStorage.getItem('username')
      let level = localStorage.getItem('user.level')
      that.menu = [
        {
          icon: 'el-icon-document',
          index: '/',
          title: '监控管理',
          show: true,
          subMenu: [
            {
              index: '/home',
              title: '实时监控',
              show: true,
            },
            {
              index: '/history',
              title: '历史记录',
              show: true,
            }
          ]
        },
        {
          icon: 'el-icon-document',
          index: '/companyManage',
          title: '系统管理',
          show: level < 4,
          subMenu: [
            {
              index: '/companyManage',
              title: '企业管理',
              show: level < 4,
            },
            {
              index: '/userManage',
              title: '用户管理',
              show: level < 4,
            }
          ]
        }
      ]

      let full_path = this.$route.fullPath
      let path_arr = full_path.split('/')
      let _path = path_arr[path_arr.length - 1]
      let path = _path.split('?')[0]
      that.defaultRouter = `/${path}`
    },


    methods: {


      clickLogout() {
        let that = this
        logout({})
          .then(res => {
            if (res.code === 2000) {
              that.$message.success('您已退出登录！')
              that.$router.push({path: '/login'})
            } else {
              that.$message.error('退出登录失败！')
            }
          })
      }

    }
  }

</script>