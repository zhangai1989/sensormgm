<template>
  <section>
    <el-tabs type="card"
             closable
             v-model="selectTabName"
             @tab-click="selectTab"
             @tab-remove="removeTab"
             class="jy-header-tabs">
      <template v-for="tab in menuTabs">
        <el-tab-pane :name="tab.name"
                     :label="tab.meta ? tab.meta.title : ''"
                     :key="tab.name">
        </el-tab-pane>
      </template>
    </el-tabs>
  </section>
</template>

<script>
export default {
  name: 'mainTabs',
  components: {},
  data () {
    return {
      selectTabName: ''
    }
  },
  created () {
    this.addViewTags()
  },
  watch: {
    currentTab () {
      this.selectTabName = this.currentTab.name
    },
    $route () {
      this.addViewTags()
    }
  },
  methods: {
    addViewTags () {
      if (this.$route.name) {
        const tab = this.$route
        this.$store.dispatch('common/setCurrentTab', tab) // 设置当前tab
        this.$store.dispatch('common/addMenuTabs', tab)
      }
    },
    selectTab (obj) {
      this.menuTabs && this.menuTabs.forEach(tab => {
        if (tab.name === obj.name) {
          this.$router.push(tab.path)
          return false
        }
      })
    },
    removeTab (name) {
      this.menuTabs && this.menuTabs.forEach((tab, i) => {
        if (tab.name === name) {
          this.$store.dispatch('common/removeMenuTabs', tab)
          if (this.selectTabName === name) {
            this.selectTabName = this.menuTabs[i - 1].name
            this.$router.push(this.menuTabs[i - 1].path)
          }
          return false
        }
      })
    }
  },
  computed: {
    currentTab () {
      return this.$store.getters.currentTab
    },
    menuTabs () {
      return this.$store.getters.menuTabs
    }
  }
}
</script>

<style lang="less">
.jy-header-tabs {
  .el-tabs__header {
    background: #ededed;
    margin: 0;
    .el-tabs__nav-wrap {
      .el-tabs__nav-prev,
      .el-tabs__nav-next {
        line-height: 40px;
        font-size: 14px;
      }
      .el-tabs__nav-prev {
        left: 3px;
      }
      .el-tabs__nav-next {
        right: 3px;
      }
      .el-tabs__nav-scroll {
        .el-tabs__nav {
          border: none;
          .el-tabs__item {
            border-left: none;
            border-bottom: 1px solid #e0e0e1;
            color: #5c6168;
            font-size: 13px;
            outline: none;
            height: 36px;
            line-height: 36px;
            &.is-active {
              background: #f3f5f7;
              border-left: 1px solid #e0e0e1;
              border-right: 1px solid #e0e0e1;
              border-bottom: none;
              color: #343a43;
            }
            &:first-child {
              border-left: none;
              .el-icon-close {
                display: none !important;
              }
            }
          }
        }
      }
    }
  }
}
</style>
