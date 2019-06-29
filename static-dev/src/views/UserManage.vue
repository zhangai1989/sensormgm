<template>
    <div>
        <!--用户管理页面-->
        <div class="page">
            <div class="header">
                <div style="display: flex;align-items: center;">
                    <el-input
                            placeholder="请输入用户名"
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
                        :data="list"
                        border
                        size="mini"
                        style="width: 100%;white-space: nowrap">
                    <el-table-column
                            fixed
                            show-overflow-tooltip
                            align="center"
                            prop="loginName"
                            label="登录名"
                            min-width="180">
                    </el-table-column>

                    <el-table-column
                            align="center"
                            prop="cname"
                            label="真实姓名"
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
                        <el-select size="small" v-model="form.areaId" placeholder="请选择" style="width: 200px" :disabled="form.id">
                            <el-option
                                    v-for="item in areas"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="企业" prop="enterpriseId">
                        <el-input size="small" v-model.trim="form.enterpriseId"/>
                    </el-form-item>
                    <el-form-item label="登录名" prop="loginName">
                        <el-input size="small" v-model.trim="form.loginName" :disabled="form.id"/>
                    </el-form-item>
                    <el-form-item label="真实姓名" prop="cname" >
                        <el-input size="small" v-model.trim="form.cname"/>
                    </el-form-item>

                    <el-form-item label="手机号" prop="mobile">
                        <el-input size="small" v-model.trim="form.mobile" maxLength="11" oninput = "value=value.replace(/[^\d]/g,'')"/>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email">
                        <el-input size="small" v-model.trim="form.email"/>
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

    import {areaList, addUser, updateUser, deleteUser, userList} from '../http/modules/index'
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
                search: '',
                list: [],
                totalNum: 0,
                pageSize: 10,
                currentPage: 1,

                areas: [],

                form: {
                    id: '',
                    areaId: '',
                    level: '',
                    loginName: '',
                    cname: '',
                    mobile: '',
                    email: '',
                    password: '123456',
                    deleteFlag: 0
                },

                title: '新增用户',
                editFlag: false,
                allowEdit: false,

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
                areaList()
                    .then(res => {
                        if (res.code === 2000) {
                            this.areas = res.result
                        }
                    })
                let level = localStorage.getItem('user.level')
                this.allowEdit = level < 4
            },

            // 重置表单
            resetForm() {
                this.form.id = ''
                this.form.areaId = this.areas[0].id
                this.form.level = parseInt(localStorage.getItem('user.level')) + 1
                this.form.loginName = ''
                this.form.cname = ''
                this.form.mobile = ''
                this.form.email = ''
                this.form.password = '123456'
            },

            openDialog(row) {
                this.resetForm()
                if(this.$refs['editForm']) {
                    this.$refs['editForm'].resetFields()
                }
                if(row) {
                    this.title = '修改用户信息'
                    this.form.id = row.id
                    this.form.areaId = parseInt(row.areaId)
                    this.form.level = row.level
                    this.form.loginName = row.loginName
                    this.form.cname = row.cname
                    this.form.mobile = row.mobile
                    this.form.email = row.email
                    this.form.password = row.password
                }
                this.editFlag = true
            },

            //获取一页列表数据
            getList(argc) {
                let that = this
                argc.pageSize = that.pageSize
                userList(argc)
                    .then(res => {
                        if (res.code === 2000) {
                            that.currentPage = argc.pageNum
                            that.totalNum = res.result.totalElements
                            res.result.content.forEach(function (item, index, array) {
                                that.areas.forEach(function (it, idx, arr) {
                                    if(it.id === parseInt(item.areaId)) {
                                        item.area = it.name
                                    }
                                })
                            })
                            that.list = res.result.content
                        }
                    })
            },

            //分页事件
            changePage(page) {

                let that = this
                let argc = {
                    pageNum:page
                }
                if (that.search !== '') {
                    argc.enterprise = that.enterprise
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
                            addUser(this.form)
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
                            updateUser(this.form)
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
                    deleteUser({id: id})
                        .then(res => {
                            if (res.code === 2000) {
                                this.$message.success('删除成功')
                                this.searchList()
                            }
                        })
                }).catch(() => {
                    console.log('取消')
                })
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
            },
        }
    }

</script>