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


// 企业管理列表
export function enterpriseList(obj) {
    return axios.post(API.enterpriseList, obj)
}
// 新增企业
export function addEnterprise(obj) {
    return axios.post(API.addEnterprise, obj)
}
// 修改企业信息
export function updateEnterprise(obj) {
    return axios.post(API.updateEnterprise, obj)
}
// 删除企业
export function deleteEnterprise(obj) {
    return axios.post(API.deleteEnterprise, obj)
}


// 用户管理列表
export function userList(obj) {
    return axios.post(API.userList, obj)
}
// 新增用户
export function addUser(obj) {
    return axios.post(API.addUser, obj)
}
// 修改用户信息
export function updateUser(obj) {
    return axios.post(API.updateUser, obj)
}
// 删除企业
export function deleteUser(obj) {
    return axios.post(API.deleteUser, obj)
}


// 获取区域
export function areaList(obj) {
    return axios.get(API.areaList, obj)
}
