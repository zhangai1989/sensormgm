<template>
  <section class="layout width100 height100 flex fxcolumn">
    <el-amap vid="amap" :center="center" :zoom="zoom">

    </el-amap>
  </section>
</template>

<style scoped>
</style>

<script>

export default {
  name: 'realMap',
  components: {
  },
  data () {
    return {
      zoom: 16,
      center: [121.59996, 31.197646]
    }
  },
  created () {
  },

  mounted () {
  },
  methods: {
  }
}

</script>
