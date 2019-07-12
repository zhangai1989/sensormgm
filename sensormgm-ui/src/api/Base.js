import Http from '@utils/Http'

// menus
export const menus_level1 = (params) => Http.setPromise(`GET`, `../../static/json/menus_level1.json`, params)
export const menus_level2 = (params) => Http.setPromise(`GET`, `../../static/json/menus_level2.json`, params)
export const menus_level3 = (params) => Http.setPromise(`GET`, `../../static/json/menus_level3.json`, params)
export const menus_level4 = (params) => Http.setPromise(`GET`, `../../static/json/menus_level4.json`, params)

// user info
export const userInfo = (params) => Http.setPromise(`GET`, `/api/v1/user/me`, params)

// 获取用户
export const getAllUser = (params) => Http.setPromise(`GET`, `/api/v1/user/all`, params)
// 修改密码
export const modifyPswd = (params) => Http.setPromise(`POST`, `/api/TUser/modifyPswd`, params)

// 账号校验
export const accountRegx = (params) => Http.setPromise(`GET`, `/api/v1/accounts/regex`, params)
