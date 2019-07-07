<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        历史记录
      </p>
      <div slot="btns">
        <el-button size="small"
                   :disabled="!exportAble"
                   @click="exportExcel">
          <i class="el-icon-document fbold"></i> {{ counter > 0 ? '剩余' + counter + 's' : '导出' }}
        </el-button>
      </div>
    </normal-bar>

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <el-cascader v-if="areaTreeDeep > 1" :options="areaTree" size="small" @change="changeTree"
                       v-model="treeValue" :props="treeProps" :show-all-levels="false" placeholder="请选择企业"></el-cascader>

          <el-date-picker
            v-model="rangeTime"
            size="small"
            type="datetimerange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="pickerOptions"
            :style="{marginLeft: areaTreeDeep > 1 ? '20px' : '0', width: '350px'}">
          </el-date-picker>
        </li>
        <el-button type="success"
                   size="small"
                   :disabled="!queryAble"
                   @click="getList({pageNum: 1})">查 询
        </el-button>
      </ul>

      <div class="jy-content mt15">
        <el-table
          v-loading="!queryAble"
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

      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next, jumper"
          :current-page.sync="currentPage"
          :pageSize="pageSize"
          @current-change="changePage"
          :total="totalNum">
        </el-pagination>
      </div>
    </div>
  </section>
</template>

<style scoped>
    .pagination {
        display: flex;
        justify-content: flex-end;
        margin-top: 22px;
        padding-bottom: 20px;
    }
</style>

<script>
import { historyList } from '@api/uploadLog'
import { getAreaTree } from '@api/area'
import { exportHistory } from '@api/export'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'history',
  components: {
    normalBar
  },
  data () {
    return {
      queryAble: true,
      areaTree: [],
      areaTreeDeep: 3,
      treeProps: {
        value: 'id',
        label: 'text',
        children: 'nodes'
      },
      treeValue: [],
      enterpriseId: '',
      rangeTime: '',
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      list: [],
      totalNum: 0,
      pageSize: 10,
      currentPage: 1,

      exportAble: true,
      counter: 0
    }
  },
  created () {
  },

  mounted () {
    this.initData()
  },
  methods: {
    // 初始化
    initData () {
      let that = this
      let userInfo = JSON.parse(localStorage.getItem('userInfo'))
      let level = parseInt(userInfo.level)
      this.areaTreeDeep = level < 3 ? 3 : level === 3 ? 2 : 1

      let treeProps = {
        areaId: parseInt(userInfo.areaId)
      }
      that.getTree(treeProps)
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
      if ('' == that.enterpriseId) {
        this.$message.warning('请先选择企业')
        return
      }
      if (this.rangeTime.length === 0) {
        this.$message.warning('请先选择时间')
        return
      }
      argc.enterpriseId = that.enterpriseId
      argc.startTime = that.rangeTime[0]
      argc.endTime = that.rangeTime[1]
      that.queryAble = false
      const res = await historyList(argc)
      that.queryAble = true
      if (res.code === 2000) {
        that.currentPage = argc.pageNum
        that.totalNum = res.result.totalElements
        that.list = res.result.content
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
      } else {
        return
      }
      that.getList(argc)
    },
    changeTree (obj) {
      let that = this
      if (obj.length < that.areaTreeDeep) {
        that.treeValue = []
        that.enterpriseId = ''
        return
      }
      that.enterpriseId = obj[obj.length - 1]
    },

    exportExcel () {
      if ('' == this.enterpriseId) {
        this.$message.warning('请先选择企业')
        return
      }
      if (this.rangeTime.length === 0) {
        this.$message.warning('请先选择时间')
        return
      }
      window.location.href='/api/export/history?enterpriseId=' + this.enterpriseId + '&beginTime=' + this.rangeTime[0] + '&endTime=' + this.rangeTime[1]
      this.exportAble = false
      this.counter = 30
      let that = this
      let intervalId = setInterval(() => {
        if (that.counter === 0) {
          that.exportAble = true
          clearInterval(intervalId)
        } else {
          that.counter--
        }
      }, 1000)
    }
  }
}

</script>
