import Http from '@utils/Http'

// 历史数据查询
export const historyList = (params) => Http.setPromise(`POST`, `/api/TUploadLog/getPageListByCondition`, params)
// 预警分页插查询
export const warningList = (params) => Http.setPromise(`POST`, `/api/TUploadLog/getWarningPageListByCondition`, params)
// 超标分页查询
export const beyondList = (params) => Http.setPromise(`POST`, `/api/TUploadLog/getBeyondPageListByCondition`, params)
// 数据分析查询
export const analysisList = (params) => Http.setPromise(`POST`, `/api/TUploadLog/getDataAnalysisByCondition`, params)
