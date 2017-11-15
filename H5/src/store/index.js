import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'

import Module from './modules/interface'

Vue.use(Vuex)

/* 例子结束 */
export default new Vuex.Store({
  actions,
  getters,
  modules: [
    Module
  ]
})
