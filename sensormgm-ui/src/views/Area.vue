<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        区域管理
      </p>
      <div slot="btns">
        <el-button size="small"
                   @click="openDialog">
          <i class="el-icon-plus fbold"></i> 新增区域
        </el-button>
      </div>
    </normal-bar>

    <div class="main-viewer">
      <el-row :gutter="20">
        <el-col :span="4">
          <el-tree
            :data="areaTree"
            node-key="id"
            :default-expanded-keys="expandedKeys"
            :props="defaultProps">
          </el-tree>
        </el-col>
        <el-col :span="20">
          <div class="jy-content mt15" style="padding: 0px">
            <el-table
              v-loading="loading"
              :data="list"
              border
              size="mini"
              class="jy-table">
              <el-table-column
                align="center"
                prop="uploadTime"
                label="上传时间">
              </el-table-column>

              <el-table-column
                align="center"
                prop="lampblack"
                label="油烟浓度(mg/m³)">
              </el-table-column>

              <el-table-column
                align="center"
                prop="temp"
                label="烟气温度（℃）">
              </el-table-column>

              <el-table-column
                align="center"
                prop="humidity"
                label="烟气湿度（%）">
              </el-table-column>

            </el-table>
          </div>
        </el-col>
      </el-row>
    </div>
  </section>
</template>

<script>
import { getHistoryList } from '@api/monitor'
import { getAreaTree } from '@api/area'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'history',
  components: {
    normalBar
  },
  data () {
    return {
      loading: false,
      areaTree: [{
        id: 1,
        label: '一级 1',
        children: [{
          id: 4,
          label: '二级 1-1',
          children: [{
            id: 9,
            label: '三级 1-1-1'
          }, {
            id: 10,
            label: '三级 1-1-2'
          }]
        }]
      }, {
        id: 2,
        label: '一级 2',
        children: [{
          id: 5,
          label: '二级 2-1'
        }, {
          id: 6,
          label: '二级 2-2'
        }]
      }, {
        id: 3,
        label: '一级 3',
        children: [{
          id: 7,
          label: '二级 3-1'
        }, {
          id: 8,
          label: '二级 3-2'
        }]
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      expandedKeys: [1, 2, 3],

      list: []
    }
  },
  created () {
    let level = parseInt(localStorage.getItem('user.level'))
    this.areaTreeDeep = level < 3 ? 3 : level === 3 ? 2 : 1
  },

  mounted () {
//    this.initData()
  },
  methods: {

    // 初始化
    initData () {
      let that = this
      let treeProps = {
        areaId: parseInt(localStorage.getItem('user.areaId'))
      }
      that.getTree(treeProps)
      let argc = {
        pageNum: 1
      }
      that.getList(argc)
    },

    async getTree (argc) {
      let that = this
      const res = await getAreaTree(argc)
      if (res.code === 2000) {
        that.areaTree = res.result
      }
    },

    // 获取一页列表数据
    async getList (argc) {
      let that = this
      argc.pageSize = that.pageSize
      that.loading = true
      const res = await getHistoryList(argc)
      if (res.code === 2000) {
        that.currentPage = argc.pageNum
        that.totalNum = res.result.totalElements
        that.list = res.result.content
        that.loading = false
      } else {
        that.loading = false
      }
    },

    // 分页事件
    changePage (page) {
      let that = this
      let argc = {
        pageNum: page
      }
      if (that.rangeTime !== '' && that.rangeTime !== null) {
        argc.startTime = that.rangeTime[0]
        argc.endTime = that.rangeTime[1]
      }
      if (that.treeValue.length === that.areaTreeDeep) {
        argc.enterpriseId = that.treeValue[2]
      }
      that.getList(argc)
    },
    changeTree (obj) {
      let that = this
      if (obj.length < that.areaTreeDeep) {
        that.treeValue = []
        return
      }
      let argc = {
        pageNum: 1,
        enterpriseId: obj[2].id
      }
      if (that.rangeTime !== '' && that.rangeTime !== null) {
        argc.startTime = that.rangeTime[0]
        argc.endTime = that.rangeTime[1]
      }
      that.getList(argc)
    },

    timeChange (rangeTime) {
      let that = this
      let argc = {
        pageNum: 1
      }
      if (rangeTime !== '' && rangeTime !== null) {
        argc.startTime = that.rangeTime[0]
        argc.endTime = that.rangeTime[1]
      }
      if (that.treeValue.length === that.areaTreeDeep) {
        argc.enterpriseId = that.treeValue[2]
      }
      that.getList(argc)
    },

    openDialog () {

    }
  }
}

</script>
