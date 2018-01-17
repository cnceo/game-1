// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import router from './router'
import Install from './components/Install'
import Audio from './libs/Audio'
import store from './store'
import Util from './libs/util'
import Sign from './libs/sign'
import md5 from 'js-md5'
import './assets/less/modal.less'
import Interface from './libs/interface'
import Fn from './libs/bytesLen'
import Handler from './libs/handlerSocket'
// import VueSocketio from 'vue-socket.io'
// import socketio from 'socket.io-client'
var JSBridge = require('./libs/JsBridge')

Vue.use(Vuex)
Vue.use(Install)
Vue.use(Audio)
Vue.use(Sign)
// Vue.use(VueSocketio, socketio('http://192.168.50.161:3030'))
// Vue.use(VueSocketio, socketio('http://172.16.20.148:3000/'), store);

Vue.prototype.$JsBridge = JSBridge.JsBridge
Vue.prototype.$axios = Util.ajax
Vue.prototype.$url = Util.ajax.defaults.baseURL
Vue.prototype.$md5 = md5
Vue.prototype.$interface = Interface
Vue.prototype.$fn = Fn
Vue.prototype.$hds = Handler

Vue.config.productionTip = false

Vue.component('touch', {
  template: '<img :src="src" @touchstart="ontouchstart" @touchmove="ontouchmove" @touchend="ontouchend"/>',
  props: {
    src: String
  },
  methods: {
    ontouchstart: function (e) {
      this.$emit('touchstart', e)
    },
    ontouchmove: function (e) {
      this.$emit('touchmove', e)
    },
    ontouchend: function (e) {
      this.$emit('touchend', e)
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
