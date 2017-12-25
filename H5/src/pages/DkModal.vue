<template>
  <div class="game-face dk-room" v-show="showDk">
    <!-- <img src="../assets/game.png" width="100%"/> -->
    <div class="z-bg" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
      <img src="../assets/imgs/img_Room_announcement-background.png" alt=""  width="100%">
    </div>
   
    <!-- 邀请好友 -->
    <div class="yq-friend g-flex-row" @touchstart="invateFriend">
      <img src="../assets/imgs/img_Room_lnvitefriends.png" alt="" width= "100%">
    </div>
    
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      showDk: false,
      gameMsg: {}
    }
  },
  props: {
    dk: {
      type: Boolean,
      default: false
    },
    ds: {
      type: String,
      default: function () {
        return {}
      }
    }
  },
  watch: {
    dk (val) {
      this.showDk = val
    },
    ds (val) {
      this.gameMsg = val
    }
  },
  created () {
    this.showDk = this.dk
    this.gameMsg = this.ds
  },
  methods: {
    // 邀请好友
    invateFriend () {
      // roomMsg(房间信息)
      // let vm = this
      let params = window.JSON.stringify({
        numId: this.gameMsg.roomId,
        baseScore: this.gameMsg.baseScore,
        baseRound: this.gameMsg.baseRound
      })
      this.$JsBridge.callHandler(
        'invateFriend' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          console.log('分享成功')
        }
      )
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
  position: fixed;
  top: 0;
  left: 0;
  padding: 2px;
  background: url('../assets/imgs/img_Room_announcement-background.png') 0 0 no-repeat;
  background-size: 100% 100%;
  z-index: 100;
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
 
}

</style>
