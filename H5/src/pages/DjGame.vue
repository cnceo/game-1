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
           'r-site': (index !== 0) && (index % 2 === 0)}" v-show="item.roomOwner == true">
            <img src="../assets/imgs/img_Room_owner.png" alt="" width="100%" height="100%">
           </div>
           <!-- {{item.banker == true}} -->
           <div class="zuja" :class="{'l-site': (index === 0) || (index % 2 !== 0),
           'r-site': (index !== 0) && (index % 2 === 0)}" v-show="item.banker == true">
           <!-- {{item.banker == true}} -->
            <img src="../assets/imgs/img_Setuphualun.png" alt="" width="100%" height="100%">
           </div>
          <!-- <div class="result" :class="{'cur-user': index === 0}">
            <span v-show="item.status === 0 && gameEnd" class="lost">
              <img :src="result[1]" alt="" width="100%">
            </span>
            <span v-show="item.status === 1 && gameEnd" class="tie">
              <img :src="result[2]" alt="" width="100%">
            </span>
            <span v-show="item.status === 2 && gameEnd" class="win">
              <img :src="result[0]" alt="" width="100%">
            </span>
          </div> -->
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
        <div class="status" :class="{'l-site': (index !== 0) && (index % 2 !== 0),
         'r-site': (index % 2 === 0), 'c-site': (index === 0)}">
            <span v-show="item.ready == false && item.roomOwner == false && item.curUser == true" @click="startReady">
              <img src="../assets/imgs/img_Room_ready.png" alt="" width="100%" height="100%">
            </span>
            <span v-show="item.ready == true && item.roomOwner == false && isStartReady">
              <img src="../assets/imgs/img_Room_readying.png" alt="" width="100%" height="100%">
            </span>
            <!-- {{item.ready == false }}
            {{item.roomOwner == true}}
            {{isFriends == true}}
            {{isFirst == true}} -->
            <span v-show="item.ready == false && item.roomOwner == true && isFriends == true && isFirst == true" @click="startGame">
              <img src="../assets/imgs/img_Setup_Exchangeaccount.png" alt="" width="100%" height="100%">
            </span>
          
            <span v-show="item.ready == false && item.roomOwner == true && isFirst == false && item.curUser == true" @click="startReady">
              <img src="../assets/imgs/img_Room_ready.png" alt="" width="100%" height="100%">
            </span>
            <span v-show="item.ready == true && item.roomOwner == true && isStartReady">
              <img src="../assets/imgs/img_Room_readying.png" alt="" width="100%" height="100%">
            </span>
         </div>
        <!-- <div class="card status" :class="{'l-site': (index !== 0) && (index % 2 !== 0),
         'r-site': (index % 2 === 0), 'c-site': (index === 0)}">
         
            <ul class="card-list" v-show="cardList.length > 0">
              <li v-for="(item, index) in cardList" :key="index">
                <img :src="item.img" alt="">
              </li>
            </ul>
         </div> -->
        <div class="xz-tip">
          <span v-show="item.xz === 0 && item.banker == false && isDownSu == false">
            <img src="../assets/imgs/img_Bet_qingxiazhu.png" alt="" width="100%" height="100%">
          </span>
          <span v-show="item.xz === 1 && item.banker == false && isDownSu == false">
            <img src="../assets/imgs/img_Bet_zhengzaixiazhu.png" alt="" width="100%" height="100%">
          </span>
        </div>
      </div>
    </div>
    <div class="user-site">
      <div class="user-item" v-for="(item, index) in scores" :key="index"
      :class="{'site0': index === 0, 'site1': index === 1, 'site2': index === 2,
      'site3': index === 3, 'site4': index === 4}">
        <div class="avater">
          <div class="result" :class="{'cur-user': index === 0}">
            <span v-show="item.winScore < 0 " :class="{'lost': item.winScore < 0}">
              <img :src="result[1]" alt="" width="100%">
            </span>
            <span v-show="item.winScore == 0" :class="{'tie': item.winScore == 0}">
              <img :src="result[2]" alt="" width="100%">
            </span>
            <span v-show="item.winScore > 0" :class="{'win': item.winScore > 0}">
              <img :src="result[0]" alt="" width="100%">
            </span>
          </div>
          <div class="xz-tip score">
            <span class="sign" v-show="item.winScore >= 0">+</span>
            <span class="sign" v-show="item.winScore < 0">-</span>
            <span class="num">{{item.winScore < 0 ? (-item.winScore) : item.winScore}}</span>
          </div>
          <div class="men-list">
            <ul v-if ="item.banker == false && item.userDoorVOList.length && item.userDoorVOList.length > 0">
              <li v-for="(its, index) in item.userDoorVOList" :key="index">
                <span v-show="its.doorNum == 1">
                  <img src="../assets/imgs/img-Chips-chu.png" alt="">
                </span>
                <span v-show="its.doorNum == 2">
                  <img src="../assets/imgs/img-Chips-tian.png" alt="">
                </span>
                <span v-show="its.doorNum == 3">
                  <img src="../assets/imgs/img-Chips-kan.png" alt="">
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!-- 发牌区域 -->
     <div class="cards" v-show="showCards">
      <ul class="card-list" ref="card" v-show="showCards"
         :class="{'active': showCards}">
        <li v-for="(item, index) in cardList" :key="index"
        v-if="index === 0 || index === 2 || index === 4 || index === 6"
        :class="{'card0': index === 0, 'card2': index === 2,
        'card4': index === 4, 'card6': index === 6, 'reverse': checkResult1}">
          <img :src="item.bImg" alt="" class="front">
          <img :src="item.fImg" alt="" class="back">
        </li>
        <li v-for="(item, index) in cardList" :key="index"
        v-if="index === 1 || index === 3 || index === 5 || index === 7"
        :class="{'card1': index === 1, 'card3': index === 3, 
        'card5': index === 5, 'card7': index === 7, 'reverse': checkResult2}">
          <img :src="item.bImg" alt="" class="front">
          <img :src="item.fImg" alt="" class="back">
        </li>
      </ul>
    </div>
    <!-- 游戏桌面 -->
    <div class="game-table">
      <div class="all-operate g-flex-row">
        <div class="host g-flex">
          <div class="host-bg">
            <div class="qz-bg bg" v-if="qz" @touchstart="isQz">
              <img src="../assets/imgs/img_Create_qiangzhuang.png" alt="" width= "100%">
            </div>
             <div class="bqz-bg bg" v-else @touchstart="isQz">
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
          <span class="rate">{{gameMsg.currentRound}}/{{gameMsg.baseRound}}</span>
          <span class="time">{{date}}</span>
          <span class="power"></span>
        </div>
        <div class="blank g-flex">
        </div>
        <div class="games">
          <img src="../assets/imgs/img_Room_roomnumber.png" alt="" width= "100%">
          <div class="box">
            <div class="rate">{{gameMsg.numId}}</div>
            <div class="room-num">{{gameMsg.currentRound}}/{{gameMsg.baseRound}}</div>
          </div>
        </div>
        <div class="setting" @touchstart="setting">
          <img src="../assets/imgs/img_Room_setup.png" alt="" width= "100%">
        </div>
        <div class="js-room">
          <img src="../assets/imgs/img_Room_dissolvetheroom.png" alt="" width= "100%" 
          v-if="isMaster || isGameStart" @touchstart="releaseRoom">
          <img src="../assets/imgs/img_Room_exitroom.jpg" alt="" width= "100%" 
          v-else @touchstart="exitRoom">
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
      <div class="room-num">房间号： {{gameMsg.numId}}</div>
      <div class="geme-type">
        <span v-show="gameMsg.gameType == '1'">清推</span>
        <span v-show="gameMsg.gameType == '2'">混推</span>
        <span v-show="gameMsg.gameType == '3'">大九</span>
        <span>{{gameMsg.baseRound}}局</span>
        <span>{{gameMsg.baseScore}}分封顶</span>
      </div>
    </div>
    <!-- 牌面 -->
    <div class="card-table g-flex-row" v-show="isGameStart">
      <div class="chu g-flex" v-for="(item, index) in tbg" :key="index"
      :class="{'chu': index === 0, 'tian': index === 1, 'kan': index === 2}">
        <img :src="item" alt="" height="100%">
        <div class="coin-box" v-show="index === 0">
          <div class="coins chu-coins" v-show="chuType">
            <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
            'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
            'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'active': chuType}" 
            >
            <img :src="item" alt="" width="100%">
            </span>
          </div>
        </div>
        <div class="coin-box" v-show="index === 1">
          <div class="coins tian-coins" v-show="tianType">
            <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
            'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
            'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'active': tianType}" 
            >
            <img :src="item" alt="" width="100%">
            </span>
          </div>
        </div>
        <div class="coin-box" v-show="index === 2">
          <div class="coins kan-coins" v-show="kanType">
              <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
              'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
              'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'active': kanType}" 
              >
              <img :src="item" alt="" width="100%">
              </span>
          </div>
        </div>
      </div>

    </div>
    <!-- <div style="padding: 60px;">
    <span style="display: block;width: 50px;height: 50px; background: #eee;" class="win" v-show="showCoins"></span>
    <div style="padding: 60px;"></div>
    <span style="display: block;width: 50px;height: 50px; background: #eee;" class="lost" v-show="showCoins"></span>
    </div>
    <button @click="xzOk">点击</button> -->
    <!-- 邀请好友 -->
    <div class="yq-friend g-flex-row" @touchstart="invateFriend" v-show="isMaster">
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
                <span class="num" v-for="(item, index) in lowz" :key="index" @click="selectCmScore(item.value)">
                  <img :src="item.img" alt="">
                </span>
              </div>
              <div class="total">
                <span class="res">{{cmScore}}</span>
                <span class="close" @click="deleteCmScore">
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
                <span class="num" v-for="(item, index) in lowz" :key="index" @click="selectTmScore(item.value)">
                  <img :src="item.img" alt="">
                </span>
              </div>
              <div class="total">
                <span class="res">{{tmScore}}</span>
                <span class="close" @click="deleteTmScore">
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
                <span class="num" v-for="(item, index) in lowz" :key="index" @click="selectKmScore(item.value)">
                  <img :src="item.img" alt="">
                </span>
              </div>
              <div class="total">
                <span class="res">{{kmScore}}</span>
                <span class="close" @click="deleteKmScore">
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
    <!-- 退出房间弹窗 -->
    <Modal :showModal="showExitModal"
    class="exit-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img-Stoppingdoor-background.png" alt=""  width="100%" height="100%">
    </div>
      <!-- <div slot="title" class="xz-title title-active">
         <img src="../assets/imgs/img_Bet_title.png" alt=""  width="100%">
      </div> -->
      <div slot="body" class="exit-body">
      {{exitText}}
      </div>
      <div slot="foot" class="exit-foot">
        <div class="ok exit-btn" @touchstart="exitRoomOk">
           <img src="../assets/imgs/img-Stoppingdoor-confirm.png" alt=""  width="100%">
        </div>
        <div class="cancel exit-btn" @touchstart="exitRoomCancel">
           <img src="../assets/imgs/img-Stoppingdoor-nostoppingdoor.png" alt=""  width="100%">
        </div>
      </div>
    </Modal>
    <!-- 游戏未开始房主解散房间弹窗 -->
    <Modal :showModal="showReleaseWaitModal"
    class="exit-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img-Stoppingdoor-background.png" alt=""  width="100%" height="100%">
    </div>
      <!-- <div slot="title" class="xz-title title-active">
         <img src="../assets/imgs/img_Bet_title.png" alt=""  width="100%">
      </div> -->
      <div slot="body" class="exit-body">
      {{releaseWaitText}}
      </div>
      <div slot="foot" class="exit-foot">
        <div class="ok exit-btn" @touchstart="releaseWaitOk">
           <img src="../assets/imgs/img-Stoppingdoor-confirm.png" alt=""  width="100%">
        </div>
        <div class="cancel exit-btn" @touchstart="releaseWaitCancel">
           <img src="../assets/imgs/img-Stoppingdoor-nostoppingdoor.png" alt=""  width="100%">
        </div>
      </div>
    </Modal>
    <!-- 游戏中解散房间弹窗 -->
    <Modal :showModal="showReleaseReadyModal"
    class="exit-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img-Stoppingdoor-background.png" alt=""  width="100%" height="100%">
    </div>
      <!-- <div slot="title" class="xz-title title-active">
         <img src="../assets/imgs/img_Bet_title.png" alt=""  width="100%">
      </div> -->
      <div slot="body" class="exit-body">
      {{releaseReadyText}}
      </div>
      <div slot="foot" class="exit-foot">
        <div class="ok exit-btn" @touchstart="releaseReadyOk">
           <img src="../assets/imgs/img-Stoppingdoor-confirm.png" alt=""  width="100%">
        </div>
        <div class="cancel exit-btn" @touchstart="releaseReadyCancel">
           <img src="../assets/imgs/img-Stoppingdoor-nostoppingdoor.png" alt=""  width="100%">
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import tabImgs from './tabImgs'
import {mapGetters} from 'vuex'

const HEAD_IMG_SIZE = 0
const MIN_USER = 2
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
      users: [],
      showSetModal: false,
      socket: null,
      id: '',
      showXzModal: false,
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
        img: tabImgs.lowz[0].img,
        value: tabImgs.lowz[0].value
      },
      {
        img: tabImgs.lowz[1].img,
        value: tabImgs.lowz[1].value
      },
      {
        img: tabImgs.lowz[2].img,
        value: tabImgs.lowz[2].value
      }],
      lowzMax: [{
        img: tabImgs.lowzMax[0].img,
        value: tabImgs.lowzMax[0].value
      },
      {
        img: tabImgs.lowzMax[1].img,
        value: tabImgs.lowzMax[1].value
      },
      {
        img: tabImgs.lowzMax[2].img,
        value: tabImgs.lowzMax[2].value
      },
      {
        img: tabImgs.lowzMax[3].img,
        value: tabImgs.lowzMax[3].value
      }],
      showCoins: false,
      isFirst: true,
      gameMsg: {},
      cmScore: 0, // 出门下注分数
      tmScore: 0, // 天门下注分数
      kmScore: 0, // 坎门下注分数
      cmType: 0, // 出门下注类型
      tmType: 0, // 天门下注类型
      kmType: 0, // 坎门下注类型
      roomNum: '', // 房间号
      roomId: '', // 房间id,
      userId: '', // 用户id
      isMaster: false, // 是否是房主
      isShowXz: true, // 庄家没有投注
      isFriends: false, // 是否邀请好友，游戏中至少2个人
      isStartReady: false, // 房主是否点击开始，
      // isReady: false // 用户是否已经准备就绪,
      isGameStart: false, // 是否游戏已经开始,
      disbandType: 0, // 游戏中解散房间类型，解散请求和响应
      disbandAgree: true, // 游戏中解散是否同意
      showExitModal: false,
      exitText: '', // 用户退出提示文字
      showReleaseWaitModal: false,
      releaseWaitText: '', // 房主提示文字
      showReleaseReadyModal: false,
      releaseReadyText: '', // 游戏中解散房间提示文字
      isCurUserReady: false,
      curCardBg: tabImgs.cards[3],
      cards: tabImgs.djCards, // 所有的牌组合方式
      cardList: [],
      showCards: false,
      checkResult1: false, // 查看结果，翻牌
      checkResult2: false, // 查看结果，翻牌
      isDown: false, // 是否已经下注过
      isDownSu: false, // 最后一个人下注完成
      chuType: false, // 投注类型：出门
      tianType: false, // 投注类型：天门
      kanType: false, // 投注类型：坎门
      coinList: [], // 投注列表
      gameEnd: false, // 显示游戏结束结果字样
      scores: [] // 最终结果
    }
  },
  props: {
    dj: {
      type: Boolean,
      default: false
    },
    uid: {
      type: String,
      dafault: ''
    },
    rid: {
      type: String,
      dafault: ''
    },
    ds: {
      type: Object,
      default: function () {
        return {}
      }
    },
    isOwner: {
      type: Boolean,
      dafault: false
    },
    cardBg: {
      type: String,
      dafault: ''
    }
  },
  computed: mapGetters({
  //  ds: 'listenGameUser',
    roomMsg: 'listenRoomMsg',
    curUser: 'listenUser',
    curUserId: 'listenUserId'
  }),
  watch: {
    dj (val) {
      this.showDj = val
    },
    // ds: {
    //   handler (val) {
    //     console.log(val)
    //     this.users = []
    //     val.forEach((item) => {
    //       if (item instanceof Object) {
    //         item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
    //         this.users.push(item)
    //       }
    //     })
    //   },
    //   deep: true
    // },
    // curUser (val) {
    //   console.log('sssssssssssssssss')
    //   for (let key in val) {
    //     console.log(key + ':' + val(key))
    //   }
    //   this.userId = val.id
    // },
    uid (val) {
      this.userId = val
    },
    rid (val) {
      this.roomId = val
    },
    ds (val) {
      console.log('获取陈宫进入房间信息')
      for (let key in val) {
        console.log(key + ':' + val[key])
      }
      this.gameMsg = val
    },
    // ds (val) {
    //   this.users = []
    //   let arr = []
    //   val.forEach((item) => {
    //     if (item instanceof Object) {
    //       item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
    //       let obj = {}
    //       for (let key in item) {
    //         obj[key] = item[key]
    //       }
    //       obj.nickname = 'Tom'
    //       obj.userId = '99000'
    //       arr.push(obj)
    //       arr.push(obj)
    //       arr.push(item)
    //       arr.push(obj)
    //       arr.push(obj)
    //     }
    //   })
    //   arr.forEach((item) => {
    //     if (item.userId === this.userId) {
    //       this.users.unshift(item)
    //     } else {
    //       this.users.push(item)
    //     }
    //   })
    //   // 获取当前设置对应信息
    //   // roomId、userId
    //   // 判断游戏中用户人数是否至少为2人
    //   if (this.users >= MIN_USER) {
    //     this.isFriends = true
    //   }
    // }
    isOwner (val) {
      // this.roomId = val.roomId
      // this.roomNum = val.numId
      // 房间创建者可以邀请好友
      // console.log('哈哈哈，我是房主')
      // this.isMaster = true
      // this.isShowXz = true
    },
    cardBg (val) {
      this.curCardBg = val
    }
  },
  created () {
    this.showDj = this.dj
    this.users = this.ds
    this.gameMsg = this.ds
    this.curCardBg = this.cardBg
    console.log('hahahahahahahah我是北极光图')
    console.log(this.curCardBg)
    // 注册交互事件
    this.registerFn()
  },
  methods: {
    registerFn () {
      let vm = this
      // 系统更新加入游戏的用户列表
      this.$JsBridge.registerHandler('updateUsers', function (data, responseCallback) {
        console.log('游戏中用户列表更新了')
        console.log(data)
        // 将原生带来的参数，显示在show标签位置
        vm.users = []
        let arr = []
        let arrs = []
        // let val = []
        // arr = vm.$hds.handler(data)
        arrs = window.JSON.parse(data)
        arrs.forEach((item) => {
          if (item != null) {
            arr.push(item)
          }
        })
        // val.forEach((item) => {
        //   if (item instanceof Object) {
        //     item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
        //     item.ready = 'false'
        //     let obj = {}
        //     let objs = {}
        //     for (let key in item) {
        //       obj[key] = item[key]
        //     }
        //     for (let key in item) {
        //       objs[key] = item[key]
        //     }
        //     obj.nickname = 'Tom'
        //     obj.userId = '99000'
        //     obj.roomOwner = 'false'
        //     obj.takeBanker = 'false'
        //     obj.ready = 'true'
        //     objs.nickname = 'Tom'
        //     objs.userId = '99000'
        //     objs.roomOwner = 'false'
        //     objs.takeBanker = 'false'
        //     objs.ready = 'true'
        //     arr.push(obj)
        //     arr.push(obj)
        //     arr.push(item)
        //     arr.push(objs)
        //     arr.push(obj)
        //   }
        // })
        // 游戏开始，禁止用户加入
        // if (vm.isGameStart) {
        //   return
        // }
        // 如果人数最少2人，即可开始游戏
        if (arr.length < MIN_USER) {
          console.log('aaaaaaaaaaaaaaaaaaaa')
          arr.forEach((item) => {
            if (Number(item.userId) === Number(vm.userId)) {
              console.log('我是1个人' + item.userId)
              console.log('我是当前用户')
              item.curUser = true
              vm.getRoomMsg()
              // 房间创建者可以邀请好友
              if (item.roomOwner) {
                console.log('我是房主')
                vm.isMaster = true
              } else {
                vm.isMaster = false
              }
               // 当前玩家若是庄家，不显示下注
              if (item.banker === true) {
                vm.isShowXz = false
              } else {
                vm.isShowXz = true
              }
              // 当前玩家准备后不可以抢庄
              if (item.ready === true) {
                vm.isCurUserReady = true
              } else {
                vm.isCurUserReady = false
              }
              console.log('我在测试1')
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              vm.users.unshift(item)
              console.log('我在测试2')
            } else {
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              console.log('我在测试3')
              vm.users.push(item)
              console.log('我在测试4')
            }
          })
        } else {
          arr.forEach((item) => {
            if (Number(item.userId) === Number(vm.userId)) {
              console.log('我是多个人' + item.userId)
              console.log('我是当前用户')
              item.curUser = true
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              vm.getRoomMsg()
              // 房间创建者可以邀请好友
              if (item.roomOwner) {
                console.log('我是房主')
                vm.isMaster = true
              } else {
                vm.isMaster = false
              }
               // 当前玩家若是庄家，不显示下注
              if (item.banker === true) {
                vm.isShowXz = false
              } else {
                vm.isShowXz = true
              }
              // 当前玩家准备后不可以抢庄
              if (item.ready === true) {
                console.log('1111')
                vm.isCurUserReady = true
              } else {
                vm.isCurUserReady = false
              }
              console.log('2222')
              vm.users.unshift(item)
              console.log('333')
            } else {
              console.log('44')
              item.curUser = false
              console.log('555')
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              vm.users.push(item)
              console.log('666')
            }
          })
       //   console.log(window.JSON.stringify(vm.users))
          console.log('bbbbbbbbbbbbbbbbbbbbb')
        //  vm.playCards()
          // 显示准备按钮
          if (!vm.isStartReady) {
            console.log('cccccccccccccccc')
            vm.isStartReady = true
          } else {
            // 房主点击开始游戏
            console.log(vm.isOtherReady(vm.users))
            if (vm.isOtherReady(vm.users)) {
              console.log('eeeeee')
              vm.isFriends = true
              console.log(vm.isFriends)
           //   vm.playCards()
            }
            // 其他玩家点击准备按钮,判断是否都已经准备就绪
            console.log('ddddddddddddddddddddddd')
            if (vm.isAllUserReady(vm.users)) {
              console.log('kkkk')
              vm.isStartReady = false
              vm.isMaster = false
              vm.isFriends = false
              // 禁止抢庄
              vm.isGameStart = true
              // 开始发牌
              vm.playCards()
            }
          }
        }
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 系统发牌
      this.$JsBridge.registerHandler('updateCards', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
        console.log('获取到系统发牌')
        console.log(window.JSON.parse(data))
        vm.cardList = []
        let res = []
        let obj = {}
        let list = window.JSON.parse(data)
        list.forEach((item, index) => {
          if (Number(item.doorNum) === 0) {
            obj = item
          } else {
            res.push(item)
          }
        })
        res.push(obj)
        res.forEach((item, index) => {
          console.log(window.JSON.stringify(item))
         // let i = index * 2
         // let j = index * 2 + 1
          // 大九
          if (item.pokerList.length === 1) {
            let arr1 = []
            for (let key in item.pokerList[0]) {
              arr1.push(item.pokerList[0][key])
            }
            for (let i = 0; i < vm.cards.length; i++) {
              let arr2 = []
              if (vm.cards[i] instanceof Object) {
                for (let key in vm.cards[i]) {
                  if (key !== 'img') {
                    arr2.push(vm.cards[i][key])
                  }
                }
              }
               // 匹配对应的牌
              if (vm.regCard(arr1, arr2)) {
                console.log('当前匹配的牌')
                console.log(vm.curCardBg)
                vm.cardList.push({
                  bImg: vm.curCardBg,
                  fImg: vm.cards[i].img
                })
                vm.cardList.push({
                  bImg: vm.curCardBg,
                  fImg: vm.curCardBg
                })
              }
            }
          }
        })
        vm.showCards = true
        setTimeout(() => {
          vm.checkResult1 = true
          setTimeout(() => {
            vm.users.forEach((item) => {
              if (Number(item.userId) === Number(vm.userId)) {
                item.xz = 0
              } else {
                item.xz = 1
              }
              // if (item.banker === true) {
              //   item.isXz = false
              // } else {
              //   item.isXz = true
              // }
            })
            if (vm.isShowXz) {
              vm.showXzModal = true
            }
          }, 500)
        }, 2500)
        console.log(window.JSON.stringify(vm.cardList))
        // vm.showDj = false
        // vm.$emit('on-close', vm.showDj)
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 系统更新投注结果
      this.$JsBridge.registerHandler('updateCoins', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
        vm.cards = window.JSON.parse(data)
        console.log('投注结果收到了')
        console.log(window.JSON.stringify(vm.cards))
        // vm.showDj = false
        // vm.$emit('on-close', vm.showDj)
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 游戏未开始解散房间
      this.$JsBridge.registerHandler('releaseWait', function (data, responseCallback) {
        console.log('游戏未开始解散房间')
        // 将原生带来的参数，显示在show标签位置
        vm.users = window.JSON.parse(data)
        vm.showDj = false
        vm.$emit('on-close', vm.showDj)
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 游戏中解散房间
      this.$JsBridge.registerHandler('releaseReady', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
        console.log('游戏中解散房间')
        vm.users = window.JSON.parse(data)
        vm.disbandType = 2
        vm.releaseReadyText = '有用户在解散房间，您是否同意？'
        vm.showReleaseReadyModal = true
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 投注后更新结果
      this.$JsBridge.registerHandler('updateResult', function (data, responseCallback) {
        console.log('投注结果收到了')
        // 将原生带来的参数，显示在show标签位置
        vm.coinList = []
       // vm.isDownSu = true
        let res = window.JSON.parse(data)
        let result = res.userGameVOList // 结果
       // let cards = res.doorVOList // 第二张牌
        let status = []
        res.userGameVOList.forEach((item) => {
          if ((Number(item.userId) !== Number(vm.userId)) && (item.betting === true)) {
            status.push(item.betting)
          }
        })
        if (status.length === result.length - 1) {
          // 最后一个人完成投注
          vm.scores = []
          vm.users.forEach((item) => {
            for (let i = 0; i < result.length; i++) {
              if (Number(item.userId) === Number(result[i].userId)) {
                vm.scores.push(item)
              }
            }
          })
          vm.gameEnd = true
        }
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
    },
    // regCard (data) {

    // },
    // 判断两个数组所包含内容是否相同
    regCard (arr1, arr2) {
      if (arr1.length !== arr2.length) {
        return false
      }
      for (let i = 0; i < arr1.length; i++) {
        if (arr1[i].toString() !== arr2[i].toString()) {
          return false
        }
      }
      return true
    },
    getRoomMsg () {
      if (this.lowz.length >= 4) {
        return
      }
      console.log(this.lowz)
      if (Number(this.gameMsg.baseScore) === 20) {
        this.lowz.push(this.lowzMax[0])
      } else if (this.gameMsg.baseScore === 50) {
        this.lowz.push(this.lowzMax[1])
      } else if (this.gameMsg.baseScore === 100) {
        this.lowz.push(this.lowzMax[2])
      } else if (this.gameMsg.baseScore === 200) {
        this.lowz.push(this.lowzMax[3])
      }
    },
    isAllUserReady (users) {
      for (let i = 0, len = users.length; i < len; i++) {
        if (users[i].ready === false) {
          // 有用户未准备就绪
          return false
        }
      }
      return true
    },
    isOtherReady (users) {
      let datas = []
      users.forEach((item) => {
        if (String(item.userId) !== String(this.userId)) {
          datas.push(item)
        }
      })
      for (let i = 0, len = datas.length; i < len; i++) {
        if (datas[i].ready === false) {
          // 有用户未准备就绪
          return false
        }
      }
      return true
    },
    // 抢庄
    isQz () {
      this.$audio.play(this.$audio.ui)
      // 若是都准备就绪，禁止抢庄
      if (this.isCurUserReady) {
        return false
      }
      this.qz = !this.qz
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1002,
          data: {
            roomId: this.roomId,
            userId: this.userId,
            takeBanker: this.qz
          }
        }
      })
      console.log('强庄了')
      // let vm = this
      this.$JsBridge.callHandler(
        'qianZuan' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
        }
      )
    },
    // 设置
    setting () {
      this.$audio.play(this.$audio.ui)
      this.$refs.setting.openSetModal()
    },
    // 退出房间
    exitRoom () {
      this.$audio.play(this.$audio.ui)
      // this.$router.push({path: '/', query: {'id': ''}})
      this.exitText = '您确定要退出房间吗？'
      this.showExitModal = true
    },
    exitRoomCancel () {
      this.$audio.play(this.$audio.btn)
      this.showExitModal = false
    },
    // 确定退出房间
    exitRoomOk () {
      this.$audio.play(this.$audio.btn)
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1005,
          data: {
            roomId: this.roomId,
            userId: this.userId
          }
        }
      })
      let vm = this
      this.$JsBridge.callHandler(
        'exitRoom' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          vm.showExitModal = false
          vm.showDj = false
          vm.$emit('on-close', vm.showDj)
        }
      )
    },
    // 解散房间
    releaseRoom () {
      this.$audio.play(this.$audio.ui)
     // this.$router.push({path: '/', query: {'id': ''}})
      if (this.isGameStart) {
        // 游戏中解散房间
        this.releaseReadyModal()
      } else {
        // 游戏还没开始解散房间
        this.releaseWaitModal()
      }
    },
    releaseWaitModal () {
      this.releaseWaitText = '您确定要解散当前房间吗？'
      this.showReleaseWaitModal = true
    },
    releaseWaitCancel () {
      this.$audio.play(this.$audio.btn)
      this.showReleaseWaitModal = false
    },
    // 游戏还没开始解散房间
    releaseWaitOk () {
      this.$audio.play(this.$audio.btn)
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1004,
          data: {
            roomId: this.roomId,
            userId: this.userId
          }
        }
      })
      let vm = this
      this.$JsBridge.callHandler(
        'releaseWait' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          vm.showReleaseWaitModal = true
          vm.showDj = false
          vm.$emit('on-close', vm.showDj)
        }
      )
    },
    releaseReadyModal () {
      this.disbandType = 1
      this.releaseReadyText = '游戏正在进行中，您确定要解散当前房间吗？'
      this.showReleaseReadyModal = true
    },
    releaseReadyCancel () {
      this.$audio.play(this.$audio.btn)
      this.showReleaseReadyModal = false
    },
    // 游戏进行中解散房间
    releaseReadyOk () {
      this.$audio.play(this.$audio.btn)
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1012,
          data: {
            userId: this.userId,
            roomId: this.roomId,
            disbandType: this.disbandType,
            agree: this.disbandAgree
          }
        }
      })
      let vm = this
      this.$JsBridge.callHandler(
        'releaseReady' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          vm.showReleaseReadyModal = true
          vm.showDj = false
          vm.$emit('on-close', vm.showDj)
        }
      )
    },
    // 关闭设置弹窗
    closeSetModal () {
      this.showSetModal = false
    },
    // 出门投注分数
    selectCmScore (val) {
      this.$audio.play(this.$audio.ui)
      this.cmType = 1
      this.cmScore += val
      if (this.cmScore >= 50) {
        this.cmScore = 50
      }
      console.log(this.cmScore)
    },
    // 删除出门投注分数
    deleteCmScore () {
      this.$audio.play(this.$audio.ui)
      this.cmType = 0
      this.cmScore = 0
    },
    // 天门投注分数
    selectTmScore (val) {
      this.$audio.play(this.$audio.ui)
      this.tmType = 2
      this.tmScore += val
      if (this.tmScore >= 50) {
        this.tmScore = 50
      }
      console.log(this.tmScore)
    },
    // 删除天门投注分数
    deleteTmScore () {
      this.$audio.play(this.$audio.ui)
      this.tmType = 0
      this.tmScore = 0
    },
    // 投注坎门
    selectKmScore (val) {
      this.$audio.play(this.$audio.ui)
      this.kmType = 3
      this.kmScore += val
      if (this.kmScore >= 50) {
        this.kmScore = 50
      }
      console.log(this.kmScore)
    },
    // 删除坎门投注分数
    deleteKmScore () {
      this.$audio.play(this.$audio.ui)
      this.kmType = 0
      this.kmScore = 0
    },
    // 确定下注
    xzOk () {
      this.$audio.play(this.$audio.btn)
      if (this.isDown) {
        return
      }
      this.kanType = true
      this.checkResult = true
      let userDoorVOList = []
      if (this.cmScore) {
        userDoorVOList.push({
          doorNum: this.cmType,
          score: this.cmScore
        })
      }
      if (this.tmScore) {
        userDoorVOList.push({
          doorNum: this.tmType,
          score: this.tmScore
        })
      }
      if (this.kmScore) {
        userDoorVOList.push({
          doorNum: this.kmType,
          score: this.kmScore
        })
      }
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1008,
          data: {
            userId: this.userId,
            roomId: this.roomId,
            userDoorVOList: userDoorVOList
          }
        }
      })
      let vm = this
      this.$JsBridge.callHandler(
        'downCoin' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          vm.showXzModal = false
          vm.showCoins = true
          vm.isDown = true
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
        }
      )
      // 后期需要注释
    },
    // 邀请好友
    invateFriend () {
      this.$audio.play(this.$audio.btn)
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
    },
    // 开始游戏
    startGame () {
      this.$audio.play(this.$audio.btn)
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1006,
          data: {
            roomId: this.roomId,
            userId: this.userId
          }
        }
      })
      // let vm = this
      this.$JsBridge.callHandler(
        'startGame' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
        }
      )
    },
    // 开始准备
    startReady () {
      this.$audio.play(this.$audio.btn)
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1003,
          data: {
            roomId: this.roomId,
            userId: this.userId,
            ready: true
          }
        }
      })
      // let vm = this
      this.$JsBridge.callHandler(
        'startReady' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
        }
      )
    },
    playCards () {
      let params = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/app'],
        params: {
          command: 1007,
          data: {
            roomId: this.roomId,
            userId: this.userId
          }
        }
      })
      // this.showXzModal = true
      // this.showCards = true
      // let vm = this
      this.$JsBridge.callHandler(
        'delayRoom' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
        }
      )
    },
    // 游戏结束后统计
    gameOver () {
      let vm = this
      let params = {
        roomId: this.roomId
      }
      let ajaxParams = window.JSON.stringify({
        host: this.$url,
        path: this.$interface['/gameover/statistics'],
        params: this.$sign({params})
      })
      console.log(ajaxParams)
      // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'gameOver' // 原生的方法名
        , {'param': ajaxParams} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          let data = window.JSON.parse(responseData)
          console.log('游戏结束统计')
          console.log(data)
          vm.publics = data.model
          vm.$store.dispatch('newsAjax', data)
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
        .zuja{
          position: absolute;
          top: 32px;
          width: 116px;
          height: 32px;
          z-index: -1;
        }
        .zuja.l-site{
          left: -110px; 
        }
        .zuja.r-site{
          right: -90px; 
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
          animation: win 0.2s linear forwards;
        }
        .result .lost{
          animation: lost 0.2s linear forwards;
        }
        .result .tie{
          animation: lost 0.2s linear forwards;
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
        top: 20px;
        width: 180px;
      }
      .status.l-site{
          left: -280px; 
        }
      .status.r-site{
          right: -220px; 
      }
      .status.c-site{
          right: 20px; 
      }
      .xz-tip{
        position: absolute;
        width: 180px;
        img{
          width: 100%;
          height: 40px;
        }
      }
      .score {

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
         top: 38%;
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
    top: 49%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 30;
    .chu,.tian,.kan{
      position: relative;
      height: 320px;
    }
    .coins{
      position: absolute;
      top: 0;
      left: 0;
      z-index: 1000;
      span{
        position: absolute;
        display: block;
        width: 70px;
        height: 56px;
        border-radius: 50%;
        z-index: 9999;
      }
      .coin0{
        left: 60px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin0.active{
        animation: coinMove0 0.1s linear forwards;
      }
      .coin1{
       left: 150px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin1.active{
        animation: coinMove1 0.1s linear forwards;
      }
      .coin2{
       left: 70px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin2.active{
        animation: coinMove2 0.1s linear forwards;
      }
      .coin3{
       left: 120px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin3.active{
       animation: coinMove3 0.1s linear forwards;
      }
      .coin4{
        left: 140px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin4.active{
        animation: coinMove4 0.2s linear forwards;
      }
      .coin5{
       left: 50px;
       box-shadow: 5px 5px 15px 2px #333;
      }
      .coin5.active{
        animation: coinMove5 0.1s linear forwards;
      }
      .coin6{
        left: 20px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin6.active{
       animation: coinMove6 0.1s linear forwards;
      }
      .coin7{
        left: 180px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin7.active{
       animation: coinMove7 0.2s linear forwards;
      }
    }
    // .chu-coins{
    //   .coin0 {
    //     left: 60px;
    //   }
    //   .coin1 {
    //      left: 30px;
    //   }
    //   .coin2 {
    //      left: 70px;
    //   }
    //   .coin3 {
    //      left: 120px;
    //   }
    //   .coin4 {
    //     left: 140px;
    //   }
    //   .coin5 {
    //     left: 50px;
    //   }
    //   .coin6 {
    //     left: 20px;
    //   }
    //   .coin7 {
    //     left: 180px;
    //   }
    // }
    // .tian-coins{
    //   .coin0 {
    //     left: 310px;
    //   }
    //   .coin1 {
    //      left: 180px;
    //   }
    //   .coin2 {
    //      left: 220px;
    //   }
    //   .coin3 {
    //      left: 270px;
    //   }
    //   .coin4 {
    //     left: 220px;
    //   }
    //   .coin5 {
    //     left: 200px;
    //   }
    //   .coin6 {
    //     left: 170px;
    //   }
    //   .coin7 {
    //     left: 330px;
    //   }
    // }
    // .chu-coins{
    //   .coin0 {
    //     left: 460px;
    //   }
    //   .coin1 {
    //      left: 330px;
    //   }
    //   .coin2 {
    //      left: 370px;
    //   }
    //   .coin3 {
    //      left: 40px;
    //   }
    //   .coin4 {
    //     left: 370px;
    //   }
    //   .coin5 {
    //     left: 350px;
    //   }
    //   .coin6 {
    //     left: 320px;
    //   }
    //   .coin7 {
    //     left: 480px;
    //   }
    // }
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
.exit-modal{
  .exit-body{
    padding: 86px 0;
    color: #fff;
    font-size: 48px;
  }
  .exit-foot{
     display: flex;
     flex-direction: row;
     padding: 0 10%;
     justify-content: space-between;
    .exit-btn{
      flex: 0 0 32%;
    }
  }
}
/**下注动画**/

@keyframes coinMove0 {
  0% {
    top: -200px;
    opacity: 0;
  }
  60% {
    top: -100px;
    opacity: 0.5;
  }
  80% {
    top: -30px;
    opacity: 1;
  }
  100% {
    top: 0px;
    opacity: 1;
  }
}

@keyframes coinMove1 {
  0% {
    top: -160px;
    opacity: 0.5;
  }
  60% {
    top: -100px;
    opacity: 1;
  }
  80% {
    top: -70px;
    opacity: 1;
  }
  100% {
    top: -20px;
    opacity: 1;
  }
}
@keyframes coinMove2 {
  0% {
    top: -160px;
    opacity: 0.5;
  }
  60% {
    top: -70px;
    opacity: 1;
  }
  80% {
    top: -10px;
    opacity: 1;
  }
  100% {
    top: 60px;
    opacity: 1;
  }
}
@keyframes coinMove3 {
  0% {
    top: -150px;
    opacity: 0.5;
  }
  60% {
    top: -100px;
    opacity: 1;
  }
  80% {
    top: 60px;
    opacity: 1;
  }
  100% {
    top: 100px;
    opacity: 1;
  }
}
@keyframes coinMove4 {
  0% {
    top: -200px;
    opacity: 0.5;
  }
  60% {
    top: -40px;
    opacity: 1;
  }
  80% {
    top: 30px;
    opacity: 1;
  }
  100% {
    top: 40px;
    opacity: 1;
  }
}
@keyframes coinMove5 {
  0% {
    top: -90px;
    opacity: 0.5;
  }
  60% {
    top: 10px;
    opacity: 1;
  }
  80% {
    top: 60px;
    opacity: 1;
  }
  100% {
    top: 110px;
    opacity: 1;
  }
}
@keyframes coinMove6 {
  0% {
    top: -160px;
    opacity: 0.5;
  }
  60% {
    top: -50px;
    opacity: 1;
  }
  80% {
    top: 10px;
    opacity: 1;
  }
  100% {
    top: 50px;
    opacity: 1;
  }
}
@keyframes coinMove7 {
  0% {
    top: -180px;
    opacity: 0.5;
  }
  60% {
    top: -90px;
    opacity: 1;
  }
  80% {
    top: 50px;
    opacity: 1;
  }
  100% {
    top: 90px;
    opacity: 1;
  }
}
/**输赢动画**/
@keyframes win {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
    transform: scale(3)
  }
  100% {
    opacity: 1;
    transform: scale(1.5)
  }
}
@keyframes lost {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
    transform: scale(2)
  }
  100% {
    opacity: 1;
    transform: scale(1)
  }
}
/**发牌动画**/
.cards{
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
  .card-list{
    position: relative;
   /* bottom: -75%; */
    top: 15%;
    width: 60%;
    height: 60px;
    left: 56%;
    transform: translateX(-70%);
    -webkit-perspective: 500;
    -moz-perspective: 500;
    -ms-perspective: 500;
    perspective: 500;
    -ms-transform: perspective(500px);
    -moz-transform: perspective(500px); /*重要*/
    li{
      display: inline-block;
      list-style: none;
      position: absolute;
      left: 25%;
    /*  bottom: 180px; */
      top: -50px;
      width: 12%;
      height: 60px;
      border: 1px solid #ddd;
      z-index: 1;
      transform-style: preserve-3d; /*重要*/
      transition: 0.6s;
      transform-style: preserve-3d; /*重要*/
      img{
        width: 100%;
      }
      .front ,.back{
        position: absolute;
        left: 0;
        top: 0;
        backface-visibility: hidden;  /*重要*/
        width: 100%;
        // height: 100%;
      }
      .front {
          transform: rotateY(0deg);
          z-index: 2;
      }
      .back {
          transform: rotateY(-180deg);
      }
    }
    .reverse{
      transform: rotateY(180deg);
    }
    .fanzhuan{
      transform: rotateY(180deg);
     // animation: reverseCard 0.3s linear 2s forwards;
    }
   .card0{
      left: 35%;
      z-index: 0;
      animation: card0Move 0.3s linear 0.5s forwards;
    }
    .card1{
      left: 40%;
      z-index: 1;
      animation: card1Move 0.3s linear 0.5s forwards;
    }
    .card2{
      left: 45%;
      z-index: 2;
      animation: card2Move 0.3s linear 1s forwards;
    }
    .card3{
      left: 50%;
      z-index: 3;
      animation: card3Move 0.3s linear 1s forwards;
    }
    .card4{
      left: 55%;
      z-index: 4;
      animation: card4Move 0.3s linear 1.5s forwards;
    }
    .card5{
      left: 60%;
      z-index: 5;
      animation: card5Move 0.3s linear 1.5s forwards;
    }
    .card6{
      left: 65%;
      z-index: 6;
      animation: card6Move 0.3s linear 2s forwards;
    }
    .card7{
      left: 70%;
      z-index: 7;
      animation: card7Move 0.3s linear 2s forwards;
    }
  }
  .card-list.active{
    animation: cardsMove 0.3s linear forwards;
  }
}
@keyframes reverseCard {
  0%{
    transform: rotateY(0deg)
  }
  100%{
    transform: rotateY(180deg)
  }
}

@keyframes cardsMove {
  0%{
    left: 100%;
    opacity: 0;
  }
  100%{
    left: 56%;
    opacity: 1;
  }
}
@keyframes card0Move {
  0%{
    left: 35%;
    top: -50px;
  }
  100%{
    left: 21%;
    top: 280px;
  }
}
@keyframes card1Move {
  0%{
    left: 40%;
    top: -50px;
  }
  100%{
    left: 26%;
    top: 280px;
  }
}
@keyframes card2Move {
  0%{
    left: 45%;
    top: -50px;
  }
  100%{
    left: 51%;
    top: 280px;
  }
}
@keyframes card3Move {
  0%{
    left: 50%;
    top: -50px;
  }
  100%{
    left: 56%;
    top: 280px;
  }
}
@keyframes card4Move {
  0%{
    left: 55%;
    top: -50px;
  }
  100%{
    left: 81%;
    top: 280px;
  }
}
@keyframes card5Move {
  0%{
    left: 60%;
    top: -50px;
  }
  100%{
    left: 86%;
    top: 280px;
  }
}
@keyframes card6Move {
  0%{
    left: 65%;
    top: -20px;
  }
  100%{
    left: 50%;
    top: -50px;
  }
}
@keyframes card7Move {
  0%{
    left: 70%;
    top: -50px;
  }
  100%{
    left: 55%;
    top: -50px;
  }
}
// @keyframes card2Move {
//   0%{
//     left: 40%;
//     bottom: 180px;
//   }
//   100%{
//     left: 80%;
//     bottom: 460px;
//   }
// }
// @keyframes card3Move {
//   0%{
//     left: 45%;
//     bottom: 180px;
//   }
//   100%{
//     left: 85%;
//     bottom: 460px;
//   }
// }
// @keyframes card4Move {
//   0%{
//     left: 50%;
//     bottom: 180px;
//   }
//   100%{
//     left: 0%;
//     bottom: 180px;
//   }
// }
// @keyframes card5Move {
//   0%{
//     left: 55%;
//     bottom: 180px;
//   }
//   100%{
//     left: 5%;
//     bottom: 180px;
//   }
// }
// @keyframes card6Move {
//   0%{
//     left: 60%;
//     bottom: 180px;
//   }
//   100%{
//     left: 80%;
//     bottom: 180px;
//   }
// }
// @keyframes card7Move {
//   0%{
//     left: 65%;
//     bottom: 180px;
//   }
//   100%{
//     left: 85%;
//     bottom: 180px;
//   }
// }
// @keyframes card8Move {
//   0%{
//     left: 70%;
//     bottom: 180px;
//   }
//   100%{
//     left: 40%;
//     bottom: 0;
//   }
// }
// @keyframes card9Move {
//   0%{
//     left: 75%;
//     bottom: 180px;
//   }
//   100%{
//     left: 45%;
//     bottom: 0;
//   }
// }
</style>
