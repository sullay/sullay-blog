import axiosConfig from './axiosConf'
import fileConfig from './fileConfig'

export default {
  install (Vue, options) {
    Vue.prototype.BaseUrl = axiosConfig.baseUrl
    Vue.prototype.fileUrl = fileConfig.baseUrl
    Vue.prototype.uploadFileUrl = fileConfig.uploadFileUrl
  }
}
