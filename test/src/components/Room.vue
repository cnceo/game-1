<template>
  <div class="hello">
    <h2>房间号: </h2>
   <div class="room" ref="room">
     <div class="others">
       <span class="avatar">123</span>
       <div class="msg">
         <span class="name">123</span>
         <span class="txt">123132fsdfdsfds1312</span>
       </div>
     </div>
     <div class="me">
       <div class="msg">
         <span class="name">123</span>
         <span class="txt">1231321312</span>
       </div>
       <span class="avatar">123</span>
     </div>
    
   </div>
    <div class="write-msg">
      <textarea name="" id="" cols="30" rows="3" class="text"></textarea>
      <span @click="sendMsg" class="send">发送</span>
    </div>
  </div>
</template>

<script>
// import mixin from '../libs/mixin'
// console.log(mixin.sockets)
import CHAT from '../api/client'
export default {
  name: 'HelloWorld',
  data () {
    return {
      CHAT,
      roomNum: '',
      curUserId: '',
      systemMsg: '',
      userName: ''
    }
  },
  watch: {
    'CHAT.systemMsg': {
      handler (val) {
        if (val) {
          let div = document.createElement('div')
          let span = document.createElement('span')
          span.innerHTML = val
          div.className = 'system'
          span.className = 'msg'
          div.appendChild(span)
          this.$refs.room.appendChild(div)
        }
      },
      deep: true
    }
  },
  // sockets: {
  //   // connect: function () {  // 这里是监听connect事件
  //   //   console.log('start socket connect...')
  //   // },
  //   join: function (val) {
  //     console.log(val)
  //     let msg = val.user.name + '加入了聊天'
  //     this.systemMsg.push(msg)
  //   }
  // },
  methods: {
    sendMsg () {
      console.log('aaa')
    }
  },
  mounted () {
    console.log(this.CHAT)
  },
  created () {
    if (!this.$router.history.current.params.userId) {
      this.$router.push({path: '/'})
    } else {
      this.curUserId = this.$router.history.current.params.userId
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
.hello{
  h2{
    margin: 0;
    height: 36px;
    line-height: 36px;
    font-size: 18px;
    background:#005aa0;
    color: #fff;
  }
}
.room{
  border: 1px solid #ddd;
  height: 80vh;
  overflow: auto;
  .others{
    text-align: left;
    margin: 15px;
    display: flex;
    flex-direction: row;
    .avatar{
      display: inline-block;
      width: 40px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      border-radius: 50%;
      background: #005aa0;
      color: #fff;
    }
    .msg{
      display: inline-block;
      flex: 1;
      margin-left: 8px;
      .name, .txt{
        display: block;
        flex: 1;
      }
      .name{
        font-size: 14px;
        color: #999;
      }
      .txt{
        display: inline-block;
        font-size: 16px;
        padding: 4px;
        width: 40%;
        background: #fff;
        border-radius: 8px;
        word-wrap:break-word
      }
    }
  }
  .me{
    text-align: right;
    margin: 15px;
    display: flex;
    flex-direction: row;
    .avatar{
      display: inline-block;
      width: 40px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      border-radius: 50%;
      background: #005aa0;
      color: #fff;
    }
    .msg{
      display: inline-block;
      flex: 1;
      margin-left: 8px;
      .name, .txt{
        display: block;
        flex: 1;
      }
      .name{
        font-size: 14px;
        color: #666;
      }
      .txt{
        display: inline-block;
        font-size: 16px;
        padding: 4px;
        width: 40%;
        background: #fff;
        border-radius: 8px;
        word-wrap:break-word
      }
    }
  }
  .system{
    text-align: center;
    margin: 15px;
    .msg{
      padding: 0 5px;
      background: #fff;
      border-radius: 5px;
      font-size: 14px;
      color: #999;
    }
  }
}
.write-msg{
  display: flex;
  flex-direction: row;
  margin: 15px;
  .text{
    flex: 1;
    height: 40px;
    margin-right: 12px;
  }
  .send{
    flex: 0 0 60px;
    width: 60px;
    height: 36px;
    line-height: 36px;
    text-align: center;
    background: #fff;
    border-radius: 10px;
    border: 1px solid #ddd;
  }
}
</style>
