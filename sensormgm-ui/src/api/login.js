import Http from '@utils/Http'

// 登录
export const login = (params) => Http.setPromise(`GET`, `/api/index/login`, params)
