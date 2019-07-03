<template>
  <el-cascader :options="areaTree" size="small" @change="changeTree"
               v-model="treeValue" :props="treeProps" :show-all-levels="false" placeholder="请选择企业"></el-cascader>
</template>

<script>
import { getAreaTree } from '@api/area'

export default {
  name: 'enterpriseSelector',
  // 显示文字，是否显示线条
  props: ['areaId', 'treeDeep'],
  data () {
    return {
      areaTree: [],
      innerAreaId: this.areaId,
      treeProps: {
        value: 'id',
        label: 'text',
        children: 'nodes'
      },
      treeValue: [],
    }
  },
  created () {
    this.getTree()
  },
  methods: {
    async getTree () {
      let that = this
      const res = await getAreaTree({areaId: this.innerAreaId})
      if (res.code === 2000) {
        that.areaTree = res.result
      }
    },
    changeTree (obj) {
      let that = this
      if (obj.length < that.treeDeep) {
        that.treeValue = []
        this.$emit('changeTree', null)
      } else {
        this.$emit('changeTree', obj[obj.length - 1])
      }
    }
  }
}
</script>
