import Http from '@utils/Http'

// 获取区域树
export const getAreaTree = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaTreeByAreaId`, params)
// 地图区域列表
export const areaMapList = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaList`, params)
// 获取区域列表
export const areaList = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaListBySession`, params)
