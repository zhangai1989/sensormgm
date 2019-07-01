<template>
  <div class="form-line fxmiddle flex width100">
    <span></span>
    <label class="fsize16">{{title}}</label>
    <p v-if="line" class="flex1"></p>
  </div>
</template>

<script>
export default {
  name: 'line',
  // 显示文字，是否显示线条
  props: ['title', 'line'],
  data () {
    return {}
  }
}
</script>

<style lang="less">
.form-line {
  span {
    display: block;
    width: 4px;
    height: 18px;
    background: #29b87a;
  }
  label {
    padding: 0 8px;
    color: #343a43;
  }
  p {
    height: 1px;
    background: #dbe2ea;
  }
}
</style>
