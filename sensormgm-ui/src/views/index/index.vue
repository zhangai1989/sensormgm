<template>
  <div class="page">
    <div class="box-1-1">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/card1.png">
        <div class="charts-box box1">
          <ve-histogram :data="areaRankData"
                        :extend="areaExtend"
                        :title="areaRankTitle"
                        :grid="gridSetting"
                        :colors="['#0adf96']"
                        :settings="areaRankSetting"
                        :legend-visible="false" height="220px">
          </ve-histogram>
        </div>
      </div>
    </div>
    <div class="box-1-2">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/card.png">
        <div class="charts-box box2">
          <ve-histogram :data="enterpriseRankData"
                        :extend="enterpriseRankExtend"
                        :title="enterpriseRankTitle"
                        :grid="gridSetting1"
                        :colors="['#f96a8f']"
                        :settings="areaRankSetting"
                        :legend-visible="false" height="316px">
          </ve-histogram>
        </div>
      </div>
    </div>
    <div class="box-1-3">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/scroll.png">
        <div class="charts-box box3">
          <div class="charts-box-title">企业超标预警</div>

          <div class="scroll-content">
            <div v-for="(item,i) in enterpriseBeyond" class="scroll-item" :style="{transform: 'translateY(' + scrollY +'px)'}">{{item}}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="box-2-1">
      <div class="title-wap">
        <img src="../../assets/img/title.png">
        <div
          style="color: #ffffff;font-size: 36px;font-weight: bold;display: flex;align-items: center;justify-content: center;height: 112px;letter-spacing: 10px;">
          油烟监控系统
        </div>
      </div>
    </div>

    <!--时间-->
    <div class="box-2-2">
      <div class="time">{{nowTime}}</div>
    </div>

    <div class="box-upload-data">
      <div class="data-total">
        <div class="data-title">上传总数</div>
        <div class="data-value">
          <div v-for="(item,i) in all_num_list" :key="i" class="num-item">
            <img :src="item.img">
          </div>
        </div>
      </div>
      <div class="data-today">
        <div class="data-title">今日上传</div>
        <div class="data-value">
          <div v-for="(item,i) in today_num_list" :key="i" class="num-item">
            <img :src="item.img">
          </div>
        </div>
      </div>
    </div>

    <!--地图-->
    <div class="box-2-3">
      <div id="container" style="width:640px; height:464px;margin-left: 30px;margin-top: 18px">
        <el-amap vid="amapDemo" :zoom="zoom" :center="center" :events="events" class="amap-demo">
          <!--market_list-->
          <el-amap-marker v-if="radio1 === '油烟'" v-for="(item, i) in market_list" :key="i" :position="item.position" :events="item.events"
                          :icon="item.icon"></el-amap-marker>
          <el-amap-marker v-if="radio1 === '风机'" v-for="(item, i) in market_list" :key="i" :position="item.position" :events="item.events"
                          :icon="item.icon"></el-amap-marker>
          <el-amap-marker v-if="radio1 === '净化器'"  v-for="(item, i) in market_list" :key="i" :position="item.position" :events="item.events"
                          :icon="item.icon"></el-amap-marker>
          <el-amap-text  v-for="(item, i) in market_list" :key="i" v-if="showMapLabel" :position="item.position"  :offset="[10,10]" :text="item.name" ></el-amap-text>
        </el-amap>
      </div>
      <div class="map-select-box">
        <div :class="showMapLabel ? 'map-select-item map-select-item-active' : 'map-select-item'" @click="toogleLabel">
          名称
        </div>
        <el-radio-group v-model="radio1" @change="changeDevice" size="small">
          <el-radio-button label="油烟"></el-radio-button>
          <el-radio-button label="风机"></el-radio-button>
          <el-radio-button label="净化器"></el-radio-button>
        </el-radio-group>
      </div>


      <div class="map-tooltip1" v-show="radio1 === '油烟'">
        <div class="tooltip-item">
          <img :src="market_icon_list[0]">
          <div style="margin-left: 10px">离线</div>
        </div>
        <div class="tooltip-item">
          <img :src="market_icon_list[1]">
          <div style="margin-left: 10px">正常</div>
        </div>
        <div class="tooltip-item">
          <img :src="market_icon_list[2]">
          <div style="margin-left: 10px">预警</div>
        </div>
        <div class="tooltip-item">
          <img :src="market_icon_list[3]">
          <div style="margin-left: 10px">超标</div>
        </div>
      </div>

      <div class="map-tooltip1" v-show="radio1 === '风机'">
        <div class="tooltip-item">
          <img :src="market_icon_list[0]">
          <div style="margin-left: 10px">停止</div>
        </div>
        <div class="tooltip-item">
          <img :src="market_icon_list[1]">
          <div style="margin-left: 10px">运行</div>
        </div>
      </div>

      <div class="map-tooltip1" v-show="radio1 === '净化器'">
        <div class="tooltip-item">
          <img :src="market_icon_list[0]">
          <div style="margin-left: 10px">停止</div>
        </div>
        <div class="tooltip-item">
          <img :src="market_icon_list[1]">
          <div style="margin-left: 10px">运行</div>
        </div>
      </div>

      <div class="map-pop-box" v-show="mapPopVisible">
        <div class="map-pop-row" style="text-align: center">{{pop_obj.name}}</div>
        <div class="map-pop-row">{{pop_obj.lastUploadTime}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">油烟:&nbsp;</span>{{pop_obj.lampblack  === -10000 ? 'NULL' : pop_obj.lampblack + 'mg/m³'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">VOC:&nbsp;</span>{{pop_obj.temp === -10000 ? 'NULL' : pop_obj.temp + 'mg/m³'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">颗粒物:&nbsp;</span>{{pop_obj.humidity === -10000 ? 'NULL' : pop_obj.humidity + 'mg/m³'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">风机:&nbsp;</span>{{pop_obj.fanElec === -10000 ? 'NULL' : pop_obj.fanElec + 'ma'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">净化器:&nbsp;</span>{{pop_obj.purifierElec === -10000 ? 'NULL' : pop_obj.purifierElec + 'ma'}}</div>
      </div>

    </div>


    <div class="box-2-4">
      <div class="area-box">
        <div v-for="(item,i) in area_list" :key="i"
             @click="changeArea(item)"
             :class="item.id === currentAreaId ? 'area-item area-item-active':'area-item area-item-unactive'">{{item.name}}
        </div>
      </div>
    </div>


    <div class="box-3-1">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/card.png">
        <div class="charts-box">
          <div class="charts-box-title">监测点位统计</div>

          <div class="statistics-box">
            <div class="statistics-item">
              <div class="statistics-item-title" style="background-color: #409EFF">总数</div>
              <div class="statistics-item-value" style="color: #409EFF;">{{deviceStatistics.allCount}}</div>
            </div>

            <div class="statistics-item">
              <div class="statistics-item-title" style="background-color: #17a2b8">在线</div>
              <div class="statistics-item-value" style="color: #409EFF;">{{deviceStatistics.onlineCount}}</div>
            </div>

            <div class="statistics-item">
              <div class="statistics-item-title" style="background-color: #888">离线</div>
              <div class="statistics-item-value" style="color: #888;">{{deviceStatistics.offlineCount}}</div>
            </div>

            <div class="statistics-item">
              <div class="statistics-item-title" style="background-color: #67C23A">正常</div>
              <div class="statistics-item-value" style="color: #67C23A;">{{deviceStatistics.normalCount}}</div>
            </div>

            <div class="statistics-item">
              <div class="statistics-item-title" style="background-color: #E6A23C">预警</div>
              <div class="statistics-item-value" style="color: #E6A23C;">{{deviceStatistics.earlyWarningCount}}</div>
            </div>

            <div class="statistics-item">
              <div class="statistics-item-title" style="background-color: #F56C6C">超标</div>
              <div class="statistics-item-value" style="color: #F56C6C;">{{deviceStatistics.beyondCount}}</div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <div class="box-3-2">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/card2.png">
        <div class="charts-box">


          <ve-pie :data="pieData"
                  :colors="pieColor"
                  :settings="pieChartSettings"
                  :legend-visible="false"
                  :title="pieTitle" height="236px"></ve-pie>

        </div>
      </div>
    </div>

    <div class="box-3-3">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/scroll.png">
        <div class="charts-box box3">
          <div class="charts-box-title">最新数据</div>

          <div class="scroll-content">
            <div v-for="(item,i) in lastLogsData" class="scroll-item" :style="{transform: 'translateY(' + scrollY1 +'px)'}">{{item}}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="go-plam" @click="jump">
      进入后台
    </div>
  </div>
</template>

<style>
  .amap-icon img{
    width: 30px;
    height: 30px;
  }

  .amap-overlay-text-container{
    background-color: #155D74;
    color: #ffffff;
  }
</style>

<style scoped>
  .page {
    width: 1920px;
    height: 1080px;
    background-color: rgb(0, 14, 40);
    position: relative;

    background: url(../../assets/img/index_bg.png) no-repeat center;
    background-size: 100% 100%;

  }

  .go-plam{
    position: absolute;
    right: 80px;
    top: 30px;
    color: #fff;
    width: 140px;
    height: 40px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    background: url(../../assets/img/jump.png) no-repeat center;
    background-size: 100% 100%;
  }

  .box-upload-data{
    position: absolute;
    top: 180px;
    left: 640px;
    width: 638px;
    height: 110px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #ffffff;
  }

  .data-total{
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 336px;
    margin-right: 80px;
    height: 110px;
  }

  .data-today{
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 222px;
    height: 110px;
  }

  .data-title{
    height: 30px;
    font-size: 30px;
    line-height: 30px;
    margin-bottom: 20px;
  }

  .data-value{
    display: flex;
    align-items: center;
  }

  .num-item img{
    display: block;
    height: 60px;
    margin-right: 6px;
  }

  .num-item:last-child img{
    margin-right: 0;
  }


  .box-1-1 {
    position: absolute;
    top: 135px;
    left: 67px;
    width: 420px;
    height: 240px;
  }

  .box-1-2 {
    position: absolute;
    top: 445px;
    left: 67px;
    width: 420px;
    height: 340px;
  }

  .box-1-3 {
    position: absolute;
    top: 815px;
    left: 67px;
    width: 420px;
    height: 255px;
  }

  .box-2-1 {
    position: absolute;
    top: 0;
    height: 108px;
    width: 1920px;
  }

  .box-2-2{
    position: absolute;
    top: 120px;
    left: 570px;
    height: 60px;
    width: 780px;
  }

  .time{
    width: 780px;
    text-align: center;
    font-size: 28px;
    font-weight: bold;
    color: #43BBDD;
  }

  .box-2-3 {
    position: absolute;
    left: 610px;
    top: 350px;
    width: 700px;
    height: 500px;
    background: url(../../assets/img/map_bg.png) no-repeat center;
    background-size: 100% 100%;
  }

  .box-2-4 {
    position: absolute;
    left: 610px;
    top: 880px;
    width: 700px;
    height: 120px;
  }

  .box-3-1 {
    position: absolute;
    right: 67px;
    top: 135px;
    width: 420px;
    height: 295px;
  }

  .box-3-2 {
    position: absolute;
    top: 505px;
    right: 67px;
    width: 420px;
    height: 295px;
  }

  .box-3-3{
    position: absolute;
    top: 815px;
    right: 67px;
    width: 420px;
    height: 255px;
  }

  .charts-box-wap {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    position: relative;
  }

  .box-border {
    width: 420px;
    display: block;
  }

  .charts-box {
    position: absolute;
    left: 0;
    top: 0;
    width: 420px;
    height: 236px;
  }

  .box1{
    width: 330px;
    height: 227px;
    left: 50px;
    top: 15px;
  }

  .box2{
    width: 330px;
    height: 227px;
    left: 50px;
  }

  .box3{
    width: 330px;
    height: 227px;
    left: 50px;
  }

  .title-wap {
    height: 112px;
    width: 1115px;
    margin: 0 auto;
    position: relative;
  }

  .title-wap img {
    position: absolute;
    top: 0;
    left: 0;
    height: 112px;
    width: 1115px;
    display: block;
  }

  .map-select-box {
    position: absolute;
    display: flex;
    top: 18px;
    left: 30px;
  }

  .map-select-item {
    color: #606266;
    font-size: 12px;
    background: #FFF;
    border: 1px solid #DCDFE6;
    width: 56px;
    height: 35px;
    margin-right: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
    cursor: pointer;
    box-sizing: border-box;
  }

  .map-select-item-active {
    color: #FFF;
    font-size: 12px;
    background-color: #409EFF;
    border-color: #409EFF;
    width: 56px;
    height: 35px;
    margin-right: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
    cursor: pointer;
    box-sizing: border-box;
  }

  .charts-box-title {
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    margin-top: 15px;
  }

  .statistics-box {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    box-sizing: border-box;
    padding: 0 30px;
    margin-top: 50px;
  }

  .statistics-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100px;
    margin-right: 20px;
    margin-bottom: 10px;
  }

  .statistics-item-title {
    padding: 5px 10px;
    color: #fff;
    border-radius: 4px;
  }

  .statistics-item-value {
    margin-top: 15px;
    margin-left: 30px;
    font-size: 24px;
    font-weight: bold;
  }

  .area-box {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    width: 700px;
    height: 120px;
    box-sizing: border-box;
    padding: 0 20px;
  }

  .area-item {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 196px;
    height: 56px;
    border-radius: 6px;
    color: #fff;
    /*background-color: #155D74;*/
    margin-right: 30px;
    cursor: pointer;
	margin-bottom: 10px;
  }

  .area-item-unactive{
    background: url(../../assets/img/area.png) no-repeat center;
    background-size: 100% 100%;
  }

  .area-item-active {
    background: url(../../assets/img/area_active.png) no-repeat center;
    background-size: 100% 100%;
  }

  .area-item:nth-child(3n) {
    margin-right: 0;
  }

  .map-tooltip1{
    position: absolute;
    width: 90px;
    box-sizing: border-box;
    border: 1px solid #37E6EB;
    bottom: 18px;
    left: 30px;
    border-radius: 6px;
    padding: 10px;
    background-color: #fff;
  }

  .map-pop-box{
    position: absolute;
    width: 200px;
    box-sizing: border-box;
    right: 35px;
    top: 23px;
    background: rgba(0,0,0,0.5);
    height: auto;
    z-index: 800;
    padding: 20px 20px 10px;
    border-radius: 6px;
  }

  .map-pop-row{
    color: #fff;
    margin-bottom: 10px;
  }

  .map-pop-row-title{
    margin-right: 10px;
  }

  .tooltip-item{
    display: flex;
    margin-bottom: 10px;
  }

  .tooltip-item:last-child{
    margin-bottom: 0;
  }


  .tooltip-item img{
    width: 20px;
    height: 20px;
  }

  .scroll-content{
    margin-top: 30px;
    height: 150px;
    box-sizing: border-box;
    color: #fff;
    overflow: hidden;
  }

</style>

<script>
  import {areaMapList, indexData, getCount} from '@api/index'

  import market1 from '../../assets/img/market1.png'
  import market2 from '../../assets/img/market2.png'
  import market3 from '../../assets/img/market3.png'
  import market4 from '../../assets/img/market4.png'


  import d0 from '../../assets/img/0.png'
  import d1 from '../../assets/img/1.png'
  import d2 from '../../assets/img/2.png'
  import d3 from '../../assets/img/3.png'
  import d4 from '../../assets/img/4.png'
  import d5 from '../../assets/img/5.png'
  import d6 from '../../assets/img/6.png'
  import d7 from '../../assets/img/7.png'
  import d8 from '../../assets/img/8.png'
  import d9 from '../../assets/img/9.png'

  export default {
    data() {
      return {
        numList:[d0,d1,d2,d3,d4,d5,d6,d7,d8,d9],
        scrollY: 0,
        scrollY1: 0,
        market_icon_list: [market1,market2,market3,market4],
        radio1: '油烟',
        showMapLabel: true,
        area_list: [],
        currentAreaId: '',
        market_list: [],
        center: [104.07, 30.68],
        zoom: 14,

        all_count: 0,
        today_count: 0,

        all_num_list: [],
        today_num_list: [],
        mapPopVisible: false,
        pop_obj: {
          name: '',
		  lastUploadTime: '',
          lampblack: '',
          temp: '',
          humidity: '',
          fanElec: '',
          purifierElec: ''
        },
        events: {
          click: ()=>{
            this.mapPopVisible = false
          }
        },

        areaRankData: {
          columns: ['name', 'per'],
          rows: []
        },
        areaExtend: {
          series: {
            barWidth: 24,
          },

          xAxis: {
            axisLabel: {
              color: '#fff',
              fontSize: 14,
              rotate: 30
            }
          },
          yAxis: {
            axisLabel: {
              color: '#fff',
              fontSize: 14,
            }
          },
        },

        areaRankTitle: {
          text: '区域污染排名',
          textStyle: {
            color: '#fff',
            align: 'center',
          },
          left: 115,
          top: 0
        },

        gridSetting: {
          top: 60,
          bottom: 0,
          left:20
        },

        gridSetting1:{
          top: 90,
          bottom: 0,
          left: 20
        },

        areaRankSetting: {
          labelMap: {
            'per': '浓度',
          },
        },

        enterpriseRankData: {
          columns: ['name', 'per'],
          rows: []
        },

        enterpriseRankTitle: {
          text: '企业污染排名top-10',
          textStyle: {
            color: '#fff',
            align: 'center',
          },
          left: 75,
          top: 10
        },

        enterpriseRankExtend: {
          series: {
            barWidth: 18,
          },

          xAxis: {
            axisLabel: {
              color: '#fff',
              fontSize: 14,
              rotate: 30
            }
          },
          yAxis: {
            axisLabel: {
              color: '#fff',
              fontSize: 14,
            }
          },
        },

        pieTitle:{
          text: '当月超标占比',
          textStyle: {
            color: '#fff',
            align: 'center',
          },
          left: 155,
          top: 10
        },

        deviceStatistics: {
          onlineCount: 0,
          earlyWarningCount: 0,
          beyondCount: 0,
          normalCount: 0,
          allCount: 0,
          offlineCount: 0
        },


        pieColor: ['#67C23A', '#E6A23C', '#F56C6C'],
        pieData: {
          columns: ['name', 'per'],
          rows: []
        },
        pieChartSettings: {
          offsetY: 130,
          radius: 60
        },
        enterpriseBeyond: [],
        nowTime: '',
        lastLogsData: []
      }
    },
    created() {
    },

    beforeDestroy () {
      clearInterval(this.ScrollYSetInterval)
      clearInterval(this.ScrollYSetInterval1)
      clearInterval(this.timeInterval)
	  clearInterval(this.countInterval)
    },

    mounted() {
      this.init()
    },
    methods: {
      init() {
        let that = this
        that.timeInterval = setInterval(function () {
          that.getNowTime()
        },1000)

		that.interval1Count()

        areaMapList({})
          .then(res => {
            if (res.code === 2000) {
              that.area_list = res.result.map(item => {
                return {
                  id: item.id,
                  latitude: item.latitude,
                  longitude: item.longitude,
                  name: item.name,
                }
              })

              if (res.result.length) {
                that.currentAreaId = res.result[0].id
                that.center = [res.result[0].longitude,res.result[0].latitude]
                that.getInitDataById(res.result[0].id)
              }
            }
          })
      },


	  interval1Count(){
		let that = this

		that.countInterval = setInterval(function () {
          getCount({})
		  .then(res => {
			if (res.code === 2000) {
			that.all_count = res.result.allCount
              that.today_count = res.result.todayCount
              that.all_num_list = that.getNumList(res.result.allCount,9)
              that.today_num_list = that.getNumList(res.result.todayCount,6)
			}
		  })
        },1000)
	  },



      getInitDataById(areaId){
        let that = this
        let argc = {
          areaId: areaId
        }
        indexData(argc)
          .then(res => {
            if(res.code === 2000){
              console.log(res.result)
              //地图处理
              that.radio1 = '油烟'
              that.showMapLabel = true
              that.changeDeviceMap(res.result.deviceList,1)
              that.deviceList = res.result.deviceList

              //区域污染排名
              that.areaRankData = {
                columns: ['name', 'per'],
                rows: res.result.areaRank
              }

              //top10
              that.enterpriseRankData = {
                columns: ['name', 'per'],
                rows: res.result.enterpriseRank
              }

              //监测点位统计
              that.deviceStatistics = res.result.deviceStatistics

              //当月超标占比
              that.pieData = {
                columns: ['name', 'per'],
                rows: [
                  {
                    name: '正常',
                    per: res.result.deviceBeyondPer.normal,
                  },
                  {
                    name: '预警',
                    per: res.result.deviceBeyondPer.earlyWarning,
                  },
                  {
                    name: '超标',
                    per: res.result.deviceBeyondPer.beyond,
                  },
                ]
              }

              //企业超标预警
              that.enterpriseBeyond = res.result.enterpriseBeyond
              that.scrollYFunc(res.result.enterpriseBeyond.length)


              that.lastLogsData = res.result.lastLogsData
              that.scrollYFunc1(res.result.lastLogsData.length)

            }
          })
      },


      getNumList(num,len){
        let that = this
        let num_len = num.toString().length
        let arr = []
        for ( let i = 0; i < len - num_len;i ++){
          let obj = {
            img: that.numList[0],
            num: 0
          }
          arr.push(obj)
        }
        num.toString().split('').map(item => {
          let obj = {
            img: that.numList[parseInt(item)],
            num: parseInt(item)
          }
          arr.push(obj)
        })
        return arr
      },

      changeDeviceMap(deviceList,deviceType){
        let that = this
        that.market_list = []
        if(deviceType === 1){
          that.market_list = deviceList.map(item => {
            return {
              name: item.name,
              position: [item.longitude, item.latitude],
              icon: that.market_icon_list[parseInt(item.lampblackStatus)],
              events: {
                click: () => {
                  that.mapPopVisible = true
                  that.pop_obj = {
                    name: item.name,
					lastUploadTime: item.lastUploadTime,
                    lampblack: item.lampblack,
                    temp: item.temp,
                    humidity: item.humidity,
                    fanElec: item.fanElec,
                    purifierElec: item.purifierElec,
                  }
                }
              }
            }
          })
        } else if(deviceType === 2){
          that.market_list = deviceList.map(item => {
            return {
              name: item.name,
              position: [item.longitude, item.latitude],
              icon: item.fanStatus === 0 ? that.market_icon_list[0]: that.market_icon_list[1],
              events: {
                click: () => {
                  that.mapPopVisible = true
                  that.pop_obj = {
                    name: item.name,
					lastUploadTime: item.lastUploadTime,
                    lampblack: item.lampblack,
                    temp: item.temp,
                    humidity: item.humidity,
                    fanElec: item.fanElec,
                    purifierElec: item.purifierElec,
                  }
                }
              }
            }
          })
        }else{
          that.market_list = deviceList.map(item => {
            return {
              name: item.name,
              position: [item.longitude, item.latitude],
              icon: item.purifierStatus === 0 ?  that.market_icon_list[0]: that.market_icon_list[1],
              events: {
                click: () => {
                  that.mapPopVisible = true
                  that.pop_obj = {
                    name: item.name,
					lastUploadTime: item.lastUploadTime,
                    lampblack: item.lampblack,
                    temp: item.temp,
                    humidity: item.humidity,
                    fanElec: item.fanElec,
                    purifierElec: item.purifierElec,
                  }
                }
              }
            }
          })
        }
      },

      closeMapPop(){
        this.mapPopVisible = false
      },


      changeDevice(val){
        let that = this
        if(val === '油烟'){
          that.changeDeviceMap(that.deviceList,1)
        }else if(val ==='风机'){
          that.changeDeviceMap(that.deviceList,2)
        }else{
          that.changeDeviceMap(that.deviceList,3)
        }
      },

      changeArea(obj){
        let that = this
        that.currentAreaId = obj.id
        that.center = [obj.longitude,obj.latitude]
        that.getInitDataById(obj.id)
        that.mapPopVisible = false
      },

      scrollYFunc(len){
        let that = this
        that.scrollY = 0
        if(that.ScrollYSetInterval){
          clearInterval(that.ScrollYSetInterval)
        }
        if(len === 0){
          return
        }
         that.ScrollYSetInterval = setInterval(function () {
           if( that.scrollY > -40 * len){
             that.scrollY = that.scrollY - 5
           }else{
             that.scrollY = 80
           }
        },500)
      },

      scrollYFunc1(len){
        let that = this
        that.scrollY1 = 0
        if(that.ScrollYSetInterval1){
          clearInterval(that.ScrollYSetInterval1)
        }
        if(len === 0){
          return
        }
        that.ScrollYSetInterval1 = setInterval(function () {
          if( that.scrollY1 > -40 * len){
            that.scrollY1 = that.scrollY1 - 5
          }else{
            that.scrollY1 = 80
          }
        },500)
      },




      toogleLabel() {
        let that = this
        that.showMapLabel = !that.showMapLabel
      },

      jump(){
        let that = this
        that.$router.push({path: '/monitor'})
      },

      getNowTime(){
        let that = this
        let now = new Date()
        let year = now.getFullYear()
        let month = now.getMonth() + 1
        month = month < 10 ? '0' + month : month
        let date = now.getDate()
        date = date < 10 ? '0' + date : date
        let hours = now.getHours()
        hours = hours < 10 ? '0' + hours : hours
        let min = now.getMinutes()
        min = min < 10 ? '0' + min : min
        let second = now.getSeconds()
        second = second < 10 ? '0' + second : second
        that.nowTime = `${year}-${month}-${date}    ${hours}:${min}:${second}`
      }
    }
  }

</script>
