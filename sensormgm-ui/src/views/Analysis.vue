<template>
  <section class="layout width100 height100 flex fxcolumn">

    <!--<normal-bar>-->
      <!--<p slot="title">-->
        <!--数据分析-->
      <!--</p>-->
    <!--</normal-bar>-->

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle" v-if="areaTreeDeep > 1">
          <el-cascader :options="areaTree" size="small" @change="changeTree"
                       v-model="treeValue" :props="treeProps" :show-all-levels="false" placeholder="请选择企业"></el-cascader>
        </li>
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
            :picker-options="pickerOptions">
          </el-date-picker>
        </li>
        <el-button type="success"
                   size="small"
                   :disabled="!queryAble"
                   @click="timeChange">查 询
        </el-button>

        <!--<el-button size="small" type="text" class="el-icon-download"></el-button>-->
      </ul>

      <div class="jy-content mt15" v-loading="!queryAble">
        <ve-line class="chart" v-if="chartData.rows.length > 0" :data="chartData" :chartSettings="chartSettings"></ve-line>
      </div>
    </div>
  </section>
</template>

<style scoped>
  .chart {
    width: calc(100%);
    height: 650px;
  }
</style>

<script>
import { analysisList } from '@api/uploadLog'
import { getAreaTree } from '@api/area'
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
      enterpriseId: [],
      rangeTime: '',
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      chartData: {
        columns: ['日期', '油烟浓度', '温度', '湿度'],
        rows: []
      },
//      chartSettings: {
//        dimension: ['日期']
//      }
      chartSettings: {}
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
      if (!that.enterpriseId) return
      argc.enterpriseId =  this.enterpriseId
      if (that.rangeTime && that.rangeTime !== '' && that.rangeTime !== null) {
        argc.startTime = that.rangeTime[0]
        argc.endTime = that.rangeTime[1]
      } else {
          return
      }

      argc.pageNo = 1
      argc.pageSize = 1000
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
              '温度': item.temp,
              '湿度': item.humidity
            })
          })
        }
      }
    },

    changeTree (obj) {
      let that = this
      that.enterpriseId = obj[obj.length - 1]
      if (obj.length < that.areaTreeDeep) {
        that.treeValue = []
        return
      }
      let argc = {
        pageNo: 1
      }
      that.getList(argc)
    },

    timeChange (rangeTime) {
      let that = this
      let argc = {
        pageNum: 1
      }
      that.getList(argc)
    }
  }
}

</script>
