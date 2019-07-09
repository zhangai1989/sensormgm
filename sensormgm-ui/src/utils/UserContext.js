/**
 * Created by zhuangwei on 2018/1/2.
 */
var UserContext = {
  getUserInfo () {
    var str = localStorage.getItem('userInfo')
    if (str === undefined || str === null || str === '') {
      window.location.href = '/'
      return null
    }
    return JSON.parse(localStorage.getItem('userInfo'))
  },

  getUserLevel () {
    var userInfo = this.getUserInfo()
    return userInfo === null ? null : userInfo.level
  },

  getUserArea () {
    var userInfo = this.getUserInfo()
    return userInfo === null ? null : userInfo.areaId
  },

  getUserEnterprise () {
    var userInfo = this.getUserInfo()
    return userInfo === null ? null : userInfo.enterpriseId
  }
}
export default UserContext
