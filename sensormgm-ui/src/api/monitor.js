import Http from '@utils/Http'

// 实时监控分页查询
export const monitorList = (params) => Http.setPromise(`POST`, `/api/TDevice/getPageListByCondition`, params)
export const getHistoryList = (params) => Http.setPromise(`POST`, `/api/TUploadLog/getPageListByCondition`, params)
