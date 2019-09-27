import axios from '../../plugins/axios'
const createAnthology = (options) => axios.post('/anthology/create', options)
const deleteAnthology = (options) => axios.post('/anthology/delete', options)
const getAnthologyList = (options) => axios.get('/anthology/findPage', { params: options })
export default {
  createAnthology,
  deleteAnthology,
  getAnthologyList
}
