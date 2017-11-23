import Util from '../../libs/util'

function fetch (url, cb) {
  Util.ajax.get(url, cb)
  .then(res => {
    return cb(res.data)
  }).catch(err => {
    window.android.read(window.JSON.stringify(err))
    if (err.response) {
      // 请求已经发出，但是服务器响应返回的状态吗不在2xx的范围内
      console.log(err.response.data)
      console.log(err.response.status)
      console.log(err.response.header)
    } else {
      // 一些错误是在设置请求的时候触发
      console.log('Error', err.message)
    }
    console.log(err.config)
  })
}

/*
 * @Description: 数据请求方法
 * @Date: 2017-07-05 08:13:38
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-11-23 17:50:50
 */
export default {
  // 用户信息
  userInfo (cb, params) {
    return fetch('/user/login?' + params, cb)
  },
  // 首页公告
  publicAjax (cb, params) {
    return fetch('/get/notice?' + params, cb)
  },
  // 清推规则
  qtRuleAjax (cb, params) {
    return fetch('/get/rule/qingtui?' + params, cb)
  },
  // 混规则
  htRuleAjax (cb, params) {
    return fetch('/get/rule/huntui?' + params, cb)
  },
  // 大九规则
  djRuleAjax (cb, params) {
    return fetch('/get/rule/dajiu?' + params, cb)
  },
  // 创建房间
  createRoom (cb, params) {
    return fetch('/room/create?' + params, cb)
  }
}
