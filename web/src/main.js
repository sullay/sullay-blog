import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import GlobalConfig from './config'
import './plugins/element.js'
import myComponents from './components'
import service from './service'
import mainCss from './assets/css/main.css'
// markdown
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(service)
Vue.use(myComponents)
Vue.use(GlobalConfig)
Vue.use(mainCss)
Vue.use(mavonEditor)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
