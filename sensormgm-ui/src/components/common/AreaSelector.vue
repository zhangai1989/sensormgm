<template>
  <el-cascader :options="areaTree" size="small" @change="changeTree" :clearable="true"
               v-model="treeValue" :props="treeProps" :show-all-levels="false" placeholder="请选择企业"></el-cascader>
</template>

<script>
import { getAreaTree } from '@api/area'
import UserContext from '@utils/UserContext'

export default {
  name: 'areaSelector',
  props: ['needEnterprise'],
  data () {
    return {
      areaTree: [],
      innerNeedEnterprise: this.needEnterprise,
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
      let areaId = UserContext.getUserArea()
      if ('' === areaId) {
        that.areaTree = []
        this.treeValue = []
        return
      }
      const res = await getAreaTree({areaId: areaId})
      if (res.code === 2000) {
        // ID = 区域ID + 企业ID
        if(res.result && res.result.length > 0) {
          that.resetId(res.result)
        }
        that.areaTree = res.result
      }
    },
    resetId (list) {
      let that = this
      list.forEach(function (item) {
        if (item.levelCode === '4') {
          item.id = item.parentId + '-' + item.id
        }
        if (item.nodes && item.nodes.length > 0) {
          that.resetId(item.nodes)
        }
      })
    },
    changeTree (arr) {
      let that = this
      let id = arr[arr.length - 1]
      let idx = id.indexOf('-')
      // 必须选企业
      if (that.innerNeedEnterprise === true) {
        if (idx === -1) {
          that.treeValue = []
          that.$emit('changeArea', null, null)
        }
      } else {
        if (idx === -1) {
          // 选择的是区域
          that.$emit('changeArea', id, null)
        } else {
          // 选择的是企业
          that.$emit('changeArea', id.substr(0, idx), id.substr(idx + 1, id.length))
        }
      }
    }
  }
}
</script>
