<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        用户管理
      </p>
      <div slot="btns">
        <el-button size="small"
                   @click="openDialog(null)">
          <i class="el-icon-plus fbold"></i> 新增用户
        </el-button>
      </div>
    </normal-bar>

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <el-select size="small" :clearable="true" v-model="condition.areaId" placeholder="请选择区域" :disabled="level > 2" @change="changeArea">
            <el-option
              v-for="item in areas"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </li>
        <li class="flex fxmiddle">
          <el-input
            placeholder="请输入用户名"
            size="small"
            :clearable="true"
            style="margin-right: 15px"
            v-model.trim="condition.name">
          </el-input>
        </li>
        <el-button type="success"
                   size="small"
                   :disabled="!queryAble"
                   @click="searchList">查 询
        </el-button>
      </ul>

      <div class="jy-content mt15">
        <el-table
          v-loading="!queryAble"
          :data="list"
          border
          size="mini"
          class="jy-table">
          <el-table-column
            fixed
            show-overflow-tooltip
            align="center"
            prop="loginName"
            label="登录名"
            min-width="120">
          </el-table-column>

          <el-table-column
            align="center"
            prop="cname"
            label="真实姓名"
            min-width="120">
          </el-table-column>
          <el-table-column
            show-overflow-tooltip
            align="center"
            prop="area"
            label="所属区域"
            min-width="180">
          </el-table-column>

          <el-table-column
            show-overflow-tooltip
            align="center"
            prop="enterpriseName"
            label="所属企业"
            min-width="180">
          </el-table-column>

          <el-table-column
            show-overflow-tooltip
            align="center"
            prop="mobile"
            label="手机号"
            min-width="180">
          </el-table-column>

          <el-table-column
            show-overflow-tooltip
            align="center"
            prop="email"
            label="邮箱"
            min-width="180">
          </el-table-column>

          <el-table-column
            align="center"
            prop="creatorName"
            label="创建人"
            width="120">
          </el-table-column>

          <el-table-column
            align="center"
            label="创建时间"
            prop="createTime"
            min-width="160">
          </el-table-column>

          <el-table-column
            label="操作"
            align="center"
            min-width="100">
            <template slot-scope="scope">
              <el-button v-if="scope.row.level > level" type="text" @click="openDialog(scope.row)">修改</el-button>
              <el-button v-if="scope.row.level > level" type="text" @click="confirmDelete(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>

        </el-table>
      </div>

      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next, jumper"
          :current-page.sync="currentPage"
          :pageSize="pageSize"
          @current-change="changePage"
          :total="totalNum">
        </el-pagination>
      </div>

      <el-dialog
        :title="title"
        :visible.sync="editFlag"
        :show-close="false"
        :close-on-click-modal="false"
        width="380px">

        <el-form :inline="true" :model="form" ref="editForm" :rules="rules" label-width="100px">
          <el-form-item label="所属区域" prop="areaId" v-if="level < 3">
            <el-select size="small" v-model="form.areaId" placeholder="请选择" :disabled="form.id !== ''" @change="changeArea">
              <el-option
                v-for="item in areas"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item v-if="form.level === 3 && form.id === ''" :disabled="level === 3 || form.enterpriseId" label="所属企业" prop="enterpriseId">
            <el-select size="small" v-model="form.enterpriseId" placeholder="请选择企业" clearable>
              <el-option
                v-for="item in areaEnterprises"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="登录名" prop="loginName">
            <el-input size="small" v-model.trim="form.loginName" :disabled="form.id !== ''"/>
          </el-form-item>
          <el-form-item label="真实姓名" prop="cname">
            <el-input size="small" v-model.trim="form.cname"/>
          </el-form-item>

          <el-form-item label="手机号" prop="mobile">
            <el-input size="small" v-model.trim="form.mobile" maxLength="11"
                      oninput="value=value.replace(/[^\d]/g,'')"/>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input size="small" v-model.trim="form.email"/>
          </el-form-item>

        </el-form>

        <span slot="footer">
                <el-button size="small" @click="editFlag = false" :disabled="!saveAble">取 消</el-button>
                <el-button size="small" type="primary" @click="submitForm('editForm')"
                           :disabled="!saveAble">保 存</el-button>
              </span>
      </el-dialog>
    </div>
  </section>
</template>

<style>
    .el-table .cell {
        white-space: nowrap;
    }
</style>

<style scoped>
    .pagination {
        display: flex;
        justify-content: flex-end;
        margin-top: 22px;
        padding-bottom: 20px;
    }

  .el-input--small, .el-select--small {
    width: 200px;
  }

</style>

<script>
import {addUser, updateUser, deleteUser, userList} from '@api/user'
import {areaEnterprise} from '@api/enterprise'
import { areaList } from '@api/area'
const normalBar = () => import('@components/common/NormalBar')
export default {
  name: 'user',
  components: {
    normalBar
  },
  data () {
    return {
      sysArea: new Map(),
      sysEnterprise: new Map(),
      level: 1,
      list: [],
      totalNum: 0,
      pageSize: 10,
      currentPage: 1,
      areas: [],
      areaEnterprises: [],
      condition: {
        areaId: '',
        enterpriseId: '',
        name: ''
      },
      form: {
        id: '',
        areaId: '',
        level: '',
        loginName: '',
        enterpriseId: '',
        cname: '',
        mobile: '',
        email: '',
        password: '123456',
        deleteFlag: 0
      },
      title: '新增用户',
      editFlag: false,
      rules: {
        areaId: [
          { required: true, message: '所属区域不能为空', trigger: 'blur' }
        ],
        loginName: [
          { required: true, message: '登录名不能为空', trigger: 'blur' }
        ],
        cname: [
          { required: true, message: '真实姓名不能为空', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '手机号不能为空', trigger: 'blur' },
          { validator: this.mobileCheck, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { validator: this.emailCheck, trigger: 'blur' }
        ]
      },
      queryAble: true,
      saveAble: true
    }
  },
  created () {},
  mounted () {
    this.initData()
    this.initSysArea()
    this.initSysEnterprise()
  },
  methods: {
    // 初始化
    async initData () {
      let that = this
      const res1 = await areaList()
      if (res1.code === 2000) {
        that.areas = res1.result
      }
      let userInfo = JSON.parse(localStorage.getItem('userInfo'))
      that.level = parseInt(userInfo.level)
      that.areaId = userInfo.areaId
    },
    initSysArea () {
      let that = this
      let allArea = JSON.parse(localStorage.getItem('allArea'))
      allArea.forEach(function (item) {
        that.sysArea.set(item.id, item.name)
      })
    },
    initSysEnterprise () {
      let that = this
      let allEnterprise = JSON.parse(localStorage.getItem('allEnterprise'))
      allEnterprise.forEach(function (item) {
        that.sysEnterprise.set(item.id, item.name)
      })
    },
    openDialog (row) {
      if (this.$refs['editForm']) {
        this.$refs['editForm'].resetFields()
      }
      if (row) {
        this.title = '修改用户信息'
        this.form.id = row.id
        this.form.areaId = parseInt(row.areaId)
        this.form.enterpriseId = parseInt(row.enterpriseId)
        this.form.level = row.level
        this.form.loginName = row.loginName
        this.form.cname = row.cname
        this.form.mobile = row.mobile
        this.form.email = row.email
        this.form.password = row.password
      } else {
        this.title = '新增用户'
        this.form.id = ''
        if (this.level < 3) {
          this.form.areaId = ''
        } else {
          this.form.areaId = areaId
        }
        this.form.enterpriseId = ''
        this.form.level = ''
        this.form.loginName = ''
        this.form.cname = ''
        this.form.mobile = ''
        this.form.email = ''
        this.form.password = '123456'
      }
      this.editFlag = true
    },

    // 获取一页列表数据
    getList (argc) {
      let that = this
      that.queryAble = false
      argc.pageSize = that.pageSize
      argc.areaId = that.condition.areaId
      argc.enterpriseId = that.condition.enterpriseId
      argc.name = that.condition.name
      userList(argc)
        .then(res => {
          if (res.code === 2000) {
            that.currentPage = argc.pageNum
            that.totalNum = res.result.totalElements
            if (res.result.content) {
              res.result.content.forEach(function (item, index, array) {
                item.area = that.sysArea.get(item.areaId)
                item.enterpriseName = that.sysEnterprise.get(item.enterpriseId)
              })
              that.list = res.result.content
            }
          }
          that.queryAble = true
        })
        .catch(function () {
          that.queryAble = true
        });
    },

    // 分页事件
    changePage (page) {
      let that = this
      let argc = {
        pageNum: page
      }
      that.getList(argc)
    },

    // 搜索
    searchList () {
      let that = this
      let argc = {
        pageNum: 1
      }
      that.getList(argc)
    },

    submitForm (formName) {
      let that = this
      that.$refs[formName].validate((valid) => {
        if (valid) {
          that.saveAble = false
          if (!that.form.id) {
            // 新增用户
            let arg = that.form
            // 如果选了企业 level=4
            if (arg.level === 3 && arg.enterpriseId && arg.enterpriseId.length > 0) {
              arg.level = 4
            }
            if (arg.enterpriseId === '') {
              arg.enterpriseId = null
            }
            addUser(arg)
              .then(res => {
                that.saveAble = true
                if (res && res.code === 2000) {
                  that.$message.success('保存成功')
                  that.editFlag = false
                  that.searchList()
                }
              })
              .catch(function () {
                that.saveAble = true
              })
          } else {
            // 修改企业
            updateUser(that.form)
              .then(res => {
                that.saveAble = true
                if (res.code === 2000) {
                  that.$message.success('修改成功')
                  that.editFlag = false
                  that.searchList()
                }
              })
              .catch(function () {
                that.saveAble = true
              })
          }
        } else {
          return false
        }
      })
    },

    confirmDelete (id) {
      let that = this
      that.$confirm('确定要删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        customClass: 'border-gray',
        type: 'success'
      }).then(() => {
        deleteUser({id: id})
          .then(res => {
            if (res.code === 2000) {
              that.$message.success('删除成功')
              that.searchList()
            }
          })
      }).catch(() => {
        console.log('取消')
      })
    },
    changeArea (val) {
      let that = this
      // 根据区域级别判断用户级别
      that.form.enterpriseId = ''
      that.areas.forEach(function (item) {
        if (val === item.id) {
          that.form.level = parseInt(item.level) + 1
          // 获取区域企业
          that.getAreaEnterprise(val)
        }
      })
    },
    changeQueryArea (areaId, enterpriseId) {
      if (!enterpriseId) {
        this.condition.areaId = areaId
      } else {
        this.condition.areaId = ''
      }
      this.condition.enterpriseId = enterpriseId
      this.getList({pageNum: 1})
    },
    async getAreaEnterprise (areaId) {
      let that = this
      that.areaEnterprises = []
      const res = await areaEnterprise({areaId: areaId})
      if (res.code === 2000) {
        that.areaEnterprises = res.result
      }
    },
    mobileCheck (rule, value, callback) {
      if (!value || value.length === 0) {
        callback()
      } else {
        if (value.match(/^1\d{10}$/) === null) {
          callback(new Error('手机号不合法'))
        } else {
          callback()
        }
      }
    },

    emailCheck (rule, value, callback) {
      if (!value || value.length === 0) {
        callback()
      } else {
        if (value.match(/(^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$)/i) === null) {
          callback(new Error('邮箱不合法'))
        } else {
          callback()
        }
      }
    }
  }
}

</script>
