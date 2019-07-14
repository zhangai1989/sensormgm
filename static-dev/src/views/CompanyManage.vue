<template>
    <div>
        <!--企业管理页面-->

        <div class="page">
            <div class="header">
                <div style="display: flex;align-items: center;">
                    <el-input
                            placeholder="请输入企业名称"
                            prefix-icon="el-icon-search"
                            size="small"
                            :clearable="true"
                            style="margin-right: 15px"
                            v-model.trim="search">
                    </el-input>
                    <el-button type="primary" size="small" @click="searchList">查询</el-button>
                </div>

                <div>
                    <el-button size="small" @click="openDialog(null)">新增</el-button>
                </div>
            </div>

            <div class="table-box">
                <el-table
                        v-loading="loading"
                        :data="list"
                        border
                        size="mini"
                        style="width: 100%;white-space: nowrap">
                    <el-table-column
                            fixed
                            show-overflow-tooltip
                            align="center"
                            prop="name"
                            label="企业名称"
                            min-width="180">
                    </el-table-column>

                    <el-table-column
                            align="center"
                            prop="code"
                            label="企业编码"
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
                            min-width="160">
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
                            v-if="allowEdit"
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
                    width="40%">

                <el-form :inline="true" :model="form" ref="editForm" :rules="rules" label-width="120px">
                    <el-form-item label="所属区域" prop="areaId">
                        <el-select size="small" v-model="form.areaId" placeholder="请选择" style="width: 200px" :disabled="!allowEdit">
                            <el-option
                                    v-for="item in areas"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="企业名称" prop="name">
                        <el-input size="small" v-model.trim="form.name"/>
                    </el-form-item>
                    <el-form-item label="企业地址" prop="address" >
                        <el-input size="small" v-model.trim="form.address"/>
                    </el-form-item>
                    <el-form-item label="经纬度" prop="pos" >
                        <el-input size="small" v-model.trim="form.pos" style="width: 200px">
                            <el-button slot="append" icon="el-icon-location-outline"></el-button>
                        </el-input>
                    </el-form-item>

                    <el-form-item label="企业负责人" prop="contact">
                        <el-input size="small" v-model.trim="form.contact"/>
                    </el-form-item>
                    <el-form-item label="企业负责人电话" prop="contactMobile">
                        <el-input size="small" v-model.trim="form.contactMobile" maxLength="11" oninput = "value=value.replace(/[^\d]/g,'')"/>
                    </el-form-item>

                    <el-form-item label="环保负责人" prop="envContact">
                        <el-input size="small" v-model="form.envContact"/>
                    </el-form-item>
                    <el-form-item label="环保负责人电话" prop="envContactMobile">
                        <el-input size="small" v-model="form.envContactMobile"/>
                    </el-form-item>
                </el-form>

                <span slot="footer">
                <el-button size="small" @click="editFlag = false" :disabled="!saveAble">取 消</el-button>
                <el-button size="small" type="primary" @click="submitForm('editForm')" :disabled="!saveAble">保 存</el-button>
              </span>
            </el-dialog>
        </div>
    </div>
</template>

<style>
    .el-table .cell {
        white-space: nowrap;
    }
</style>

<style scoped>

    .page {
        background: #FFFFFF;
        border: 1px solid #DAE3ED;
        border-radius: 6px;
        padding: 20px 24px;
        width: calc(100vw - 304px)
    }

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 20px;
    }

    .table-box {
        font-size: 12px;
    }

    .pagination {
        display: flex;
        justify-content: flex-end;
        margin-top: 22px;
        padding-bottom: 20px;
    }

</style>

<script>

  import {areaList, addEnterprise, updateEnterprise, deleteEnterprise, enterpriseList} from '../http/modules/index'
  import ElForm from "../../node_modules/element-ui/packages/form/src/form";
  import ElFormItem from "../../node_modules/element-ui/packages/form/src/form-item";
  import ElInput from "../../node_modules/element-ui/packages/input/src/input";

  export default {
    components: {
      ElInput,
      ElFormItem,
      ElForm},
    data() {
      return {
        loading: false,
        search: '',
        list: [],
        totalNum: 0,
        pageSize: 10,
        currentPage: 1,

        areas: [],

        form: {
          id: '',
          areaId: '',
          name: '',
          address: '',
          longitude: 0.0,
          latitude: 0.0,
          pos: '',
          contact: '',
          contactMobile: '',
          envContact: '',
          envContactMobile: ''
        },

        title: '新增企业',
        editFlag: false,
        allowEdit: false,

        rules: {
          areaId: [
            { required: true, message: '所属区域不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '企业名称不能为空', trigger: 'blur' }
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
    created() {

    },

    mounted() {
      this.initData()
    },
    methods: {
      //初始化
      initData() {

        let that = this

        areaList()
          .then(res => {
            if (res.code === 2000) {
              that.areas = res.result
            }
          })
        let level = localStorage.getItem('user.level')
        that.allowEdit = (level === '1' || level === '2')
        let argc = {
          pageNum: 1
        }
        that.getList(argc)
      },

      // 重置表单
      resetForm() {
        this.form.id = ''
        this.form.areaId = parseInt(localStorage.getItem('user.areaId'))
        this.form.name = ''
        this.form.address = ''
        this.form.longitude = 0.0
        this.form.latitude = 0.0
        this.form.pos = ''
        this.form.contact = ''
        this.form.contactMobile = ''
        this.form.envContact = ''
        this.form.envContactMobile = ''
      },
      openDialog(row) {
        this.resetForm()
        if(this.$refs['editForm']) {
          this.$refs['editForm'].resetFields()
        }
        if(row) {
          this.title = '修改企业信息'
          this.form.id = row.id
          this.form.areaId = row.areaId
          this.form.name = row.name
          this.form.address = row.address
          this.form.longitude = row.longitude
          this.form.latitude = row.latitude
          this.form.pos = row.pos
          this.form.contact = row.contact
          this.form.contactMobile = row.contactMobile
          this.form.envContact = row.envContact
          this.form.envContactMobile = row.envContactMobile
        }
        this.editFlag = true
      },


      //获取一页列表数据
      getList(argc) {
        let that = this
        argc.pageSize = that.pageSize
        argc.areaId = parseInt(localStorage.getItem('user.areaId'))
        that.loading = true
        enterpriseList(argc)
          .then(res => {
            if (res.code === 2000) {
              that.currentPage = argc.pageNum
              that.totalNum = res.result.totalElements
              res.result.content.forEach(function (item, index, array) {
                that.areas.forEach(function (it, idx, arr) {
                  if(it.id === item.areaId) {
                    item.area = it.name
                  }
                })
                item.pos = item.longitude + " / " + item.latitude
              })
              that.list = res.result.content
            }

            that.loading = false
          })
      },


      //分页事件
      changePage(page) {

        let that = this
        let argc = {
          pageNum:page
        }
        if (that.search !== '') {
          argc.enterprise = that.search
        }

        if(that.status !== '全部'){
          argc.status = that.status
        }

        that.getList(argc)
      },


      //搜索
      searchList() {
        let that = this
        if (that.search.length > 10) {
          that.$message.warning('搜索内容请勿太长!')
          return
        }

        let argc = {
          pageNum: 1,
        }

        if (that.search !== '') {
          argc.name = that.search
        }
        that.getList(argc)
      },

      submitForm(formName) {

        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.saveAble = false
            if(!this.form.id) {
              // 新增企业
              addEnterprise(this.form)
                .then(res => {
                  this.saveAble = true
                  if (res.code === 2000) {
                    this.$message.success('保存成功')
                    this.editFlag = false
                    this.searchList()
                  }else {
                    this.$message.error(res.message)
                  }
                })
            }else {
              // 修改企业
              updateEnterprise(this.form)
                .then(res => {
                  this.saveAble = true
                  if (res.code === 2000) {
                    this.$message.success('修改成功')
                    this.editFlag = false
                    this.searchList()
                  }
                })
            }
          } else {
            return false
          }
        })

      },

      confirmDelete(id) {
        this.$confirm('确定要删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          customClass: 'border-gray',
          type: 'success'
        }).then(() => {
          deleteEnterprise({id: id})
            .then(res => {
              if (res.code === 2000) {
                this.$message.success('删除成功')
                this.searchList()
              }
            })
        }).catch(() => {
          console.log('取消')
        })
      }
    }
  }

</script>