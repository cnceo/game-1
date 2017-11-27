<template>
  <div class="user-face g-flex-column">
    <div class="face-bar">   
      <div class="box g-flex-row">
        <!--用户信息-->
        <div class="user-info g-flex-row">
          <div class="user-avatar">
            <img src="../assets/imgs/Avatarframe.png" width="100%"  height="100%"/>
            <div class="user-img">
              <img :src="userInfo.headimgurl" width="100%" />
            </div>
          </div>
          <div class="user-id-card">
            <div class="user-name f-relative">{{userInfo.nickname}}</div></br>
            <div class="user-id f-relative">{{userInfo.id}}</div>
          </div>
          <div class="user-room-card">
            <div class="user-black f-relative"></div></br>
            <div class="user-card f-relative">{{userInfo.roomNum}}</div>
          </div>
        </div>
        <!--基本设置-->
        <ul class="base-settting g-flex-row">
          <li @click="help">
            <img src="../assets/imgs/rules.png" width="100%"/>
          </li>
          <li @click="message">
            <img src="../assets/imgs/message.png" width="100%"/>
          </li>
          <li @click="setting">
            <img src="../assets/imgs/steup.png" width="100%"/>
          </li>
        </ul>
     </div>
    </div>
    <div class="voice-info">
      <p class="text">这里是公告信息</p>
      <span class="s-icon"></span>
    </div>
    <div class="face-body g-flex-row">
      <div class="bg-girl">
        <img src="../assets/imgs/beautyGirl.png"  width="100%"/> 
      </div>
      <div class="all-room">
        <div class="room-item create-room" @click="createRoom">
          <img src="../assets/imgs/createroom.png"  width="100%"/> 
        </div>
        <div class="room-item join-room" @click="joinRoom">
          <img src="../assets/imgs/joinroom.png"  width="100%"/> 
        </div>
      </div>
    </div>
    <div class="z-bg" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
      <img src="../assets/imgs/background.png" alt=""  width="100%">
    </div>

    <!--规则-->
    <Modal :showModal="showRuleModal"
    @on-close="closeRuleModal"
    class="rule-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img_Rules_Rulebackground.png" alt=""  width="100%" height="100%">
    </div>
      <div slot="title" class="rule-title title-active">
        <img src="../assets/imgs/img_Rules_rule.png" alt=""  width="100%">
      </div>
      <div slot="body" class="rule-body">
        <div class="game-tabs">
          <span v-for="(item, index) in gameTabs" @click="changeGame(index)" :key="index"
          :class="{'game-active': item.select}">
            <img :src="item.img" alt="">
          </span>
        </div>
        <div class="game-box">
          <div v-show="selectGame === 0" v-html="qtRules"></div>
          <div v-show="selectGame === 1" v-html="htRules">混推内容</div>
          <div v-show="selectGame === 2" v-html="djRules">大九</div>
        </div>
      </div>
    </Modal>

    <!--消息-->
    <Modal :showModal="showMsgModal"
    @on-close="closeMsgModal"
    class="msg-modal">
      <div slot="title" class="msg-title title-active">
         <img src="../assets/imgs/img_Message_message.png" alt=""  width="100%">
      </div>
      <div slot="body" class="msg-body">{{public}}</div>
    </Modal>

    <!--设置-->
    <Setting ref="setting"></Setting>

    <!--创建房间-->
    <Modal :showModal="showCreateRoom"
    @on-close="closeCreateRoom"
    class="create-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img_Message_messagebackground.png" alt=""  width="100%" height="100%">
    </div>
      <div slot="title" class="create-title title-active">创建房间</div>
      <div slot="body" class="create-body">
        <div class="create-content">
          <div class="game-tabs">
            <span v-for="(item, index) in createRoomTabs" @click="changeRoom(index)" :key="index"
            >
              <img :src="item.img" alt="">
            </span>
          </div>
          <div class="game-box">
            <div v-show="selectRoom === 0">
              <div class="row">
                <span class="label">
                   <img src="../assets/imgs/img_Create_Thenumberofinnings.png" alt="" width="100%">
                </span>
                <selectType :types="ds1_1" :bigImg="bigImg" class="bar" @on-select="selectTime1"></selectType>
              </div>
              <div class="row">
                 <span class="label">
                   <img src="../assets/imgs/img_Create_Froction.png" alt="" width="100%">
                </span>
                <selectType :types="ds1_2" :bigImg="bigImg" class="bar" @on-select="selectScore1"></selectType>
              </div>
            </div>
            <div v-show="selectRoom === 1">
              <div class="row">
                 <span class="label">
                   <img src="../assets/imgs/img_Create_Thenumberofinnings.png" alt="" width="100%">
                </span>
                <selectType :types="ds2_1" :bigImg="bigImg" class="bar" @on-select="selectTime2"></selectType>
              </div>
              <div class="row">
                 <span class="label">
                   <img src="../assets/imgs/img_Create_Froction.png" alt="" width="100%">
                </span>
                <selectType :types="ds2_2" :bigImg="bigImg" class="bar" @on-select="selectScore2"></selectType>
              </div>
            </div>
            <div v-show="selectRoom === 2">
             <div class="row">
                 <span class="label">
                   <img src="../assets/imgs/img_Create_Thenumberofinnings.png" alt="" width="100%">
                </span>
                <selectType :types="ds3_1" :bigImg="bigImg" class="bar" @on-select="selectTime3"></selectType>
              </div>
              <div class="row">
                 <span class="label">
                   <img src="../assets/imgs/img_Create_Froction.png" alt="" width="100%">
                </span>
                <selectType :types="ds3_2" :bigImg="bigImg" class="bar" @on-select="selectScore3"></selectType>
              </div>
            </div>
          </div>
        </div>
        <div class="foot-change">
          <div class="toggle">
            <span class="box" @click="createGameRoom">
              <img src="../assets/imgs/img_Create_Createaroom.png" alt="" width="100%">
            </span>
          </div>
          <div class="toggle">
            <span class="box" @click="invoiceGameRoom">
              <img src="../assets/imgs/img_Create_Topoenaroom.png" alt="" width="100%">
            </span>  
          </div>    
        </div>
      </div>
    </Modal>

    <!--加入房间-->
    <Modal :showModal="showJoinRoom"
    @on-close="closeJoinRoom"
    class="join-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img_Join_background.png" alt=""  width="100%" height="100%">
    </div>
      <div slot="title" class="join-title title-active">
        <img src="../assets/imgs/img_Join_joinyourroom.png" alt=""  width="100%">
      </div>
      <div slot="body" class="join-body">
        <div class="fill-room-card">
          <div class="box">
            <span class="num-item" v-for="(item, index) in roomNums" :key="index">
              {{item}}
            </span>
          </div>
        </div>
        <div class="select-num">
          <div class="num-row" v-for="(item, index) in nums" :key="index">
            <span class="num-cell" v-for="(its, i) in item" :key="i" @click="selectNum(its.num, i)">
              <img :src="its.img" alt="" width="100%" height="100%">
            </span>
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import tabImgs from './tabImgs'
const MAX_ROOM_NUM = 4
const HEAD_IMG_SIZE = 0

export default {
  name: 'HelloWorld',
  data () {
    return {
      userInfo: {
        id: '',
        nickname: '',
        roomNum: '',
        headimgurl: ''
      },
      showRuleModal: false,
      showMsgModal: false,
      showCreateRoom: false,
      showJoinRoom: false,
      tabs: [
        {
          img: tabImgs.hoverImgs[0],
          select: true
        },
        {
          img: tabImgs.imgs[1],
          select: false
        },
        {
          img: tabImgs.imgs[2],
          select: false
        }
      ],
      gameTabs: [],
      selectGame: 0,
      ds1: [
        {
          img: tabImgs.tables[0],
          select: true,
          value: 10
        },
        {
          img: tabImgs.tables[1],
          select: false,
          value: 30
        }
      ],
      ds2: [
        {
          img: tabImgs.scores[0],
          select: true,
          value: 20
        },
        {
          img: tabImgs.scores[1],
          select: false,
          value: 50
        },
        {
          img: tabImgs.scores[2],
          select: false,
          value: 100
        },
        {
          img: tabImgs.scores[3],
          select: false,
          value: 200
        }
      ],
      ds0_1: [],
      ds0_2: [],
      ds1_1: [],
      ds1_2: [],
      ds2_1: [],
      ds2_2: [],
      ds3_1: [],
      ds3_2: [],
      nums: tabImgs.nums,
      roomNums: ['', '', '', ''],
      numIndex: 0,
      createRoomTabs: [],
      selectRoom: 0,
      bigImg: false,
      public: '',
      qtRules: '',
      htRules: '',
      djRules: '',
      createRoomData: {
        round: 10,
        score: 20,
        substitute: false
      }
    }
  },
  created () {
    // 初始化数据
    this.handleArray([this.gameTabs, this.createRoomTabs], this.tabs)
    this.handleArray([this.ds1_1, this.ds2_1, this.ds3_1], this.ds1)
    this.handleArray([this.ds1_2, this.ds2_2, this.ds3_2], this.ds2)
    // 防止切换路由用户数据丢失
    // if (this.userMsg) {
    let vm = this
    let params = {
      openid: this.userMsg.openid,
      nickname: this.userMsg.nickname,
      sex: this.userMsg.sex,
      headimgurl: this.userMsg.headimgurl
    }
    let ajaxParams = window.JSON.stringify(this.$url + this.$interface['/user/login'] + this.$sign(params))
    this.$JsBridge.callHandler(
      'getUserMsg' // 原生的方法名
      , {'param': ajaxParams} // 带个原生方法的参数
      , function (responseData) { // 响应原生回调方法
        let data = window.JSON.parse(responseData)
        data.model.headimgurl = data.model.headimgurl + HEAD_IMG_SIZE
        Object.keys(vm.userInfo).forEach((key) => {
          vm.userInfo[key] = data.model[key]
        })
        vm.$store.dispatch('userInfo', vm.userInfo)
      }
    )
   // }
  },
  computed: mapGetters({
    userMsg: 'listenWxUser',
    users: 'listenUser',
    indexPublic: 'listenPublic',
    qtRule: 'listenQtRule',
    htRule: 'listenHtRule',
    djRule: 'listenDjRule'
  }),
  watch: {
    userMsg (val) {
      let vm = this
      let params = {
        openid: val.openid,
        nickname: val.nickname,
        sex: val.sex,
        headimgurl: val.headimgurl
        // openid: 'oO8p8wqkSseyl3KPu7Sm02jskdlw',
        // nickname: 'Jeffery',
        // sex: '1',
        // headimgurl: 'http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqevDmfhVRfeibjyianWrRWYlCSkjOAhgOiaDkAnHQkib6DVSsl8u8wSLPo5FEYCr0triauYl7DqkbiaKyg/0'
      }
      let ajaxParams = window.JSON.stringify(this.$url + this.$interface['/user/login'] + this.$sign(params))
      // let vm = this
      // this.$axios.get('/user/login?' + ajaxParams)
      // .then(function (res) {
      //   Object.keys(vm.userInfo).forEach((key) => {
      //     vm.userInfo[key] = res.data.model[key]
      //   })
      //   vm.userInfo.headimgurl = params.headimgurl
      //   console.log(vm.userInfo)
      //   window.android.read(window.JSON.stringify(res))
      // })
      // .catch(function (error) {
      //   console.log(error)
      //   window.android.read('出错了')
      //   window.android.read(window.JSON.stringify(error))
      // })
     // this.$store.dispatch('userInfo', ajaxParams)
      this.$JsBridge.callHandler(
        'getUserMsg' // 原生的方法名
        , {'param': ajaxParams} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          let data = window.JSON.parse(responseData)
          data.model.headimgurl = data.model.headimgurl + HEAD_IMG_SIZE
          Object.keys(vm.userInfo).forEach((key) => {
            vm.userInfo[key] = data.model[key]
          })
          vm.$store.dispatch('userInfo', vm.userInfo)
        }
      )
    },
    users (val) {
      Object.keys(this.userInfo).forEach((key) => {
        this.userInfo[key] = val[key]
      })
    },
    indexPublic (val) {
      this.public = val.model
    },
    qtRule (val) {
      this.qtRules = val.model
    },
    htRule (val) {
      this.htRules = val.model
    },
    djRule (val) {
      this.djRules = val.model
    }
  },
  methods: {
    handleArray (arr, data) {
      arr.forEach((item, index) => {
        data.forEach((its, i) => {
          if (its instanceof Object) {
            let obj = {}
            Object.keys(its).forEach((key) => {
              obj[key] = its[key]
            })
            item.push(obj)
          }
        })
      })
    },
    message () {
      this.$audio.play(this.$audio.ui)
      this.showMsgModal = true
      let vm = this
      let ajaxParams = window.JSON.stringify(this.$url + this.$interface['/get/notice'] + this.$sign({}))
      // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'getPublic' // 原生的方法名
        , {'param': ajaxParams} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          let data = window.JSON.parse(responseData)
          vm.public = data.model
          vm.$store.dispatch('publicAjax', data)
        }
      )
     // this.$store.dispatch('publicAjax', ajaxParams)
    },
    closeMsgModal () {
      this.showMsgModal = false
    },
    help () {
      this.$audio.play(this.$audio.ui)
      this.showRuleModal = true
      let vm = this
      let ajaxParams1 = window.JSON.stringify(this.$url + this.$interface['/get/rule/qingtui'] + this.$sign({}))
      let ajaxParams2 = window.JSON.stringify(this.$url + this.$interface['/get/rule/huntui'] + this.$sign({}))
      let ajaxParams3 = window.JSON.stringify(this.$url + this.$interface['/get/rule/dajiu'] + this.$sign({}))
      if (!this.qtRules) {
        // 调用android原生内部方法
        this.$JsBridge.callHandler(
          'getQtMsg' // 原生的方法名
          , {'param': ajaxParams1} // 带个原生方法的参数
          , function (responseData) { // 响应原生回调方法
            let data = window.JSON.parse(responseData)
            vm.qtRules = data.model
            vm.$store.dispatch('qtRuleAjax', data)
          }
        )
      }
      if (!this.htRules) {
        this.$JsBridge.callHandler(
          'getHtMsg' // 原生的方法名
          , {'param': ajaxParams2} // 带个原生方法的参数
          , function (responseData) { // 响应原生回调方法
            let data = window.JSON.parse(responseData)
            vm.htRules = data.model
            vm.$store.dispatch('htRuleAjax', data)
          }
        )
      }
      if (!this.djRules) {
        this.$JsBridge.callHandler(
          'getDjMsg' // 原生的方法名
          , {'param': ajaxParams3} // 带个原生方法的参数
          , function (responseData) { // 响应原生回调方法
            let data = window.JSON.parse(responseData)
            vm.djRules = data.model
            vm.$store.dispatch('djRuleAjax', data)
          }
        )
      }
      // this.$store.dispatch('qtRuleAjax', ajaxParams)
      // this.$store.dispatch('htRuleAjax', ajaxParams)
      // this.$store.dispatch('djRuleAjax', ajaxParams)
    },
    closeRuleModal () {
      this.showRuleModal = false
    },
    setting () {
      this.$audio.play(this.$audio.ui)
      this.$refs.setting.openSetModal()
    },
    createRoom () {
      this.$audio.play(this.$audio.btn)
      this.showCreateRoom = true
      this.createRoomData.substitute = false
    },
    closeCreateRoom () {
      this.showCreateRoom = false
    },
    joinRoom () {
      this.$audio.play(this.$audio.btn)
      this.showJoinRoom = true
    },
    closeJoinRoom () {
      this.showJoinRoom = false
    },
    selectNum (item, index) {
      this.$audio.play(this.$audio.ui)
      if (typeof item === 'string') {
        this.handleSelect(index)
        return false
      }
      // 填入数字
      if (this.numIndex > MAX_ROOM_NUM - 1) {
        return false
      }
      this.$set(this.roomNums, this.numIndex, item)
      this.numIndex++
      // 进入房间
      if (this.numIndex === MAX_ROOM_NUM) {
        // this.$store.dispatch('getRoom', this.roomNums.join(''))
        // window.android.saveRoom(this.roomNums.join(''))
        this.$router.push({path: '/game', params: {}})
      }
    },
    handleSelect (index) {
      this.$audio.play(this.$audio.ui)
      if (index === 0) {
        // 重输
        this.roomNums.forEach((item, index) => {
          this.$set(this.roomNums, index, '')
        })
        this.numIndex = 0
      } else if (index === 2) {
        // 删除
        if (this.numIndex < 0) {
          return false
        }
        this.numIndex--
        this.$set(this.roomNums, this.numIndex, '')
      }
    },
    changeGame (index) {
      this.$audio.play(this.$audio.ui)
      this.gameTabs.forEach((item, ids) => {
        if (ids === index) {
          item.img = tabImgs.hoverImgs[ids]
        } else {
          item.img = tabImgs.imgs[ids]
        }
      })
      this.selectGame = index
    },
    changeRoom (index) {
      this.$audio.play(this.$audio.ui)
      this.createRoomTabs.forEach((item, ids) => {
        if (ids === index) {
          item.img = tabImgs.hoverImgs[ids]
        } else {
          item.img = tabImgs.imgs[ids]
        }
      })
      this.selectRoom = index
    },
    selectTime1 (data) {
      this.$audio.play(this.$audio.ui)
      this.createRoomData.round = data
    },
    selectScore1 (data) {
      this.$audio.play(this.$audio.ui)
      this.createRoomData.score = data
    },
    selectTime2 () {
      this.$audio.play(this.$audio.ui)
    },
    selectScore2 () {
      this.$audio.play(this.$audio.ui)
    },
    selectTime3 () {
      this.$audio.play(this.$audio.ui)
    },
    selectScore3 () {
      this.$audio.play(this.$audio.ui)
    },
    createGameRoom () {
      this.$audio.play(this.$audio.btn)
      let params = {
        userId: this.userInfo.id,
        baseScore: this.createRoomData.score,
        baseRound: this.createRoomData.round,
        substitute: this.createRoomData.substitute
      }
      let ajaxParams = window.JSON.stringify(this.$url + this.$interface['/room/create'] + this.$sign(params))
      // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'createRoom' // 原生的方法名
        , {'param': ajaxParams} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
        }
      )
    },
    invoiceGameRoom () {
      this.$audio.play(this.$audio.btn)
      this.createRoomData.substitute = true
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
.g-flex-column{
  display: flex;
  flex-direction: column;
}
.g-flex-row{
  display: flex;
  flex-direction: row;
}
.user-face{
  font-size: 36px;
  height: 100%;
  // background: url('../assets/imgs/background.png') 0 0 no-repeat;
  // background-size: 100% 100%;
  .face-bar{
    flex: 0 0 210px;
    height: 210px;
    padding: 0 60px;
    background: url('../assets/imgs/bar.png') 0 0 no-repeat;
    background-size: 100% 100%;
    .base-settting{
      flex: 0 0 450px;
      width: 450px;
      margin: 30px 0;
      justify-content: flex-end;
      li{
        width: 150px;
        height: 150px;
        line-height: 150px;
        margin-left: 40px;
        img{
          position: relative;
          top: 40%;
          transform: translateY(-50%);
        }
      }
      li:first-child{
        img{
          transform: translateY(-60%);
        }
      }
    }
    .user-info {
      flex: 1;
      margin: 30px 0;
      font-size: 32px;
      color: #fff; 
      .f-relative{
        position: relative;
        width: 180px;
        height: 50px;
        line-height: 50px;
        padding-left: 80px;
        padding-top: 6px;
        text-align: left;
        color: #fff;
      }
      .user-avatar{
        position: relative;
        flex: 0 0 150px;
        width: 150px;
        height: 150px;
        line-height: 150px;
        margin-right: 30px;
        border-radius: 20px;
        overflow: hidden;
        background-image: url('../assets/imgs/Avatarframe.png');
        background-size: 100% 100%;
        .user-img{
          position: absolute;
          top: -2px;
          left: 0;
          width: 118px;
          height: 118px;
          padding: 15px;
          border-radius: 20px;
          overflow: hidden;
          img{
            width: 118px;
            height: 118px;
            border-radius: 20px;
            border: 1px solid #000;
            box-shadow: 0px 0px 10px #000;
          }
        }
      }
      .user-id-card{
        margin-right: 30px;
        .user-name{
          background-image: url('../assets/imgs/username.png');
          background-size: 100% 100%;
        }
        .user-id{
          background: url('../assets/imgs/userID.png') 0 0 no-repeat;
          background-size: 100% 100%;
        }
      }
      .user-room-card{
        .user-card{
          background: url('../assets/imgs/roomcard.png') 0 0 no-repeat;
          background-size: 100% 100%;
        }
      }
    }
  }
  .voice-info{
    position: relative;
    left: 50%;
    transform: translateX(-50%);
    width: 50%;
    height: 60px;
    line-height: 60px;
    text-align: left;
    color: #fff;
    background: url('../assets/imgs/Announcement_background.png') 0 0 no-repeat;
    background-size: 100% 100%;
    p.text{
      margin-left: 180px;
    }
    .s-icon{
      position: absolute;
      top: 5px;
      left: 60px;
      width: 80px;
      height: 50px;
      background: url('../assets/imgs/Announcement.png') 0 0 no-repeat;
      background-size: 100% 100%;
    }
  }
  .face-body{
     flex: 1;
     padding-top: 10px;
     .bg-girl{
       flex: 0 0 520px;
       width: 520px;
       margin-left: 60px;
       img{
         width: 80%;
       }
     }
     .all-room{
       flex: 1;
       .room-item{
         width: 600px;
         height: 120px;
         line-height: 120px;
         font-size: 66px;
         transform: skewX(-10deg);
         color: #fff;
       }
       .create-room{
         margin-top: 40px;
       }
       .join-room{
         margin-top: 84px;
       }
     }
  }
}
.title-active{
  display: inline-block;
}

.rule-body, .create-body{
  margin-top: 30px;
  color: #fff;
}
.msg-body{
  margin: 60px 40px 30px 40px;
  color: #fff;
}
.rule-body, .create-content{
  display: flex;
  .game-tabs{
    flex: 0 0 230px;
    width: 230px;
    padding: 0 15px;
    span{
      display: block;
      width: 100%;
      height: 72px;
      line-height: 72px;
      margin-top: 30px;
      img{
        width: 100%;
      }
    }
  }
  
}
.rule-body{
  .game-box{
    flex: 0 0 58%;
    padding: 0 3% 0 3%;
    width: 84%;
    height: 44vh;
    margin: 5vh 0 5vh 3%;
    overflow: auto;
  }
}
 .create-content{
   .game-box{
    flex: 0 0 70%;
    padding: 0 1% 0 1%;
    height: 35vh;
    margin: 2vh 0;
    overflow: auto;
  }
 }
.join-modal{
  .modal-content{
    .join-body{
      margin-top: 15px;
      .fill-room-card{
        display: flex;
        flex-direction: row;
        align-items: center;
        flex: 1;
        width: 100%;
        height: 60px;
        // padding: 10px 14%;
        background: url('../assets/imgs/img_Join_input.png') 0 0 no-repeat;
        background-size: 100% 100%;
        .box{
          display: flex;
          flex-direction: row;
          width: 100%;
          margin: 0 14%;
          height: 60px;
          font-size: 0;
          .num-item{
            display: inline-block;
            flex: 0 0 24%;
            width: 24%;
            height: 60px;
            line-height: 60px;
            text-align: center;
            font-size: 72px;
            color: #fff;
          }
          .num-item:first-child{
            flex: 0 0 23%;
            width: 23%;
            margin-left: 15px;
          }
        }
      }
      .select-num{
        display: flex;
        flex-direction: column;
        align-items: center;
        .num-row{
          display: flex;
          flex-direction: row;
          flex: 1;  
          .num-cell{
            flex: 1;
            width: 245px;
            height: 60px;
            margin-top: 20px;
            color: pink;
          }
          .num-cell:not(:last-child) {
            margin-right: 50px;
          }
        }
      }
    }
  }
}
.create-body{
  font-size: 0;
.row{
  // display: flex;
  flex-direction: row;
  align-items: center;
  .label{
    display: inline-block;
    //flex: 0 0 100px;
    width: 100px;
    vertical-align: top;
  }
  
  .bar{
    //flex: 1;
     display: inline-block;
     width: 80%;
  }
}
.row:nth-child(2){
.label{
    vertical-align: top;
  }
}
}
.set-modal{
  .row{
    margin: 80px 0;
  }
}
.create-modal{
  .create-content{
    .game-box{
      height: 100%;
      .row{
        padding: 25px 0 25px 0;
      }
    }
  }
}
.foot-change{
  font-size: 0;
  width: 80%;
  margin: 24px auto;
  .toggle{
    display: inline-block;
    width: 50%;
    text-align: center;
    .box{
      display: inline-block;
      width: 240px;
    }
  }
}

</style>
