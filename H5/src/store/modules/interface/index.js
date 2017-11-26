import api from '../../../api/interface'
import * as types from '../../mutation-types'

const state = {
  users: {}, // 用户信息
  wxUser: {}, // 用户微信信息
  music: {}, // 音量数据
  public: {}, // 首页公告
  qtRules: [], // 清推规则
  htRules: [], // 混推规则
  djRules: [] // 大九规则
}

const mutations = {
  [types.USER_MSG] (state, { data }) {
    state.users = data
  },
  [types.WX_USER_INFO] (state, { data }) {
    state.wxUser = data
  },
  [types.MUSIC_VOID] (state, { data }) {
    state.music = data
  },
  [types.INDEX_PUBLIC] (state, { data }) {
    state.public = data
  },
  [types.QT_RULE] (state, { data }) {
    state.qtRules = data
  },
  [types.HT_RULE] (state, { data }) {
    state.htRules = data
  },
  [types.DJ_RULE] (state, { data }) {
    state.djRules = data
  }
}

const actions = {
  // 获取用户微信信息
  getWxUserInfo ({ commit }, data) {
    commit(types.WX_USER_INFO, { data })
  },
  // 用户信息
  userInfo ({ commit }, data) {
    commit(types.USER_MSG, { data })
  },
  // 获取音量数据
  getMusic ({ commit }, data) {
    commit(types.MUSIC_VOID, { data })
  },
  // 首页公告
  publicAjax ({ commit }, params) {
    api.publicAjax(data => {
      commit(types.INDEX_PUBLIC, { data })
    }, params)
  },
  // 清推规则
  qtRuleAjax ({ commit }, params) {
    api.qtRuleAjax(data => {
      commit(types.QT_RULE, { data })
    }, params)
  },
  // 混推规则
  htRuleAjax ({ commit }, params) {
    api.htRuleAjax(data => {
      commit(types.HT_RULE, { data })
    }, params)
  },
  // 大九规则
  djRuleAjax ({ commit }, params) {
    api.djRuleAjax(data => {
      commit(types.DJ_RULE, { data })
    }, params)
  },
  // 创建房间
  createRoom ({ commit }, params) {
    return new Promise((resolve, reject) => {
      api.createRoom(data => {
        resolve(data)
      }, params)
    })
  }
}

const getters = {
  listenWxUser: state => state.wxUser,
  listenUser: state => state.users,
  listenMusic: state => state.music,
  listenPublic: state => state.public,
  listenQtRule: state => state.qtRules,
  listenHtRule: state => state.htRules,
  listenDjRule: state => state.djRules
}

export default {
  state,
  getters,
  actions,
  mutations
}
