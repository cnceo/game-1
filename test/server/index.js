var io = require('socket.io')
var http = require('http')
var server = http.createServer(function (req, res) {
  var headers = {}
  headers['Access-Control-Allow-Origin'] = '*'
  headers['Access-Control-Allow-Methods'] = 'POST, GET, PUT, DELETE, OPTIONS'
  headers['Access-Control-Allow-Credentials'] = true
  headers['Access-Control-Max-Age'] = '86400' // 24 hours
  headers['Access-Control-Allow-Headers'] = 'X-Requested-With, Access-Control-Allow-Origin, X-HTTP-Method-Override, Content-Type, Authorization, Accept'
  res.writeHead(200, headers)
  res.end()
})
server.listen(1337, 'localhost')
console.log('Server running at http://192.168.50.161:1337/')

var rooms = {}
// var onlineUsers = {}
var onlineCount = 0
var isJoin = false
// io.set('transports', ['websocket', 'xhr-polling', 'jsonp-polling', 'htmlfile', 'flashsocket'])
// io.set('origins', '*:*')
var socket = io.listen(server)
socket.on('connection', function (socket) {
  console.log('客户端链接成功....')
  socket.emit('sendToClient', { hello: '你好，我是一个程序员，来自服务器' })
  socket.on('join', function (user) {
    console.log(user)
    let roomId = user.roomNum
    if (!rooms[roomId]) {
      rooms[roomId] = []
    }
    for (let i = 0, len = rooms[roomId].length; i < len; i++) {
      if (rooms[roomId][i].hasOwnProperty(user.id)) {
        isJoin = true
      }
    }
    if (!isJoin) {
      rooms[roomId].push(user)
      onlineCount++
    }
    socket.emit('join', {onlineUsers: rooms[roomId], onlineCount: onlineCount, user: user})
    console.log(rooms)
  })
  socket.on('disconnect', () => {
    console.log('有一用户退出连接')
  })
})
