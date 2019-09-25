import axios from '../../plugins/axios'
import url from '../../config/fileConfig'
const uploadFile = (options) => axios.post(url.uploadFileUrl, options, {
  headers: {
    'Content-type': 'multipart/form-data'
  }
})

export default {
  uploadFile
}
