<template>
  <section class="layout width100 height100 flex fxcolumn">
    <normal-bar>
      <p slot="title">
        工单详情
      </p>
      <div slot="btns">
        <el-button-group>
          <el-button size="small">
            <i class="iconfont icon-return "></i> 返回上一步
          </el-button>
        </el-button-group>
        <el-button-group>
          <el-button size="small" class="ml10">
            <i class="iconfont icon-submit "></i> 提交九曳
          </el-button>
          <el-button size="small">
            <i class="iconfont icon-turndown"></i> 驳回
          </el-button>
        </el-button-group>
      </div>
    </normal-bar>
    <div class="main-viewer">
      <div class="jy-order_detail jy-detail">
        <div class="detail__block">
          <p class="jy-detail_item">
            <span class="jy-detail-label jy-detail-label__mr f12">工单号</span>
            <span class="jy-detail_text">123123123</span>
          </p>
          <p class="jy-detail_item">
            <span class="jy-detail-label jy-detail-label__mr f12">运单号</span>
            <span class="tran-no ">123123123</span>
            <i class="iconfont icon-transport"></i>
            <span class="fr jy-state_progress__text f16">受理中</span>
          </p>
          <div class="jy-hr"></div>
          <p class="jy-detail_item flex fx-baseline  mt10">
            <span class="jy-detail-label jy-detail-label__mr shrink0 f12">内容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span class="jy-detail_text fxwrap">123123123</span>
          </p>
        </div>
        <!-- 基本信息 -->
        <div class="detail__block mt10">
          <jy-line title="基本信息"></jy-line>
          <div class="jy-hr mt15 mb15"></div>
          <p class="jy-detail_item w49">
            <span class="jy-detail-label jy-detail-label__mr">工单号</span>
            <span class="jy-detail_text">123123123</span>
          </p>
          <p class="jy-detail_item w49">
            <span class="jy-detail-label jy-detail-label__mr ">运单号</span>
            <span class="jy-detail_text">123123123</span>
          </p>
          <p class="jy-detail_item w49">
            <span class="jy-detail-label jy-detail-label__mr ">&nbsp;&nbsp;&nbsp;状态</span>
            <span class="jy-detail_text ">123123123</span>
          </p>
          <p class="jy-detail_item w49">
            <span class="jy-detail-label jy-detail-label__mr ">受理人</span>
            <span class="jy-detail_text">章三</span>
          </p>
          <p class="jy-detail_item flex fx-baseline  ">
            <span class="jy-detail-label jy-detail-label__mr shrink0 ">&nbsp;&nbsp;&nbsp;附件</span>
            <span class="jy-detail_text fxwrap">
              <ul>
                <li>
                  <i class="iconfont icon-annex f12"></i>
                  <span class="jy-link">123123123123123123123</span>
                  <i class="iconfont icon-download f14"></i>
                </li>
              </ul>
            </span>
          </p>
        </div>
        <!-- 问题反馈 -->
        <div class="detail__block mt10">
          <jy-line title="问题反馈"></jy-line>
          <div class="jy-hr mt15 mb15"></div>

          <!-- 发表 -->
          <div v-show="isShowPublish">
            <el-input type="textarea" class="mb15" resize="none" rows="5" placeholder="请输入"></el-input>
            <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed" :file-list="fileList">
              <i class="iconfont icon-annex c-gray f12 ml10"></i>
              <span class="c-gray">上传附件</span>
            </el-upload>
            <el-button class="jy-default_button mt30 ml10">发表</el-button>
            <span class="c-gray cursor ml20">取消</span>
          </div>
          <div v-show="!isShowPublish">
            <el-button class="ml10" size="small">留言</el-button>
          </div>
        </div>
      </div>
      <!-- 操作记录 -->
      <div class="jy-log">
        <jy-line title="操作日志" class="ml21"></jy-line>
        <div class="jy-hr mt15 mb15 ml8"></div>
        <ul>
          <li class="log-detail" v-for="item in 10" :key="item">
            <div>
              <img src="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3194870919,2314083395&fm=27&gp=0.jpg">
            </div>
            <div>
              <p><span class="c-gray f14">安居强</span><span class="c-gray f12 ml10">10/7</span></p>
              <span class="c-text mt5">
                工单反馈。测试测试，客户说他最近要去火星一趟
              </span>
              <ul class="mt10">
                <li>
                  <i class="iconfont icon-annex f12 c-gray"></i>
                  <span class="jy-link ml5">123123123123123123123</span>
                </li>
              </ul>
            </div>
          </li>

        </ul>
      </div>
    </div>
  </section>
</template>

<script>

const normalBar = () => import('@components/common/NormalBar')
const JyLine = () => import('@components/common/JyLine')
export default {
  components: { normalBar, JyLine },
  name: 'layout',
  data () {
    return {
      fileList: [],
      isShowPublish: false
    }
  },
  computed: {

  },
  created () {
  },
  methods: {
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePreview (file) {
      console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>

<style lang="less" scoped>
@import url("../../assets/styles/index.less");

.f16 {
  font-size: 16px;
}
.icon-submit {
  color: #12b7b5;
}
.icon-turndown {
  color: #de1818;
}
.main-viewer {
  background: #edf0f5;
  flex-direction: row;
}
.jy-order_detail {
  box-sizing: border-box;
  margin-right: 15px;
  width: 80%;
  .detail__block {
    width: 100%;
    box-sizing: border-box;
    display: inline-block;
    background: #ffffff;
    padding: 12px 14px 13px 20px;
    .tran-no {
      color: #119061;
      font-size: 20px;
    }
    .icon-transport {
      margin-left: 4px;
    }
  }
}

::-webkit-scrollbar {
  display: none;
}

.jy-log {
  width: 30%;
  background: #ffffff;
  padding: 25px 28px 18px 0px;
  height: 90%;
  overflow-y: auto;
  .ml8 {
    margin-left: 8px;
  }
  .ml21 {
    margin-left: 21px;
  }
  ul {
    .log-detail {
      border-bottom: 3px dotted #f9f9f9;
      display: flex;
      padding-bottom: 23px;
      img {
        width: 26px;
        height: 26px;
        margin-left: 27px;
        margin-right: 12px;
      }
    }
  }
}

.w49 {
  display: inline-block;
  width: 49%;
}
</style>
