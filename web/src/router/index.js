import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
const router = new Router({
  routes: [{
    path: '/',
    name: 'index',
    component: () => import('../views/index.vue')
  }, {
    path: '/article',
    name: 'article',
    component: () => import('../views/article.vue')
  }, {
    path: '/writer',
    name: 'writer',
    component: () => import('../views/writer.vue')
  }]
})
export default router
