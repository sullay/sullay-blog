import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
const router = new Router({
  routes: [{
    path: '/',
    component: () => import('../views/index.vue'),
    children: [{
      path: '',
      name: 'index',
      component: () => import('../views/index/index.vue')
    }, {
      path: 'article',
      name: 'article',
      component: () => import('../views/index/article.vue')
    } ]
  }, {
    path: '/writer',
    name: 'writer',
    component: () => import('../views/writer.vue')
  }]
})
export default router
