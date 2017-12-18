<template>
  <div class="game-face dj-room" v-show="showDj">
    <!-- <img src="../assets/game.png" width="100%"/> -->
    <div class="z-bg" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
      <img src="../assets/imgs/img_Room_announcement-background.png" alt=""  width="100%">
    </div>
    <!-- 用户位置 -->
    <div class="user-site" v-show="users.length >= 1 && users.length <= 5">
      <div class="user-item" v-for="(item, index) in users" :key="index"
      :class="{'site0': index === 0, 'site1': index === 1, 'site2': index === 2,
      'site3': index === 3, 'site4': index === 4}">
        <div class="avater">
          <img src="../assets/imgs/img_Room_avatarframe.png" alt="" width="100%" height="100%">
          <div class="head">
            <img :src="item.headimgurl" alt="" width="100%" height="100%">
          </div>
          <div class="master" :class="{'l-site': (index === 0) || (index % 2 !== 0),
           'r-site': (index !== 0) && (index % 2 === 0)}" v-show="item.master">
            <img src="../assets/imgs/img_Room_owner.png" alt="" width="100%" height="100%">
           </div>
          <div class="result" :class="{'cur-user': index === 0}">
            <span v-show="item.status === 0" class="lost">
              <img :src="result[1]" alt="" width="100%">
            </span>
            <span v-show="item.status === 1" class="tie">
              <img :src="result[2]" alt="" width="100%">
            </span>
            <span v-show="item.status === 2" class="win">
              <img :src="result[0]" alt="" width="100%">
            </span>
          </div>
        </div>
        <div class="msg" :class="{'g-inline': index === 0,'l-msg': (index !== 0) && (index % 2 !== 0),
         'r-msg': (index === 0) || (index % 2 === 0)}">
          <div class="name">
            <img src="../assets/imgs/img_Room_name.png" alt="" width="100%" height="100%">
            <div class="text">
              {{item.nickname}}
            </div>
          </div>
          <div class="money">
            <img src="../assets/imgs/img_Room_goldcoin.png" alt="" width="100%" height="100%">
            <div class="text">
              {{item.score}}
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
        <div class="xz-tip">
          <span v-show="item.xz === 0">
            <img src="../assets/imgs/img_Bet_qingxiazhu.png" alt="" width="100%" height="100%">
          </span>
          <span v-show="item.xz === 1">
            <img src="../assets/imgs/img_Bet_zhengzaixiazhu.png" alt="" width="100%" height="100%">
          </span>
        </div>
      </div>
    </div>
    <!-- 游戏桌面 -->
    <div class="game-table">
      <div class="all-operate g-flex-row">
        <div class="host g-flex">
          <div class="host-bg">
            <div class="qz-bg bg" v-if="qz" >
              <img src="../assets/imgs/img_Create_qiangzhuang.png" alt="" width= "100%">
            </div>
             <div class="bqz-bg bg" v-else>
              <img src="../assets/imgs/img_Create_buqiangzhuang.png" alt="" width= "100%">
            </div>
            <span class="cur" v-if="qz" @touchstart="isQz" :class="{'active': qz}">
              <img src="../assets/imgs/img_Create_buzhuang.png" alt="" width= "100%" height= "100%">
            </span>
            <span class="cur" v-else @touchstart="isQz">
              <img src="../assets/imgs/img_Create_qz.png" alt="" width= "100%" height= "100%">
            </span>
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
        <div class="setting" @touchstart="setting">
          <img src="../assets/imgs/img_Room_setup.png" alt="" width= "100%">
        </div>
        <div class="js-room" @touchstart="releaseRoom">
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
      <div class="room-num">房间号： 11111{{ds}}</div>
      <div class="geme-type">
        <span>清推</span><span>20局</span><span>50分封顶</span>
      </div>
    </div>
    <!-- 牌面 -->
    <div class="card-table g-flex-row">
      <div class="chu g-flex" v-for="(item, index) in tbg" :key="index"
      :class="{'chu': index === 0, 'tian': index === 1, 'kan': index === 2}">
        <img :src="item" alt="" height="100%">
      </div>
      <div class="coins">
        <!-- <transition name="move"> -->
          <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
          'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
          'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'active': showCoins}">
            <img :src="item" alt="" width="100%" v-if="showCoins">
          </span>
        <!-- </transition> -->
      </div>
    </div>
    <!-- 邀请好友 -->
    <div class="yq-friend g-flex-row" @touchstart="invateFriend">
      <img src="../assets/imgs/img_Room_lnvitefriends.png" alt="" width= "100%">
    </div>
    <!-- 交流 -->
    <div class="talk">
      <div class="voice"></div>
      <div class="txt-msg"></div>
    </div>
    <!-- 设置 -->
    <Setting :account="showAccount" ref="setting"></Setting>
    <!-- 下注弹窗 -->
    <Modal :showModal="showXzModal"
    :showClose="showClose"
    :site="site"
    class="xz-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img_Bet_background.png" alt=""  width="100%" height="100%">
    </div>
      <div slot="title" class="xz-title title-active">
         <img src="../assets/imgs/img_Bet_title.png" alt=""  width="100%">
      </div>
      <div slot="body" class="xz-body">
        <ul>
          <li class="item">
            <span class="label">
              <img src="../assets/imgs/img_Bet_chumen.png" alt="">
            </span>
            <div class="select-xz">
              <div class="nums">
                <span class="num" v-for="(item, index) in lowz" :key="index">
                  <img :src="item.img" alt="">
                </span>
              </div>
              <div class="total">
                <span class="res">20</span>
                <span class="close">
                  <img src="../assets/imgs/img_Bet_huishan.png" alt="">
                </span>
              </div>
            </div>
          </li>
          <li class="item">
            <span class="label">
              <img src="../assets/imgs/img_Bet_tianmen.png" alt="">
            </span>
            <div class="select-xz">
              <div class="nums">
                <span class="num" v-for="(item, index) in lowz" :key="index">
                  <img :src="item.img" alt="">
                </span>
              </div>
              <div class="total">
                <span class="res">20</span>
                <span class="close">
                  <img src="../assets/imgs/img_Bet_huishan.png" alt="">
                </span>
              </div>
            </div>
          </li>
          <li class="item">
            <span class="label">
              <img src="../assets/imgs/img_Bet_kanmen.png" alt="">
            </span>
            <div class="select-xz">
              <div class="nums">
                <span class="num" v-for="(item, index) in lowz" :key="index">
                  <img :src="item.img" alt="">
                </span>
              </div>
              <div class="total">
                <span class="res">20</span>
                <span class="close">
                  <img src="../assets/imgs/img_Bet_huishan.png" alt="">
                </span>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div slot="foot" class="xz-foot">
        <div class="ok xz-btn" @touchstart="xzOk">
           <img src="../assets/imgs/img-Stoppingdoor-confirm.png" alt=""  width="100%">
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import tabImgs from './tabImgs'
import avatar from '../assets/imgs/head_img.jpg'
import {mapGetters} from 'vuex'

const HEAD_IMG_SIZE = 0
export default {
  name: 'app',
  data () {
    let hours = new Date().getHours()
    let minutes = new Date().getMinutes()
    let curHours = hours < 10 ? ('0' + hours) : hours
    let curMinutes = minutes < 10 ? ('0' + minutes) : minutes
    return {
      date: curHours + '：' + curMinutes,
      showDj: false,
      showAccount: false,
      qz: false,
      users: [
        {
          name: '小乐',
          avatar: avatar,
          money: 3000,
          master: true,
          result: 0,
          status: 0,
          card: null,
          xz: 0
        },
        {
          name: '小刚',
          avatar: avatar,
          money: 3000,
          master: true,
          result: 1,
          status: 1,
          card: null,
          xz: 1
        },
        {
          name: '小王',
          avatar: avatar,
          money: 3000,
          master: false,
          result: 1,
          status: 1,
          card: null,
          xz: 0
        },
        {
          name: '小李',
          avatar: avatar,
          money: 3000,
          master: false,
          result: 2,
          status: 2,
          card: null,
          xz: 1
        },
        {
          name: '小张',
          avatar: avatar,
          money: 3000,
          master: true,
          result: 0,
          status: 0,
          card: null,
          xz: 0
        }
      ],
      showSetModal: false,
      socket: null,
      id: '',
      showXzModal: true,
      showClose: false,
      site: true,
      mType: [
        {
          img: tabImgs.mtype[0],
          select: false
        },
        {
          img: tabImgs.mtype[1],
          select: false
        },
        {
          img: tabImgs.mtype[2],
          select: false
        }
      ],
      tbg: tabImgs.tbg,
      result: tabImgs.result,
      coins: tabImgs.coins,
      lowz: [{
        img: tabImgs.lowz[0],
        selected: false
      },
      {
        img: tabImgs.lowz[1],
        selected: false
      },
      {
        img: tabImgs.lowz[2],
        selected: false
      },
      {
        img: tabImgs.lowz[3],
        selected: false
      }],
      showCoins: false
    }
  },
  props: {
    dj: {
      type: Boolean,
      default: false
    }
  },
  computed: mapGetters({
    ds: 'listenGameUser'
  }),
  watch: {
    dj (val) {
      this.showDj = val
    },
    ds: {
      handler (val) {
        this.users = []
        val.forEach((item) => {
          if (item instanceof Object) {
            item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
            this.users.push(item)
          }
        })
      },
      deep: true
    }
  },
  created () {
    this.showDj = this.dj
    this.users = this.ds
  },
  methods: {
    isQz () {
      this.qz = !this.qz
    },
    setting () {
      this.$refs.setting.openSetModal()
    },
    releaseRoom () {
     // this.$router.push({path: '/', query: {'id': ''}})
      this.showDj = false
      this.$emit('on-close', this.showDj)
    },
    closeSetModal () {
      this.showSetModal = false
    },
    xzOk () {
      this.showXzModal = false
      this.showCoins = true
    },
    invateFriend () {
      // let vm = this
      this.$JsBridge.callHandler(
        'invateFriend' // 原生的方法名
        , {'param': ''} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
        }
      )
    }
  }
}
</script>

<style scoped lang="less">
.move-enter-active, .move-leave-active {
  transition: opacity .5s
}
.move-enter, .move-leave-to /* .fade-leave-active in below version 2.1.8 */ {
  opacity: 0
}
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
  position: fixed;
  top: 0;
  left: 0;
  padding: 2px;
  background: url('../assets/imgs/img_Room_announcement-background.png') 0 0 no-repeat;
  background-size: 100% 100%;
  z-index: 100;
  .user-site{
    position: absolute;
    top: 0;
    left: 0;
    z-index: 100;
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
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          z-index: 99;
        }
        .result.cur-user{
          left: 200px;
        }
        .result .win{
          display: block;
          width: 100%;
          transform: scale(1.5)
        }
      }
      .msg{
        position: relative;
        width: 180px;
        color: #fff;
        font-size: 24px;
        font-weight: bold;
        margin-top: 5px;
        .money{
          position: relative;
          width: 180px;
          height: 40px;
          line-height: 42px;
          margin-top: 5px;
          // background: url('../assets/imgs/img_Room_goldcoin.png') 0 0 no-repeat;
          // background-size: 100% 100%;
        }
        .name{
          position: relative;
          width: 180px;
          height: 40px;
          line-height: 42px;
          // background: url('../assets/imgs/img_Room_name.png') 0 0 no-repeat;
          // background-size: 100% 100%;
        }
        .text{
          position: absolute;
          top: 0;
          left: 36%;
        }
      }
      .g-inline{ 
        left: -50px;
        bottom: 20px;
        margin-top: 20px;
         font-size: 0;
        .money,.name{
          display: inline-block;
          vertical-align: middle;
          font-size: 24px;
        }
        .money{
          margin-top: 0;
        }
        .name{
          margin-right: 8px;
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
      .xz-tip{
        position: absolute;
        width: 180px;
        img{
          width: 100%;
          height: 40px;
        }
      }
    }
    .site0{
      left: 40%;
      bottom: 0;
      transform: translateX(-50%);
      .status{
        top: 20px;
        left: 240px;
      }
      .msg{
         width: 370px;
      }
      .xz-tip{
        position: absolute;
        top: -50px;
        left: -30px;
      }
    }
    .site1{
      right: 0px;
      bottom: 26vh;
      .xz-tip{
        position: absolute;
        top: 50px;
        left: -200px;
      }
    }
    .site2{
      left: 50px;
      bottom: 26vh;
      .xz-tip{
        position: absolute;
        top: 50px;
        right: -150px;
      }
    }
    .site3{
      right: 0px;
      top: 16vh;
      .xz-tip{
        position: absolute;
        top: 50px;
        left: -200px;
      }
    }
    .site4{
      left: 50px;
      top: 16vh;
      .xz-tip{
        position: absolute;
        top: 50px;
        right: -150px;
      }
    }
  }
  .game-table{
    position: relative;
    height: 100vh;
    .all-operate{
      .host{
        .host-bg{
          position: relative;
          width: 68%;
          height: 8vh;
          // background: url('../assets/imgs/img_Room_scramblefor.png') 0 0 no-repeat;
          // background-size: 100% 100%;
          .bg{
            height: 100%;
          }
          .cur{
            position: absolute;
            top: 0;
            left: 0;
            width: 50%;
            height: 100%;
            transition: left 0.1s linear;
          }
          .cur.active{
            left: 50%;
          }
        }
      }
      .tip{
        width: 65%;
        height: 3vh;
        margin: 5px 0 0 10px;
        background: url('../assets/imgs/img_Room_nooperation.png') 0 0 no-repeat;
        background-size: 100% 100%;
      }
    }
    .intro{
      position: absolute;
      bottom: 16px;
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
      line-height: 8vh;
      font-family: 'microsoft yahei';
      font-size: 22px;
      .time{
        margin-left: 30px;
      }
    }
    .games{
      position: relative;
      width: 15%;
      height: 8vh;
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
         top: 31%;
      }
      .room-num{
        top: 6%;
      }
    }
    .setting{
      width: 5%;
      height: 8vh;
      margin-right: 30px;
      img{
        height: 8vh;
      }
      // background: url('../assets/imgs/img_Room_setup.png') 0 0 no-repeat;
      // background-size: 100% 100%;
    }
    .js-room{
      width: 15%;
      height: 8vh;
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
    z-index: 30;
    .chu,.tian,.kan{
      height: 340px;
    }
    .coins{
      position: absolute;
      top: 0;
      left: 0;
      span{
        position: absolute;
        display: block;
        width: 70px;
        height: 70px;
      }
      .coin0{
        top: 10px;
        left: 160px;
        opacity: 0.5;
        transition: all 0.3s linear;
      }
      .coin0.active{
        top: 150px;
        opacity: 1;
      }
      .coin1{
        top: 50px;
        left: 30px;
        opacity: 0.5;
        transition: all 0.3s linear 0.1s;
      }
      .coin1.active{
        top: 130px;
        opacity: 1;
      }
      .coin2{
        top: 180px;
        left: 70px;
      }
      .coin3{
        top: 250px;
        left: 120px;
      }
      .coin4{
        top: 200px;
        left: 140px;
      }
      .coin5{
       top: 260px;
       left: 50px;
      }
      .coin6{
       top: 200px;
        left: 20px;
      }
      .coin7{
        top: 240px;
        left: 180px;
      }
    }
  }
  .yq-friend{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 22%;
    height: 12vh;
    z-index: 100;
    // background: url('../assets/imgs/img_Room_lnvitefriends.png') 0 0 no-repeat;
    // background-size: 100% 100%;
  }
  .talk{
    position: fixed;
    bottom: 8%;
    right: 16%;
    .voice{
      width: 110px;
      height: 80px;
       margin-bottom: 15px;
      background: url('../assets/imgs/img_Room_yuyin.png') 0 0 no-repeat;
      background-size: 100% 100%;
    }
    .txt-msg{
      width: 110px;
      height: 80px;
      background: url('../assets/imgs/img_Room_biaoqing.png') 0 0 no-repeat;
      background-size: 100% 100%;
    }
  }
}
.xz-modal{
  .xz-body{
    ul{
      margin: 30px 20px 0 20px;
      li.item{
        display: flex;
        flex-direction: row;
        margin-bottom: 20px;
        .label{
          flex: 0 0 150px;
          width: 150px;
          display: flex;
          align-items: center;
          img{          
            width: 100%;
            vertical-align: middle;
          }
        }
        .select-xz{
          flex: 1;
          width: 890px;
          height: 84px;
          margin-left: 30px;
          background: #6dc4d8;
          border-radius: 30px;
          .nums{
            display: inline-block;
            .num{
              display: inline-block;
              width: 80px;
              height: 80px;
              margin-left: 20px;
              img{
                display: inline-block;
                width: 100%;
                vertical-align: middle;
                margin-top: 6px;
              }
            }
            .num:first-child{
              margin-left: 0;
            }
          }
          .total{
            position: relative;
            display: inline-block;
            width: 160px;
            height: 60px;
            line-height: 60px;
            text-align: left;
            font-family: 'microsoft yahei';
            font-size: 56px;
            font-weight: bold;
            padding-left: 10px;
            margin: 10px 10px 0;
            color: #000;
            background: #fff;
            border-radius: 10px;
            vertical-align: middle;
            .close{
              position: absolute;
              top: 0;
              right: 6px;
              width: 80px;
              height: 100%;
              img{
                width: 100%;
              }
            }
          }
        }
      }
      li.item:last-child{
        margin-bottom: 0;
      }
    }
  }
  .xz-foot{
     padding: 0 10%;
     text-align: center;
    .xz-btn{
      position: relative;
      top: -10px;
      display: inline-block;
      width: 32%;
    }
  }
}
</style>
