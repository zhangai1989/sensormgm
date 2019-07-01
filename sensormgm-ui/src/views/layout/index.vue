<template>
  <el-container class="layout-wrapper">
    <el-header height="50px">
      <main-header></main-header>
    </el-header>
    <el-container>
      <el-aside width="auto">
        <left-sidebar></left-sidebar>
      </el-aside>
      <el-main>
        <main-tabs></main-tabs>
        <main-content></main-content>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { LeftSidebar, MainHeader, MainTabs, MainContent } from '@components/common'

export default {
  name: 'layout',
  components: {
    LeftSidebar,
    MainHeader,
    MainTabs,
    MainContent
  },
  data () {
    return {
      tabs: []
    }
  },
  created () { },
  methods: {},
  computed: {
    currentTab () {
      return this.$store.getters.currentTab
    }
  }
}
</script>

<style lang="less" scoped>
.layout-wrapper {
  .tabs-wrapper {
    width: 100%;
    border-bottom: 1px solid #ddd;
    overflow: hidden;
    .tabs {
      margin: 10px 10px -1px 10px;
      float: left;
      display: flex;
      flex-direction: row;
      border: 1px solid #ddd;
      border-radius: 4px 4px 0 0;
      .tab {
        border-bottom: 1px solid transparent;
        border-left: 1px solid #e4e7ed;
        padding: 10px 20px;
        cursor: pointer;
        color: #666;
        &.selected {
          color: #29b87a;
        }
      }
      :first-child {
        border-left: none;
      }
    }
  }
  .el-header {
    padding: 0px;
    height: auto;
    width: 100%;
  }
  .el-main {
    padding: 0px;
  }
  .el-container {
    width: 100%;
    height: 100%;
  }
  .content-wrapper {
    background: #ecf0f5;
    //padding: 10px;
    width: 100%;
    height: 100%;
    overflow: hidden;
    .content-wrapper-router {
      padding: 10px;
      margin: 10px 0 0 10px;
      overflow: auto;
    }
  }
  .content-sidebar {
    width: 100%;
    height: 100%;
    background: #ecf0f5;
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    overflow: hidden;
    .content-sidebar-router {
      padding: 10px;
      margin-left: 10px;
      overflow: auto;
      flex: 1;
      float: left; //兼容低版本浏览器
      width: 70%; //兼容低版本浏览器
    }
  }
}
</style>
