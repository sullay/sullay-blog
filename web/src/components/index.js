import TransitionFade from './transition-fade.vue'
const myComponents = {
  install: function (Vue) {
    Vue.component('TransitionFade', TransitionFade)
  }
}

// 导出组件
export default myComponents
