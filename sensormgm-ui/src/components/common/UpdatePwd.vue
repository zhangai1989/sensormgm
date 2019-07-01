<template>
  <el-dialog title="修改密码" :modal-append-to-body="false" :visible.sync="dialog" width="400px" :before-close="handleClose">
    <div v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" class="normal-set">
        <el-form-item label="原密码" prop="oldPswd">
          <el-input size="small" type="password" v-model="form.oldPswd" placeholder="请输入原密码" maxlength="16" minlength="8"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPswd">
          <el-input size="small" type="password" v-model="form.newPswd" placeholder="请输入新密码" maxlength="16" minlength="8"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confPwd">
           <el-input size="small" type="password" v-model="form.confPwd" placeholder="再次输入密码" maxlength="16" minlength="8"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose" size="small">取消</el-button>
      <el-button type="success" @click="handleConfirm('form')" size="small">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { modifyPswd, accountRegx } from '@api/Base'
export default {
  props: ['dialog'],
  name: 'layout',
  data () {
    return {
      loading: false,
      form: {
        oldPswd: '',
        newPswd: '',
        confPwd: ''
      },
      accountRegx: {},
      rules: {
        oldPswd: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPswd: [
          { required: true, validator: this.validateNewPswd, trigger: 'blur' }
        ],
        confPwd: [
          { required: true, validator: this.validateConfPwd, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    //    this.getAccountRegx()
  },
  watch: {
    dialog (newVal) {
      if (newVal) {
        this.form = {
          oldPswd: '',
          newPswd: '',
          confPwd: ''
        }
        setTimeout(() => {
          this.$refs['form'].resetFields()
        }, 0)
      }
    }
  },
  methods: {
    evil (fn) {
      let Fn = Function
      return new Fn('return ' + fn)()
    },
    validateNewPswd  (rule, value, callback) {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        let reg = this.evil(this.accountRegx.passwordRegExpression)
        if (value.match(reg) === null) {
          callback(new Error(this.accountRegx.passwordErrMsg))
        } else {
          callback()
        }
      }
    },
    validateConfPwd  (rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.newPswd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    },
    // 关闭
    handleClose (done) {
      this.$emit('update:dialog', false)
    },
    // 获取账号校验规则
    async getAccountRegx () {
      try {
        const res = await accountRegx()
        this.accountRegx.passwordMaxLen = res.items.passwordMaxLen
        this.accountRegx.passwordRegExpression = res.items.passwordRegExpression
        this.accountRegx.passwordErrMsg = res.items.passwordErrMsg
      } catch (error) {
        console.error(error)
      }
    },
    // 确定
    async handleConfirm (formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            this.loading = true
            const res = await modifyPswd(this.form)
            this.loading = false
            if (res.items) {
              this.$emit('update:dialog', false)
            } else {
              this.$message({ message: res.message, type: 'warning', showClose: true })
              return false
            }
          } catch (error) {
            this.loading = false
            console.error(error)
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
@import url("../../assets/styles/index.less");
</style>
