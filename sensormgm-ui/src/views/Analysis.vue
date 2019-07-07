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
      rangeTime: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      chartData: {
        columns: ['日期', '油烟浓度', '温度', '湿度'],
        rows: []
      },
      chartSettings: {
        dimension: ['日期'],
        height: '900px'
      },
    }
  },
  created () {
    let date = this.$moment(new Date()).format('YYYY-MM-DD')
    this.rangeTime.push(date + ' 00:00:00')
    this.rangeTime.push(date + ' 23:59:59')
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
        this.enterpriseId = ''
        return
      }
    }
  }
}

</script>
