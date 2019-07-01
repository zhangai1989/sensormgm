<template>
  <div class="normal-bar flex fxmiddle shrink0" :style="{background: bg||'#f7f8fa'}">
    <div class="title flex1">
      <slot name="title"></slot>
    </div>
    <slot name="btns"></slot>
  </div>
</template>

<script>
export default {
  name: 'mainHeader',
  props: ['bg'],
  data () {
    return {

    }
  },
  mounted () {

  },
  methods: {

  }
}
</script>

<style lang="less" scoped>
.normal-bar {
  height: 60px;
  padding: 0 20px;
  .title {
    font-size: 20px;
    font-weight: bold;
  }
  .el-button--small {
    font-size: 14px;
    padding: 9px 11px;
    color: #343a43;
  }
}
</style>
