import Http from '@utils/Http'

// 获取区域树
export const getAreaTree = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaTreeByAreaId`, params)
// 地图区域列表
export const areaMapList = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaList`, params)
// 获取区域列表
export const areaList = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaListBySession`, params)
// 区域明细查询
export const areaDetail = (params) => Http.setPromise(`GET`, `/api/TArea/getSingleById`, params)
// 新增区域
export const addArea = (params) => Http.setPromise(`POST`, `/api/TArea/insert`, params)
// 修改区域
export const updateArea = (params) => Http.setPromise(`POST`, `/api/TArea/update`, params)
// 删除区域
export const deleteArea = (params) => Http.setPromise(`POST`, `/api/TArea/deleteById`, params)
// 获取所有区域
export const allArea = (params) => Http.setPromise(`GET`, `/api/TArea/getAllList`, params)
