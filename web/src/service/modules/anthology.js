import axios from '../../plugins/axios'
const createAnthology = (options) => axios.post('/anthology/create', options)
const updateAnthology = (options) => axios.post('/anthology/update', options)
const deleteAnthology = (options) => axios.post('/anthology/delete', options)
const getAnthologyList = (options) => axios.get('/anthology/findPageByUser', { params: options })
export default {
  createAnthology,
  updateAnthology,
  deleteAnthology,
  getAnthologyList
}
