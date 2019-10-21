import axios from 'axios'
const baseUrl = 'http://www.sullay.cn/api'
// const baseUrl = 'http://192.168.0.139:7000'

axios.defaults.baseURL = baseUrl
export default {
  baseUrl
}
