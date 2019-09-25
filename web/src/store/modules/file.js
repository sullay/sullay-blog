import api from '../../service/modules'
const actions = {
  async uploadFile ({ commit }, options) {
    const result = await api.file.uploadFile(options)
    return result
  }
}

export default {
  actions
}
