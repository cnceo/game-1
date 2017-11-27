<template>
  <div class="game-face">
    <!-- <img src="../assets/game.png" width="100%"/> -->
    <div class="z-bg" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
      <img src="../assets/imgs/img_Room_announcement-background.png" alt=""  width="100%">
    </div>
    <!-- 用户位置 -->
    <div class="user-site" v-show="users.length >= 2 && users.length <= 5">
      <div class="user-item" v-for="(item, index) in users" :key="index"
      :class="{'site0': index === 0, 'site1': index === 1, 'site2': index === 2,
      'site3': index === 3, 'site4': index === 4}">
        <div class="avater">
          <img src="../assets/imgs/img_Room_avatarframe.png" alt="" width="100%" height="100%">
          <div class="head">
            <img :src="item.avatar" alt="" width="100%" height="100%">
          </div>
          <div class="master" :class="{'l-site': (index === 0) || (index % 2 !== 0),
           'r-site': (index !== 0) && (index % 2 === 0)}" v-show="item.master">
            <img src="../assets/imgs/img_Room_owner.png" alt="" width="100%" height="100%">
           </div>
          <div class="result" :class="{'cur-user': index === 0}">
            <span v-show="item.status === 0">输</span>
            <span v-show="item.status === 1">和</span>
            <span v-show="item.status === 2">赢</span>
          </div>
        </div>
        <div class="msg" :class="{'g-inline': index === 0,'l-msg': (index !== 0) && (index % 2 !== 0),
         'r-msg': (index === 0) || (index % 2 === 0)}">
          <div class="name">
            <img src="../assets/imgs/img_Room_name.png" alt="" width="100%" height="100%">
            <div class="text">
              {{item.name}}
            </div>
          </div>
          <div class="money">
            <img src="../assets/imgs/img_Room_goldcoin.png" alt="" width="100%" height="100%">
            <div class="text">
              {{item.money}}
            </div>
          </div>
        </div>
        <div class="status"  :class="{'l-site': (index !== 0) && (index % 2 !== 0),
         'r-site': (index === 0) || (index % 2 === 0)}">
            <span v-show="item.status === 0">
              <img src="../assets/imgs/img_Room_ready.png" alt="" width="100%" height="100%">
            </span>
            <span v-show="item.status === 1">
              <img src="../assets/imgs/img_Room_readying.png" alt="" width="100%" height="100%">
            </span>
            <span v-show="item.status === 2 && index === 0">
              <img src="../assets/imgs/img_Setup_Exchangeaccount.png" alt="" width="100%" height="100%">
            </span>
         </div>
        <div class="card">{{item.card}}</div>
        
      </div>
    </div>
    <!-- 游戏桌面 -->
    <div class="game-table">
      <div class="all-operate g-flex-row">
        <div class="host g-flex">
          <div class="host-bg">
            <img src="../assets/imgs/img_Room_scramblefor.png" alt="" width= "100%">
            <span></span><span></span>
          </div>
          <p class="tip"></p>
        </div>
        <div class="message">
          <span class="rate">10/20</span>
          <span class="time">{{date}}</span>
          <span class="power"></span>
        </div>
        <div class="blank g-flex">
        </div>
        <div class="games">
          <img src="../assets/imgs/img_Room_roomnumber.png" alt="" width= "100%">
          <div class="box">
            <div class="rate">10/20</div>
            <div class="room-num">11111</div>
          </div>
        </div>
        <div class="setting" @click="setting">
          <img src="../assets/imgs/img_Room_setup.png" alt="" width= "100%">
        </div>
        <div class="js-room" @click="jsRoom">
          <img src="../assets/imgs/img_Room_dissolvetheroom.png" alt="" width= "100%">
        </div>
      </div>
      <div class="intro g-flex">
        <div class="help">
          <img src="../assets/imgs/img_Room_lntroduction.png" alt="" width= "100%">
        </div>
        <div class="play-info">
          <img src="../assets/imgs/img_Room_play.png" alt="" width= "100%">
        </div>
      </div>
    </div>
    <!-- 游戏信息 -->
    <div class="game-info">
      <div class="room-num">房间号： 11111</div>
      <div class="geme-type">
        <span>清推</span><span>20局</span><span>50分封顶</span>
      </div>
    </div>
    <!-- 牌面 -->
    <div class="card-table g-flex-row">
      <div class="chu g-flex">出</div>
      <div class="tian g-flex">天</div>
      <div class="kan g-flex">坎</div>
    </div>
    <!-- 邀请好友 -->
    <div class="yq-friend g-flex-row">
      <img src="../assets/imgs/img_Room_lnvitefriends.png" alt="" width= "100%">
    </div>
    <!-- 交流 -->
    <div class="talk">
      <div class="txt-msg"></div>
      <div class="voice"></div>
    </div>
    <!-- 设置 -->
    <Setting :account="showAccount" ref="setting"></Setting>
  </div>
</template>

<script>
import avatar from '../assets/imgs/head_img.jpg'
export default {
  name: 'app',
  data () {
    let hours = new Date().getHours()
    let minutes = new Date().getMinutes()
    let curHours = hours < 10 ? ('0' + hours) : hours
    let curMinutes = minutes < 10 ? ('0' + minutes) : minutes
    return {
      date: curHours + '：' + curMinutes,
      showAccount: false,
      users: [
        {
          name: '小乐',
          avatar: avatar,
          money: 3000,
          master: true,
          result: 0,
          status: 0,
          card: null
        },
        {
          name: '小刚',
          avatar: avatar,
          money: 3000,
          master: true,
          result: 1,
          status: 1,
          card: null
        },
        {
          name: '小王',
          avatar: avatar,
          money: 3000,
          master: false,
          result: 1,
          status: 1,
          card: null
        },
        {
          name: '小李',
          avatar: avatar,
          money: 3000,
          master: false,
          result: 2,
          status: 2,
          card: null
        },
        {
          name: '小张',
          avatar: avatar,
          money: 3000,
          master: true,
          result: 0,
          status: 0,
          card: null
        }
      ],
      showSetModal: false,
      socket: null,
      id: ''
    }
  },
  sockets: {
    connect: function () {  // 这里是监听connect事件
      this.id = this.$socket.id
      console.log(this.id)
    },
    sendToClient: function (val) {
      console.log(val)
    }
  },
  mounted () {
    this.$socket.emit('sendToServer', '我来自客户端') // 在这里触发connect事件
  },
  created () {
    let data = window.music.replace(/[0-9a-zA-Z_]+/g, function (str) {
      return '"' + str + '"'
    })
    window.music = window.JSON.parse(data)
    // 即时通讯
    this.doSocket()
  },
  methods: {
    setting () {
      this.$refs.setting.openSetModal()
    },
    jsRoom () {
      this.$router.push({path: '/', query: {}})
    },
    closeSetModal () {
      this.showSetModal = false
    },
    doSocket () {
     // let vm = this
      console.log(this)
      // this.socket = io.connect(this.$url)
      // this.socket.on('message', function (obj) {
      //   console.log(obj)
      // })
    }
  }
}
</script>

<style scoped lang="less">
.g-flex-column{
  display: flex;
  flex-direction: column;
}
.g-flex-row{
  display: flex;
  flex-direction: row;
}
.g-flex{
  flex: 1;
}

.game-face{
  padding: 2px;
  background: url('../assets/imgs/img_Room_announcement-background.png') 0 0 no-repeat;
  background-size: 100% 100%;
  .user-site{
    position: relative;
    .user-item{
      position: fixed;
      .avater{
        position: relative;
        width: 125px;
        height: 95px;
        // background: url('../assets/imgs/img_Room_avatarframe.png') 0 0 no-repeat;
        // background-size: 100% 100%;
        .head{
          position: absolute;
          top: 0;
          left: 0;
          width: 100px;
          height: 70px;
          padding: 11px 16px 15px 14px;
          overflow: hidden;
          img{
            border-radius: 12px;
            box-shadow: 0 0 10px #000;
          }
        }
        .master{
          position: absolute;
          top: 12px;
          width: 116px;
          height: 32px;
          // background: url('../assets/imgs/img_Room_owner.png') 0 0 no-repeat;
          // background-size: 100% 100%;
          z-index: -1;
        }
        .master.l-site{
          left: -110px; 
        }
        .master.r-site{
          right: -90px; 
        }
        .result{
          position: absolute;
          top: 30px;
          left: 50px;
          color: #f00;
        }
        .result.cur-user{
          left: 150px;
        }
      }
      .msg{
        position: relative;
        width: 180px;
        color: #fff;
        font-size: 28px;
        font-weight: bold;
        .money{
          position: relative;
          width: 180px;
          height: 50px;
          line-height: 50px;
          // background: url('../assets/imgs/img_Room_goldcoin.png') 0 0 no-repeat;
          // background-size: 100% 100%;
        }
        .name{
          position: relative;
          width: 180px;
          height: 50px;
          line-height: 50px;
          // background: url('../assets/imgs/img_Room_name.png') 0 0 no-repeat;
          // background-size: 100% 100%;
        }
        .text{
          position: absolute;
          top: 0;
          left: 30%;
        }
      }
      .g-inline{ 
        left: -50px;
        bottom: 20px;
        margin-top: 20px;
        .money,.name{
          display: inline-block;
        }
      }
      .msg.r-msg{
        left: -30px;
      }
      .msg.l-msg{
        left: -30px;
      }
      .status{
        position: absolute;
        top: 40px;
        width: 180px;
      }
      .status.l-site{
          left: -280px; 
        }
      .status.r-site{
          right: -220px; 
      }
    }
    .site0{
      left: 40%;
      bottom: 0;
      transform: translateX(-50%);
      .status{
        top: 20px;
      }
    }
    .site1{
      right: 20px;
      bottom: 26vh;
    }
    .site2{
      left: 50px;
      bottom: 26vh;
    }
    .site3{
      right: 20px;
      top: 15vh;
    }
    .site4{
      left: 50px;
      top: 15vh;
    }
  }
  .game-table{
    position: relative;
    height: 100vh;
    .all-operate{
      .host{
        .host-bg{
          width: 68%;
          height: 6vh;
          // background: url('../assets/imgs/img_Room_scramblefor.png') 0 0 no-repeat;
          // background-size: 100% 100%;
        }
      }
      .tip{
        width: 65%;
        height: 3vh;
        margin: 20px 0 0 10px;
        background: url('../assets/imgs/img_Room_nooperation.png') 0 0 no-repeat;
        background-size: 100% 100%;
      }
    }
    .intro{
      position: absolute;
      bottom: 20px;
      left: 20px;
      .help{
         width: 110px;
        height: 80px;
        // background: url('../assets/imgs/img_Room_lntroduction.png') 0 0 no-repeat;
        // background-size: 100% 100%;
      }
      .play-info{
         width: 110px;
        height: 80px;
        margin-top: 12px;
        // background: url('../assets/imgs/img_Room_play.png') 0 0 no-repeat;
        // background-size: 100% 100%;
      }
    }
    .message{
      position: relative;
      left: -5%;
      color: #fff;
      line-height: 7vh;
      font-family: 'microsoft yahei';
      font-size: 22px;
      .time{
        margin-left: 30px;
      }
    }
    .games{
      position: relative;
      width: 15%;
      height: 6vh;
      margin-right: 40px;
      font-size: 14px;
      color: #fff;
      font-family: 'microsoft yahei';
      // background: url('../assets/imgs/img_Room_roomnumber.png') 0 0 no-repeat;
      // background-size: 100% 100%;
      .rate,.room-num{
        position: absolute;
        left: 50%;
      }
      .rate{
        bottom: 6%;
      }
      .room-num{
        top: 5%;
      }
    }
    .setting{
      width: 5%;
      height: 6vh;
      margin-right: 30px;
      // background: url('../assets/imgs/img_Room_setup.png') 0 0 no-repeat;
      // background-size: 100% 100%;
    }
    .js-room{
      width: 15%;
      height: 6vh;
      // background: url('../assets/imgs/img_Room_dissolvetheroom.png') 0 0 no-repeat;
      // background-size: 100% 100%;
    }
  }
  .game-info{
    position: absolute;
    top: 90px;
    left: 50%;
    transform: translateX(-50%);
    color: #fff;
    font-family: 'microsoft yahei';
    font-size: 24px;
    .geme-type{
      margin-top: 12px;
      span{
        display: inline-block;
        margin: 0 30px;
      }
    }
  }
  .card-table{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    .chu,.tian,.kan{
      width: 120px;
      height: 320px;
      border: 3px solid #000;
    }
  }
  .yq-friend{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 22%;
    height: 12vh;
    // background: url('../assets/imgs/img_Room_lnvitefriends.png') 0 0 no-repeat;
    // background-size: 100% 100%;
  }
  .talk{
    position: fixed;
    bottom: 13%;
    right: 18%;
    .voice{
      width: 110px;
      height: 80px;
      background: url('../assets/imgs/img_Room_yuyin.png') 0 0 no-repeat;
      background-size: 100% 100%;
    }
    .txt-msg{
      width: 110px;
      height: 80px;
      margin-bottom: 15px;
      background: url('../assets/imgs/img_Room_biaoqing.png') 0 0 no-repeat;
      background-size: 100% 100%;
    }
  }
}
</style>
