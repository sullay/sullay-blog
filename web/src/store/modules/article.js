import api from '../../service/modules'
const actions = {
  async createArticle ({ commit }, options) {
    const result = await api.article.createArticle(options)
    return result
  },
  async updateArticle ({ commit }, options) {
    const result = await api.article.updateArticle(options)
    return result
  },
  async deleteArticle ({ commit }, options) {
    const result = await api.article.deleteArticle(options)
    return result
  },
  async getArticleList ({ commit }, { AnthologyId, page = 0, size = 100 }) {
    const result = await api.article.getArticleList({ AnthologyId, page, size })
    return result
  }
}

export default {
  actions
}
