<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        角色权限
      </p>
      <div slot="btns">
        <el-button size="small"
                   @click="openEditDialog()">
          <i class="el-icon-plus fbold"></i> 新增角色
        </el-button>
      </div>
    </normal-bar>
    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <label>角色名称</label>
          <el-input placeholder="请输入角色名称"
                    maxlength="50"
                    v-model="queryCondition.roleName"
                    clearable>
          </el-input>
        </li>
        <el-button type="success"
                   size="small"
                   :disabled="!queryAble"
                   @click="query(1)">查 询</el-button>
      </ul>
      <div class="jy-content mt15">
        <el-table ref="multipleTable"
                  :data="tableData"
                  v-loading="!queryAble"
                  tooltip-effect="dark"
                  style="width: 100%"
                  class="jy-table">
          <el-table-column prop="name"
                           label="角色名称"
                           min-width="150"
                           show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="crePerson"
                           label="创建人"
                           min-width="150"
                           show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="creTime"
                           label="创建时间"
                           min-width="150"
                           :formatter="$root.formatDatetime">
          </el-table-column>
          <el-table-column prop="modPerson"
                           label="修改人"
                           min-width="150"
                           show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="modTime"
                           label="最后修改时间"
                           min-width="150"
                           :formatter="$root.formatDatetime">
          </el-table-column>
          <el-table-column label="操作"
                           width="100">
            <template slot-scope="scope">
              <el-button type="text" @click="openEditDialog(scope.row)">编辑</el-button>
              <el-button type="text" @click="deleteRole(scope.row.roleId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination class="fade-up"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :page-sizes="pageSizes"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next"
                       :total="total">
        </el-pagination>
      </div>
    </div>
    <!-- 新增/修改角色 -->
    <role-form :dialog="dialog" :editRoleId="editRoleId" :editRoleName="editRoleName" @refreshTable="query(1)"></role-form>
  </section>
</template>

<script>
import axios from 'axios'
import { PAGE_SIZES, PAGE_SIZE } from '@configs'

const normalBar = () => import('@components/common/NormalBar')
const roleForm = () => import('./compons/roleForm')
export default {
  components: { normalBar, roleForm },
  name: 'role',
  data () {
    return {
      dialog: {
        new: false
      },
      editRoleId: null,
      editRoleName: null,
      input: '',
      queryCondition: {
        roleName: ''
      },
      queryAble: true,
      pageNo: 1,
      pageSize: PAGE_SIZE,
      total: 0,
      tableData: [],
      multipleSelection: []
    }
  },
  computed: {
    pageSizes () {
      return PAGE_SIZES
    }
  },
  created () {
  },
  methods: {
    query (pageNo) {
      this.tableData = []
      let params = {
        roleName: this.queryCondition.roleName,
        pageNo: pageNo === 1 ? 1 : this.pageNo,
        pageSize: this.pageSize
      }
      this.queryAble = false
      axios.get('/api/v1/role', { params: params }).then((response) => {
        this.queryAble = true
        if (response.data.code === '') {
          this.tableData = response.data.items.list
          this.total = response.data.items.total
        } else {
          this.$message.error(response.data.message)
        }
      }, (err) => {
        this.$message.error(err.data.message)
        this.queryAble = true
      })
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.pageNo = 1
      this.query(1)
    },
    handleCurrentChange (val) {
      this.pageNo = val
      this.query()
    },
    openEditDialog (roleVo) {
      this.dialog.new = true
      if (roleVo !== undefined && roleVo !== null) {
        this.editRoleId = roleVo.roleId
        this.editRoleName = roleVo.name
      } else {
        this.editRoleId = null
        this.editRoleName = null
      }
    },
    deleteRole (roleId) {
      this.$confirm('确定要删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        customClass: 'border-gray',
        type: 'success'
      }).then(() => {
        let params = {roleId: roleId}
        axios.delete('/api/v1/role/delete', {params: params}).then((response) => {
          if (response.data.code === '') {
            this.editForm.roles = response.data.items.list
          } else {
            this.$message.error('删除角色异常:' + response.data.message)
          }
          this.query(1)
        }, (err) => {
          this.query(1)
          this.$message.error('删除角色异常:' + err.data.message)
        })
      }).catch(() => {
        console.log('取消')
      })
    }
  }
}
</script>

<style lang="less">
.layout {
}
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

.enabled {
  padding: 4px 10px 4px 10px;
  line-height: 28px;
  background: rgba(229,242,253,1);
  border-radius: 4px;
  border: 1px solid rgba(187,219,255,1);
  font-size: 12px;
  font-family:MicrosoftYaHei;
  color: rgba(44,92,197,1);
}

.disabled {
  padding: 4px 10px 4px 10px;
  line-height: 28px;
  background: rgba(242,242,242,1);
  border-radius: 4px;
  border: 1px solid rgba(198,198,198,1);
  font-size: 12px;
  font-family:MicrosoftYaHei;
  color: rgba(86,86,86,1);;
}
</style>
