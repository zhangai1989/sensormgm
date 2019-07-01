import Http from '@utils/Http'

// menus
export const menus = (params) => Http.setPromise(`GET`, `../../static/json/menus.json`, params)

// user info
export const userInfo = (params) => Http.setPromise(`GET`, `/api/v1/user/me`, params)

// 获取用户
export const getAllUser = (params) => Http.setPromise(`GET`, `/api/v1/user/all`, params)
// 修改密码
export const modifyPswd = (params) => Http.setPromise(`POST`, `/api/v1/accounts/modifyPswd`, params)

// 账号校验
export const accountRegx = (params) => Http.setPromise(`GET`, `/api/v1/accounts/regex`, params)
