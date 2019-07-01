const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"local"',
  API_ROOT: '"https://jpp.qa.saas.sh-jiuye.com.cn"',
  LOGIN_URL: '"https://account.qa.saas.sh-jiuye.com.cn/jpplogin.html?app=19"',
  LOGOUT_URL: '"https://account.qa.saas.sh-jiuye.com.cn/logout.html?app=19"',
  DFS_URL: '"https://192.168.0.48:8443/"'
})
