import io from 'socket.io-client'
const CHAT = {
  socket: null,
  systemMsg: '',
  init: (url, data) => {
    this.socket = io.connect(url)
    console.log('start socket connect...')
    this.socket.emit('join', data)
    setInterval(() => {
      this.socket.emit('heartbeat', 1)
    }, 10000)
    // 加入房间系统信息
    this.socket.on('join', (msg) => {
      console.log(msg)
      CHAT.systemMsg = msg.user.name + '加入了聊天'
    })
  }
  // join: (name, params) => {
  //   CHAT.socket.emit(name, params)
  // }
}
export default CHAT
