<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        实时监控
      </p>
      <div slot="btns">
        <div>
          <span style="font-size: 14px;margin-right: 10px">自动刷新</span>
          <el-switch
            v-model="autoReload"
            active-color="#13ce66"
            @change="changeAutoReload">
          </el-switch>
        </div>
      </div>
    </normal-bar>

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <el-select v-model="status" @change="changeStatus" size="small" placeholder="请选择" style="width: 80px">
            <el-option
              v-for="(item, i) in status_list"
              :key="i"
              :label="item.title"
              :value="item.label">
            </el-option>
          </el-select>
        </li>
        <li class="flex fxmiddle">
          <el-input
            placeholder="请输入企业名称"
            size="small"
            v-model.trim="search">
          </el-input>
        </li>
        <el-button type="success" size="small" @click="searchList">查 询</el-button>
      </ul>

      <div class="jy-content mt15">
        <el-table
          v-loading="loading"
          :data="list"
          border
          size="mini"
          class="jy-table">
          <el-table-column
            fixed
            prop="enterpriseName"
            label="企业名称"
            min-width="140">
          </el-table-column>

          <el-table-column
            fixed
            align="center"
            label="网络状态"
            max-width="100">
            <template slot-scope="scope">
              <i class="iconfont icon-wifi"
                 :style="{color:scope.row.status === 'ONLINE' ? '#53ee33': '#d1d1d1'}"></i>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            prop="lampblackWarning"
            label="限值"
            min-width="100">
          </el-table-column>

          <el-table-column
            align="center"
            prop="lampblack"
            label="油烟浓度(mg/m³)"
            min-width="130">
          </el-table-column>

          <el-table-column
            align="center"
            prop="temp"
            label="VOC(mg/m³)"
            min-width="130">
          </el-table-column>

          <el-table-column
            align="center"
            prop="humidity"
            label="颗粒物(mg/m³)"
            min-width="120">
          </el-table-column>

          <el-table-column
            align="center"
            label="风机状态"
            max-width="120">
            <template slot-scope="scope">
              <i v-show="scope.row.fanStatus" class="iconfont icon-start"
                 style="color:#67C23A;font-size: 14px"></i>
              <i v-show="!scope.row.fanStatus" class="iconfont icon-stop" style="color:#F56C6C"></i>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            prop="fanElec"
            label="风机电流"
            max-width="120">
          </el-table-column>

          <el-table-column
            align="center"
            label="净化器状态"
            max-width="120">
            <template slot-scope="scope">
              <i v-show="scope.row.purifierStatus" class="iconfont icon-start"
                 style="color:#67C23A;font-size: 14px"></i>
              <i v-show="!scope.row.purifierStatus" class="iconfont icon-stop" style="color:#F56C6C"></i>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            prop="purifierElec"
            label="净化器电流"
            width="120">
          </el-table-column>

          <el-table-column
            align="center"
            prop="lastUploadTime"
            label="最后上传时间"
            min-width="150">
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
import { monitorList } from '@api/monitor'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'real',
  components: {
    normalBar
  },
  data () {
    return {
      sysEnterprise: new Map(),
      loading: false,
      autoReload: false,
      search: '',
      status: '全部',
      status_list: [{
        title: '全部',
        label: '全部'
      }, {
        title: '在线',
        label: 'ONLINE'
      }, {
        title: '离线',
        label: 'OFFLINE'
      }],
      list: [],
      totalNum: 0,
      pageSize: 10,
      currentPage: 1
    }
  },
  created () {
  },

  mounted () {
    this.initData()
    this.initSysEnterprise()
  },

  beforeDestroy () {
    clearInterval(this.intervalId)
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
    initSysEnterprise () {
      let that = this
      let allEnterprise = JSON.parse(localStorage.getItem('allEnterprise'))
      allEnterprise.forEach(function (item) {
        that.sysEnterprise.set(parseInt(item.id), item.name)
      })
    },

    // 获取一页列表数据
    getList (argc) {
      let that = this
      argc.pageSize = that.pageSize
      that.loading = true
      monitorList(argc)
        .then(function (res) {
          that.loading = false
          if (res.code === 2000) {
            that.currentPage = argc.pageNum
            that.totalNum = res.result.totalElements
            if (res.result.content && res.result.content.length > 0) {
              res.result.content.forEach(function (item) {
                if(item.lampblack === -10000) item.lampblack = '--'
                if(item.temp === -10000) item.temp = '--'
                if(item.humidity === -10000) item.humidity = '--'
                if(item.fanElec === -10000) item.fanElec = '--'
                if(item.purifierElec === -10000) item.purifierElec = '--'
                item.enterpriseName = that.sysEnterprise.get(item.enterpriseId)
              })
            }
            that.list = res.result.content
          }
        })
        .catch(function () {
          that.loading = false
        });
    },

    // 分页事件
    changePage (page) {
      let that = this
      let argc = {
        pageNum: page
      }
      if (that.search !== '') {
        argc.enterprise = that.search
      }

      if (that.status !== '全部') {
        argc.status = that.status
      }
      that.getList(argc)
    },
    // 搜索
    searchList () {
      let that = this
      if (that.search.length > 30) {
        that.$message.warning('搜索内容请勿太长!')
        return
      }
      let argc = {
        pageNum: 1
      }
      if (that.search !== '') {
        argc.enterprise = that.search
      }
      if (that.status !== '全部') {
        argc.status = that.status
      }
      that.getList(argc)
    },

    // 筛选状态事件
    changeStatus (label) {
      let that = this
      let argc = {
        pageNum: 1
      }
      if (that.search !== '') {
        argc.enterprise = that.search
      }
      if (label !== '全部') {
        argc.status = label
      }
      that.getList(argc)
    },

    // 刷新
    reloadList () {
      let that = this
      let argc = {
        pageNum: 1
      }
      if (that.search !== '') {
        argc.enterprise = that.search
      }

      if (that.status !== '全部') {
        argc.status = that.status
      }
      that.getList(argc)
    },
    changeAutoReload (val) {
      let that = this
      if (val) {
        that.intervalId = setInterval(() => {
          that.reloadList()
        }, 5000)
      } else {
        clearInterval(that.intervalId)
      }
    }
  }
}

</script>
