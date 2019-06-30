<template>
    <div class="page">
        <div class="box-1-1"></div>
        <div class="box-1-2"></div>
        <div class="box-1-3"></div>
        <div class="box-2-1"></div>
        <div class="box-2-2"></div>

        <!--地图-->
        <div class="box-2-3">
            <div id="container" style="width:718px; height:497px"></div>
            <div class="map-select-box">
                <div :class="showMapLabel ? 'map-select-item map-select-item-active' : 'map-select-item'" @click="toogleLabel">名称</div>
                <el-radio-group v-model="radio1" size="small">
                    <el-radio-button label="油烟"></el-radio-button>
                    <el-radio-button label="风机"></el-radio-button>
                    <el-radio-button label="净化器"></el-radio-button>
                </el-radio-group>
            </div>
        </div>
        <div class="box-2-4"></div>
        <div class="box-3-1"></div>
        <div class="box-3-2"></div>
    </div>
</template>

<style scoped>
    .page{
        width: 1920px;
        height: 1080px;
        background-color: #000000;
        background: url(../assets/img/index_bg.jpg) no-repeat center;
        background-size: 100% 100%;
    }

    .box-1-1{
        position: absolute;
        left: 59px;
        top: 181px;
        width: 471px;
        height: 247px;
    }
    .box-1-2{
        position: absolute;
        left: 59px;
        top: 465px;
        width: 471px;
        height: 247px;
    }

    .box-1-2{
        position: absolute;
        left: 59px;
        top: 748px;
        width: 471px;
        height: 247px;
    }

    .box-2-1{

    }
    .box-2-2{
        position: absolute;
        left: 660px;
        top: 140px;
        width: 600px;
        height: 95px;
    }
    .box-2-3{
        position: absolute;
        left: 596px;
        top: 294px;
        width: 719px;
        height: 497px;
    }

    .map-select-box{
        position: absolute;
        display: flex;
        top: 10px;
        left: 10px;
    }

    .map-select-item{
        color: #606266;
        font-size: 12px;
        background: #FFF;
        border: 1px solid #DCDFE6;
        width: 56px;
        height: 32px;
        margin-right: 5px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 4px;
        cursor: pointer;
    }

    .map-select-item-active{
        color: #FFF;
        font-size: 12px;
        background-color: #409EFF;
        border-color: #409EFF;
        width: 56px;
        height: 32px;
        margin-right: 5px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 4px;
        cursor: pointer;
    }

</style>

<script>
  import AMap from 'AMap'
  import {areaMapList,indexData} from '../http/modules/index'
  export default {
    data() {
      return {
        radio1: '油烟',
        showMapLabel: true,
        area_list: [],
        currentAreaId: '',
        market_list: []

      }
    },
    created() {
    },

    mounted(){
      this.init()
    },
    methods: {
      init () {

        let that = this

        areaMapList({})
          .then(res => {
            if(res.code === 2000){
              that.area_list = res.result.map(item => {
                return {
                  id: item.id,
                  latitude: item.latitude,
                  longitude: item.longitude,
                  name: item.name,
                }
              })

              if(res.result.length){
                that.currentAreaId = res.result[0].id

                let map = new AMap.Map('container', {
                  center: [res.result[0].longitude, res.result[0].latitude],
                  resizeEnable: true,
                  zoom: 10
                })

                let argc = {
                  areaId: res.result[0].id
                }

                indexData(argc)
                  .then(res => {
                    if(res.code === 2000){
                      console.log(res.result)
                      let market_list = []

                      let deviceList = [
                        {
                          name: '企业1111',
                          longitude: 104.07,
                          latitude: 30.68
                        },
                        {
                          name: '企业222',
                          longitude: 104.04,
                          latitude: 30.62
                        },
                        {
                          name: '企业333',
                          longitude: 104.02,
                          latitude: 30.64
                        }
                      ]

                      deviceList.map((item,i)=>{
                        let marker = new AMap.Marker({
                          position: new AMap.LngLat(item.longitude, item.latitude),
                          icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
                          imageSize: new AMap.Size(20, 30),
                          clickable: true
                        })
                        marker.setOffset(new AMap.Pixel(-9.5, -16.5))
                        marker.setTitle(item.name);
                        marker.setLabel({ offset: new AMap.Pixel(10, -25), content: item.name });
                        market_list.push(marker)
                      })

                      console.log(market_list)

                      map.add(market_list)
                    }
                  })
              }
            }
          })
      },


      toogleLabel(){
        let that = this
        that.showMapLabel = !that.showMapLabel
      },
    }
  }

</script>