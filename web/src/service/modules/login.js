import axios from '../../plugins/axios'
const register = (options) => axios.post('/user/register', options)
const login = (options) => axios.post('/user/login', options)
const getUser = (options) => axios.get('/user/getCurrentUser', options)
export default {
  register,
  login,
  getUser
}
