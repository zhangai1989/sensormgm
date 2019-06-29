<template>
    <div>
        <!--实时监控页面-->

        <div class="page">
            <div class="header">
                <div style="display: flex;align-items: center;">

                    <el-select v-model="status" @change="changeStatus" size="small" placeholder="请选择">
                        <el-option
                                v-for="(item, i) in status_list"
                                :key="i"
                                :label="item.title"
                                :value="item.label">
                        </el-option>
                    </el-select>


                    <el-input
                            placeholder="请输入企业名称"
                            prefix-icon="el-icon-search"
                            size="small"
                            style="margin:0 15px"
                            v-model.trim="search">
                    </el-input>
                    <el-button type="primary" size="small" @click="searchList">搜索</el-button>
                </div>

                <div>
                    <el-button type="primary" size="small" @click="reloadList">刷新</el-button>
                </div>
            </div>

            <div class="table-box">
                <el-table
                        :data="list"
                        border
                        size="mini"
                        style="width: 100%;white-space: nowrap">
                    <el-table-column
                            fixed
                            align="center"
                            prop="enterpriseName"
                            label="企业名称"
                            width="120">
                    </el-table-column>

                    <el-table-column
                            fixed
                            align="center"
                            label="网络状态"
                            width="120">
                        <template slot-scope="scope">
                            <i class="iconfont icon-wifi"
                               :style="{color:scope.row.status === 'ONLINE' ? '#67C23A': '#F56C6C'}"></i>
                        </template>

                    </el-table-column>


                    <el-table-column
                            align="center"
                            prop="lampblackWarning"
                            label="限值"
                            width="50">
                    </el-table-column>


                    <el-table-column
                            align="center"
                            prop="lampblack"
                            label="油烟浓度"
                            width="120">
                    </el-table-column>


                    <el-table-column
                            align="center"
                            prop="temp"
                            label="烟气温度"
                            width="120">
                    </el-table-column>


                    <el-table-column
                            align="center"
                            prop="humidity"
                            label="油烟湿度"
                            width="120">
                    </el-table-column>


                    <el-table-column
                            align="center"
                            label="风机状态"
                            width="120">
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
                            width="120">
                    </el-table-column>

                    <el-table-column
                            align="center"
                            label="净化器状态"
                            width="120">
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
    </div>
</template>

<style>
    .el-table .cell {
        white-space: nowrap;
    }
</style>

<style scoped>

    .page {
        background: #FFFFFF;
        border: 1px solid #DAE3ED;
        border-radius: 6px;
        padding: 20px 24px;
        width: calc(100vw - 304px)
    }

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 20px;
    }

    .table-box {
        font-size: 12px;
    }

    .pagination {
        display: flex;
        justify-content: flex-end;
        margin-top: 22px;
        padding-bottom: 20px;
    }
</style>

<script>

  import {monitorList} from '../http/modules/index'

  export default {
    data() {
      return {
        search: '',
        status: '全部',
        status_list: [
          {
            title: '全部',
            label: '全部'
          },
          {
            title: '在线',
            label: 'ONLINE'
          },
          {
            title: '离线',
            label: 'OFFLINE'
          }
        ],
        list: [
          {
            id: 1,
            enterpriseId: 1,
            enterpriseName: '沃尔沃二若',
            status: 'ONLINE',//ONLINE/OFFLINE,
            lampblackWarning: 1,
            lampblack: -10000,
            temp: -10000,
            humidity: -10000,
            fanStatus: false,
            fanElec: -10000,
            purifierStatus: false,
            purifierElec: -10000
          },
          {
            id: 1,
            enterpriseId: 1,
            enterpriseName: '沃尔沃二若',
            status: 'OFFLINE',//ONLINE/OFFLINE,
            lampblackWarning: 1,
            lampblack: -10000,
            temp: -10000,
            humidity: -10000,
            fanStatus: true,
            fanElec: -10000,
            purifierStatus: false,
            purifierElec: -10000
          },
          {
            id: 1,
            enterpriseId: 1,
            enterpriseName: '沃尔沃二若',
            status: 'OFFLINE',//ONLINE/OFFLINE,
            lampblackWarning: 1,
            lampblack: -10000,
            temp: -10000,
            humidity: -10000,
            fanStatus: false,
            fanElec: -10000,
            purifierStatus: false,
            purifierElec: -10000
          },
        ],

        totalNum: 0,
        pageSize: 10,
        currentPage: 1,
      }
    },
    created() {
    },

    mounted() {
      this.initData()
    },
    methods: {


      //初始化
      initData() {
        let that = this
        let argc = {
          pageNum: 1
        }
        that.getList(argc)
      },


      //获取一页列表数据
      getList(argc) {
        let that = this
        argc.pageSize = that.pageSize

        monitorList(argc)
          .then(res => {
            if (res.code === 2000) {
              that.currentPage = argc.pageNum
              that.totalNum = res.result.totalElements
              that.list = res.result.content
            }
          })
      },


      //分页事件
      changePage(page) {

        let that = this
        let argc = {
          pageNum:page
        }
        if (that.search !== '') {
          argc.enterprise = that.enterprise
        }

        if(that.status !== '全部'){
          argc.status = that.status
        }

        that.getList(argc)
      },


      //搜索
      searchList() {
        let that = this
        if (that.search.length > 10) {
          that.$message.warning('搜索内容请勿太长!')
          return
        }

        let argc = {
          pageNum: 1,
        }

        if (that.search !== '') {
          argc.enterprise = that.enterprise
        }

        if(that.status !== '全部'){
          argc.status = that.status
        }

        that.getList(argc)
      },

      //筛选状态事件
      changeStatus(label){
        let that = this
        let argc = {
          pageNum: 1,
        }
        if (that.search !== '') {
          argc.enterprise = that.enterprise
        }

        if(label !== '全部'){
          argc.status = label
        }
        that.getList(argc)
      },

      //刷新
      reloadList(){
        let that = this
        let argc = {
          pageNum: 1,
        }
        if (that.search !== '') {
          argc.enterprise = that.enterprise
        }

        if(that.status !== '全部'){
          argc.status = that.status
        }
        that.getList(argc)
      }
    }
  }

</script>