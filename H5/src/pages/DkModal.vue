<template>
  <div class="game-face dk-room" v-show="showDk">
    <!-- <img src="../assets/game.png" width="100%"/> -->
    <div class="z-bg" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
      <img src="../assets/imgs/img_Room_announcement-background.png" alt=""  width="100%">
    </div>
    <!-- 游戏信息 -->
    <div class="game-info">
      <div class="room-num">房间号： {{gameMsg.numId}}</div>
      <div class="geme-type">
        <span v-show="gameMsg.gameType == '1'">清推</span>
        <span v-show="gameMsg.gameType == '2'">混推</span>
        <span v-show="gameMsg.gameType == '3'">大九</span>
        <span>{{gameMsg.baseRound}}局</span>
        <span>{{gameMsg.baseScore}}分封顶</span>
      </div>
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
        numId: this.gameMsg.numId,
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
  background: url('../assets/imgs/img_Room_announcement-background.png') 0 0 no-repeat;
  background-size: 100% 100%;
  z-index: 100;
  width: 100%;
  height: 100%;
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
