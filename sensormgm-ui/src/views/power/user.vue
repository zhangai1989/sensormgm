<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        用户管理
      </p>
      <div slot="btns">
        <el-button size="small"
                   @click="openEditDialog()">
          <i class="el-icon-plus fbold"></i> 新增账号
        </el-button>
      </div>
    </normal-bar>
    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <label>用户姓名</label>
          <el-input placeholder="请输入用户姓名"
                    v-model="queryCondition.cname"
                    clearable>
          </el-input>
        </li>
        <li class="flex fxmiddle">
          <label>手机号</label>
          <el-input placeholder="请输入用户手机号"
                    v-model="queryCondition.mobile"
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
          <el-table-column prop="cname"
                           label="用户姓名"
                           min-width="150"
                           show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="mobile"
                           label="手机号码"
                           min-width="150"
                           show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="email"
                           label="邮箱"
                           min-width="150"
                           show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="enabled"
                           label="状态"
                           min-width="150">
            <template slot-scope="scope">
              <label class="enabled" v-if="scope.row.enabled">
                启用
              </label>
              <label class="disabled" v-else>
                禁用
              </label>
            </template>
          </el-table-column>
          <el-table-column prop="createTime"
                           label="创建时间"
                           min-width="150"
                           :formatter="$root.formatDatetime">
          </el-table-column>
          <el-table-column label="操作"
                           width="100">
            <template slot-scope="scope">
              <el-button type="text" @click="openEditDialog(scope.row.userId)">编辑</el-button>
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
    <!-- 新增/修改帐号 -->
    <userForm :dialog="dialog" :editUserId="editUserId" @refreshTable="query(1)"></userForm>
  </section>
</template>

<script>
import axios from 'axios'
import { PAGE_SIZES, PAGE_SIZE } from '@configs'

const normalBar = () => import('@components/common/NormalBar')
const userForm = () => import('./compons/userForm')
export default {
  components: { normalBar, userForm },
  name: 'user',
  data () {
    return {
      dialog: {
        new: false
      },
      editUserId: null,
      input: '',
      queryCondition: {
        cname: '',
        mobile: ''
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
        cname: this.queryCondition.cname,
        mobile: this.queryCondition.mobile,
        pageNo: pageNo === 1 ? 1 : this.pageNo,
        pageSize: this.pageSize
      }
      this.queryAble = false
      axios.get('/api/v1/user', { params: params }).then((response) => {
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
    openEditDialog (userId) {
      this.dialog.new = true
      this.editUserId = userId
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
