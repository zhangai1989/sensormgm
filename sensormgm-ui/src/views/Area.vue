<template>
  <section class="layout width100 height100 flex fxcolumn">

    <normal-bar>
      <p slot="title">
        区域管理
      </p>
      <div slot="btns">
        <el-button size="small"
                   @click="dialogVisible = true">
          <i class="el-icon-plus fbold"></i> 新增区域
        </el-button>
      </div>
    </normal-bar>

    <div class="main-viewer">
      <el-row>
        <el-col :span="5">
          <el-tree
            :data="areaTree"
            node-key="id"
            :default-expanded-keys="expandedKeys"
            :expand-on-click-node="false"
            :props="defaultProps"
            @node-click="loadDetail">
          </el-tree>
        </el-col>
        <el-col :span="19">
          <div class="detail" v-if="showEdit">
            <el-form :inline="true" :model="form" ref="editForm" v-loading="!editAble" :rules="rules" label-width="100px">
              <el-form-item prop="name" label="区域名称">
                <el-input size="small" v-model.trim="form.name" maxlength="15"/>
              </el-form-item>

              <el-form-item prop="longitude" label="经度">
                <el-input size="small" v-model.trim="form.longitude"/>
              </el-form-item>

              <el-form-item prop="latitude" label="纬度">
                <el-input size="small" v-model.trim="form.latitude"/>
              </el-form-item>
            </el-form>
            <span slot="footer" style="margin-top: 10px">
              <el-button size="small" :disabled="!editAble" @click="deleteConfirm(form.id)">删 除</el-button>
              <el-button style="margin-left: 35px" size="small" type="primary" @click="updateArea" :disabled="!editAble">修 改</el-button>
           </span>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="420px">
      <el-form v-loading="!saveAble" :inline="true" :model="form" ref="addForm" :rules="rules" label-width="100px">
        <el-form-item prop="parentId" label="父级区域">
          <el-select size="small" v-model="form.parentId" placeholder="请选择">
            <el-option
              v-for="item in areas"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="name" label="区域名称">
          <el-input size="small" v-model.trim="form.name" maxlength="15"/>
        </el-form-item>

        <el-form-item prop="longitude" label="经度">
          <el-input size="small" v-model.trim="form.longitude"/>
        </el-form-item>

        <el-form-item prop="latitude" label="纬度">
          <el-input size="small" v-model.trim="form.latitude"/>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="small" @click="dialogVisible = false" :disabled="!saveAble">取 消</el-button>
        <el-button size="small" type="primary" :disabled="!saveAble" @click="saveArea">保 存</el-button>
     </span>
    </el-dialog>
  </section>
</template>

<script>
import { addArea, deleteArea, updateArea, getAreaTree, areaDetail } from '@api/area'
const normalBar = () => import('@components/common/NormalBar')

export default {
  name: 'history',
  components: {
    normalBar
  },
  data () {
    return {
      loading: false,
      userLevel: 0,
      areaTree: [],
      areas: [],
      defaultProps: {
        children: 'nodes',
        label: 'text'
      },
      expandedKeys: [],
      list: [],

      form: {
        level: 0,
        parentId: '',
        name: '',
        longitude: 0,
        latitude: 0
      },
      rules: {
        parentId: [
          { required: true, message: '父级区域不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '区域名称不能为空', trigger: 'blur' }
        ],
        longitude: [
          { required: true, message: '经度不能为空', trigger: 'blur' }
        ],
        latitude: [
          { required: true, message: '纬度不能为空', trigger: 'blur' }
        ]
      },
      title: '新增区域',
      dialogVisible: false,
      saveAble: true,
      editAble: true,
      showEdit: false
    }
  },
  created () {
  },

  mounted () {
    this.getTree()
  },
  methods: {
    async getTree () {
      let that = this
      let userInfo = JSON.parse(localStorage.getItem('userInfo'))
      that.userLevel = userInfo.level
      let treeProps = {
        areaId: parseInt(userInfo.areaId)
      }
      that.expandedKeys = []
      that.areas = []
      if (that.userLevel === 1) {
        that.areas.push({id: 0, name: '根'})
      }
      const res = await getAreaTree(treeProps)
      if (res.code === 2000) {
        that.areaTree = res.result
        if (res.result && res.result.length > 0) {
          res.result.forEach(function (item, index, array) {
            that.expandedKeys.push(item.id)
            that.areas.push({
              id: item.id, name: item.text
            })
            if (item.nodes && item.nodes.length > 0) {
              item.nodes.forEach(function (node, idx, arr) {
                that.expandedKeys.push(node.id)
              })
            }
          })
        }
      }
    },
    saveArea () {
      this.$refs['addForm'].validate((valid) => {
        if (valid) {
          this.form.level = (this.form.parentId === 0 ? 1 : 2)
          this.addArea()
        }
      })
    },
    async addArea () {
      this.saveAble = false
      this.form.deleteFlag = 0
      const res = await addArea(this.form)
      this.saveAble = true
      if (res === 2000) {
        this.$message.success('区域新增成功')
        this.dialogVisible = false
        this.getTree()
      }
    },
    async loadDetail (node) {
      this.resetForm()
      if (node.levelCode * 1 > 2) {
        this.showEdit = false
        return
      }
      this.showEdit = true
      this.editAble = false
      const res = await areaDetail({id: node.id})
      if (res.code === 2000) {
        this.editAble = true
        this.form.id = res.result.id
        this.form.name = res.result.name
        this.form.longitude = res.result.longitude
        this.form.latitude = res.result.latitude
      }
    },
    deleteConfirm (id) {
      let that = this
      that.$confirm('确定要删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        customClass: 'border-gray',
        type: 'success'
      }).then(() => {
        that.deleteArea(id)
      }).catch(() => {
        console.log('取消')
      })
    },
    async deleteArea (id) {
      let that = this
      const res = await deleteArea({id: id})
      if (res.code === 2000) {
        that.$message.success('删除成功')
        that.showEdit = false
        that.getTree()
      }
    },
    async updateArea () {
      let that = this
      const res = await updateArea({
        id: that.form.id,
        name: that.form.name,
        longitude: that.form.longitude,
        latitude: that.form.latitude
      })
      if (res.code === 2000) {
        that.$message.success('修改成功')
        that.getTree()
      }
    },
    resetForm () {
      this.form.level = 0
      this.form.parentId = ''
      this.form.name = ''
      this.form.longitude = ''
      this.form.latitude = ''
    }
  }
}

</script>

<style scoped>
  .el-input--small, .el-select--small {
    width: 250px;
  }
  .detail {
    text-align: center;
    padding-top: 30px;
    padding-bottom: 30px;
    background-color: #f9f9f9;
    width: 500px;
    box-shadow: 10px 10px 5px #888888;
  }
</style>
