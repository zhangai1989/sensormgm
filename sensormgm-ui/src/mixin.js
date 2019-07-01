import moment from 'moment'
import download from 'downloadjs'

var Mixin = {
  methods: {
    _formatDatetime (value) {
      return value == null ? '' : moment(value).format('YYYY-MM-DD HH:mm:ss')
    },
    formatDatetime (row, column) {
      return this._formatDatetime(row[column.property])
    },
    __downloadFile (url, fileName, path, func) {
      if (url && path && path !== '') {
        fileName =
          fileName ||
          url
            .split('/')
            .pop()
            .split('?')[0]
        var ajax = new XMLHttpRequest()
        ajax.open('GET', url, true)
        ajax.responseType = 'blob'
        ajax.onload = function (e) {
          var userAgent = navigator.userAgent // 取得浏览器的userAgent字符串
          var isFFIE =
            userAgent.indexOf('Firefox') > -1 ||
            (!!window.ActiveXObject || 'ActiveXObject' in window) // 判断是否Firefox或IE浏览器
          // eslint-disable-next-line no-useless-call
          func && func.call(null, true)
          if (isFFIE) {
            let types = path.substring(path.lastIndexOf('.'), path.length)
            download(
              e.target.response,
              fileName + types,
              'application/octet-stream'
            )
          } else {
            download(e.target.response, fileName, 'application/octet-stream')
          }
        }
        setTimeout(function () {
          ajax.send()
        }, 0) // allows setting custom ajax headers using the return:
        return ajax
      } else {
        // eslint-disable-next-line no-useless-call
        func && func.call(null, false)
        this.$message({
          // eslint-disable-next-line no-undef
          message: res.message,
          type: 'error'
        })
      }
    }
  }
}
export default Mixin
