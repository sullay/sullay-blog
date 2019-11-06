import moment from 'moment'
export default {
  install (Vue) {
    Vue.filter('filterTime', (val) => {
      return val ? moment(val).format('YYYY年 MM月 DD日') : ''
    })
  }
}
