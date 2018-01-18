<template>
  <div class="game-face dk-room" v-show="showDk">
    <div class="dk-content">
      <div class="dk-bg" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
        <img src="../assets/imgs/img-Stoppingdoor-background.png" alt=""  width="100%" height="100%">
      </div>

      <div class="dk-title">
        <img src="../assets/imgs/img_Daikai_beijing.png" alt=""  width="100%">
      </div>
      <div class="dk-body">
        <ul>
          <li class="num">
            <div class="img">
              <img src="../assets/imgs/img_Daikai_fangjianhao.png" alt="" height="100%">
            </div>
            <span>{{gameMsg.numId}}</span>
          </li>
          <li class="rule">
            <div class="img">
              <img src="../assets/imgs/img_Daikai_guize.png" alt="" height="100%">
            </div>
            <span v-html="rules"></span>
          </li>
        </ul>
      </div>
      <div class="dk-foot">
        <div class="ok dk-btn" @touchstart="copyNum">
           <img src="../assets/imgs/img_Daikai_fuzhi.png" alt=""  width="100%">
        </div>
        <div class="cancel dk-btn" @touchstart="invateFriend">
           <img src="../assets/imgs/img_Daikai_yaoqing.png" alt=""  width="100%">
        </div>
      </div>
      <span class="close" @touchstart="closeModal">
        <img src="../assets/imgs/img_Daikai_cuohao.png" alt="" width="100%">
      </span>
    </div>
    <div class="dk-mask"></div>
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      showDk: false,
      roomNum: '',
      gameMsg: {},
      rules: ''
    }
  },
  props: {
    dk: {
      type: Boolean,
      default: false
    },
    ds: {
      type: Object,
      default: function () {
        return {}
      }
    },
    ru: {
      type: String,
      default: ''
    }
  },
  watch: {
    dk (val) {
      this.showDk = val
    },
    ds (val) {
      this.gameMsg = val
    },
    ru (val) {
      this.rules = val
    }
  },
  created () {
    this.showDk = this.dk
    this.gameMsg = this.ds
  },
  methods: {
    closeModal () {
      this.showDk = false
      this.$emit('on-close', false)
    },
    copyNum () {
      let vm = this
      this.roomNum = this.gameMsg.numId
      this.$JsBridge.callHandler(
        'copyRoom' // 原生的方法名
        , {'param': vm.roomNum} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          console.log('复制成功')
        }
      )
    },
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
.dk-room{
  .dk-content{
    position: fixed;
    top: 50%;
    right: 50%;
    width: 60%;
    height: 60%;
    transform: translate(50%, -50%);
   // background: rgba(0, 0, 0, .8);
    z-index: 1000;
    .dk-title{
      position: absolute;
      top: -10px;
      left: 50%;
      width: 60%;
      transform: translateX(-50%);
    }
    .close{
      position: absolute;
      top: -20px;
      right: -20px;
      width: 80px;
      color: #fff;
      z-index: 1001;
    }
    .dk-body{
      padding: 130px 50px 40px;
      color: #fff;
      font-size: 48px;
      ul{
        li.num{
          display: flex;
          height: 72px;
          .img{
            flex: 0 0 80px;
            height: 40px;
            text-align: right;
            padding-left: 64px;
          }
          span{
            flex: 1;
          }
        }
        li.rule{
          display: flex;
        //  height: 72px;
          .img{
            flex: 0 0 80px;
            height: 40px;
            text-align: right;
          }
          span{
            flex: 1;
            width: calc(~"100% - 200px");
            margin-left: 15px;
            font-size: 42px;
            word-wrap: break-word;
            vertical-align: top;
          }
        }
      }
    }
    .dk-foot{
      display: flex;
      flex-direction: row;
      padding: 0 10%;
      justify-content: space-between;
      .dk-btn{
        flex: 0 0 38%;
      }
    }
  }
  .dk-mask{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
   // background: rgb(105, 105, 105, .6);
    background: transparent;
    z-index: 100;
  }
}

</style>
