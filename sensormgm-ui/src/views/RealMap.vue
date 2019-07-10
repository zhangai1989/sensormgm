<template>
  <section class="layout width100 height100">
    <div class="height100" style="float: left; width: calc(100% - 300px)">
      <el-amap vid="amap" :center="center" :zoom="zoom">
        <el-amap-marker v-for="item in positions" :position="item"/>
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
            <el-col span="2">
              <i class="iconfont icon-wifi status"
                 :style="{color:item.deviceStatus === 'ONLINE' ? '#53ee33': '#d1d1d1'}"></i>
            </el-col>
            <el-col span="15">
              <span class="dname">{{ item.name }}</span>
            </el-col>
            <el-col span="7" class="val-box">
              <span class="dval" :class="{'normal':item.dataStatus === 1, 'beyond':item.dataStatus === 3, 'warning':item.dataStatus === 2}">0.090mg/m³</span>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
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
    color: #7a7a7a;
    font-family: '华文中宋', 'Times New Roman', 'Microsoft YaHei';
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
      center: [1, 2]
    }
  },
  created () {
    this.filterList = this.deviceList
  },

  mounted () {
    this.areaId = UserContext.getUserArea()
    this.loadArea()
    this.loadDevice()
  },
  methods: {
    // 初始化
    async loadArea () {
      let that = this
      const res = await areaList()
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
      if (res.code === 2000) {
        if (res.result && res.result.length > 0) {
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
          this.center = [res.result[0].longitude, res.result[0].latitude]
          res.result.forEach(function (item) {
            that.deviceList.push({
              deviceStatus: item.status,
              dataStatus: 1,
              name: item.name,
              longitude: item.longitude,
              latitude: item.latitude,
              lampblack: item.lampblack,
//              dataStatus: item.lampblackStatus
            })
            that.positions.push([23, 45])
          })
        }
      }
    },
    changeArea () {
      this.center = [16.0, 17.0]
    },
    selectDevice (item) {
      this.center = [item.longitude, item.latitude]
    }
  }
}

</script>
