import TransitionRouter from './transition-router.vue'
import TransitionList from './transition-list.vue'
import TransitionListIndex from './transition-list-index.vue'
import TransitionLeft from './transition-left.vue'
import TransitionRight from './transition-right.vue'
import TransitionRotate from './transition-rotate.vue'
import TransitionGrow from './transition-grow.vue'
const myComponents = {
  install: function (Vue) {
    Vue.component('TransitionRouter', TransitionRouter)
    Vue.component('TransitionList', TransitionList)
    Vue.component('TransitionListIndex', TransitionListIndex)
    Vue.component('TransitionLeft', TransitionLeft)
    Vue.component('TransitionRight', TransitionRight)
    Vue.component('TransitionRotate', TransitionRotate)
    Vue.component('TransitionGrow', TransitionGrow)
  }
}

// 导出组件
export default myComponents
