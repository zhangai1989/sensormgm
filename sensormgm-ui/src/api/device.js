import Http from '@utils/Http'

// 实时监控分页查询
export const deviceList = (params) => Http.setPromise(`GET`, `/api/TDevice/getDeviceList`, params)
