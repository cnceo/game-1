<template>
  <div class="game-face">
    <!-- <img src="../assets/game.png" width="100%"/> -->
    <!-- 用户位置 -->
    <div class="user-site" v-show="users.length >= 2 && users.length <= 5">
      <div class="user-item" v-for="(item, index) in users" :key="index"
      :class="{'site0': index === 0, 'site1': index === 1, 'site2': index === 2,
      'site3': index === 3, 'site4': index === 4}">
        <div class="avater">
          <img :src="item.avatar" alt="" width="100%" style="border-radius: 50%">
          <div class="master" :class="{'l-site': (index === 0) || (index % 2 !== 0),
           'r-site': (index !== 0) && (index % 2 === 0)}" v-show="item.master">房主</div>
          <div class="result" :class="{'cur-user': index === 0}">
            <span v-show="item.status === 0">输</span>
            <span v-show="item.status === 1">和</span>
            <span v-show="item.status === 2">赢</span>
          </div>
        </div>
        <div class="name">{{item.name}}</div>
        <div class="money">{{item.money}}</div>
        <div class="status"  :class="{'l-site': (index !== 0) && (index % 2 !== 0),
         'r-site': (index === 0) || (index % 2 === 0)}" v-show="item.status">
            <span v-show="item.status === 0">准备</span>
            <span v-show="item.status === 1">已准备</span>
            <span v-show="item.status === 2">开始</span>
         </div>
        <div class="card">{{item.card}}</div>
        
      </div>
    </div>
    <!-- 游戏桌面 -->
    <div class="game-table">
      <div class="all-operate g-flex-row">
        <div class="host g-flex">
          <span>抢庄</span><span>不抢庄</span>
          <p class="tip">抢庄后不可操作</p>
        </div>
        <div class="message g-flex">
          <span class="rate">10/20</span>
          <span class="time">{{date}}</span>
          <span class="power"></span>
        </div>
        <div class="blank g-flex">
        </div>
        <div class="games g-flex">
          <div class="rate">局数：10/20</div>
          <div class="room-num">房间号：11111</div>
        </div>
        <div class="setting" @click="setting">设置</div>
        <div class="setting">解散房间</div>
      </div>
      <div class="intro g-flex">
        <div class="help">帮助？？？</div>
        <div class="play-info">玩法介绍</div>
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
      showSetModal: false
    }
  },
  created () {
    let data = window.music.replace(/[0-9a-zA-Z_]+/g, function (str) {
      return '"' + str + '"'
    })
    window.music = window.JSON.parse(data)
  },
  methods: {
    setting () {
      this.$refs.setting.openSetModal()
    },
    closeSetModal () {
      this.showSetModal = false
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
  background: url('../assets/imgs/img_Room_announcement-background.png') 0 0 no-repeat;
  background-size: 100% 100%;
  .user-site{
    .user-item{
      position: fixed;
      .avater{
        position: relative;
        width: 120px;
        .master{
          position: absolute;
          top: 20px;
          width: 120px;
        }
        .master.l-site{
          left: -120px; 
        }
        .master.r-site{
          right: -120px; 
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
      .status{
        position: absolute;
        top: 90px;
        width: 120px;
      }
      .status.l-site{
          left: -180px; 
        }
      .status.r-site{
          right: -180px; 
      }
    }
    .site0{
      left: 50%;
      bottom: 0;
      transform: translateX(-50%)
    }
    .site1{
      right: 0;
      bottom: 30%;
    }
    .site2{
      left: 0;
      bottom: 30%;
    }
    .site3{
      right: 0;
      bottom: 60%;
    }
    .site4{
      left: 0;
      bottom: 60%;
    }
  }
  .game-table{
    position: relative;
    height: 100vh;
    .intro{
      position: absolute;
      bottom: 0;
      left: 0;
    }
  }
  .game-info{
    position: absolute;
    top: 100px;
    left: 50%;
    transform: translateX(-50%)
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
}
</style>
