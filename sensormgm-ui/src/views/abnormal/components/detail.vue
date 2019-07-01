<template>
  <el-dialog title="报备详情" :modal-append-to-body="false" :visible.sync="dialog.new" width="400px">
    <div>
      <el-form ref="form" :model="form" label-width="80px" class="normal-set jy-detail">
        <el-form-item label="运单号" class="jy-detail_item">
          <span class="jy-detail_text">请输入运单号</span>
        </el-form-item>
        <el-form-item label="处理时间" class="jy-detail_item">
          <span class="jy-detail_text">问题描述</span>
        </el-form-item>
        <el-form-item label="处理结果" class="jy-detail_item">
          <span class="jy-detail_text">问题描述</span>
        </el-form-item>
        <el-form-item label="问题描述" class="jy-detail_item">
          <span class="jy-detail_text">问题描述</span>
        </el-form-item>
        <el-form-item label="附件" class="jy-detail_item">
          <ul class="jy-detail_img">
            <li>
              <img src="./../../../assets/img/face.jpg">
            </li>
            <li>
              <img src="./../../../assets/img/face.jpg">
            </li>
            <li>
              <img src="./../../../assets/img/face.jpg">
            </li>
          </ul>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </el-dialog>

</template>

<script>
export default {
  props: ['dialog'],
  name: 'detail',
  data () {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    }
  },
  created () {
  },
  methods: {
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleClose (done) {
    }
  },
  watch: {
    'dialog.new' (val) {
      console.log(val ? '模态框打开' : '模态框关闭')
    }
  }
}
</script>

<style lang="less" scoped>
@import url("../../../assets/styles/index.less");
</style>
