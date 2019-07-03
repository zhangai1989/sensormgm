<template>
  <div class="page">
    <div class="box-1-1">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/border.png">
        <div class="charts-box">
          <ve-histogram :data="areaRankData"
                        :extend="areaExtend"
                        :title="areaRankTitle"
                        :grid="gridSetting"
                        :settings="areaRankSetting"
                        :legend-visible="false" height="236px">
          </ve-histogram>


        </div>
      </div>
    </div>
    <div class="box-1-2">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/border.png">
        <div class="charts-box">
          <ve-histogram :data="enterpriseRankData"
                        :extend="enterpriseRankExtend"
                        :title="enterpriseRankTitle"
                        :grid="gridSetting"
                        :settings="areaRankSetting"
                        :legend-visible="false" height="236px">
          </ve-histogram>
        </div>
      </div>
    </div>
    <div class="box-1-3">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/border.png">
        <div class="charts-box">
          <div class="charts-box-title">企业超标预警</div>

          <div class="scroll-content">
            <div v-for="(item,i) in enterpriseBeyond" class="scroll-item" :style="{transform: 'translateY(' + scrollY +'px)'}">{{item}}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="box-2-1">
      <div class="title-wap">
        <img src="../../assets/img/title_bg.png">
        <div
          style="color: #ffffff;font-size: 36px;font-weight: bold;display: flex;align-items: center;justify-content: center;height: 90px">
          油烟监控系统
        </div>
      </div>
    </div>

    <!--地图-->
    <div class="box-2-3">
      <div id="container" style="width:720px; height:499px;">
        <el-amap vid="amapDemo" :zoom="zoom" :center="center" class="amap-demo">
          <!--market_list-->
          <el-amap-marker v-if="radio1 === '油烟'" v-for="(item, i) in market_list" :key="i" :position="item.position"
                          :icon="item.icon"></el-amap-marker>
          <el-amap-marker v-if="radio1 === '风机'" v-for="(item, i) in market_list" :key="i" :position="item.position"
                          :icon="item.icon"></el-amap-marker>
          <el-amap-marker v-if="radio1 === '净化器'"  v-for="(item, i) in market_list" :key="i" :position="item.position"
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

    </div>


    <div class="box-2-4">
      <div class="area-box">
        <div v-for="(item,i) in area_list" :key="i"
             @click="changeArea(item)"
             :class="item.id === currentAreaId ? 'area-item area-item-active':'area-item'">{{item.name}}
        </div>
      </div>
    </div>


    <div class="box-3-1">
      <div class="charts-box-wap">
        <img class="box-border" src="../../assets/img/border.png">
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
        <img class="box-border" src="../../assets/img/border.png">
        <div class="charts-box">


          <ve-pie :data="pieData"
                  :colors="pieColor"
                  :settings="pieChartSettings"
                  :legend-visible="false"
                  :title="areaRankTitle" height="236px"></ve-pie>

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
  }

  .go-plam{
    position: absolute;
    right: 30px;
    top: 30px;
    color: #fff;
    background-color: #37E6EB;
    width: 100px;
    height: 40px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
  }


  .box-1-1 {
    position: absolute;
    top: 135px;
    left: 0;
    width: 505px;
    height: 295px;
  }

  .box-1-2 {
    position: absolute;
    top: 455px;
    left: 0;
    width: 505px;
    height: 295px;
  }

  .box-1-3 {
    position: absolute;
    top: 775px;
    left: 0;
    width: 505px;
    height: 295px;
  }

  .box-2-1 {
    position: absolute;
    top: 0;
    height: 108px;
    width: 1920px;
  }

  .box-2-3 {
    position: absolute;
    left: 596px;
    top: 294px;
    width: 721px;
    height: 499px;
    border: 3px solid rgb(55, 230, 235);
    border-radius: 6px;
  }

  .box-2-4 {
    position: absolute;
    left: 596px;
    top: 850px;
    width: 719px;
    height: 120px;
  }

  .box-3-1 {
    position: absolute;
    right: 0;
    top: 135px;
    width: 505px;
    height: 295px;
  }

  .box-3-2 {
    position: absolute;
    top: 455px;
    right: 0;
    width: 505px;
    height: 295px;
  }

  .charts-box-wap {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    position: relative;
  }

  .box-border {
    width: 505px;
    display: block;
  }

  .charts-box {
    position: absolute;
    left: 24px;
    top: 26px;
    width: 457px;
    height: 236px;
    background-color: rgba(21, 93, 116, 0.5);
    border: 1px solid #155D74;
    border-radius: 6px;
  }

  .title-wap {
    height: 108px;
    width: 623px;
    margin: 0 auto;
    position: relative;
  }

  .title-wap img {
    position: absolute;
    top: 0;
    left: 0;
    height: 108px;
    width: 623px;
    display: block;
  }

  .map-select-box {
    position: absolute;
    display: flex;
    top: 10px;
    left: 10px;
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
    color: #37E6EB;
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
    margin-top: 20px;
  }

  .statistics-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100px;
    margin-right: 30px;
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
    width: 719px;
    height: 120px;
    box-sizing: border-box;
    padding: 0 30px;
  }

  .area-item {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 150px;
    height: 40px;
    border-radius: 6px;
    color: #fff;
    background-color: #155D74;
    margin-right: 18px;
    cursor: pointer;
  }

  .area-item-active {
    background-color: #37E6EB;
  }

  .area-item:nth-child(4n) {
    margin-right: 0;
  }

  .map-tooltip1{
    position: absolute;
    width: 90px;
    box-sizing: border-box;
    border: 1px solid #37E6EB;
    bottom: 20px;
    left: 15px;
    border-radius: 6px;
    padding: 10px;
    background-color: #fff;
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
    padding: 0 20px;
    color: #fff;
    overflow: hidden;
  }

</style>

<script>
  import {areaMapList, indexData} from '@api/index'

  import market1 from '../../assets/img/market1.png'
  import market2 from '../../assets/img/market2.png'
  import market3 from '../../assets/img/market3.png'
  import market4 from '../../assets/img/market4.png'

  export default {
    data() {
      return {
        scrollY: 0,
        market_icon_list: [market1,market2,market3,market4],
        radio1: '油烟',
        showMapLabel: true,
        area_list: [],
        currentAreaId: '',
        market_list: [],
        center: [104.07, 30.68],
        zoom: 14,

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
            color: '#37E6EB',
            align: 'center',
          },
          left: 175,
          top: 10
        },

        gridSetting: {
          top: 40,
          bottom: 0,
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
            color: '#37E6EB',
            align: 'center',
          },
          left: 125,
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

        deviceStatistics: {
          onlineCount: 0,
          earlyWarningCount: 0,
          beyondCount: 0,
          normalCount: 0,
          allCount: 0
        },


        pieColor: ['#67C23A', '#E6A23C', '#F56C6C'],
        pieData: {
          columns: ['name', 'per'],
          rows: []
        },
        pieChartSettings: {
          offsetY: 130,
          radius: 70
        },
        enterpriseBeyond: [],
      }
    },
    created() {
    },

    beforeDestroy () {
      clearInterval(this.ScrollYSetInterval)
    },

    mounted() {
      this.init()
    },
    methods: {
      init() {

        let that = this

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

            }
          })
      },

      changeDeviceMap(deviceList,deviceType){
        let that = this
        that.market_list = []
        if(deviceType === 1){
          that.market_list = deviceList.map(item => {
            return {
              name: item.name,
              position: [item.longitude, item.latitude],
              icon: that.market_icon_list[parseInt(item.lampblackStatus)]
            }
          })
        } else if(deviceType === 2){
          that.market_list = deviceList.map(item => {
            return {
              name: item.name,
              position: [item.longitude, item.latitude],
              icon: item.fanStatus === 0 ? that.market_icon_list[0]: that.market_icon_list[1]
            }
          })
        }else{
          that.market_list = deviceList.map(item => {
            return {
              name: item.name,
              position: [item.longitude, item.latitude],
              icon: item.purifierStatus === 0 ?  that.market_icon_list[0]: that.market_icon_list[1]
            }
          })
        }
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


      toogleLabel() {
        let that = this
        that.showMapLabel = !that.showMapLabel
      },

      jump(){
        let that = this
        that.$router.push({path: '/monitor'})
      }
    }
  }

</script>
