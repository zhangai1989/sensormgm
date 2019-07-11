<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        企业管理
      </p>
      <div slot="btns">
        <el-button size="small"
                   @click="openDialog(null)">
          <i class="el-icon-plus fbold"></i> 新增企业
        </el-button>
      </div>
    </normal-bar>

    <div class="main-viewer">
      <ul class="bg-f8f f8f-set fxmiddle flex normal-set">
        <li class="flex fxmiddle">
          <el-select size="small" :clearable="true" v-model="condition.areaId" placeholder="请选择区域" style="width: 190px; line-height: 34px; height: 34px" @change="getList({pageNum: 1})">
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
            placeholder="请输入设备编号"
            size="small"
            :clearable="true"
            maxlength="30"
            v-model.trim="condition.code"
            style="width: 250px; line-height: 34px; height: 34px">
          </el-input>
        </li>
        <li class="flex fxmiddle">
          <el-input
            placeholder="请输入企业名称"
            size="small"
            :clearable="true"
            maxlength="30"
            v-model.trim="condition.name"
            style="width: 250px; line-height: 34px; height: 34px">
          </el-input>
        </li>
        <el-button type="success"
                   size="small"
                   @click="searchList">查 询
        </el-button>
      </ul>

      <div class="jy-content mt15">
        <el-table
          v-loading="loading"
          :data="list"
          border
          size="mini"
          class="jy-table">
          <el-table-column
            fixed
            show-overflow-tooltip
            prop="name"
            label="企业名称"
            min-width="180">
          </el-table-column>

          <el-table-column
            align="center"
            prop="code"
            label="设备编码"
            width="140">
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
            prop="address"
            label="地址"
            min-width="180">
          </el-table-column>

          <el-table-column
            show-overflow-tooltip
            align="center"
            prop="pos"
            label="经度/纬度"
            :readonly="true"
            min-width="180">
          </el-table-column>

          <el-table-column
            align="center"
            prop="contact"
            label="企业负责人"
            width="120">
          </el-table-column>

          <el-table-column
            align="center"
            prop="contactMobile"
            label="企业负责人电话"
            width="120">
          </el-table-column>

          <el-table-column
            align="center"
            prop="envContact"
            label="环境负责人"
            width="120">
          </el-table-column>

          <el-table-column
            align="center"
            label="环境负责人电话"
            prop="envContactMobile"
            width="120">
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
            min-width="150">
          </el-table-column>

          <el-table-column
            align="center"
            prop="modifierName"
            label="修改人"
            width="120">
          </el-table-column>

          <el-table-column
            align="center"
            label="修改时间"
            prop="modifyTime"
            min-width="150">
          </el-table-column>

          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            min-width="100">
            <template slot-scope="scope">
              <el-button type="text" @click="openDialog(scope.row)">修改</el-button>
              <el-button type="text" @click="confirmDelete(scope.row.id)">删除</el-button>
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
        width="710px">

        <el-form :inline="true" :model="form" ref="editForm" :rules="rules" label-width="120px">
          <el-form-item label="所属区域" prop="areaId">
            <el-select size="small" v-model="form.areaId" placeholder="请选择" :disabled="form.id != ''">
              <el-option
                v-for="item in areas"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="企业名称" prop="name">
            <el-input size="small" v-model.trim="form.name" placeholder="请输入企业名称"/>
          </el-form-item>
          <el-form-item label="设备编码" prop="code" v-if="'' === form.id">
            <el-input size="small" v-model.trim="form.code" placeholder="请输入设备编码"/>
          </el-form-item>
          <el-form-item label="企业地址" prop="address">
            <el-input size="small" v-model.trim="form.address" placeholder="请输入企业地址"/>
          </el-form-item>
          <el-form-item label="经/纬度" prop="pos">
            <el-input size="small" v-model.trim="form.longitude" style="width: 98px" placeholder="经度"/>
            <el-input size="small" v-model.trim="form.latitude" style="width: 98px" placeholder="纬度"/>
          </el-form-item>

          <el-form-item label="企业负责人" prop="contact">
            <el-input size="small" v-model.trim="form.contact" placeholder="请输入企业负责人"/>
          </el-form-item>
          <el-form-item label="企业负责人电话" prop="contactMobile">
            <el-input size="small" v-model.trim="form.contactMobile" maxLength="11"
                      oninput="value=value.replace(/[^\d]/g,'')" placeholder="请输入企业负责人电话"/>
          </el-form-item>

          <el-form-item label="环保负责人" prop="envContact">
            <el-input size="small" v-model="form.envContact" placeholder="请输入企业负责人"/>
          </el-form-item>
          <el-form-item label="环保负责人电话" prop="envContactMobile">
            <el-input size="small" v-model="form.envContactMobile" placeholder="请输入环保负责人电话"/>
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

<style scoped>
    .el-table .cell {
      white-space: nowrap;
    }
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
import {addEnterprise, updateEnterprise, deleteEnterprise, enterpriseList} from '@api/enterprise'
import { areaList } from '@api/area'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'enterprise',
  components: {
    normalBar
  },
  data () {
    return {
      loading: false,
      search: '',
      list: [],
      totalNum: 0,
      pageSize: 10,
      currentPage: 1,

      areas: [],
      condition: {
        areaId: '',
        code: '',
        name: ''
      },
      form: {
        id: '',
        areaId: '',
        name: '',
        code: '',
        address: '',
        longitude: '',
        latitude: '',
        contact: '',
        contactMobile: '',
        envContact: '',
        envContactMobile: '',
        deleteFlag: 0
      },

      title: '新增企业',
      editFlag: false,

      rules: {
        areaId: [
          { required: true, message: '所属区域不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '企业/设备编码不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '企业地址不能为空', trigger: 'blur' }
        ],
        contact: [
          { required: true, message: '企业负责人不能为空', trigger: 'blur' }
        ]
      },
      saveAble: true
    }
  },
  created () {
  },

  mounted () {
    this.initData()
  },
  methods: {
    // 初始化
    async initData () {
      let that = this
      const res = await areaList()
      if (res.code === 2000) {
        if (res.result) {
          res.result.forEach(function (item, index, array) {
            if (item.level === '1') {
              item.disabled = true
            } else {
              item.disabled = false
            }
            that.areas.push(item)
          })
        }
      }
      let userInfo = JSON.parse(localStorage.getItem('userInfo'))
      this.level = parseInt(userInfo.level)
    },
    openDialog (row) {
      if (this.$refs['editForm']) {
        this.$refs['editForm'].resetFields()
      }
      if (row) {
        this.title = '修改企业信息'
        this.form.id = row.id
        this.form.areaId = row.areaId
        this.form.name = row.name
        this.form.code = row.code
        this.form.address = row.address
        this.form.longitude = row.longitude
        this.form.latitude = row.latitude
        this.form.pos = row.pos
        this.form.contact = row.contact
        this.form.contactMobile = row.contactMobile
        this.form.envContact = row.envContact
        this.form.envContactMobile = row.envContactMobile
      } else {
        this.title = '新增企业'
        this.form.id = ''
        this.form.areaId = ''
        this.form.name = ''
        this.form.code = ''
        this.form.address = ''
        this.form.longitude = ''
        this.form.latitude = ''
        this.form.pos = ''
        this.form.contact = ''
        this.form.contactMobile = ''
        this.form.envContact = ''
        this.form.envContactMobile = ''
      }
      this.editFlag = true
    },

    // 获取一页列表数据
    getList (argc) {
      let that = this
      argc.pageSize = that.pageSize
      if (that.condition.areaId && that.condition.areaId !== '') {
        argc.areaId = that.condition.areaId
      }
      if (that.condition.code && that.condition.code !== '') {
        argc.code = that.condition.code
      }
      if (that.condition.name && that.condition.name !== '') {
        argc.name = that.condition.name
      }
      that.loading = true
      enterpriseList(argc)
        .then(res => {
          that.loading = false
          if (res.code === 2000) {
            that.currentPage = argc.pageNum
            that.totalNum = res.result.totalElements
            res.result.content.forEach(function (item, index, array) {
              that.areas.forEach(function (it, idx, arr) {
                if (it.id === item.areaId) {
                  item.area = it.name
                }
              })
              item.pos = item.longitude + '/ ' + item.latitude
            })
            that.list = res.result.content
          }
        })
        .catch(function () {
          that.loading = false
        })
    },

    // 分页事件
    changePage (page) {
      let that = this
      let argc = {
        pageNum: page
      }
      if (that.search !== '') {
        argc.enterprise = that.search
      }
      if (that.status !== '全部') {
        argc.status = that.status
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
            // 新增企业
            addEnterprise(that.form)
              .then(res => {
                that.saveAble = true
                if (res.code === 2000) {
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
            updateEnterprise(this.form)
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
        deleteEnterprise({id: id})
          .then(res => {
            if (res.code === 2000) {
              that.$message.success('删除成功')
              that.searchList()
            }
          })
      }).catch(() => {
        console.log('取消')
      })
    }
  }
}

</script>
