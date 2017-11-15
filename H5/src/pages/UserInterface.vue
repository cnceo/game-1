<template>
  <div class="user-face g-flex-column">
    <div class="face-bar">   
      <div class="box g-flex-row">
        <!--用户信息-->
        <!-- <div class="user-info g-flex-row">
          <div class="user-avatar">
            <img src="../assets/Avatarframe.png" width="100%"  height="100%"/>
            <div class="user-img">
              <img src="../assets/head_img.jpg" width="100%" />
            </div>
          </div>
          <div class="user-id-card">
            <div class="user-name f-relative">{{userInfo.name}}</div></br>
            <div class="user-id f-relative">{{userInfo.id}}</div>
          </div>
          <div class="user-room-card">
            <div class="user-black f-relative"></div></br>
            <div class="user-card f-relative">{{userInfo.card}}</div>
          </div>
        </div> -->
        <!--基本设置-->
        <ul class="base-settting g-flex-row">
          <!-- <li @click="help">
            <img src="../assets/rules.png" width="100%"/>
          </li>
          <li @click="message">
            <img src="../assets/message.png" width="100%"/>
          </li>
          <li @click="setting">
            <img src="../assets/steup.png" width="100%"/>
          </li> -->
        </ul>
     </div>
    </div>
    <!-- <div class="voice-info">
      <p class="text">这里是公告信息</p>
      <span class="s-icon"></span>
    </div>
    <div class="face-body g-flex-row">
      <div class="bg-girl">
        <img src="../assets/beautyGirl.png"  width="100%"/> 
      </div>
      <div class="all-room">
        <div class="room-item create-room" @click="createRoom">
          <img src="../assets/createroom.png"  width="100%"/> 
        </div>
        <div class="room-item join-room" @click="joinRoom">
          <img src="../assets/joinroom.png"  width="100%"/> 
        </div>
      </div>
    </div>
    <div class="z-bg" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
      <img src="../assets/background.png" alt=""  width="100%">
    </div> -->

    <!--规则-->
    <Modal :showModal="showRuleModal"
    @on-close="closeRuleModal"
    class="rule-modal">
      <div slot="title" class="rule-title title-active">规则</div>
      <div slot="body" class="rule-body">
        <div class="game-tabs">
          <span v-for="(item, index) in gameTabs" @click="changeGame(index)" :key="index"
          :class="{'game-active': item.select}">{{item.label}}</span>
        </div>
        <div class="game-box">
          <div v-show="selectGame === 0">清推内容</div>
          <div v-show="selectGame === 1">混推内容</div>
          <div v-show="selectGame === 2">大九</div>
        </div>
      </div>
    </Modal>

    <!--消息-->
    <Modal :showModal="showMsgModal"
    @on-close="closeMsgModal"
    class="msg-modal">
      <div slot="title" class="msg-title title-active">信息</div>
      <div slot="body" class="msg-body">这里是公告信息</div>
    </Modal>

    <!--设置-->
    <Setting :sounds="music" ref="setting"></Setting>

    <!--创建房间-->
    <Modal :showModal="showCreateRoom"
    @on-close="closeCreateRoom"
    class="create-modal">
      <div slot="title" class="create-title title-active">创建房间</div>
      <div slot="body" class="create-body">
        <div class="create-content">
          <div class="game-tabs">
            <span v-for="(item, index) in createRoomTabs" @click="changeRoom(index)" :key="index"
            :class="{'game-active': item.select}">{{item.label}}</span>
          </div>
          <div class="game-box">
            <div v-show="selectRoom === 0">
              <div class="row">
                <span class="label">局数：</span>
                <selectType :types="ds1_1" class="bar" @on-select="selectTime1"></selectType>
              </div>
              <div class="row">
                <span class="label">分数：</span>
                <selectType :types="ds1_2" class="bar" @on-select="selectScore1"></selectType>
              </div>
            </div>
            <div v-show="selectRoom === 1">
              <div class="row">
                <span class="label">局数：</span>
                <selectType :types="ds2_1" class="bar" @on-select="selectTime2"></selectType>
              </div>
              <div class="row">
                <span class="label">分数：</span>
                <selectType :types="ds2_2" class="bar" @on-select="selectScore2"></selectType>
              </div>
            </div>
            <div v-show="selectRoom === 2">
              <div class="row">
                <span class="label">局数：</span>
                <selectType :types="ds3_1" class="bar" @on-select="selectTime3"></selectType>
              </div>
              <div class="row">
                <span class="label">分数：</span>
                <selectType :types="ds3_2" class="bar" @on-select="selectScore3"></selectType>
              </div>
            </div>
          </div>
        </div>
        <div class="foot-change">
          <span class="toggle" @click="createGameRoom">创建房间</span>
          <span class="toggle" @click="invoiceGameRoom">代开房间</span>    
        </div>
      </div>
    </Modal>

    <!--加入房间-->
    <Modal :showModal="showJoinRoom"
    @on-close="closeJoinRoom"
    class="join-modal">
      <div slot="title" class="join-title title-active">加入房间</div>
      <div slot="body" class="join-body">
        <div class="fill-room-card">
          <span class="num-item" v-for="(item, index) in roomNums" :key="index">
            {{item}}
          </span>
        </div>
        <div class="select-num">
          <div class="num-row" v-for="(item, index) in nums" :key="index">
            <span class="num-cell" v-for="(its, i) in item" :key="i" @click="selectNum(its, i)">{{its}}</span>
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>

const MAX_ROOM_NUM = 4
export default {
  name: 'HelloWorld',
  data () {
    return {
      userInfo: {
        name: '',
        id: '',
        card: ''
      },
      music: null,
      showRuleModal: false,
      showMsgModal: false,
      showCreateRoom: false,
      showJoinRoom: false,
      tabs: [
        {
          label: '清推',
          select: true
        },
        {
          label: '混推',
          select: false
        },
        {
          label: '大九',
          select: false
        }
      ],
      gameTabs: [],
      selectGame: 0,
      ds1: [
        {
          label: '10局',
          select: true
        },
        {
          label: '30局（房卡X2）',
          select: false
        }
      ],
      ds2: [
        {
          label: '20分',
          select: true
        },
        {
          label: '50分',
          select: false
        },
        {
          label: '100分',
          select: false
        },
        {
          label: '200分',
          select: false
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
      nums: [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9],
        ['重输', 0, '删除']
      ],
      roomNums: ['', '', '', ''],
      numIndex: 0,
      createRoomTabs: [],
      selectRoom: 0
    }
  },
  created () {
    // 初始化数据
    this.handleArray([this.gameTabs, this.createRoomTabs], this.tabs)
    this.handleArray([this.ds1_1, this.ds2_1, this.ds3_1], this.ds1)
    this.handleArray([this.ds1_2, this.ds2_2, this.ds3_2], this.ds2)
    // 获取Android传来的数据
    window.music = '{max: 31, cur: 15}'
    let timer = null
    let total = 0
    timer = setInterval(() => {
      if (window.user) {
        total++
        this.userInfo = this.handleJSON(window.user)
      }
      if (window.music) {
        total++
        this.music = Object.assign({}, this.handleJSON(window.music))
      }
      if (total >= 2) {
        clearInterval(timer)
        timer = null
      }
    }, 500)
    // 获取消息信息
    console.log(this.$store)
    this.$store.dispatch('indexPublic', {})
    function createCORS (method, url) {
      var xhr = new XMLHttpRequest()
      if ('withCredentials' in xhr) {
        xhr.open(method, url, true)
      } else if (typeof XDomainRequest !== 'undefined') {
        xhr = new XDomainRequest()
        xhr.open(method, url)
      } else {
        xhr = null
      }
      return xhr
    }
    var request = createCORS('post', 'http://www.syhpgkj.com:8080/app/get/notice')
    if (request) {
      request.onload = function (data) {
        console.log(request.responseText)
      }
      request.send()
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
    handleJSON (json) {
      let data = json.replace(/[0-9a-zA-Z_]+/g, function (str) {
        return '"' + str + '"'
      })
      return window.JSON.parse(data)
    },
    message () {
      this.$audio.play()
      this.showMsgModal = true
      console.log(this.showMsgModal)
    },
    closeMsgModal () {
      this.showMsgModal = false
    },
    help () {
      this.$audio.play()
      this.showRuleModal = true
    },
    closeRuleModal () {
      this.showRuleModal = false
    },
    setting () {
      this.$audio.play()
      this.$refs.setting.openSetModal()
    },
    createRoom () {
      this.showCreateRoom = true
    },
    closeCreateRoom () {
      this.showCreateRoom = false
    },
    joinRoom () {
      this.showJoinRoom = true
    },
    closeJoinRoom () {
      this.showJoinRoom = false
    },
    selectNum (item, index) {
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
        this.$router.push({path: '/game', params: {}})
      }
    },
    handleSelect (index) {
      if (index === 0) {
        // 重输
        this.roomNums.forEach((item, index) => {
          this.$set(this.roomNums, index, '')
        })
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
      this.gameTabs.forEach((item, ids) => {
        if (ids === index) {
          item.select = true
        } else {
          item.select = false
        }
      })
      this.selectGame = index
    },
    changeRoom (index) {
      this.createRoomTabs.forEach((item, ids) => {
        if (ids === index) {
          item.select = true
        } else {
          item.select = false
        }
      })
      this.selectRoom = index
    },
    selectTime1 () {

    },
    selectScore1 () {

    },
    selectTime2 () {

    },
    selectScore2 () {

    },
    selectTime3 () {

    },
    selectScore3 () {

    },
    createGameRoom () {

    },
    invoiceGameRoom () {

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
  // background: url('../assets/background.png') 0 0 no-repeat;
  // background-size: 100% 100%;
  .face-bar{
    flex: 0 0 200px;
    height: 200px;
    padding: 0 60px;
    background: url('../assets/bar.png') 0 0 no-repeat;
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
        background-image: url('../assets/Avatarframe.png');
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
          background-image: url('../assets/username.png');
          background-size: 100% 100%;
        }
        .user-id{
          background: url('../assets/userID.png') 0 0 no-repeat;
          background-size: 100% 100%;
        }
      }
      .user-room-card{
        .user-card{
          background: url('../assets/roomcard.png') 0 0 no-repeat;
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
    background: url('../assets/Announcement_background.png') 0 0 no-repeat;
    background-size: 100% 100%;
    p.text{
      margin-left: 180px;
    }
    .s-icon{
      position: absolute;
      top: 5px;
      left: 60px;
      width: 80px;
      height: 56px;
      background: url('../assets/Announcement.png') 0 0 no-repeat;
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
  width: 300px;
  height: 120px;
  background: #fad700;
}
.msg-body, .rule-body, .create-body, .join-body{
  margin-top: 120px;
  color: #fff;
}
.rule-body, .create-content{
  display: flex;
  .game-tabs{
    flex: 0 0 120px;
    width: 120px;
    margin-right: 80px;
    span{
      display: block;
      width: 120px;
      height: 80px;
      line-height: 80px;
      margin-top: 30px;
      background: #fad700;
    }
    span.game-active{
      background: #ddd;
    }
  }
  .game-box{
    flex: 1;
  }
}
.join-modal{
  .fill-room-card{
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 20px 120px;
    background: yellow;
    .num-item{
      flex: 0 0 120px;
      width: 120px;
      height: 80px;
      border-right: 3px solid #000;
    }
    .num-item:first-child{
      border-left: 3px solid #000;
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
      margin-top: 20px;
      .num-cell{
        flex: 1;
        width: 210px;
        height: 60px;
        color: pink;
        background: yellow;
      }
      .num-cell:not(:last-child) {
        margin-right: 50px;
      }
    }
  }
}
.row{
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 60px 0;
  .label{
    flex: 0 0 150px;
    width: 150px;
  }
  .bar{
    flex: 1;
  }
}
</style>
