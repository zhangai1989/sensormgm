import axios from 'axios'
import {Message, Loading} from 'element-ui'

let requestLoading
axios.interceptors.request.use(config => {
  // loading
  requestLoading = Loading.service({text: '加载中...'});
  // let urls = config.url.split('/')
  // //除登录外请求头添加token参数  getAfterSaleRecordsAndEqByPhone
  // if (urls[urls.length - 1] !== 'login' && urls[urls.length - 1] !== 'registerTmp' && urls[urls.length - 1] !== 'getAfterSaleRecordsAndEqByPhone' && urls[urls.length - 1] !== 'getRegisterVerificationCode') {
  //   let token = localStorage.getItem('token')
  //   if (token) {
  //     config.headers.Token = token
  //   }
  // }
  return config
}, error => {
  return Promise.reject(error)
})

axios.interceptors.response.use(response => {
  return response
}, error => {
  return Promise.resolve(error.response)
})

function checkStatus(response) {
  // loading
  // 如果http状态码正常，则直接返回数据

  if (response && (response.status === 200 || response.status === 304 || response.status === 400)) {
    return response
    // 如果不需要除了data之外的数据，可以直接 return response.data
  }
  // 异常状态下，把错误信息返回去
  return {
    status: -404,
    msg: '网络异常'
  }
}

function checkCode(res) {
  // 如果code异常(这里已经包括网络错误，服务器错误，后端抛出的错误)，可以弹出一个错误提示，告诉用户
  if (requestLoading) {
    requestLoading.close()
  }
  if (res.status === -404) {
    Message({
      showClose: true,
      message: '网络异常！请稍后重试！',
      type: 'error'
    })
    return {
      code: 500,
      message: '网络异常'
    }
  }


  //登录过期拦截
  if(res.data.code === 3000){
    window.location.href = "/"
  }

  //下载请求返回完整的 res
  // let _urls = res.config.url.split('/')
  // if(_urls[_urls.length - 1] === 'exportExcel'||_urls[_urls.length - 1] ===  'exportAgainById' || _urls[_urls.length - 1] === 'download'){
  //   return res
  // }
  return res.data
}

export default {
  post(url, params) {
    let data = params || {}
    let headers = {
    }
    return axios.post(url, data, headers)
      .then(function (response) {
        return checkStatus(response)
      })
      .then(res => {
        return checkCode(res)
      })
      .catch(function (err) {
        console.log(err)
      })
  },
  put(url, params) {
    let data = params || {}
    let headers = {
    }
    return axios.put(url, data, headers)
      .then(function (response) {
        return checkStatus(response)
      })
      .then(res => {
        return checkCode(res)
      })
      .catch(function (err) {
        console.log(err)
      })
  },
  get(url, params) {
    let data = params || {}

    let headers = {
    }
    return axios.get(url, {params: data}, headers)
      .then(function (response) {
        return checkStatus(response)
      })
      .then(res => {
        return checkCode(res)
      })
      .catch(function (err) {
        console.log(err)
      })
  },
  login(url, params) {
    let data = params || {}
    let headers = {
    }
    return axios.get(url, {params: data}, headers)
      .then(function (response) {
        return checkStatus(response)
      })
      .then(res => {
        return checkCode(res)
      })
      .catch(function (err) {
        console.log(err)
      })
  },

  download(url, params) {
    let data = params || {}
    data.userId = localStorage.getItem('userId')
    return axios({
      method: 'POST',
      url: url,
      headers: {'Content-Type': 'application/json'},
      data: data,
      responseType: 'blob'
    })
      .then(response => {
        return checkStatus(response)
      })
      .then(res => {
        return checkCode(res)
      })
      .catch(function (err) {
        console.log(err)
      })
  },


  delete(url, params) {
    let data = params || {}
    let headers = {
    }
    return axios.delete(url, {params: data}, headers)
      .then(function (response) {
        return checkStatus(response)
      })
      .then(res => {
        return checkCode(res)
      })
      .catch(function (err) {
        console.log(err)
      })
  }
}
