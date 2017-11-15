import api from '../../../api/interface'
import * as types from '../../mutation-types'

const state = {
  public: {}
}

const mutations = {
  [types.INDEX_PUBLIC] (state, { data }) {
    state.public = data
  }
}

const actions = {
  // 获取薪酬规则页面数据
  indexPublic ({ commit }) {
    api.indexPublic(data => {
      console.log(data)
      commit(types.INDEX_PUBLIC, { data })
    })
  }
}

const getters = {
  listenPublic: state => state.public
}

export default {
  state,
  getters,
  actions,
  mutations
}
