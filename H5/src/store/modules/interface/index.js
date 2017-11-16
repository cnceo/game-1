import api from '../../../api/interface'
import * as types from '../../mutation-types'

const state = {
  music: {}, // 音量数据
  public: {} // 首页公告
}

const mutations = {
  [types.MUSIC_VOID] (state, { data }) {
    state.music = data
  },
  [types.INDEX_PUBLIC] (state, { data }) {
    state.public = data
  }
}

const actions = {
  // 获取音量数据
  getMusic ({ commit }, data) {
    commit(types.MUSIC_VOID, { data })
  },
  // 首页公告
  indexPublic ({ commit }, params) {
    api.indexPublic(data => {
      console.log(data)
      commit(types.INDEX_PUBLIC, { data })
    }, params)
  }
}

const getters = {
  listenMusic: state => state.music,
  listenPublic: state => state.public
}

export default {
  state,
  getters,
  actions,
  mutations
}
