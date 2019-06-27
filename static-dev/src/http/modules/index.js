/**
 * Created by worth-a-dime on 2018/4/2.
 */

import API from '../api'
import axios from '../http'


// 登录
export function Login(obj) {
  return axios.post(API.login, obj)
}


export function logout(obj) {
  return axios.post(API.logout, obj)
}