import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
const router = new Router({
  routes: [{
    path: '/',
    name: 'test1',
    component: () => import('../views/test.vue')
  }, {
    path: '/test2',
    name: 'test2',
    component: () => import('../views/test2.vue')
  }]
})
export default router
