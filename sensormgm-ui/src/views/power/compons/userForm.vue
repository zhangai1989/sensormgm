<template>
  <el-dialog :title="title"
             :modal-append-to-body="false"
             :visible.sync="dialog.new"
             width="400px"
             @open="initForm">
    <div>
      <el-form ref="form"
               :model="editForm"
               v-loading="!saveAble"
               label-width="80px">
        <jy-line :title="'用户信息'"
                 :line="true"></jy-line>
        <div class="mt15"></div>
        <el-form-item label="用户姓名">
          <el-input size="small"
                    maxLength="60"
                    placeholder="请输入姓名"
                    v-model="editForm.cname"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input size="small"
                    maxLength="11"
                    placeholder="请输入手机号"
                    v-model="editForm.mobile"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input size="small"
                    maxLength="60"
                    placeholder="请输入邮箱"
                    v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item v-if="undefined === editUserId" label="登录密码">
          <el-input size="small"
                    maxLength="16"
                    placeholder="请输入登录密码"
                    type="password"
                    v-model="editForm.password"></el-input>
        </el-form-item>
        <jy-line :title="'权限设置'"
                 :line="true"></jy-line>
        <el-form-item label="状态" style="margin-top: 15px">
          <el-switch v-model="editForm.enabled" active-color="#56C695"/>
        </el-form-item>
        <el-form-item label="角色分配">
          <el-checkbox-group v-model="roleNames">
            <el-checkbox v-for="item in editForm.roles" :label="item.name" @change="chooseItem(item.roleId)"/>
          </el-checkbox-group>
        </el-form-item>

      </el-form>
    </div>
    <span slot="footer"
          class="dialog-footer">
      <el-button @click="dialog.new = false"
                 :disabled="!saveAble"
                 size="small">取 消</el-button>
      <el-button type="success"
                 @click="saveUser"
                 :disabled="!saveAble"
                 size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import axios from 'axios'

const jyLine = () => import('@components/common/JyLine')
export default {
  props: ['dialog', 'editUserId'],
  components: {
    jyLine },
  name: 'layout',
  data () {
    return {
      title: '新增用户',
      dialogVisible: false,
      editForm: {
        tenantId: null,
        userId: null,
        cname: null,
        mobile: null,
        email: null,
        password: null,
        enabled: true,
        roles: [],
        roleIds: []
      },
      roleNames: [],
      saveAble: true
    }
  },
  created () {
    this.queryAllRole()
  },
  methods: {
    initForm () {
      this.resetForm()
      if (undefined !== this.editUserId && this.editUserId !== null) {
        this.title = '修改用户'
        let params = {
          userId: this.editUserId
        }
        this.saveAble = false
        axios.get('/api/v1/user/userWithRoles', { params: params }).then((response) => {
          this.saveAble = true
          if (response.data.code === '') {
            let userVo = response.data.items
            this.editForm.tenantId = userVo.tenantId
            this.editForm.userId = userVo.userId
            this.editForm.cname = userVo.cname
            this.editForm.mobile = userVo.mobile
            this.editForm.email = userVo.email
            this.editForm.enabled = userVo.enabled
            let self = this
            if (undefined !== userVo.roles && userVo.roles !== null && userVo.roles.length > 0) {
              userVo.roles.forEach(function (item, index, array) {
                self.editForm.roles.push({
                  roleId: item.roleId,
                  name: item.name
                })
                if (item.has) {
                  self.editForm.roleIds.push(item.roleId)
                  self.roleNames.push(item.name)
                }
              })
            }
          } else {
            this.$message.error('查询角色异常:' + response.data.message)
          }
        }, (err) => {
          this.saveAble = true
          this.$message.error('查询角色异常:' + err.data.message)
        })
      } else {
        this.title = '新增用户'
        this.queryAllRole()
      }
    },
    resetForm () {
      this.editForm.tenantId = null
      this.editForm.userId = null
      this.editForm.cname = ''
      this.editForm.mobile = ''
      this.editForm.email = ''
      this.editForm.password = ''
      this.editForm.enabled = true
      this.editForm.roles = []
      this.editForm.roleIds = []
      this.roleNames = []
      this.saveAble = true
    },
    handleClose (done) {
    },
    queryAllRole () {
      let params = {
        pageNo: 1,
        pageSize: 1000
      }
      this.saveAble = false
      axios.get('/api/v1/role', { params: params }).then((response) => {
        this.saveAble = true
        if (response.data.code === '') {
          this.editForm.roles = response.data.items.list
        } else {
          this.$message.error('查询角色异常:' + response.data.message)
        }
      }, (err) => {
        this.saveAble = true
        this.$message.error('查询角色异常:' + err.data.message)
      })
    },
    chooseItem (id) {
      if (this.editForm.roleIds.indexOf(id) === -1) {
        this.editForm.roleIds.push(id)
      } else {
        this.editForm.roleIds.splice(this.editForm.roleIds.indexOf(id), 1)
      }
    },
    saveUser () {
      if (this.editForm.cname.trim().length === 0) {
        this.$message.error('用户姓名不能为空')
        return
      }
      if (this.editForm.mobile.trim().length === 0) {
        this.$message.error('手机号不能为空')
        return
      }
      if (this.editForm.email.trim().length === 0) {
        this.$message.error('邮箱不能为空')
        return
      }
      if (undefined === this.editUserId) {
        if (this.editForm.password === '') {
          this.$message.error('密码不能为空')
          return
        }
        if (this.editForm.password.length < 6) {
          this.$message.error('密码不能少于6位')
          return
        }
      }
      this.saveAble = false
      axios.put('/api/v1/user', this.editForm).then((response) => {
        this.saveAble = true
        if (response.data.code === '') {
          this.dialog.new = false
          this.$message.success(this.editForm.userId == null ? '用户创建成功' : '用户修改成功')
          this.$emit('refreshTable', 1)
        } else {
          this.$message.error((this.editForm.userId == null ? '用户创建失败:' : '用户修改失败:') + response.data.message)
        }
      }, (err) => {
        this.saveAble = true
        this.$message.error((this.editForm.userId == null ? '用户创建失败:' : '用户修改失败:') + err.data.message)
      })
    }
  }
}
</script>

<style lang="less">
.f8f-set {
  min-height: 66px;
  border: 1px solid #e9ebf0;
  border-radius: 2px;
  padding: 0 20px;
}
.normal-set {
  li {
    padding-right: 30px;
  }
  li > label {
    padding-right: 10px;
  }
  .el-input {
    width: 180px;
    height: 34px;

    input {
      font-size: 14px;
      height: 34px;
      padding-left: 10px;
    }
  }
}
.jy-table {
  th {
    background: #f9fafb;
    color: #343a43;
    font-size: 13px;
    font-weight: 800;
  }
  td {
    color: #71757b;
    font-size: 14px;
  }
}
</style>
