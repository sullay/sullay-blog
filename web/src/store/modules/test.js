import api from '../../service/modules'
const actions = {
  async test ({ commit }) {
    const result = await api.test.test()
    return result
  }
}

export default {
  actions
}
