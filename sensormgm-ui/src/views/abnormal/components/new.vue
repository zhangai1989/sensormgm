<template>
  <el-dialog title="提报问题" :modal-append-to-body="false" :visible.sync="dialog.new" width="400px">
    <div>
      <el-form ref="form" :model="form" label-width="80px" class="normal-set">
        <el-form-item label="运单号">
          <el-input size="small" v-model="form.name" placeholder="请输入运单号"></el-input>
        </el-form-item>
        <el-form-item label="问题描述">
          <el-input type="textarea" placeholder="请输入内容" v-model="form.region" rows="5" maxlength="500" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-upload action="" class="jy-upload_img" list-type="picture-card" :auto-upload="false" :on-remove="handleRemove" limit="3">
            <i class="el-icon-plus"></i>
            <span class="fsize12">
              上传照片
              <br />
              (最多3张)
            </span>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.new = false" size="small">取消</el-button>
      <el-button type="success" @click="dialog.new = false" size="small">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: ['dialog'],
  name: 'layout',
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
