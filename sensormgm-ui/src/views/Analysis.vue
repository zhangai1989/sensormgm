<template>
  <div class="layout width100 height100 flex fxcolumn" style="text-align: center">
    <normal-bar>
      <p slot="title">
        数据分析
      </p>
    </normal-bar>

    <chart class="chart" :options="orgOptions" :auto-resize="false"></chart>
    <chart class="chart" :options="orgOptions" :auto-resize="false"></chart>
  </div>
</template>

<script>
const normalBar = () => import('@components/common/NormalBar')
export default {
  name: 'analysis',
  components: {
    normalBar
  },
  data () {
    return {
      orgOptions: {}
    }
  },
  mounted () {
    this.orgOptions = {
      title: {
        text: '折线图堆叠'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data:['邮件营销','联盟广告']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['周一','周二','周三','周四','周五','周六','周日']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name:'邮件营销',
          type:'line',
          stack: '总量',
          data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
          name:'联盟广告',
          type:'line',
          stack: '总量',
          data:[220, 182, 191, 234, 290, 330, 310]
        }
      ]
    }
  }
}
</script>

<style scoped>
  .chart {
    width: calc(100% - 182px);
    height: 46%;
  }
</style>
