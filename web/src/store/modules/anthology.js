import api from '../../service'
const actions = {
  async createAnthology ({ commit }, options) {
    const result = await api.anthology.createAnthology(options)
    return result
  },
  async updateAnthology ({ commit }, options) {
    const result = await api.anthology.updateAnthology(options)
    return result
  },
  async deleteAnthology ({ commit }, options) {
    const result = await api.anthology.deleteAnthology(options)
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
