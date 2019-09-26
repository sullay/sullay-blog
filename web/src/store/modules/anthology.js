import api from '../../service/modules'
const actions = {
  async createAnthology ({ commit }, options) {
    let key = localStorage.getItem('key')
    if (key) {
      options.passWord = key
    }
    const result = await api.anthology.createAnthology(options)
    return result
  },
  async getAnthologyList ({ commit }, { page = 0, size = 100 }) {
    const result = await api.anthology.getAnthologyList({ page, size })
    return result
  }
}

export default {
  actions
}
