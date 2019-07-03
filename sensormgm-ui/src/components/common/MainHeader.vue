<template>
  <header class="main-header">
    <div class="main-header-flex">

      <!--header logo start-->
      <div class="jy-header-logo">
        <i @click.stop="onCollapse" class="expand iconfont icon-shrink"></i>
        <!--<i class="logo iconfont icon-logo"></i>-->
        <span class="name">油烟监控平台</span>
      </div>
      <!--header logo end-->

      <!--user info flag start-->
      <div class="header-user-flag">
        <div class="user-info">
          <img class="face" src="./../../assets/img/head.png" title="face" alt="face" />
          <span class="name">{{userInfo.cname}}</span>
          <span class="arrow-down el-icon-arrow-down"></span>
        </div>
        <div class="user-info-tabs">
          <span class="icon--user-abs el-icon-caret-top"></span>
          <ul>
            <li @click="handleUpdatePwd"><span class="icon iconfont icon-system-management" ></span>修改密码</li>
            <li @click="layout"><span class="icon iconfont icon-exit"></span>退出</li>
          </ul>
        </div>
      </div>
      <!--user info flag end-->
    </div>
    <update-pwd :dialog.sync="updatePwdDialog"></update-pwd>
  </header>
</template>

<script>

import Cookies from 'js-cookie'
import { LOGIN_URL } from '@configs'
import { loginOut } from '@api/login'

const UpdatePwd = () => import('@components/common/UpdatePwd')
export default {
  name: 'mainHeader',
  components: {UpdatePwd},
  data () {
    return {
      menus: [],
      loading: false,
      userInfo: {},
      updatePwdDialog: false
    }
  },
  created () {
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
  },
  methods: {
    // 左侧菜单收缩展开处理
    onCollapse () {
      this.$root.eventHub.$emit('asideIsCollapse')
    },
    // 修改密码
    handleUpdatePwd () {
      this.updatePwdDialog = true
    },
    // 退出
    layout () {
      localStorage.removeItem('userInfo')
      Cookies.remove('JSESSIONID') // 去除cookie
      loginOut({})
        .then(res => {
          location.href = LOGIN_URL
        })
    }
  }
}
</script>

<style lang="less" scoped>
.main-header {
  background: url(../../assets/img/header.png) no-repeat 100% 100%;
  background-size: cover;
  -moz-background-size: cover;
  -webkit-background-size: cover;
  width: calc(100vw);
  height: 100%;
  border-bottom: 1px solid #d2d2d2;
  .main-header-flex {
    display: flex;
    align-items: center;
    height: 100%;
    justify-content: space-between;
    .jy-header-logo {
      margin: 0px 80px 0px 0px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #fff;
      .expand {
        font-size: 18px;
        margin-left: 12px;
        color: #afd4e1;
        cursor: pointer;
      }
      .logo {
        font-size: 32px;
        color: #fff;
        margin: 0px 0px 0px 10px;
      }
      .name {
        margin: 0px 0px 0px 10px;
        padding: 0px 10px;
        font-size: 18px;
        border-left: 1px solid rgba(255, 255, 255, 0.3);
      }
    }
  }
  .header-user-flag {
    padding-right: 25px;
    position: relative;
    height: 100%;
    display: flex;
    align-items: center;
    cursor: pointer;
    .user-info {
      display: flex;
      justify-content: center;
      align-items: center;
      .face {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        margin-right: 6px;
      }
      .name {
        font-size: 14px;
        color: #fff;
        cursor: pointer;
      }
      .arrow-down {
        color: #fff;
        margin-left: 12px;
        font-weight: bold;
      }
    }
    &:hover {
      .user-info-tabs {
        display: block;
      }
    }
  }
}
.user-info-tabs {
  width: 150px;
  padding-top: 12px;
  background-color: #fff;
  display: none;
  position: absolute;
  top: 49px;
  right: 12px;
  z-index: 10;
  box-shadow: 2px 2px 6px #999;
  .icon--user-abs {
    position: absolute;
    top: -11px;
    right: 12px;
    color: #fff;
    font-size: 16px;
  }
  ul {
    li {
      display: flex;
      align-items: center;
      line-height: 40px;
      font-size: 13px;
      color: #183247;
      padding: 0 12px;
      cursor: pointer;
      .icon {
        margin-right: 8px;
      }
      &:hover {
        background: #f4f4f4;
      }
    }
  }
}
</style>
