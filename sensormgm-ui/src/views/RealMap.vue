<template>
  <section class="layout width100 height100">
    <div class="height100" style="float: left; width: calc(100% - 300px)">
      <el-amap vid="amap" :center="center" :zoom="zoom" :events="events">
        <el-amap-marker v-for="(item, i) in filterList" :key="i" :position="item.positions" :events="item.events"/>
        <el-amap-text  v-for="(item, i) in filterList" :key="i" :position="item.positions"  :offset="[0, -45]" :text="item.name" ></el-amap-text>
      </el-amap>
    </div>
    <div class="height100" style="float: left; width: 299px; border-left: 1px solid #28a745;">
      <el-select class="serach" size="small" v-model="areaId" placeholder="请选择区域" @change="changeArea">
        <el-option
          v-for="item in areas"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-input size="small" v-model.trim="search" class="serach" placeholder="企业名称"/>

      <div class="list">
        <div class="list-item" v-for="item in filterList" @click="selectDevice(item)">
          <el-row>
            <el-col :span=2>
              <i class="iconfont icon-wifi status"
                 :style="{color:item.deviceStatus === 'ONLINE' ? '#53ee33': '#d1d1d1'}"></i>
            </el-col>
            <el-col :span=15>
              <span class="dname" :class="{'selected-dname': selectedDevice === item.id, 'not-selected-dname': selectedDevice !== item.id}">{{ item.name }}</span>
            </el-col>
            <el-col :span=7 class="val-box">
              <span class="dval" :class="{'normal':item.dataStatus === 1, 'beyond':item.dataStatus === 3, 'warning':item.dataStatus === 2}">{{ item.lampblack }}mg/m³</span>
            </el-col>
          </el-row>
        </div>
      </div>

      <div class="map-pop-box" v-show="mapPopVisible">
        <div class="map-pop-row" style="text-align: center">{{popObj.name}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">详细地址:&nbsp;</span>{{popObj.address}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">企业负责人:&nbsp;</span>{{popObj.contact}}</div>
        <div v-if="popObj.envContact != '()'" class="map-pop-row"><span class="map-pop-row-title">环保负责人:&nbsp;</span>{{popObj.envContact }}</div>

        <div class="map-pop-row"><span class="map-pop-row-title">油烟:&nbsp;</span>{{popObj.lampblack  === -10000 ? 'NULL' : popObj.lampblack + 'mg/m³'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">VOC:&nbsp;</span>{{popObj.temp === -10000 ? 'NULL' : popObj.temp + 'mg/m³'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">颗粒物:&nbsp;</span>{{popObj.humidity === -10000 ? 'NULL' : popObj.humidity + 'mg/m³'}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">当前风机状态:&nbsp;</span>{{popObj.fanStatus}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">当前净化器状态:&nbsp;</span>{{popObj.purifierStatus}}</div>
        <div class="map-pop-row"><span class="map-pop-row-title">最后上传时间:&nbsp;</span>{{popObj.lastUploadTime}}</div>
      </div>
    </div>
  </section>
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
  .map-pop-box{
    position: absolute;
    width: 280px;
    box-sizing: border-box;
    right: 310px;
    top: 95px;
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

  .serach {
    width: 280px;
    margin-left: 10px;
    margin-top: 10px;
  }

  .list {
    margin-top: 10px;
    border-top: 1px solid #d9d9d9;
    height: calc(100% - 100px);
  }
  .list-item {
    line-height: 45px;
    border-bottom: 1px solid #d9d9d9;
    cursor: pointer;
  }
  .status {
    margin-left: 10px;
    margin-right: 10px;
  }

  .dname {
    margin-left: 10px;
    font-size: 16px;
    font-family: '华文中宋', 'Times New Roman', 'Microsoft YaHei';
  }
  .not-selected-dname {
    color: #7a7a7a;
  }
  .selected-dname {
    color: #474747;
  }
  .val-box {
    text-align: right;
    padding-right: 5px;
  }
  .dval {
    color: white;
    font-size: 8px;
    padding: 6px 5px 6px 5px;
    border-radius: 5px;
  }
  .normal {
    background-color: #28a745;
  }
  .beyond {
    background-color: #ffc107;
  }
  .warning {
    background-color: #dc3545;
  }
</style>

<script>
import { areaList } from '@api/area'
import { deviceList } from '@api/device'
import UserContext from '@utils/UserContext'
import ElInput from "../../node_modules/element-ui/packages/input/src/input";
import ElRow from "element-ui/packages/row/src/row";
import ElCol from "element-ui/packages/col/src/col";

export default {
  name: 'realMap',
  components: {
    ElCol,
    ElRow,
    ElInput
  },
  data () {
    return {
      areas: [],
      areaId: -1,
      search: '',
      deviceList: [],
      positions: [],
      filterList: [],
      zoom: 16,
      center: [1, 2],
      selectedDevice: -1,
      mapPopVisible: false,
      popObj: {
        name: '',
        address: '',
        contact: '',
        envContact: '',
        lampblack: '',
        temp: '',
        humidity: '',
        fanStatus: '',
        purifierStatus: '',
        lastUploadTime: ''
      },
      events: {
        click: ()=>{
          this.mapPopVisible = false
        }
      }
    }
  },
  created () {
    this.filterList = this.deviceList
  },

  mounted () {
    this.areaId = UserContext.getUserArea()
    this.loadArea()
    this.loadDevice()
    this.autoReload()
  },
  watch: {
    search (val) {
      this.filter(this.areaId, val)
    }
  },
  methods: {
    // 初始化
    async loadArea () {
      let that = this
      let userAreaName
      let allArea = JSON.parse(localStorage.getItem('allArea'))
      allArea.forEach(function (item) {
        if (item.id === that.areaId) {
          userAreaName = item.name
        }
      })
      that.areas.push({
        id: that.areaId,
        name: userAreaName
      })
      const res = await areaList()
      if (res.code === 2000) {
        if (res.result && res.result.length > 0) {
          that.center = [res.result[0].longitude, res.result[0].latitude]
          res.result.forEach(function (item) {
            that.areas.push({
              id: item.id,
              name: item.name
            })
          })
        }
      }
    },
    async loadDevice () {
      let that = this
      const res = await deviceList({})
      if (res.code === 2000) {
        if (res.result && res.result.length > 0) {
          that.center = [res.result[0].longitude, res.result[0].latitude]
          that.deviceList = []
          res.result.forEach(function (item) {
            that.deviceList.push({
              areaId: item.areaId,
              deviceStatus: item.status,
              name: item.name,
              longitude: item.longitude,
              latitude: item.latitude,
              lampblack: item.lampblack,
              temp: item.temp,
              humidity: item.humidity,
              dataStatus: item.lampblackStatus,
              address: item.address,
              contact: item.contact + '(' + item.contactMobile + ')',
              envContact: item.envContact + '(' + item.envContactMobile + ')',
              lastUploadTime: item.lastUploadTime,
              positions: [item.longitude, item.latitude],
              events: {
                click: () => {
                  that.mapPopVisible = true
                  that.popObj = {
                    name: item.name,
                    address: item.address,
                    contact: item.contact + '(' + item.contactMobile + ')',
                    envContact: item.envContact + '(' + item.envContactMobile + ')',
                    lampblack: item.lampblack,
                    temp: item.temp,
                    humidity: item.humidity,
                    fanStatus: 1 === item.fanStatus ? '开' : '关',
                    purifierStatus: 1 === item.purifierStatus ? '开' : '关',
                    lastUploadTime: item.lastUploadTime
                  }
                }
              }
            })
          })
          that.filter(that.areaId, that.search)
        }
      }
    },
    changeArea (val) {
      this.filter(val, this.search)
    },
    filter (areaId, name) {
      let that = this
      that.filterList = []
      if (that.deviceList && that.deviceList.length > 0) {
        that.deviceList.forEach(function (item) {
          if (item.areaId === parseInt(areaId) || areaId === UserContext.getUserArea()) {
            if ('' === that.search) {
              that.filterList.push(item)
            } else {
              if (item.name.indexOf(name) > -1) {
                that.filterList.push(item)
              }
            }
          }
        })
      }
    },
    selectDevice (item) {
      let that = this
      that.center = [item.longitude, item.latitude]
      that.selectedDevice = item.id
      that.mapPopVisible = true
      that.popObj = {
        name: item.name,
        address: item.address,
        contact: item.contact,
        envContact: item.envContact,
        lampblack: item.lampblack,
        temp: item.temp,
        humidity: item.humidity,
        fanStatus: 1 === item.fanStatus ? '开' : '关',
        purifierStatus: 1 === item.purifierStatus ? '开' : '关',
        lastUploadTime: item.lastUploadTime
      }
    },
    autoReload () {
      let that = this
      setInterval(() => {
        that.loadDevice()
      }, 10000)
    }
  }
}

</script>
