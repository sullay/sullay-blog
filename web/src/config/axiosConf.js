import axios from 'axios'
const baseUrl = 'http://www.sullay.cn/api'
// const baseUrl = 'http://182.61.37.108/blog-api'
// const baseUrl = 'http://192.168.0.116:7000/api'

axios.defaults.baseURL = baseUrl
export default {
  baseUrl
}
