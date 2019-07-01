<template>
  <section class="tab-content">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key"></router-view>
      </keep-alive>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'mainContent',
  components: {},
  data () {
    return {}
  },
  created () {
    console.log('---执行了---')
  },
  methods: {},
  computed: {
    cachedViews () {
      const tabs = this.$store.getters.menuTabs
      return tabs && tabs.map(item => {
        if (!(item.meta && item.meta.noCache)) {
          return item.name
        }
      })
    },
    key () {
      return this.$route.fullPath
    }
  }
}
</script>

<style lang="less" scope>
.tab-content {
  // padding: 15px 10px 10px 15px;
  height: calc(100vh - 50px - 36px - 0px);
  overflow: auto;
}
</style>
