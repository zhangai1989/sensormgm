import Http from '@utils/Http'

// 企业分页查询
export const enterpriseList = (params) => Http.setPromise(`POST`, `/api/TEnterprise/getPageListByCondition`, params)
// 新增企业
export const addEnterprise = (params) => Http.setPromise(`PUT`, `/api/TEnterprise/insert`, params)
// 修改企业
export const updateEnterprise = (params) => Http.setPromise(`POST`, `/api/TEnterprise/update`, params)
// 删除企业
export const deleteEnterprise = (params) => Http.setPromise(`DELETE`, `/api/TEnterprise/deleteById`, params)
// 查询所有企业
export const allEnterprise = (params) => Http.setPromise(`GET`, `/api/TEnterprise/getEnterpriseBySession`, params)
