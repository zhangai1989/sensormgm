import Http from '@utils/Http'

let prefix = ''
if (process.env.NODE_ENV === 'local') {
  prefix = '/accountApi'
}

// auth init
export const authInit = (params) => Http.setPromise(`POST`, `${prefix}/init`, params)

// local env login
export const goLogin = (params) => Http.setPromise(`POST`, `${prefix}/gologin`, params)
