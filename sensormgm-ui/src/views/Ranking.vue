<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        污染排名
      </p>
    </normal-bar>

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <el-select v-model="form.field" style="width: 80px">
            <el-option label="油烟" value="lampblack"/>
            <el-option label="温度" value="temp"/>
            <el-option label="湿度" value="humidity"/>
          </el-select>
        </li>
        <li class="flex fxmiddle">
          <el-select v-model="form.sort" style="width: 80px">
            <el-option label="降序" value="desc"/>
            <el-option label="升序" value="asc"/>
          </el-select>
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
            prop="name"
            label="企业名称">
          </el-table-column>
          <el-table-column
            align="center"
            prop="lampblack"
            label="油烟浓度(mg/m³)">
          </el-table-column>
          <el-table-column
            align="center"
            prop="temp"
            label="烟气温度"
            max-width="120">
          </el-table-column>

          <el-table-column
            align="center"
            prop="humidity"
            label="油烟湿度"
            max-width="120">
          </el-table-column>
        </el-table>
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

</style>

<script>
import { rankingList } from '@api/uploadLog'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'ranking',
  components: {
    normalBar
  },
  data () {
    return {
      queryAble: true,
      form: {
        field: 'lampblack',
        sort: 'desc'
      },
      rangeTime: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      list: [],
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
      that.getList()
    },
    // 获取一页列表数据
    async getList () {
      let that = this
      if (that.rangeTime.length == 0) {
        return
      }
      if (that.rangeTime !== '' && that.rangeTime !== null) {
        that.form.startTime = that.rangeTime[0]
        that.form.endTime = that.rangeTime[1]
      }
      that.queryAble = false
      const res = await rankingList(this.form)
      that.queryAble = true
      if (res.code === 2000) {
        that.list = res.result
      }
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
    }
  }
}

</script>
