import api from '../../service'
import md5 from 'md5'
const actions = {
  async register ({ commit }, { userName, email, password }) {
    const result = await api.login.register({ userName, email, password: md5(password) })
    return result
  },
  async login ({ commit }, { userName, password }) {
    const result = await api.login.login({ userName, password: md5(password) })
    return result
  },
  async getUser ({ commit }) {
    const result = await api.login.getUser()
    return result
  }
}
export default {
  actions
}
