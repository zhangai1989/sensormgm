<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        超标查询
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
          <el-date-picker
            v-model="rangeTime"
            size="small"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            @change="timeChange"
            :picker-options="pickerOptions"
            style="width: 270px">
          </el-date-picker>
        </li>
        <el-button type="success"
                   size="small"
                   :disabled="!queryAble"
                   @click="timeChange">查 询
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
            prop="enterpriseName"
            label="企业名称">
          </el-table-column>
          <el-table-column
            align="center"
            prop="uploadTime"
            label="记录时间">
          </el-table-column>
          <el-table-column
            align="center"
            prop="lampblack"
            label="油烟浓度(mg/m³)">
          </el-table-column>
          <el-table-column
            prop="multiple"
            label="超标倍数">
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

<style>
  .el-table .cell {
    white-space: nowrap;
  }
</style>

<style scoped>
  .pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: 22px;
    padding-bottom: 20px;
  }
</style>

<script>
import { beyondList } from '@api/uploadLog'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'outStandard',
  components: {
    normalBar
  },
  data () {
    return {
      queryAble: true,
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
      let argc = {
        pageNum: 1
      }
      that.getList(argc)
    },

    // 获取一页列表数据
    async getList (argc) {
      if (this.rangeTime.length == 0) {
        return
      }
      let that = this
      argc.pageSize = that.pageSize
      that.queryAble = false
      const res = await beyondList(argc)
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
      that.getList(argc)
    },

    exportExcel () {
      this.exportAble = false
      this.counter = 60
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
