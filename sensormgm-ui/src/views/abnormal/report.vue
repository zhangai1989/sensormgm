<template>
  <section class="layout width100 height100 flex fxcolumn">
    <normal-bar>
      <p slot="title">
        异常报备
      </p>
      <div slot="btns">
        <el-button size="small" @click="newDialog.new=true">
          <i class="iconfont icon-report-edtior "></i> 提报问题
        </el-button>
        <el-button size="small" @click="importDialog.new=true">
          <i class="iconfont icon-import "></i> 导入
        </el-button>
        <el-button size="small" @click="exportDialog.new=true">
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
          <label>发起人</label>
          <el-select v-model="info.create" placeholder="选择创建人" clearable filterable multiple>
            <el-option v-for="item in peopleList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </li>
        <li class="flex fxmiddle">
          <label>创建时间</label>
          <el-date-picker class="box-date" v-model="info.datetime" type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </li>
        <el-button type="success" size="small">查询</el-button>
      </ul>
      <el-tabs class="normal-tabs" v-model="activeName">
        <el-tab-pane label="全部" name="first"></el-tab-pane>
        <el-tab-pane label="未处理" name="second"></el-tab-pane>
        <el-tab-pane label="已处理" name="third"></el-tab-pane>
      </el-tabs>
      <div class="jy-content mt15">
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" class="jy-table">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column label="运单号">
            <template slot-scope="scope"><span class="jy-link">{{ scope.row.date }}</span></template>
          </el-table-column>
          <el-table-column prop="name" label="状态" width="100">
            <template slot-scope="scope">
              <span class="jy-state jy-state_success">{{ scope.row.state=='a'?'受理中':scope.row.state=='b'?'已完成':'已作废' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="问题描述" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="address" label="处理结果">
          </el-table-column>
          <el-table-column prop="address" label="创建事件">
          </el-table-column>
          <el-table-column prop="address" label="创建人">
          </el-table-column>
          <el-table-column prop="address" label="处理时间">
          </el-table-column>
        </el-table>
        <el-pagination class="fade-up" :page-sizes="pageSizes" :page-size="pageSize" layout="total, sizes, prev, pager, next" :total="total" background>
        </el-pagination>
      </div>
    </div>
    <!-- 新增帐号 -->
    <new-report :dialog="newDialog"></new-report>
    <detail-report :dialog="detailDialog"></detail-report>
    <div class="report-list">
      <import-report :dialog="importDialog"></import-report>
    </div>
  </section>
</template>

<script>
import { PAGE_SIZES, PAGE_SIZE } from '@configs'

const normalBar = () => import('@components/common/NormalBar')

const newReport = () => import('./components/new')
const importReport = () => import('./components/import')
const detailReport = () => import('./components/detail')
export default {
  components: { normalBar, newReport, detailReport, importReport },
  name: 'layout',
  data () {
    return {
      newDialog: {
        new: false
      },
      importDialog: {
        new: false
      },
      detailDialog: {
        new: false
      },
      exportDialog: {
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

  }
}
</script>

<style lang="less" scoped>
@import url("../../assets/styles/index.less");
</style>
