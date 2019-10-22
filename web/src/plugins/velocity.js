import velocity from 'velocity-animate'

export default {
  install (Vue) {
    Vue.prototype.Velocity = velocity
    Vue.prototype.velocity = velocity
  }
}
