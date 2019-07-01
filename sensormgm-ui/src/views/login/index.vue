<template>
  <div class="page">

    <div class="login-bg">
      <img src="../../assets/img/login.png">
    </div>
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
        <el-button type="primary" style="width: 300px" @click="login">登录</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>

  .page {
    width: 100vw;
    height: 100vh;
    overflow: hidden;
  }

  .login-bg {
    width: 100vh;
  }

  .login-bg img {
    width: 100vw;
    /*min-height: 100vh;*/
  }

  .login-box {
    position: absolute;
    top: 30vh;
    right: 5vw;
    width: 500px;
    padding: 30px;
    background-color: #fff;
    border: 1px solid #DAE3ED;
    box-shadow: 0 7px 27px 0 rgba(133, 169, 231, 0.51);
    border-radius: 10px;
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
import { login } from '@api/login'
export default {
  data () {
    return {
      userName: '',
      password: ''
    }
  },
  created () {
  },
  methods: {
    async login () {
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
      let loadingInstance = that.$loading({
        lock: true,
        text: '登录中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      const res = await login(argc)
      loadingInstance.close()
      if (res.code === 2000) {
        localStorage.setItem('userInfo', JSON.stringify(res.result, null, 0))
        that.$router.push({path: '/monitor'})
      } else {
        that.$message({
          message: res.message,
          type: 'error'
        })
      }
    }
  }
}

</script>
