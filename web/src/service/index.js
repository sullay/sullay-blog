import modules from './modules'

export default {
  install (Vue) {
    Vue.prototype.$api = modules
  }
}
