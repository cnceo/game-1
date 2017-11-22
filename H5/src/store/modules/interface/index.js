import api from '../../../api/interface'
import * as types from '../../mutation-types'

const state = {
  wxUser: {}, // 用户微信信息
  music: {}, // 音量数据
  public: {} // 首页公告
}

const mutations = {
  [types.WX_USER_INFO] (state, { data }) {
    state.wxUser = data
  },
  [types.MUSIC_VOID] (state, { data }) {
    state.music = data
  },
  [types.INDEX_PUBLIC] (state, { data }) {
    state.public = data
  }
}

const actions = {
  // 获取用户微信信息
  getWxUserInfo ({ commit }, data) {
    commit(types.WX_USER_INFO, { data })
  },
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
  listenWxUser: state => state.wxUser,
  listenMusic: state => state.music,
  listenPublic: state => state.public
}

export default {
  state,
  getters,
  actions,
  mutations
}
