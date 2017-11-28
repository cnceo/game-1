// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// import VueSocketio from 'vue-socket.io'
// import mixin from './libs/mixin'
// import socketio from 'socket.io-client'

// Vue.use(VueSocketio, socketio('http://192.168.50.161:3030'))
Vue.config.productionTip = false

// Vue.prototype.$mix = mixin
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
