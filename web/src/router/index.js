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
      path: 'article/:id',
      name: 'article',
      component: () => import('../views/index/article.vue')
    } ]
  }, {
    path: '/sign',
    component: () => import('../views/sign.vue'),
    children: [{
      path: 'in',
      name: 'sign_in',
      component: () => import('../views/sign/in.vue')
    }, {
      path: 'up',
      name: 'sign_up',
      component: () => import('../views/sign/up.vue')
    } ]
  }, {
    path: '/writer',
    name: 'writer',
    component: () => import('../views/writer.vue')
  }, {
    path: '*',
    redirect: '/'
  }]
})
export default router
