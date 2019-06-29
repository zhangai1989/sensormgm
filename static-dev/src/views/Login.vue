<template>
    <div>


        <div class="login-box">
            <div class="title">油烟检测系统</div>
            <div class="login-input-box">
                <div class="input">
                    <!--el-icon-user-solid-->
                    <el-input v-model="userName" prefix-icon="el-icon-user-solid" style="width: 300px"
                              placeholder="请输入用户名"></el-input>
                </div>
            </div>
            <div class="login-input-box">
                <div class="input">
                    <el-input v-model="password" prefix-icon="el-icon-lock" style="width: 300px" type="password"
                              placeholder="请输入密码"></el-input>
                </div>
            </div>

            <div class="login-btn">
                <el-button type="primary" @click="login">登录</el-button>
            </div>
        </div>

    </div>
</template>

<style scoped>

    .login-box {
        margin: 20vh auto;
        padding: 30px;
        border: 1px solid #DAE3ED;
        box-shadow: 0 7px 27px 0 rgba(133, 169, 231, 0.51);
        border-radius: 10px;
        width: 600px;
    }

    .title {
        font-size: 24px;
        font-weight: bold;
        text-align: center;
        margin-bottom: 50px;
    }

    .login-input-box {
        display: flex;
        align-items: center;
        margin-bottom: 30px;
        justify-content: center;
    }

    .login-btn {
        display: flex;
        align-items: center;
        justify-content: center;
    }

</style>

<script>

  import {Login} from '../http/modules/index'

  export default {
    data() {
      return {
        userName: '',
        password: ''
      }
    },
    created() {
    },
    methods: {

      login() {
        let that = this

        let argc = {
          userName: that.userName,
          password: that.password
        }

        if (argc.userName === '') {
          that.$message({
            message: '请填写用户账号!',
            type: 'warning'
          })
          return
        }

        if (argc.password === '') {
          that.$message({
            message: '请填写登录密码!',
            type: 'warning'
          })
          return
        }

        Login(argc)
          .then(res => {
            if (res.code === 2000) {
              localStorage.setItem('username',res.result.loginName)
              that.$message({
                message: '登录成功！',
                type: 'success'
              })
              that.$router.push({path: '/home'})
            } else {
              that.$message({
                message: res.message,
                type: 'error'
              })
            }
          })
      }
    }
  }

</script>