var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var index = require('./routes/index');
var users = require('./routes/users');

var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', index);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

server.listen(3030,{origins:'*'});

var rooms = {};
var onlineUsers = {};
var onlineCount = 0;
var isJoin = false;
io.on('connection', function (socket) {
  console.log('客户端链接成功....');
  socket.emit('sendToClient', { hello: '你好，我是一个程序员，来自服务器' });
  socket.on('join', function (user) {
    console.log(user);
    let roomId = user.roomNum
    if (!rooms[roomId]) {
      rooms[roomId] = [];
    }
    for (let i = 0, len = rooms[roomId].length; i < len; i++) {
      if (rooms[roomId][i].hasOwnProperty(user.id)) {
        isJoin = true;
      }
    }
    if (!isJoin) {
      rooms[roomId].push(user);
      onlineCount++;
    }
    socket.emit('join', {onlineUsers: rooms[roomId], onlineCount: onlineCount, user: user})
    console.log(rooms)
  });
  socket.on("disconnect",() => {
    console.log("有一用户退出连接");
   });
});

module.exports = app;
