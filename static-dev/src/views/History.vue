<template>
    <div>
        <!--实时监控页面-->

        <div class="page">
            <div class="header">
                <div style="display: flex;align-items: center;">
                    <div v-show="area_tree_deep > 1">
                        <el-cascader :options="area_tree" size="small" @change="changeTree"  v-model="tree_value" :props="tree_props" :show-all-levels="false"></el-cascader>
                    </div>
                </div>

                <div style="display: flex;align-items: center">
                    <div style="font-size: 14px;margin-right: 15px">起止时间</div>
                    <el-date-picker
                            v-model="range_time"
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

                </div>
            </div>

            <div class="table-box">
                <el-table
                        v-loading="loading"
                        :data="list"
                        border
                        size="mini"
                        style="width: 100%;white-space: nowrap">
                    <el-table-column
                            align="center"
                            prop="uploadTime"
                            label="发布时间">
                    </el-table-column>

                    <el-table-column
                            align="center"
                            prop="lampblack"
                            label="油烟浓度(mg/m³)">
                    </el-table-column>

                    <el-table-column
                            align="center"
                            prop="temp"
                            label="烟气温度（℃）">
                    </el-table-column>

                    <el-table-column
                            align="center"
                            prop="humidity"
                            label="烟气湿度（%）">
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

  import {getAreaTree,getHistoryList, monitorList} from '../http/modules/index'

  export default {
    data() {
      return {
        loading: false,
        area_tree: [],
        area_tree_deep: 3,
        tree_props: {
          value: 'id',
          label: 'text',
          children: 'nodes'
        },
        tree_value: [],

        range_time: '',

        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },

        list: [],

        totalNum: 0,
        pageSize: 10,
        currentPage: 1,
      }
    },
    created() {

      let level = parseInt(localStorage.getItem('user.level'))
      this.area_tree_deep = level < 3 ? 3: level === 3 ? 2 : 1
    },

    mounted() {
      this.initData()
    },
    methods: {


      //初始化
      initData() {
        let that = this
        let tree_argc = {
          areaId: parseInt(localStorage.getItem('user.areaId'))
        }
        that.getTree(tree_argc)

        let argc = {
          pageNum: 1
        }
        that.getList(argc)
      },


      getTree(argc){
        let that = this

        getAreaTree(argc)
          .then(res => {
            if(res.code === 2000){
              that.area_tree = res.result
            }
          })
      },


      //获取一页列表数据
      getList(argc) {
        let that = this
        argc.pageSize = that.pageSize
        that.loading = true
        getHistoryList(argc)
          .then(res => {
            if (res.code === 2000) {
              that.currentPage = argc.pageNum
              that.totalNum = res.result.totalElements
              that.list = res.result.content
              that.loading = false
            }else{
              that.loading = false
            }
          })
      },


      //分页事件
      changePage(page) {

        let that = this
        let argc = {
          pageNum:page
        }


        if(that.range_time !== '' && that.range_time !== null){
          argc.startTiem = that.range_time[0]
          argc.endTime = that.range_time[1]
        }

        if(that.tree_value.length === that.area_tree_deep){
          argc.enterpriseId = that.tree_value[2]
        }
        that.getList(argc)
      },
      changeTree(obj){
        let that = this
        if(obj.length < that.area_tree_deep){
          that.tree_value = []
          return
        }

        let argc = {
          pageNum: 1,
          enterpriseId: obj[2].id
        }
        if(that.range_time !== '' && that.range_time !== null){
          argc.startTiem = that.range_time[0]
          argc.endTime = that.range_time[1]
        }

        that.getList(argc)
      },

      timeChange(range_time){
        let that = this

        let argc = {
          pageNum: 1,
        }

        if(range_time !== '' && range_time !== null){
          argc.startTiem = that.range_time[0]
          argc.endTime = that.range_time[1]
        }

        if(that.tree_value.length === that.area_tree_deep){
          argc.enterpriseId = that.tree_value[2]
        }
        that.getList(argc)
      }
    }
  }

</script>