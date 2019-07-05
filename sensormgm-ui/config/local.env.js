const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"local"',
  API_ROOT: '"http://127.0.0.1:8080"',
  LOGIN_URL: '"http://127.0.0.1:8899/#/login"'
})
