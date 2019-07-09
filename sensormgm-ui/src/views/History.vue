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
        <li class="flex fxmiddle" v-if="userLevel !== '4'">
          <enterprise-selector @changeEnterprise="changeEnterprise"/>
        </li>
        <li class="flex fxmiddle">
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
            style="width: 350px">
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
            label="VOC(mg/m³)">
          </el-table-column>

          <el-table-column
            align="center"
            prop="humidity"
            label="颗粒物(mg/m³)">
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
import UserContext from '@utils/UserContext'
const normalBar = () => import('@components/common/NormalBar')
const enterpriseSelector = () => import('@components/common/EnterpriseSelector')

export default {
  name: 'history',
  components: {
    normalBar,
    enterpriseSelector
  },
  data () {
    return {
      queryAble: true,
      userLevel: 0,
      enterpriseId: '',
      rangeTime: [],
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
    let date = this.$moment(new Date()).format('YYYY-MM-DD')
    this.rangeTime.push(date + ' 00:00:00')
    this.rangeTime.push(date + ' 23:59:59')

    this.userLevel = UserContext.getUserLevel()
    if (this.userLevel === 4) {
      this.enterpriseId = UserContext.getUserEnterprise()
    }
  },

  mounted () {},
  methods: {
    // 获取一页列表数据
    async getList (argc) {
      let that = this
      argc.pageSize = that.pageSize
      if (that.enterpriseId === '') {
        that.$message.warning('请先选择企业')
        return
      }
      if (that.rangeTime === null || that.rangeTime.length === 0) {
        that.$message.warning('请先选择时间')
        return
      }
      if (new Date(that.rangeTime[1]).getTime() - new Date(that.rangeTime[0]).getTime() > 1000 * 60 * 60 * 24 * 31) {
        this.$message.warning('最多只能查询31天的数据')
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
        if (that.list && that.list.length > 0) {
          that.list.forEach(function (item) {
            if (item.lampblack === -10000) {
              item.lampblack = '--'
            }
            if (item.temp === -10000) {
              item.temp = '--'
            }
            if (item.humidity === -10000) {
              item.humidity = '--'
            }
          })
        }
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
    exportExcel () {
      if (this.enterpriseId === '') {
        this.$message.warning('请先选择企业')
        return
      }
      if (this.rangeTime.length === 0) {
        this.$message.warning('请先选择时间')
        return
      }
      if (new Date(this.rangeTime[1]).getTime() - new Date(this.rangeTime[0]).getTime() > 1000 * 60 * 60 * 24 * 31) {
        this.$message.warning('最多只能导出31天的数据')
        return
      }
      window.location.href = '/api/export/history?enterpriseId=' + this.enterpriseId + '&beginTime=' + this.rangeTime[0] + '&endTime=' + this.rangeTime[1]
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
    },
    changeEnterprise (id) {
      this.enterpriseId = id
    }
  }
}

</script>
