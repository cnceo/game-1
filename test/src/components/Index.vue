<template>
  <div class="hello">
    <!-- <h2>欢迎加入房间畅聊</h2> -->
    <div class="room">
     <input type="text" placeholder="请输入姓名" v-model="userName">
   </div>
   <div class="room">
     <input type="text" placeholder="请输入房间号" v-model="roomNum">
   </div>
    <div class="btn">
      <span @click="joinRoom">加入房间</span>
    </div>
  </div>
</template>

<script>
// import mixin from '../libs/mixin'
import CHAT from '../api/client'
export default {
  name: 'HelloWorld',
  data () {
    return {
      roomNum: '',
      userName: ''
    }
  },
 // mixins: [mixin],
 // sockets: {
    // connect: function () {  // 这里是监听connect事件
    //   console.log('start socket connect...')
    // }
  // },
  methods: {
    joinRoom () {
      if (!this.roomNum) {
        alert('请先输入房间号')
        return
      }
      CHAT.init('http://192.168.50.161:1337', { id: 123, name: this.userName, roomNum: this.roomNum })
      // this.$socket.send('{id: 123, name: "jeffery", roomNum: ' + this.roomNum + '}')
      // this.$socket.emit('joinRoom', { id: 123, name: this.userName, roomNum: this.roomNum })
      // this.$router.push({path: '/Room', query: {userId: 123}})
      this.$router.push({name: 'Room', params: {userId: 123}})
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.room{
  input{
    height: 36px;
  }
}
.btn{
  margin-top: 60px;
  span{
    display: inline-block;
    width: 120px;
    height: 36px;
    line-height: 36px;
    text-align: center;
    border: 1px solid #ddd;
    background: #fff;
    border-radius: 8px;
    cursor: pointer;
  }
  span:hover,span:active{
    color: #005aa0;
    border-color:#005aa0;
  }
}
</style>
