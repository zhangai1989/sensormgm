module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://10.9.46.183:8080/api', //对应自己的接口
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          'api': ''
        }
      }
    }
  }
}