import Http from '@utils/Http'

// 用户分页查询
export const userList = (params) => Http.setPromise(`POST`, `/api/TUser/getPageListByCondition2`, params)
// 新增用户
export const addUser = (params) => Http.setPromise(`POST`, `/api/TUser/insert`, params)
// 修改用户
export const updateUser = (params) => Http.setPromise(`POST`, `/api/TUser/update`, params)
// 删除用户
export const deleteUser = (params) => Http.setPromise(`POST`, `/api/TUser/deleteById`, params)
