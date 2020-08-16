import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './assets/global.css'
import consts from '@/util/consts'
require('echarts/lib/echarts')
require('echarts/lib/chart/bar')
// require('echarts/lib/component/tooltip')
// require('echarts/lib/component/title')
// require('echarts/lib/component/legend')
// require('echarts/lib/component/legendScroll')

Vue.config.productionTip = false
Vue.prototype.$consts = consts

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
