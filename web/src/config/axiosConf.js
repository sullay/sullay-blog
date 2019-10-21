import axios from 'axios'
const baseUrl = 'http://127.0.0.1:7000/api'
// const baseUrl = 'http://192.168.0.139:7000'

axios.defaults.baseURL = baseUrl
export default {
  baseUrl
}
