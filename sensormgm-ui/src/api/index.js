import Http from '@utils/Http'

// 地图区域列表
export const areaMapList = (params) => Http.setPromise(`GET`, `/api/TArea/getAreaList`, params)
// 首页数据
export const indexData = (params) => Http.setPromise(`GET`, `api/homePage/getHomePageDataByAreaId`, params)

