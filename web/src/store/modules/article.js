import api from '../../service'
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
  },
  async getArticleListAll ({ commit }, { id = 0, pageSize = 5 }) {
    const result = await api.article.getArticleListAll({ id, pageSize })
    return result
  },
  async getArticleById ({ commit }, { id }) {
    const result = await api.article.getArticleById({ id })
    return result
  }
}

export default {
  actions
}
