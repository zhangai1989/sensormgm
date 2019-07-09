<template>
  <section class="layout width100 height100 flex fxcolumn">

    <!--<normal-bar>-->
      <!--<p slot="title">-->
        <!--数据分析-->
      <!--</p>-->
    <!--</normal-bar>-->

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
            style="width: 350px"
            :picker-options="pickerOptions">
          </el-date-picker>
        </li>
        <el-button type="success"
                   size="small"
                   :disabled="!queryAble"
                   @click="getList">查 询
        </el-button>

        <!--<el-button size="small" type="text" class="el-icon-download"></el-button>-->
      </ul>

      <div class="jy-content mt15" v-loading="!queryAble">
        <ve-line height="700px" v-if="chartData.rows.length > 0" :data="chartData" :settings="chartSettings"></ve-line>
      </div>
    </div>
  </section>
</template>

<script>
import { analysisList } from '@api/uploadLog'
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
      enterpriseId: [],
      rangeTime: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      chartData: {
        columns: ['日期', '油烟浓度', 'VOC', '颗粒物'],
        rows: []
      },
      chartSettings: {
        dimension: ['日期'],
        height: '900px'
      }
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
    async getList () {
      let that = this
      if (that.rangeTime === null || that.rangeTime.length === 0) {
        this.$message.warning('请先选择时间')
        return
      }
      if (new Date(that.rangeTime[1]).getTime() - new Date(that.rangeTime[0]).getTime() > 1000 * 60 * 60 * 24 * 31) {
        that.$message.warning('最多只能查询31天的数据')
        return
      }
      if (that.enterpriseId === '') {
        that.$message.warning('请先选择企业')
        return
      }
      let argc = {
        enterpriseId: that.enterpriseId,
        startTime: that.rangeTime[0],
        endTime: that.rangeTime[1]
      }
      that.queryAble = false
      const res = await analysisList(argc)
      that.chartData.rows = []
      that.queryAble = true
      if (res.code === 2000) {
        let list = res.result
        if (list && list.length > 0) {
          list.forEach(function (item, index, array) {
            that.chartData.rows.push({
              '日期': item.uploadTime,
              '油烟浓度': item.lampblack,
              'VOC': item.temp,
              '颗粒物': item.humidity
            })
          })
        }
      }
    },
    changeEnterprise (id) {
      this.enterpriseId = id
    }
  }
}

</script>
