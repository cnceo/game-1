import api from '../../../api/interface'
import * as types from '../../mutation-types'

const state = {
  users: {}, // 用户信息
  userId: '',
  wxUser: {}, // 用户微信信息
  music: {}, // 音量数据
  public: {}, // 首页公告
  news: {}, // 新闻消息
  qtRules: [], // 清推规则
  htRules: [], // 混推规则
  djRules: [], // 大九规则
  roomMsg: '', // 房间号等创建信息
  gameUsers: [] // 游戏中所有用户信息
}

const mutations = {
  [types.USER_MSG] (state, { data }) {
    state.users = data
  },
  [types.USER_ID] (state, { data }) {
    state.userId = data
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
  [types.NEWS_MSG] (state, { data }) {
    state.news = data
  },
  [types.QT_RULE] (state, { data }) {
    state.qtRules = data
  },
  [types.HT_RULE] (state, { data }) {
    state.htRules = data
  },
  [types.DJ_RULE] (state, { data }) {
    state.djRules = data
  },
  [types.SAVE_ROOM_MSG] (state, { data }) {
    state.roomMsg = data
  },
  [types.GAME_USERS] (state, { data }) {
    state.gameUsers = data
  }
}

const actions = {
  // 获取用户微信信息
  getWxUserInfo ({ commit }, data) {
    commit(types.WX_USER_INFO, { data })
  },
  // 用户信息
  userInfo ({ commit }, data) {
    // api.userAjax(data => {
    commit(types.USER_MSG, { data })
    //  }, params)
  },
  saveUserId ({ commit }, data) {
    // api.userAjax(data => {
    commit(types.USER_ID, { data })
    //  }, params)
  },
  // 获取音量数据
  getMusic ({ commit }, data) {
    commit(types.MUSIC_VOID, { data })
  },
  // 首页公告
  publicAjax ({ commit }, data) {
   // api.publicAjax(data => {
    commit(types.INDEX_PUBLIC, { data })
  //  }, params)
  },
  // 新闻消息
  newsAjax ({ commit }, data) {
    // api.newsAjax(data => {
    commit(types.NEWS_MSG, { data })
   //  }, params)
  },
  // 清推规则
  qtRuleAjax ({ commit }, data) {
  //  api.qtRuleAjax(data => {
    commit(types.QT_RULE, { data })
  //  }, params)
  },
  // 混推规则
  htRuleAjax ({ commit }, data) {
   // api.htRuleAjax(data => {
    commit(types.HT_RULE, { data })
   // }, params)
  },
  // 大九规则
  djRuleAjax ({ commit }, data) {
   // api.djRuleAjax(data => {
    commit(types.DJ_RULE, { data })
   // }, params)
  },
  // 保存房间号和用户id
  saveCreateMsg ({ commit }, data) {
    // api.djRuleAjax(data => {
    commit(types.SAVE_ROOM_MSG, { data })
    // }, params)
  },
  // 创建房间
  createRoom ({ commit }, params) {
    return new Promise((resolve, reject) => {
      api.createRoom(data => {
        resolve(data)
      }, params)
    })
  },
  // 加入房间，保存游戏用户信息
  saveUsers ({ commit }, data) {
    commit(types.GAME_USERS, { data })
  }
}

const getters = {
  listenWxUser: state => state.wxUser,
  listenUser: state => state.users,
  listenUserId: state => state.userId,
  listenMusic: state => state.music,
  listenPublic: state => state.public,
  listenNews: state => state.news,
  listenQtRule: state => state.qtRules,
  listenHtRule: state => state.htRules,
  listenDjRule: state => state.djRules,
  listenRoomMsg: state => state.roomMsg,
  listenGameUser: state => state.gameUsers
}

export default {
  state,
  getters,
  actions,
  mutations
}
