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
          <el-select v-model="form.field" style="width: 90px">
            <el-option label="油烟" value="lampblack"/>
            <el-option label="VOC" value="temp"/>
            <el-option label="颗粒物" value="humidity"/>
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
                   @click="getList">查 询
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
            label="VOC(mg/m³)">
            max-width="120">
          </el-table-column>

          <el-table-column
            align="center"
            prop="humidity"
            label="颗粒物(mg/m³)"
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
    let date = this.$moment(new Date()).format('YYYY-MM-DD')
    this.rangeTime.push(date + ' 00:00:00')
    this.rangeTime.push(date + ' 23:59:59')
  },

  mounted () {},
  methods: {
    // 获取一页列表数据
    getList () {
      let that = this
      if (null == that.rangeTime || that.rangeTime.length == 0) {
        this.$message.warning('请先选择时间')
        return
      }
      if (new Date(this.rangeTime[1]).getTime() - new Date(this.rangeTime[0]).getTime() > 1000 * 60 * 60 * 24 * 31) {
        this.$message.warning('最多只能查询31天的数据')
        return
      }
      if (that.rangeTime !== '' && that.rangeTime !== null) {
        that.form.startTime = that.rangeTime[0]
        that.form.endTime = that.rangeTime[1]
      }
      that.queryAble = false
      rankingList(this.form)
        .then(function (res) {
          that.queryAble = true
          if (res.code === 2000) {
            that.list = res.result
          }
        })
        .catch(function () {
          that.queryAble = true
        })

    }
  }
}

</script>
