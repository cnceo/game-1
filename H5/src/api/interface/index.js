import Util from '../../libs/util'

function fetch (url, params, cb) {
  Util.ajax.post(url, params, cb)
  .then(res => {
    return cb(res.data)
  }).catch(err => {
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
 * @Last Modified time: 2017-11-15 17:02:02
 */
export default {
  // 首页公告
  indexPublic (cb, params) {
    return fetch('/get/notice', params, cb)
  }
}
