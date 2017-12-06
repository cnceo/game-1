import io from 'socket.io-client'
import util from '../libs/bytesLen'
var JSBridge = require('../libs/JsBridge')
const CHAT = {
  socket: null,
  systemMsg: '',
  roomNum: '',
  init: (url, data) => {
    this.socket = io.connect(url, {path: '/app/socket.io'})
    let params = window.JSON.stringify(data)
    let bytes = util.getBytesLength(params)
    console.log(bytes + params)
    JSBridge.JsBridge.callHandler(
      'onlines' // 原生的方法名
      , {'param': params} // 带个原生方法的参数
      , function (responseData) { // 响应原生回调方法

      }
    )
    this.socket.emit('LOGIN', data)
    JSBridge.JsBridge.callHandler(
      'outlines' // 原生的方法名
      , {'param': params} // 带个原生方法的参数
      , function (responseData) { // 响应原生回调方法

      }
    )
    setInterval(() => {
      this.socket.emit('heartbeat', 1)
    }, 10000)
    // 加入房间系统信息
    this.socket.on('LOGIN', (msg) => {
      console.log(msg)
      JSBridge.JsBridge.callHandler(
        'toastMessage' // 原生的方法名
        , {'param': window.JSON.stringify(msg)} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法

        }
      )
      CHAT.systemMsg = msg.user.name + '加入了聊天'
      CHAT.roomNum = msg.roomNum
    })
    // 加入房间系统信息
    this.socket.on('connect_error', (msg) => {
      console.log(msg)
      JSBridge.JsBridge.callHandler(
        'toastErr' // 原生的方法名
        , {'param': window.JSON.stringify(msg)} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法

        }
      )
      CHAT.systemMsg = msg.user.name + '加入了聊天'
      CHAT.roomNum = msg.roomNum
    })
  }

  // join: (name, params) => {
  //   CHAT.socket.emit(name, params)
  // }
}
export default CHAT
