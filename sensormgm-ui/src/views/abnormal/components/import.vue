<template>
  <el-dialog title="批量导入" :modal-append-to-body="false" :visible.sync="dialog.new" width="600px" class="import-dialog" ref="importDialog">
    <div class="jy-import_desc">
      <div>
        <i class="iconfont icon-note"></i>
        <div class="note-desc">
          <p>注意：</p>
          <p>1、请下载系统提供的模板，不允许修改模板格式以及Sheet名称</p>
          <p>2、导入数据时请仔细填写表格中的每一项数据，并严格按照制定的格式录入</p>
          <p>3、一次性导入的数据量不宜过大，对于大数据量建议分批次导入</p>
        </div>
      </div>
    </div>
    <div slot="footer">
      <div class="jy-import_option">
        <p>导入步骤：</p>
        <p>1、点击<el-button class="jy-download_excel">下载excel表格</el-button>，在Excel表格中录入数据</p>
        <p>2、选择Excel数据文件上传</p>
        <el-button icon="el-icon-edit" class="jy-default_button jy-upload__button">上传</el-button>
      </div>
    </div>
  </el-dialog>

</template>

<script>
export default {
  props: ['dialog'],
  name: 'detail',
  data () {
    return {
      dialogImageUrl: '',
      dialogVisible: false
    }
  },
  watch: {
    dialog: {
      handler (val) {
        if (val.new) {
          this.$nextTick(() => {
            let dialogBodyDom = document.querySelector('.import-dialog .el-dialog__body')
            let dialogFooterDom = document.querySelector('.import-dialog .el-dialog__footer')
            if (dialogBodyDom) dialogBodyDom.setAttribute('style', 'background:#fff9c3;')
            if (dialogFooterDom) dialogFooterDom.setAttribute('style', 'padding:0')
          })
        }
      },
      deep: true
    }
  },
  mounted () {

  },
  methods: {

  }
}
</script>

<style lang="less" scoped>
@import url("../../../assets/styles/index.less");

.jy-import_desc {
  width: 100%;
  height: 141px;
  p {
    font-size: 14px;
    color: #484d55;
    line-height: 22px;
  }
  .note-desc {
    float: right;
    margin-right: 17px;
  }
}

.jy-import_option {
  background: #ffffff;
  height: 191px;
  text-align: left;
  padding-left: 20px;
  padding-top: 12px;
  p {
    color: #190d0d;
    margin-bottom: 12px;
    &:first-child {
      color: #71757b;
    }
    color: #989ca8;
  }

  .jy-upload__button {
    margin-top: 12px;
  }

  .jy-download_excel {
    width: 110px;
    height: 24px;
    line-height: 24px;
    padding: 0;
    background: #6ab6f0;
    border-radius: 2px;
    color: #ffffff;
  }
}

.icon-note {
  font-size: 48px;
  float: left;
  margin-top: 23px;
}
</style>
