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
import VueSocketio from 'vue-socket.io'
// import socketio from 'socket.io-client'
var JSBridge = require('./libs/JsBridge')

Vue.use(Vuex)
Vue.use(Install)
Vue.use(Audio)
Vue.use(Sign)
Vue.use(VueSocketio, 'http://localhost:80')
// Vue.use(VueSocketio, socketio('http://172.16.20.148:3000/'), store);

// const store = new Vuex.Store({
//   // 存储状态值
//   state: {
//     user: ''
//   },
//   // 状态值的改变方法,操作状态值
//   // 提交mutations是更改Vuex状态的唯一方法
//   mutations: {
//     increment (state, params) {
//       // 变更状态
//       state.user = state
//     }
//   },
//   actions: {
//     increment (context, params) {
//       context.commit('increment', params)
//     }
//   }
// })

Vue.prototype.$JsBridge = JSBridge.JsBridge
Vue.prototype.$axios = Util.ajax
Vue.prototype.$url = Util.ajax.defaults.baseURL
Vue.prototype.$md5 = md5
Vue.prototype.$interface = Interface
// import JsBridge from 'JsBridge'
// window.getUserInfo = function (user) {
//   window.user = user
//   // return '返回值'
// }
// window.getSound = function (music) {
//   window.music = music
//   // return '返回值'
// }
// window.getPublic = function (publics) {
//   window.publics = publics
//   // return '返回值'
// }
// window.getUserInfo("I'm from native6!!!")

Vue.config.productionTip = false

// console.log(JsBridge)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
