import axios from '../../plugins/axios'
const createArticle = (options) => axios.post('/article/create', options)
const updateArticle = (options) => axios.post('/article/update', options)
const deleteArticle = (options) => axios.post('/article/delete', options)
const getArticleList = (options) => axios.get('/article/findPage', { params: options })
export default {
  createArticle,
  updateArticle,
  deleteArticle,
  getArticleList
}
