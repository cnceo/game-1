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
           <div class="zuja" :class="{'l-site': (index === 0) || (index % 2 !== 0),
           'r-site': (index !== 0) && (index % 2 === 0)}" v-show="item.banker == true">
            <img src="../assets/imgs/img_Setuphualun.png" alt="" width="100%" height="100%">
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
        <div class="status" :class="{'l-site': (index !== 0) && (index % 2 !== 0),
         'r-site': (index % 2 === 0), 'c-site': (index === 0)}">
            <span v-show="item.ready == false && item.roomOwner == false && item.curUser == true" @click="startReady">
              <img src="../assets/imgs/img_Room_ready.png" alt="" width="100%" height="100%">
            </span>
            <span v-show="item.ready == true && item.roomOwner == false && isStartReady">
              <img src="../assets/imgs/img_Room_readying.png" alt="" width="100%" height="100%">
            </span>
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
        <div class="xz-tip">
          <span v-show="item.xz == 0 && item.banker == false && isDownSu == true">
            <img src="../assets/imgs/img_Bet_qingxiazhu.png" alt="" width="100%" height="100%">
          </span>
          <span v-show="item.xz == 1 && item.banker == false && isDownSu == true">
            <img src="../assets/imgs/img_Bet_zhengzaixiazhu.png" alt="" width="100%" height="100%">
          </span>
        </div>
      </div>
    </div>
    <!--最终结果页面-->
    <div class="user-site">
      <div class="user-item" v-for="(item, index) in scores" :key="index"
      :class="{'site0': index === 0, 'site1': index === 1, 'site2': index === 2,
      'site3': index === 3, 'site4': index === 4}" v-show="scores.length > 0">
        <div class="avater">
          <div class="result" :class="{'cur-user': index == 0}">
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
          <div class="score" v-show="showScore" :class="{'showRes': showScore}">
            <span class="sign" v-show="item.winScore >= 0">+</span>
            <span class="sign" v-show="item.winScore < 0">-</span>
            <span class="num" v-show="item.winScore >= 0">{{item.winScore}}</span>
            <span class="num" v-show="item.winScore < 0">{{-item.winScore}}</span>
          </div>
          <!-- <div class="men-list">
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
          </div> -->
        </div>
      </div>
    </div>
    <!-- 发牌区域 -->
     <div class="cards" v-show="showCards">
      <ul class="card-list" ref="card" v-show="showCards"
         :class="{'active': showCards}">
        <li v-for="(item, index) in cardList" :key="index"
        v-if="index === 0 || index === 1 || index === 2 || index === 3"
        :class="{'card0': index === 0, 'card1': index === 1,
        'card2': index === 2, 'card3': index === 3, 'reverse': checkResult1}"
        >
          <img :src="item.bImg" alt="" class="front">
          <img :src="item.fImg" alt="" class="back">
        </li>
        <li v-for="(item, index) in cardList" :key="index"
        v-if="index === 4 || index === 5 || index === 6 || index === 7"
        :class="{'card4': index === 4, 'card5': index === 5,
        'card6': index === 6, 'card7': index === 7, 'reverse': checkResult2}"
        >
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
    <!-- 下注筹码 -->
    <div class="card-table g-flex-row" v-show="isGameStart">
      <div class="chu g-flex" v-for="(item, index) in tbg" :key="index"
      :class="{'chu': index === 0, 'tian': index === 1, 'kan': index === 2}">
        <img :src="item" alt="" height="100%">
        <div class="coin-box" v-show="index === 0">
          <div class="coins chu-coins" v-show="menType">
            <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
            'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
            'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'coin8': index === 8, 'coin9': index === 9, 'active': menType,
            'move': item.show == 'move', 'hide': item.show == 'hide'}"
            >
            <img :src="item.value" alt="" width="100%">
            </span>
          </div>
        </div>
        <div class="coin-box" v-show="index === 1">
          <div class="coins tian-coins" v-show="menType">
            <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
            'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
            'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'coin8': index === 8, 'coin9': index === 9, 'active': menType,
            'move': item.show == 'move', 'hide': item.show == 'hide'}"
            >
            <img :src="item.value" alt="" width="100%">
            </span>
          </div>
        </div>
        <div class="coin-box" v-show="index === 2">
          <div class="coins kan-coins" v-show="menType">
              <span v-for="(item, index) in coins" :key="index" :class="{'coin0': index === 0,
              'coin1': index === 1, 'coin2': index === 2, 'coin3': index === 3, 'coin4': index === 4,
              'coin5': index === 5, 'coin6': index === 6, 'coin7': index === 7, 'coin8': index === 8, 'coin9': index === 9, 'active': menType,
               'move': item.show == 'move', 'hide': item.show == 'hide'}"
              >
              <img :src="item.value" alt="" width="100%">
              </span>
          </div>
        </div>
      </div>

    </div>

    <!-- 邀请好友 -->
    <div class="yq-friend g-flex-row" @touchstart="invateFriend" v-show="isMaster && isFirst == true ">
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
    :showClose="showClose"
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
    :showClose="showClose"
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
    :showClose="showClose"
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
    <!-- 单局结束分享页 -->
    <Modal :showModal="showShareModal"
    :showClose="showClose"
    class="share-modal">
    <div slot="modal-bg" class="modal-bg">
      <img src="../assets/imgs/img-End-background.png" alt=""  width="100%" height="100%">
    </div>
      <div slot="title" class="share-title title-active">
         <img src="../assets/imgs/img-End-title.png" alt=""  width="100%">
      </div>
      <div slot="body" class="share-body">
         <ul>
           <li v-for="(item, index) in allScores" :key="index" :class="{'winBg': item.userWincore >= 0,
             'loseBg': item.userWincore < 0}">
             <div class="user-tit">
               <!-- <img :src="item.headimgurl" width="60px" height="60px"/> -->
               <div class="user-avatar">
                  <img src="../assets/imgs/Avatarframe.png" width="100%"  height="100%"/>
                  <div class="user-img">
                    <img :src="item.headimgurl" width="100px"/>
                  </div>
                </div>
                <div class="user-id-card">
                  <div class="user-name f-relative">
                    <img src="../assets/imgs/img-End-netname.png"/>
                    <span>{{item.nickname}}</span>
                    </div>
                  <div class="user-id f-relative">
                     <img src="../assets/imgs/img-End-id.png"/>
                     <span>{{item.userId}}</span>
                    </div>
                </div>
              </div>
              <div class="item">
               <img src="../assets/imgs/img-End-yingcishu.png" width="160px"/>
               <span class="num">{{item.winNum}}</span>
               <span class="line"></span>
              </div>
              <div class="item">
               <img src="../assets/imgs/img-End-shucishu.png" width="160px"/>
               <span class="num">{{item.loseNum}}</span>
               <span class="line"></span>
              </div>
              <div class="item">
                <img src="../assets/imgs/img-End-hecishu.png" width="160px"/>
                <span class="num">{{item.heNum}}</span>
                <span class="line"></span>
              </div>
              <div class="item">
               <img src="../assets/imgs/img-End-zhuangcishu.png" width="160px"/>
               <span class="num">{{item.bankerNum}}</span>
               <span class="line"></span>
              </div>
              <div class="item">
                <img src="../assets/imgs/img-End-totalscore.png" width="100px"/>
                <span class="num">{{item.userWincore}}</span>
                <!-- <span class="line"></span> -->
              </div>
           </li>
         </ul>
      </div>
      <div slot="foot" class="share-foot">
        <div class="ok share-btn" @touchstart="share">
           <img src="../assets/imgs/img-End-share.png" alt=""  width="100%">
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import tabImgs from './tabImgs'
import {mapGetters} from 'vuex'
// import avatar from '../assets/imgs/head_img.jpg'
const HEAD_IMG_SIZE = 0
const MIN_USER = 2
export default {
  name: 'app',
  data () {
    return {
      date: '', // 当前时间
      timer: null, // 定时器
      timer1: null,
      timer2: null,
      timer3: null,
      timer4: null,
      timer5: null,
      showDj: false, // 是否显示该游戏桌面
      showAccount: false, // 是否显示设置中的切换账号功能
      qz: false, // 抢庄切换
      users: [], // 游戏中玩家列表
      showXzModal: false, // 是否显示下注弹窗
      showClose: false, // 是否显示弹窗关闭按钮
      site: true, // 弹窗显示位置
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
      result: tabImgs.result, // 赢输和图
      coins: [
        {
          value: tabImgs.coins[0],
          show: ''
        },
        {
          value: tabImgs.coins[1],
          show: ''
        },
        {
          value: tabImgs.coins[2],
          show: ''
        },
        {
          value: tabImgs.coins[3],
          show: ''
        },
        {
          value: tabImgs.coins[4],
          show: ''
        },
        {
          value: tabImgs.coins[5],
          show: ''
        },
        {
          value: tabImgs.coins[6],
          show: ''
        },
        {
          value: tabImgs.coins[7],
          show: ''
        },
        {
          value: tabImgs.coins[8],
          show: ''
        },
        {
          value: tabImgs.coins[9],
          show: ''
        }
      ],
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
      isFirst: true, // 是否是第一局游戏，房主显示开始游戏和准备区别
      gameMsg: {}, // 房间相关信息
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
      isStartReady: false, // 玩家是否准备，
      isGameStart: false, // 是否游戏已经开始,
      disbandType: 0, // 游戏中解散房间类型，解散请求和响应
      disbandAgree: false, // 游戏中解散是否同意
      showExitModal: false,
      exitText: '', // 用户退出提示文字
      showReleaseWaitModal: false,
      releaseWaitText: '', // 房主提示文字
      showReleaseReadyModal: false,
      releaseReadyText: '', // 游戏中解散房间提示文字
      isCurUserReady: false, // 当前玩家是否准备，不可抢庄
      curCardBg: tabImgs.cards[3],
      cards: tabImgs.djCards, // 所有的牌组合方式
      cardList: [], // 最终展示牌
      showCards: false, // 是否显示发牌
      checkResult1: false, // 查看牌1结果，翻牌
      checkResult2: false, // 查看牌2结果，翻牌
      isDown: false, // 是否已经下注过，不可重复下注
      isDownSu: false, // 最后一个人下注完成
      // chuType: false, // 投注类型：出门
      // tianType: false, // 投注类型：天门
      // kanType: false, // 投注类型：坎门
      menType: false, // 显示投注动画
    //  coinList: [], // 投注列表
      scores: [], // 最终结果分数
      showScore: false,
      // collCoins: [true, true, true, true, true],
      showShareModal: false,
      allScores: [] // 单局结束统计
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
    uid (val) {
      this.userId = val
    },
    rid (val) {
      this.roomId = val
    },
    ds (val) {
      this.gameMsg = val
    },
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
    // 注册交互事件
    this.registerFn()
    // 获取当前时间
    this.updateTime()
  },
  destroyed () {
    this.closeTime()
  },
  methods: {
    closeTime () {
      clearInterval(this.timer)
      this.timer = null
    },
    updateTime () {
      this.timer = setInterval(() => {
        let hours = new Date().getHours()
        let minutes = new Date().getMinutes()
        let curHours = hours < 10 ? ('0' + hours) : hours
        let curMinutes = minutes < 10 ? ('0' + minutes) : minutes
        this.date = curHours + '：' + curMinutes
      }, 1000)
    },
    registerFn () {
      let vm = this
      // 系统更新加入游戏的用户列表
      this.$JsBridge.registerHandler('updateUsers', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
        vm.users = []
        let arr = []
        let arrs = []
        arrs = window.JSON.parse(data)
        arrs.forEach((item) => {
          if (item != null) {
            arr.push(item)
          }
        })
        // 游戏开始，禁止用户加入
        // if (vm.isGameStart) {
        //   return
        // }
        // 如果人数最少2人，即可开始游戏
        if (arr.length < MIN_USER) {
          arr.forEach((item) => {
            if (Number(item.userId) === Number(vm.userId)) {
              item.curUser = true
              // 匹配最大下注值
              vm.getRoomMsg()
              // 房间创建者可以邀请好友
              if (item.roomOwner) {
                vm.isMaster = true
              } else {
                vm.isMaster = false
              }
              //  // 当前玩家若是庄家，不显示下注
              // if (item.banker === true) {
              //   vm.isShowXz = false
              // } else {
              //   vm.isShowXz = true
              // }
              // 当前玩家准备后不可以抢庄
              if (item.ready === true) {
                vm.isCurUserReady = true
              } else {
                vm.isCurUserReady = false
              }
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              vm.users.unshift(item)
            } else {
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              vm.users.push(item)
            }
          })
        } else {
          arr.forEach((item) => {
            if (Number(item.userId) === Number(vm.userId)) {
              item.curUser = true
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              // 匹配最大下注值
              vm.getRoomMsg()
              // 房间创建者可以邀请好友
              if (item.roomOwner) {
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
              vm.users.unshift(item)
            } else {
              item.curUser = false
              item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
              vm.users.push(item)
            }
          })
          // 显示准备按钮
          if (!vm.isStartReady) {
            vm.isStartReady = true
          } else {
            // 其他玩家点击准备按钮,房主显示开始游戏按钮
            if (vm.isOtherReady(vm.users)) {
              vm.isFriends = true
            }
            // 判断是否都已经准备就绪
            if (vm.isAllUserReady(vm.users)) {
              console.log('所有人准备就绪')
              vm.isStartReady = false
              vm.isFriends = false
              vm.isMaster = false
              // 禁止抢庄
              vm.isGameStart = true
              // 开始发牌
              vm.users.forEach((its) => {
                if (Number(its.userId) === Number(vm.userId) && (its.banker === true)) {
                  console.log('发牌了')
                  vm.playCards()
                }
              })
            }
          }
        }
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 系统发牌
      this.$JsBridge.registerHandler('updateCards', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
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
                vm.cardList.push({
                  bImg: vm.curCardBg,
                  fImg: vm.cards[i].img
                })
              }
            }
          }
        })
        vm.showCards = true
        vm.isDownSu = true
        vm.timer1 = setTimeout(() => {
          vm.checkResult1 = true
          vm.timer2 = setTimeout(() => {
            vm.users.forEach((item) => {
              if (Number(item.userId) === Number(vm.userId)) {
                item.xz = 0
              } else {
                item.xz = 1
              }
            })
            if (vm.isShowXz) {
              vm.showXzModal = true
            }
          }, 500)
        }, 3000)
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // // 系统更新投注结果
      // this.$JsBridge.registerHandler('updateCoins', function (data, responseCallback) {
      //   // 将原生带来的参数，显示在show标签位置
      //   vm.cards = window.JSON.parse(data)
      //   console.log('投注结果收到了')
      //   console.log(window.JSON.stringify(vm.cards))
      //   // vm.showDj = false
      //   // vm.$emit('on-close', vm.showDj)
      //   // 调用responseCallback方法可以带传参数到原生
      //   responseCallback('')
      // })
      // 投注后更新结果
      this.$JsBridge.registerHandler('updateResult', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
     //   vm.coinList = []
        let res = window.JSON.parse(data)
        let result = res.userGameVOList // 结果
        let list = res.doorVOList // 第二张牌
        let scds = []
        let status = []
        let coins = []
        let obj = {}
        result.forEach((item) => {
          if ((item.banker === false) && (item.betting === true)) {
            status.push(item.betting)
          }
        })
        list.forEach((item, index) => {
          if (Number(item.doorNum) === 0) {
            obj = item
          } else {
            scds.push(item)
          }
        })
        scds.push(obj)
        // 除庄家外，最后一个人投注完成,更新结果
        if (status.length === result.length - 1) {
          // 最后一个人完成投注
          scds.forEach((item) => {
            let arr1 = []
            for (let key in item.pokerList[1]) {
              arr1.push(item.pokerList[1][key])
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
                vm.cardList.push({
                  bImg: vm.curCardBg,
                  fImg: vm.cards[i].img
                })
              }
            }
          })
          vm.isDownSu = false
          vm.menType = true
          vm.timer3 = setTimeout(() => {
            vm.checkResult2 = true
            vm.scores = []
            vm.showScore = true
            vm.users.forEach((item) => {
              for (let i = 0; i < result.length; i++) {
                if (Number(item.userId) === Number(result[i].userId)) {
                  vm.scores.push(result[i])
                }
              }
            })
            // 硬币动画 //coins 0, 1, 2, 3, 4
            let winer = false
            let bankerId = ''
            let curIndex = ''
            result.forEach((item) => {
              // 庄家赢了
              if (item.banker === true) {
                bankerId = item.userId
                if (item.winScore > 0) {
                  winer = true
                }
              }
              // 庄家输了
            })
            // 获取庄家位置
            vm.users.forEach((item, index) => {
              if (Number(item.userId) === Number(bankerId)) {
                curIndex = index
              }
            })
            // 庄家赢了
            vm.timer4 = setTimeout(() => {
              if (winer) {
                console.log('庄家赢了')
                if (Number(bankerId) === Number(vm.userId)) {
                  console.log('是当前玩家')
                  for (let i = 0; i < 10; i++) {
                    if (i === 0 || i === 1) {
                      coins[i] = 'move'
                    } else {
                      coins[i] = 'hide'
                    }
                  }
                } else {
                  console.log('不是当前玩家')
                  for (let i = 0; i < 10; i++) {
                    if ((i === (curIndex * 2)) || (i === (curIndex * 2 + 1))) {
                      coins[i] = 'move'
                    } else {
                      coins[i] = 'hide'
                    }
                  }
                }
              } else {
                // 闲家赢了
                console.log('闲家赢了')
                for (let i = 0; i < 10; i++) {
                  if ((i === (curIndex * 2)) || (i === (curIndex * 2 + 1)) || i >= (result.length * 2)) {
                    coins[i] = 'hide'
                  } else {
                    coins[i] = 'move'
                  }
                }
              }
              vm.coins.forEach((item, index) => {
                item.show = coins[index]
              })
            }, 6000)
            vm.timer5 = setTimeout(() => {
              if (res.gameover === true) {
                vm.gameOver()
              } else {
                vm.resetParams()
              }
            }, 10000)
          }, 4500)
        }
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
       // 游戏未开始解散房间
      this.$JsBridge.registerHandler('releaseWait', function (data, responseCallback) {
        console.log('游戏未开始解散房间')
        // 将原生带来的参数，显示在show标签位置
       // vm.users = window.JSON.parse(data)
        let msg = '房主解散了房间'
        vm.closeTime()
        vm.resetParams()
        vm.showReleaseWaitModal = false
        vm.isFirst = true
        vm.isGameStart = false
        vm.users = []
        vm.$emit('on-close', msg)
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
      // 游戏中解散房间
      this.$JsBridge.registerHandler('releaseReady', function (data, responseCallback) {
        // 将原生带来的参数，显示在show标签位置
        console.log('游戏中解散房间')
        let flag = false
        let user = window.JSON.parse(data)
        if (user.disband === true) {
          let msg = '房间已经解散'
          vm.closeTime()
          vm.resetParams()
          vm.showReleaseReadyModal = false
          vm.isFirst = true
          vm.isGameStart = false
          vm.users = []
          vm.$emit('on-close', msg)
        } else {
          if (Number(user.opeUserId) !== Number(vm.userId) && Number(user.disbandType) === 1) {
            user.dissolveUserList.forEach((its) => {
              if (Number(its.userId) === Number(vm.userId)) {
                flag = true
              }
            })
            if (!flag) {
              console.log('aaaa')
              vm.disbandType = 2
              vm.releaseReadyText = user.opeUserId + '在解散房间，您是否同意？'
              vm.showReleaseReadyModal = true
            }
          } else {
            vm.showReleaseReadyModal = false
          }
        }
        // 调用responseCallback方法可以带传参数到原生
        responseCallback('')
      })
    },
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
          vm.isFirst = true
          vm.isGameStart = false
          let msg = '您已经退出了房间'
          vm.closeTime()
          vm.resetParams()
          vm.$emit('on-close', msg)
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
      // let vm = this
      this.$JsBridge.callHandler(
        'releaseWait' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          // vm.showReleaseWaitModal = true
          // vm.showDj = false
          // vm.$emit('on-close', vm.showDj)
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
      this.disbandAgree = false
      this.askOtherAgree()
    //  this.showReleaseReadyModal = false
    },
    // 游戏进行中解散房间
    releaseReadyOk () {
      this.$audio.play(this.$audio.btn)
      this.disbandAgree = true
      this.askOtherAgree()
    //  this.showReleaseReadyModal = false
    },
    askOtherAgree () {
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
      // let vm = this
      this.$JsBridge.callHandler(
        'releaseReady' // 原生的方法名
        , {'param': params} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          // if (Number(window.JSON.parse(responseData)) === 200) {
          //   vm.$router.push({path: router, params: {}})
          // }
          //
          // vm.showReleaseReadyModal = true
          // vm.showDj = false
          // vm.$emit('on-close', vm.showDj)
        }
      )
    },
    // 出门投注分数
    selectCmScore (val) {
      this.$audio.play(this.$audio.ui)
      this.cmType = 1
      this.cmScore += val
      if (this.cmScore >= this.gameMsg.baseScore) {
        this.cmScore = this.gameMsg.baseScore
      }
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
      if (this.tmScore >= this.gameMsg.baseScore) {
        this.tmScore = this.gameMsg.baseScore
      }
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
      if (this.kmScore >= this.gameMsg.baseScore) {
        this.kmScore = this.gameMsg.baseScore
      }
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
        params: this.$sign(params)
      })
      // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'gameOver' // 原生的方法名
        , {'param': ajaxParams} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法
          let data = window.JSON.parse(responseData)
          let arrs = []
          data.model.forEach((item) => {
            item.headimgurl = item.headimgurl + HEAD_IMG_SIZE
            if (Number(item.userId) === Number(vm.userId)) {
              arrs.unshift(item)
            } else {
              arrs.push(item)
            }
          })
          vm.allScores = arrs
          vm.showShareModal = true
        }
      )
    },
    // 单局结束分享
    share () {
      //
      this.showShareModal = false
      this.resetParams()
    },
    // 单局游戏结束参数重置
    resetParams () {
      this.cmScore = 0
      this.tmScore = 0
      this.kmScore = 0
      this.cmType = 0
      this.tmType = 0
      this.kmType = 0
      this.disbandType = 0
      this.disbandAgree = false
      // this.isMaster = false
      // this.isFriends = false
      this.showXzModal = false
      this.isFirst = false
      this.isCurUserReady = false
      // this.isGameStart = false
      this.isShowXz = true
      this.showCards = false
      this.checkResult1 = false
      this.checkResult2 = false
      this.isDown = false
      this.isDownSu = false
      this.menType = false
      this.showScore = false
      this.qz = false
      this.cardList = []
      this.scores = []
      this.allScores = []
      this.coins.forEach((item) => {
        item.show = ''
      })
      this.timer = null
      this.timer1 = null
      this.timer2 = null
      this.timer3 = null
      this.timer4 = null
      this.timer5 = null
      clearTimeout(this.timer1)
      clearTimeout(this.timer2)
      clearTimeout(this.timer3)
      clearTimeout(this.timer4)
      clearTimeout(this.timer5)
      this.users.forEach((item) => {
        item.ready = false
      })
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
       //   left: 0;
          width: 100%;
          height: 100%;
          z-index: 99;
        }

        .result .win{
          display: block;
          width: 100%;
          opacity: 0;
          animation: small 0.3s linear 5s forwards;
        }
        .result .lost{
          display: block;
          width: 100%;
          opacity: 0;
          animation: small 0.3s linear 5s forwards;
        }
        .result .tie{
          display: block;
          width: 100%;
          opacity: 0;
          animation: small 0.3s linear 5s forwards;
        }
        .result.cur-user .win{
          opacity: 0;
        //  left: 200px;
          animation: big 0.3s linear 5s forwards;
        }
        .result.cur-user .lost{
          opacity: 0;
        //  left: 200px;
           animation: big 0.3s linear 5s forwards;
        }
        .result.cur-user .tie{
          opacity: 0;
        //  left: 200px;
          animation: big 0.3s linear 5s forwards;
        }
        .score{
          position: absolute;
          z-index: 99999;
        }
        .score .sign, .score .num{
          font-size: 48px;
          color: #fff;
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
        z-index: 99999;
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
      .score{
        top: -40px;
        right: -200px; // -50
        opacity: 0;
      }
      .score.showRes{
         animation: scores0 0.3s linear 4s forwards;
      }
      .avater .result{
        top: -60px;
        right: -100px;
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
      .score{
        top: -60px;
        left: -300px; // -200
        opacity: 0;
      }
      .score.showRes{
         animation: scores1 0.3s linear 4s forwards;
      }
      .avater .result{
        top: -90px;
        left: -50px;
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
      .score{
        top: -60px;
        right: -200px; // -150
        opacity: 0;
      }
      .score.showRes{
         animation: scores2 0.3s linear 4s forwards;
      }
      .avater .result{
        top: -90px;
        right: 0px;
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
      .score{
        top: 20px;
        left: -300px; // -200
        opacity: 0;
      }
      .score.showRes{
         animation: scores3 0.3s linear 4s forwards;
      }
      .avater .result{
        top: 0px;
        left: -50px;
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
      .score{
        top: 20px;
        right: -200px; // -150
        opacity: 0;
      }
      .score.showRes{
         animation: scores4 0.3s linear 4s forwards;
      }
      .avater .result{
        top: 0px;
        right: 0px;
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
    z-index: 999;
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
        animation: coinMove0 0.2s linear forwards;
      }
      .coin0.hide{
        display: none;
        opacity: 0;
      }
      .coin1{
       left: 150px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin1.active{
        animation: coinMove1 0.2s linear forwards;
      }
      .coin1.hide{
        display: none;
        opacity: 0;
      }
      .coin2{
       left: 70px;
        box-shadow: 5px 5px 15px 2px #333;
      }

      .coin2.active{
        animation: coinMove2 0.2s linear forwards;
      }

      .coin2.hide{
        display: none;
        opacity: 0;
      }
      .coin3{
       left: 120px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin3.active{
       animation: coinMove3 0.2s linear forwards;
      }

      .coin3.hide{
        display: none;
        opacity: 0;
      }
      .coin4{
        left: 140px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin4.active{
        animation: coinMove4 0.2s linear forwards;
      }

      .coin4.hide{
        display: none;
        opacity: 0;
      }
      .coin5{
       left: 50px;
       box-shadow: 5px 5px 15px 2px #333;
      }
      .coin5.active{
        animation: coinMove5 0.2s linear forwards;
      }

      .coin5.hide{
        display: none;
        opacity: 0;
      }
      .coin6{
        left: 20px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin6.active{
       animation: coinMove6 0.2s linear forwards;
      }

      .coin6.hide{
        display: none;
        opacity: 0;
      }
      .coin7{
        left: 180px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin7.active{
       animation: coinMove7 0.2s linear forwards;
      }

      .coin7.hide{
        display: none;
        opacity: 0;
      }
      .coin8{
        left: 150px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin8.active{
       animation: coinMove8 0.2s linear forwards;
      }

      .coin8.hide{
        display: none;
        opacity: 0;
      }
      .coin9{
        left: 160px;
        box-shadow: 5px 5px 15px 2px #333;
      }
      .coin9.active{
       animation: coinMove9 0.2s linear forwards;
      }

      .coin9.hide{
        display: none;
        opacity: 0;
      }
    }
    .chu-coins{
       .coin0.move{
        animation: Move0_0 0.3s linear forwards;
      }

      .coin1.move{
        animation: Move0_1 0.3s linear forwards;
      }
      .coin2.move{
        animation: Move0_2 0.3s linear forwards;
      }
      .coin3.move{
        animation: Move0_3 0.3s linear forwards;
      }
      .coin4.move{
        animation: Move0_4 0.3s linear forwards;
      }
      .coin5.move{
        animation: Move0_5 0.3s linear forwards;
      }
      .coin6.move{
        animation: Move0_6 0.3s linear forwards;
      }
      .coin7.move{
        animation: Move0_7 0.3s linear forwards;
      }
      .coin8.move{
        animation: Move0_8 0.3s linear forwards;
      }
      .coin9.move{
        animation: Move0_9 0.3s linear forwards;
      }
    }
    .tian-coins{
       .coin0.move{
        animation: Move1_0 0.3s linear forwards;
      }

      .coin1.move{
        animation: Move1_1 0.3s linear forwards;
      }
      .coin2.move{
        animation: Move1_2 0.3s linear forwards;
      }
      .coin3.move{
        animation: Move1_3 0.3s linear forwards;
      }
      .coin4.move{
        animation: Move1_4 0.3s linear forwards;
      }
      .coin5.move{
        animation: Move1_5 0.3s linear forwards;
      }
      .coin6.move{
        animation: Move1_6 0.3s linear forwards;
      }
      .coin7.move{
        animation: Move1_7 0.3s linear forwards;
      }
      .coin8.move{
        animation: Move1_8 0.3s linear forwards;
      }
       .coin9.move{
        animation: Move1_9 0.3s linear forwards;
      }
    }
    .kan-coins{
       .coin0.move{
        animation: Move2_0 0.3s linear forwards;
      }

      .coin1.move{
        animation: Move2_1 0.3s linear forwards;
      }
      .coin2.move{
        animation: Move2_2 0.3s linear forwards;
      }
      .coin3.move{
        animation: Move2_3 0.3s linear forwards;
      }
      .coin4.move{
        animation: Move2_4 0.3s linear forwards;
      }
      .coin5.move{
        animation: Move2_5 0.3s linear forwards;
      }
      .coin6.move{
        animation: Move2_6 0.3s linear forwards;
      }
      .coin7.move{
        animation: Move2_7 0.3s linear forwards;
      }
      .coin8.move{
        animation: Move2_8 0.3s linear forwards;
      }
       .coin9.move{
        animation: Move2_9 0.3s linear forwards;
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
.share-modal{
  .share-body{
    padding: 86px 0 10px 0;
    color: #fff;
    font-size: 48px;
    ul{
      display: flex;
    }
    li{
      flex: 1;
      margin: 0 10px;
      .user-tit{
        display: flex;
        padding: 15px 20px;
        .user-avatar{
          position: relative;
          flex: 0 0 100px;
          width: 100px;
          height: 100px;
          line-height: 100px;
          border-radius: 16px;
          overflow: hidden;
          background-image: url('../assets/imgs/Avatarframe.png');
          background-size: 100% 100%;
          .user-img{
            position: absolute;
            top: -5px;
            left: -4px;
            width: 78px;
            height: 78px;
            padding: 15px;
            border-radius: 20px;
            overflow: hidden;
            img{
              width: 78px;
              height: 78px;
              border-radius: 16px;
              border: 1px solid #000;
              box-shadow: 0px 0px 10px #000;
            }
          }
        }
        .user-id-card{
          display: inline-block;
          flex: 1;
        //  width: calc(~"100% - 200px");
          padding-left: 15px;
          margin-right: 30px;
          font-size: 32px;
          text-align: left;
          .user-name{
            padding: 0 15px 0 0;
            margin: 5px 0;
            border-radius: 12px;
          //  background: rgba(0, 0, 0, .5) url('../assets/imgs/img-End-netname.png') 0 0 no-repeat;
          //  background-size: contain;
            img{
              height: 42px;
              vertical-align: middle;
            }
            span{
              display: inline-block;
              vertical-align: middle;
            }
          }
          .user-id{
            padding: 0 15px 0 0;
            margin: 5px 0;
            border-radius: 12px;
          //  background: rgba(0, 0, 0, .5) url('../assets/imgs/img-End-id.png') 0 0 no-repeat;
           // background-size: contain;
            img{
              height: 42px;
              vertical-align: middle;
            }
            span{
              display: inline-block;
              vertical-align: middle;
            }
          }
        }
      }
      .item{
        position: relative;
        text-align: left;
        margin: 0 20px;
        .num{
          font-size: 32px;
          position: absolute;
          top: 2px;
          right: 20px;
        }
        .line{
          position: absolute;
          bottom: 9px;
          left: 0;
          width: 100%;
          height: 2px;
          background: #efefef;
        }
      }
    }
    li.winBg{
      border-radius: 24px;
      background-image: url('../assets/imgs/img-End-smallbackground1.png');
      background-size: 100% 130%;
    }
    li.loseBg{
      border-radius: 24px;
      background-image: url('../assets/imgs/img-End-smallbackground2.png');
      background-size: 100% 130%;
    }
  }
  .share-foot{
     display: flex;
     flex-direction: row;
     padding: 0 10%;
     justify-content: space-between;
     text-align: center;
    .share-btn{
      flex: 0 0 24%;
      margin: 0 auto;
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
@keyframes coinMove8 {
  0% {
    top: -120px;
    opacity: 0.5;
  }
  60% {
    top: -60px;
    opacity: 1;
  }
  80% {
    top: 20px;
    opacity: 1;
  }
  100% {
    top: 150px;
    opacity: 1;
  }
}
@keyframes coinMove9 {
  0% {
    top: -100px;
    opacity: 0.5;
  }
  60% {
    top: -60px;
    opacity: 1;
  }
  80% {
    top: 20px;
    opacity: 1;
  }
  100% {
    top: 120px;
    opacity: 1;
  }
}
/**分钱动画**/
@keyframes Move0_0 {
  0% {
    top: 0px;
    left: 60px;
  }
  100% {
    top: 400px;
    left: 20px;
  //  opacity: 0;
  }
}
@keyframes Move1_0 {
  0% {
    top: 0px;
    left: 60px;
  }
  100% {
    top: 380px;
    left: -160px;
  //  opacity: 0;
  }
}
@keyframes Move2_0 {
  0% {
    top: 0px;
    left: 60px;
  }
  100% {
    top: 400px;
    left: -360px;
  //  opacity: 0;
  }
}
@keyframes Move0_1 {
  0% {
    top: 150px;
    left: 100px;
  }
  100% {
    top: 360px;
    left: 20px;
  //  opacity: 0;
  }
}
@keyframes Move1_1 {
  0% {
   top: 150px;
    left: 100px;
  }
  100% {
    top: 360px;
    left: -120px;
  //  opacity: 0;
  }
}
@keyframes Move2_1 {
  0% {
    top: 150px;
    left: 100px;
  }
  100% {
    top: 420px;
    left: -420px;
  //  opacity: 0;
  }
}
@keyframes Move0_2 {
  0% {
    top: -20px;
    left: 150px;
  }
  100% {
    top: 200px;
    left: 900px;
   // opacity: 0;
  }
}
@keyframes Move1_2 {
  0% {
    top: -20px;
    left: 150px;
  }
  100% {
    top: 160px;
    left: 600px;
   // opacity: 0;
  }
}
@keyframes Move2_2 {
  0% {
    top: -20px;
    left: 150px;
  }
  100% {
    top: 210px;
    left: 300px;
   // opacity: 0;
  }
}
@keyframes Move0_3 {
  0% {
    top: 60px;
    left: 70px;
  }
  100% {
    top: 230px;
    left: 900px;
   // opacity: 0;
  }
}
@keyframes Move1_3 {
  0% {
    top: 60px;
    left: 70px;
  }
  100% {
    top: 230px;
    left: 600px;
   // opacity: 0;
  }
}
@keyframes Move2_3 {
  0% {
    top: 60px;
    left: 70px;
  }
  100% {
    top: 180px;
    left: 300px;
   // opacity: 0;
  }
}
@keyframes Move0_4 {
  0% {
    top: 100px;
    left: 120px;
  }
  100% {
    top: 200px;
    left: -200px;
   // opacity: 0;
  }
}
@keyframes Move1_4 {
  0% {
     top: 100px;
    left: 120px;
  }
  100% {
    top: 160px;
    left: -480px;
   // opacity: 0;
  }
}
@keyframes Move2_4 {
  0% {
    top: 100px;
    left: 120px;
  }
  100% {
    top: 210px;
    left: -640px;
   // opacity: 0;
  }
}
@keyframes Move0_5 {
  0% {
    top: 40px;
    left: 140px;
  }
  100% {
    top: 240px;
    left: -160px;
   // opacity: 0;
  }
}
@keyframes Move1_5 {
  0% {
    top: 40px;
    left: 140px;
  }
  100% {
    top: 250px;
    left: -480px;
   // opacity: 0;
  }
}
@keyframes Move2_5 {
  0% {
    top: 40px;
    left: 140px;
  }
  100% {
    top: 160px;
    left: -640px;
   // opacity: 0;
  }
}
@keyframes Move0_6 {
  0% {
    top: 110px;
    left: 50px;
  }
  100% {
    top: -50px;
    left: 900px;
   // opacity: 0;
  }
}
@keyframes Move1_6 {
  0% {
    top: 110px;
    left: 50px;
  }
  100% {
    top: -80px;
    left: 600px;
   // opacity: 0;
  }
}
@keyframes Move2_6 {
  0% {
    top: 110px;
    left: 50px;
  }
  100% {
    top: -50px;
    left: 300px;
   // opacity: 0;
  }
}
@keyframes Move0_7 {
  0% {
    top: 50px;
    left: 20px;
  }
  100% {
    top: -90px;
    left: 900px;
   // opacity: 0;
  }
}
@keyframes Move1_7 {
  0% {
    top: 50px;
    left: 20px;
  }
  100% {
    top: -30px;
    left: 600px;
   // opacity: 0;
  }
}
@keyframes Move2_7 {
  0% {
     top: 50px;
    left: 20px;
  }
  100% {
    top: -110px;
    left: 320px;
   // opacity: 0;
  }
}
@keyframes Move0_8 {
  0% {
    top: 90px;
    left: 180px;
  }
  100% {
    top: -50px;
    left: -200px;
   // opacity: 0;
  }
}
@keyframes Move1_8 {
  0% {
     top: 90px;
    left: 180px;
  }
  100% {
    top: -100px;
    left: -520px;
   // opacity: 0;
  }
}
@keyframes Move2_8 {
  0% {
    top: 90px;
    left: 180px;
  }
  100% {
    top: -50px;
    left: -640px;
   // opacity: 0;
  }
}
@keyframes Move0_9 {
  0% {
    top: 150px;
    left: 150px;
  }
  100% {
    top: -90px;
    left: -180px;
   // opacity: 0;
  }
}
@keyframes Move1_9 {
  0% {
     top: 150px;
    left: 150px;
  }
  100% {
    top: -30px;
    left: -480px;
   // opacity: 0;
  }
}
@keyframes Move2_9 {
  0% {
     top: 150px;
    left: 150px;
  }
  100% {
    top: -110px;
    left: -640px;
   // opacity: 0;
  }
}
/**分数动画**/
@keyframes scores0 {
  0% {
    right: -200px;
    opacity: 1;
  }
  100%{
    right: -250px;
    opacity: 1;
  }
}
@keyframes scores1 {
  0% {
    left: -300px;
    opacity: 1;
  }
  100%{
    left: -250px;
    opacity: 1;
  }
}
@keyframes scores2 {
  0% {
    right: -150px;
    opacity: 1;
  }
  100%{
    right: -200px;
    opacity: 1;
  }
}
@keyframes scores3 {
  0% {
    left: -300px;
    opacity: 1;
  }
  100%{
    left: -250px;
    opacity: 1;
  }
}
@keyframes scores4 {
  0% {
    right: -150px;
    opacity: 1;
  }
  100%{
    right: -200px;
    opacity: 1;
  }
}
/**输赢动画**/
@keyframes big {
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
@keyframes small {
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
  top: 10%;
  width: 100%;
  height: 35%;
  z-index: 1000;
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
      animation: card1Move 0.3s linear 1s forwards;
    }
    .card2{
      left: 45%;
      z-index: 2;
      animation: card2Move 0.3s linear 1.5s forwards;
    }
    .card3{
      left: 50%;
      z-index: 3;
      animation: card3Move 0.3s linear 2s forwards;
    }
    .card4{
      left: 55%;
      z-index: 4;
      animation: card4Move 0.3s linear 0.5s forwards;
    }
    .card5{
      left: 60%;
      z-index: 5;
      animation: card5Move 0.3s linear 1s forwards;
    }
    .card6{
      left: 65%;
      z-index: 6;
      animation: card6Move 0.3s linear 1.5s forwards;
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
   // left: 26%;
    left: 52%;
    top: 280px;
  }
}
@keyframes card2Move {
  0%{
    left: 45%;
    top: -50px;
  }
  100%{
   // left: 51%;
    left: 82%;
    top: 280px;
  }
}
@keyframes card3Move {
  0%{
    left: 50%;
    top: -50px;
  }
  100%{
   // left: 56%;
    left: 50%;
    top: -50px;
  }
}
@keyframes card4Move {
  0%{
    left: 55%;
    top: -50px;
  }
  100%{
   // left: 81%;
    left: 26%;
    top: 280px;
  }
}
@keyframes card5Move {
  0%{
    left: 60%;
    top: -50px;
  }
  100%{
   // left: 86%;
    left: 57%;
    top: 280px;
  }
}
@keyframes card6Move {
  0%{
    left: 65%;
    top: -20px;
  }
  100%{
   // left: 50%;
    left: 87%;
    top: 280px;
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
