/**
 * Created by worth-a-dime on 2018/4/2.
 */

import API from '../api'
import axios from '../http'


// 登录
export function Login(obj) {
  return axios.get(API.login, obj)
}




//获取监控列表
export function monitorList(obj) {
  return axios.post(API.monitorList, obj)
}
