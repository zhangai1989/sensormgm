
import baseUrl from './config'


export default {
  login: baseUrl + 'index/login',
  logout: baseUrl + 'index/loginOut',
  monitorList: baseUrl + 'TDevice/getPageListByCondition',
  areaMapList: baseUrl + 'TArea/getAreaList',
  indexData: baseUrl + 'homePage/getHomePageDataByAreaId',


  enterpriseList: baseUrl + 'TEnterprise/getPageListByCondition',
  addEnterprise: baseUrl + 'TEnterprise/insert',
  updateEnterprise: baseUrl + 'TEnterprise/update',
  deleteEnterprise: baseUrl + 'TEnterprise/deleteById',
  areaList: baseUrl + 'TArea/getAreaListBySession',

  addUser: baseUrl + 'TUser/insert',
  userList: baseUrl + 'TUser/getPageListByCondition2',
  updateUser: baseUrl + 'TUser/update',
  deleteUser: baseUrl + 'TUser/deleteById',
  EnterpriseAll: baseUrl + 'TEnterprise/getEnterpriseBySession',



  getAreaTree: baseUrl + 'TArea/getAreaTreeByAreaId',
  getHistoryList: baseUrl + 'TUploadLog/getPageListByCondition',

}
