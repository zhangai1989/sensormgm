
import baseUrl from './config'


export default {
  login: baseUrl + 'index/login',
  monitorList: baseUrl + 'TDevice/getPageListByCondition',

  enterpriseList: baseUrl + 'TEnterprise/getPageListByCondition',
  addEnterprise: baseUrl + 'TEnterprise/insert',
  updateEnterprise: baseUrl + 'TEnterprise/update',
  deleteEnterprise: baseUrl + 'TEnterprise/deleteById',
  areaList: baseUrl + 'TArea/getAreaListBySession',

  addUser: baseUrl + 'TUser/insert',
  userList: baseUrl + 'TUser/getPageListByCondition',
  updateUser: baseUrl + 'TUser/update',
  deleteUser: baseUrl + 'TUser/deleteById'
}
