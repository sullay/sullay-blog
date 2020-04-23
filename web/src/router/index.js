import Vue from 'vue'
import Router from 'vue-router'
import myVue from '../main'
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
    meta: { needLogin: true },
    component: () => import('../views/writer.vue')
  }, {
    path: '*',
    redirect: '/'
  }]
})
router.beforeEach((to, from, next) => {
  if (!localStorage.getItem('userInfo') && to.meta.needLogin) {
    myVue.$message.error('未登录系统')
    next('/sign/in')
  } else {
    next()
  }
})
export default router
