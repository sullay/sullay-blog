import axios from '../../plugins/axios'
const createAnthology = (options) => axios.post('/anthology/create', options)
const getAnthologyList = (options) => axios.get('/anthology/findPage', { params: options })
export default {
  createAnthology,
  getAnthologyList
}
