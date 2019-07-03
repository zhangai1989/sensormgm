<template>
  <section class="layout width100 height100 flex fxcolumn">

    <!--<normal-bar>-->
      <!--<p slot="title">-->
        <!--数据分析-->
      <!--</p>-->
    <!--</normal-bar>-->

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <el-cascader v-if="areaTreeDeep > 1" :options="areaTree" size="small" @change="changeTree"
                       v-model="treeValue" :props="treeProps" :show-all-levels="false" placeholder="请选择企业"></el-cascader>

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
            :style="{marginLeft: areaTreeDeep > 1 ? '20px' : '0', width: '270px'}">
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
        <chart class="chart" v-if="orgOptions.xAxis.data.length > 0" :options="orgOptions" :auto-resize="true"></chart>
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
      orgOptions: {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['烟气温度', '烟气湿度', '油烟浓度']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '烟气温度',
            type: 'line',
            smooth: true,
            data: []
          },
          {
            name: '烟气湿度',
            type: 'line',
            smooth: true,
            data: []
          },
          {
            name: '油烟浓度',
            type: 'line',
            smooth: true,
            data: []
          }
        ]
      }
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
      that.orgOptions.xAxis.data = []
      that.orgOptions.series[0].data = []
      that.orgOptions.series[1].data = []
      that.orgOptions.series[2].data = []
      that.queryAble = true
      if (res.code === 2000) {
        let list = res.result
        if (list && list.length > 0) {
          list.forEach(function (item, index, array) {
            that.orgOptions.xAxis.data.push(item.uploadTime)
            that.orgOptions.series[0].data.push(item.temp)
            that.orgOptions.series[1].data.push(item.humidity)
            that.orgOptions.series[2].data.push(item.lampblack)
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
