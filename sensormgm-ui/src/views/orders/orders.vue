<template>
  <section class="layout width100 height100 flex fxcolumn">
    <normal-bar>
      <p slot="title">
        工单管理
      </p>
      <div slot="btns">
        <el-button size="small">
          <i class="iconfont icon-export "></i> 导出
        </el-button>
      </div>
    </normal-bar>
    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <label class="width55">运单号</label>
          <el-input placeholder="支持多个运单号查询" v-model="info.no" clearable>
          </el-input>
        </li>
        <li class="flex fxmiddle">
          <label class="width55">工单号</label>
          <el-input placeholder="支持多个工单号查询" v-model="info.no" clearable>
          </el-input>
        </li>
        <li class="flex fxmiddle">
          <label>创建时间</label>
          <el-date-picker class="box-date" v-model="info.datetime" type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </li>
        <el-button type="success" size="small">查询</el-button>
      </ul>
      <el-tabs class="normal-tabs" v-model="activeName">
        <el-tab-pane label="全部" name="first"></el-tab-pane>
        <el-tab-pane label="受理中" name="second"></el-tab-pane>
        <el-tab-pane label="已完成" name="third"></el-tab-pane>
        <el-tab-pane label="已作废" name="four"></el-tab-pane>
      </el-tabs>
      <div class="jy-content mt15">
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" class="jy-table">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column label="工单号">
            <template slot-scope="scope"><span class="link" @click="handleGoDetail(scope.row)">{{ scope.row.date }}</span></template>
          </el-table-column>
          <el-table-column label="运单号" prop="address">
          </el-table-column>
          <el-table-column prop="name" label="状态" width="100">
            <template slot-scope="scope">
              <span class="jy-state jy-state_success">{{ scope.row.state=='a'?'受理中':scope.row.state=='b'?'已完成':'已作废' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="工单内容" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="address" label="创建时间">
          </el-table-column>
        </el-table>
        <el-pagination class="fade-up" :page-sizes="pageSizes" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" background>
        </el-pagination>
      </div>
    </div>
    <transmit :dialog="dialog"></transmit>
  </section>
</template>

<script>
import { PAGE_SIZES, PAGE_SIZE } from '@configs'

const normalBar = () => import('@components/common/NormalBar')
const transmit = () => import('./components/transmit')
export default {
  components: { normalBar, transmit },
  name: 'layout',
  data () {
    return {
      dialog: {
        new: false
      },
      input: '',
      tableData: [
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-08',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-06',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }
      ],
      info: {},
      peopleList: [],
      activeName: 'first',
      total: 7
    }
  },
  computed: {
    pageSizes () {
      return PAGE_SIZES
    },
    pageSize () {
      return PAGE_SIZE
    }
  },
  created () {
  },
  methods: {
    // 跳转详情
    handleGoDetail () {
      // orderDetail
      this.$router.push({ name: 'orderDetail' })
    }
  }
}
</script>

<style lang="less" scoped>
@import url("../../assets/styles/index.less");
</style>
