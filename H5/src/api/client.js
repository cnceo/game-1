import io from 'socket.io-client'
import util from '../libs/bytesLen'
const CHAT = {
  socket: null,
  systemMsg: '',
  roomNum: '',
  init: (url, data) => {
    this.socket = io.connect(url)
    let params = window.JSON.stringify(data)
    let bytes = util.getBytesLength(params)
    console.log(bytes + params)
    this.socket.emit('LOGIN', bytes + params)
    setInterval(() => {
      this.socket.emit('heartbeat', 1)
    }, 10000)
    // 加入房间系统信息
    this.socket.on('join', (msg) => {
      console.log(msg)
      CHAT.systemMsg = msg.user.name + '加入了聊天'
      CHAT.roomNum = msg.roomNum
    })
  }
  // join: (name, params) => {
  //   CHAT.socket.emit(name, params)
  // }
}
export default CHAT
