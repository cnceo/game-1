import axios from 'axios'
// import env from '../config/env';
// import querystring from 'qs'

let util = {

}

const ajaxUrl = process.env.NODE_ENV === 'production' ? 'http://www.syhpgkj.com:8080' : process.env.NODE_ENV === 'development' ? 'http://www.syhpgkj.com:8080' : 'http://www.syhpgkj.com:8080'

// 基本配置
let instanceParams = {
  baseURL: ajaxUrl,
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000
  // data: {
  //   token: null
  // },
 // transformRequest: [function (data) {
    // 在这里根据自己的需求改变数据
    // data.token = cookie.getCookie('token') == undefined ? '' : cookie.getCookie('token');
    // return JSON.stringify(data)
 // }]
}

util.ajax = axios.create(instanceParams)

export default util
