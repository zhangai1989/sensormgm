<template>
  <el-dialog :title="title"
             class="roleForm"
             :modal-append-to-body="false"
             :visible.sync="dialog.new"
             width="700px"
             @open="initForm">
    <div v-loading="!saveAble">
      <el-form ref="form"
               style="padding-left: 50px; border-bottom: 1px solid #E9EBF0"
               :model="editForm"
               label-width="80px">
        <el-form-item label="角色名称">
          <el-input size="small"
                    maxLength="50"
                    style="width: 300px"
                    placeholder="请输入角色名称"
                    v-model="editForm.name"></el-input>
        </el-form-item>
      </el-form>
      <p style="margin-left: 50px; margin-top: 10px;">设置功能权限</p>
      <table class="authTable">
        <tr>
          <th>
            功能名称
          </th>
          <th>
            类型
          </th>
          <th>
            <el-checkbox v-model="allSelect" @change="selectAll">全选</el-checkbox>
          </th>
        </tr>

        <template v-for="item in editForm.roleFunctions">
          <tr>
            <th class="treeName">
              {{ item.name }}
            </th>
            <th>
              {{ 'MENU' === item.type ? '菜单' : '按钮' }}
            </th>
            <th>
              <el-checkbox v-model="item.has" @change="selectParent"/>
            </th>
          </tr>
          <tr v-for="child in item.children">
            <th style="padding-left: 70px;" class="treeName">
              {{ child.name }}
            </th>
            <th>
              {{ 'MENU' === child.type ? '菜单' : '按钮' }}
            </th>
            <th>
              <el-checkbox v-model="child.has" @change="selectFun"/>
            </th>
          </tr>
        </template>
      </table>
    </div>
    <span slot="footer"
          class="dialog-footer">
      <el-button @click="dialog.new = false"
                 :disabled="!saveAble"
                 size="small">取 消</el-button>
      <el-button type="success"
                 @click="saveRole"
                 :disabled="!saveAble"
                 size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import axios from 'axios'
export default {
  props: ['dialog', 'editRoleId', 'editRoleName'],
  name: 'layout',
  data () {
    return {
      saveAble: true,
      title: '新增角色权限',
      allSelect: false,
      editForm: {
        roleId: null,
        name: null,
        roleFunctions: []
      }
    }
  },
  methods: {
    initForm () {
      this.allSelect = false
      this.editForm.roleFunctions = []
      this.editForm.roleId = this.editRoleId
      this.editForm.name = this.editRoleName
      this.title = this.editRoleId === null ? '新增角色权限' : '修改角色权限'
      // 查询角色功能
      let params = {roleId: this.editRoleId}
      this.saveAble = false
      axios.get('/api/v1/role/function', { params: params }).then((response) => {
        this.saveAble = true
        if (response.data.code === '') {
          this.editForm.roleFunctions = response.data.items
        } else {
          this.$message.error('查询角色功能异常:' + response.data.message)
        }
      }, (err) => {
        this.$message.success('查询角色功能异常:' + err.data.message)
        this.saveAble = true
      })
    },
    saveRole () {
      if (this.editForm.name.trim().length === 0) {
        this.$message.error('角色名称不能为空')
        return
      }
      this.saveAble = false
      axios.put('/api/v1/role', this.editForm).then((response) => {
        this.saveAble = true
        if (response.data.code === '') {
          this.dialog.new = false
          this.$message.success(this.editForm.roleId == null ? '角色权限创建成功' : '角色权限修改成功')
        } else {
          this.$message.error('查询角色功能异常:' + response.data.message)
        }
        this.$emit('refreshTable', 1)
      }, (err) => {
        this.saveAble = true
        this.$message.error((this.editForm.roleId == null ? '角色权限创建失败:' : '角色权限修改失败:') + err.data.message)
      })
    },
    selectAll (val) {
      this.editForm.roleFunctions.forEach(function (item, index, array) {
        item.has = val
        item.children.forEach(function (child, idx, arr) {
          child.has = val
        })
      })
    },
    selectParent (val) {
      // 父节点取消勾选子节点也取消
      this.editForm.roleFunctions.forEach(function (item, index, array) {
        item.children.forEach(function (child, idx, arr) {
          child.has = item.has
        })
      })
    },
    selectFun (val) {
      // 子节点选中父节点必须选中
      this.editForm.roleFunctions.forEach(function (item, index, array) {
        let childSelected = false
        item.children.forEach(function (child, idx, arr) {
          if (child.has) {
            childSelected = true
          }
        })
        if (childSelected) {
          item.has = true
        }
        // 子节点全部取消选中 父节点取消选中
        if (item.children.length > 0) {
          let selected = false
          item.children.forEach(function (child, idx, arr) {
            if (!selected && child.has) {
              selected = true
            }
          })
          item.has = selected
        }
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

  .roleForm .el-dialog__body {
    padding-left: 0px;
    padding-right: 0px;
  }
  .authTable {
    width: 100%;
    margin-top: 20px;
  }
  .authTable tr {
    height: 34px;
  }
  .treeName {
    font-family:MicrosoftYaHei;
    color:rgba(52,58,67,1);
  }
  .authTable tr:first-child {
    background-color: #D5D5D5;
    font-size:13px;
    font-family:MicrosoftYaHei;
    color: rgba(113,117,123,1);
    border:1px dashed rgb(167, 167, 167);
  }

  .authTable th {
    text-align: left;
  }
  .authTable th:first-child {
    width: 50%;
    padding-left: 50px;
  }
  .authTable th:nth-child(2), .authTable th:nth-child(3) {
    width: 25%;
  }
</style>
