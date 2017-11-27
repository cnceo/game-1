<template>
  <div class="setting-sound">
    <Modal :showModal="showSetModal"
    @on-close="closeSetModal"
    class="set-modal">
      <div slot="title" class="set-title">
        <div class="set-tabs">
          <span v-for="(item, index) in setTabs" @click="changeSet(index)" :key="index" 
          class="title-active">
             <img :src="item.img" alt="" width="100%">
          </span>
        </div>
      </div>
      <div slot="body" class="set-body">
        <div class="set-box">
          <div v-show="selectSet === 0">
            <div class="row">
              <span class="label">
                <img src="../assets/imgs/img_Setup_Desktop.png" alt="" width="100%">
              </span>
              <selectType :types="ds0_1" class="bar" @on-select="selectDesktop"></selectType>
            </div>
            <div class="row">
              <span class="label">
                <img src="../assets/imgs/img_Setup_Rowsurface.png" alt="" width="100%">
              </span>
              <selectType :types="ds0_2" class="bar" @on-select="selectCard"></selectType>
            </div>
          </div>
          <div v-show="selectSet === 1">
            <ul class="sound-bar">
              <li class="row">
                <span class="label">
                  <img src="../assets/imgs/img_Setup_Sound.png" alt="" width="100%">
                </span>
                <SoundBar :sound="sound" class="bar" @on-change="changeSound"></SoundBar>
              </li>
              <li class="row">
                <span class="label">
                  <img src="../assets/imgs/img_Setup_Music.png" alt="" width="100%">
                </span>
                <SoundBar :sound="music" class="bar" @on-change="changeMusic"></SoundBar>
              </li>
            </ul>
            <span class="toggle" @click="changeAccount" v-show="account">
              <img src="../assets/imgs/img_Setup_Exchangeaccount.png" alt="" width="100%">  
            </span> 
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import tabImgs from '../pages/tabImgs'

export default {
  name: 'app',
  props: {
    account: {
      type: Boolean,
      default: true
    }
  },
  watch: {
    sounds: {
      handler: function (val) {
        // console.log(val)
        this.music = val.music
        this.sound = val.sound
      },
      deep: true
    }
  },
  computed: mapGetters({
    'sounds': 'listenMusic'
  }),
  data () {
    return {
      showSetModal: false,
      sound: {},
      music: {},
      setTabs: [
        {
          img: tabImgs.hoverTabs[0],
          select: true
        },
        {
          img: tabImgs.tabs[1],
          select: false
        }
      ],
      selectSet: 0,
      ds0: [
        {
          img: tabImgs.cards[0],
          select: true
        },
        {
          img: tabImgs.cards[1],
          select: false
        },
        {
          img: tabImgs.cards[2],
          select: false
        }
      ],
      ds0_1: [],
      ds0_2: []
    }
  },
  created () {
    // 初始化数据
    this.handleArray([this.ds0_1, this.ds0_2], this.ds0)
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
    openSetModal () {
      this.$audio.play(this.$audio.ui)
      this.showSetModal = true
    },
    closeSetModal () {
      this.showSetModal = false
    },
    changeSet (index) {
      this.$audio.play(this.$audio.ui)
      this.setTabs.forEach((item, ids) => {
        if (ids === index) {
          item.img = tabImgs.hoverTabs[index]
        } else {
          item.img = tabImgs.tabs[index]
        }
      })
      this.selectSet = index
    },
    selectDesktop (data) {
      this.$audio.play(this.$audio.ui)
     // console.log(data)
    },
    selectCard (data) {
      this.$audio.play(this.$audio.ui)
    // console.log(data)
    },
    // 音效设置
    changeSound (val) {
      this.$audio.setvol(val)
      // 保存设置的音效
      this.$store.dispatch('getMusic', {
        music: this.music,
        sound: {
          max: this.sound.max,
          cur: val
        }
      })
    },
    // 音乐设置
    changeMusic (val) {
       // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'setSound' // 原生的方法名
        , {'param': val} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法

        }
      )
       // 保存设置的音效
      this.$store.dispatch('getMusic', {
        music: {
          max: this.music.max,
          cur: val
        },
        sound: this.sound
      })
    },
    changeAccount () {
      this.$audio.play(this.$audio.btn)
      // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'changeAccount' // 原生的方法名
        , {'param': '返回登陆页'} // 带个原生方法的参数
        , function (responseData) { // 响应原生回调方法

        }
      )
    }
  }
}
</script>

<style scoped lang="less">
.title-active{
  display: inline-block;
}
.setting-sound{
  .set-modal{
    .set-tabs{
      .set-active{
        background: #ddd;
      }
    }
    .set-body{
      margin-top: 30px;
      .set-box{
        .row {
            display: flex;
            flex-direction: row;
            align-items: center;
            margin: 30px 0;
            .label{
            // flex: 0 0 180px;
             width: 150px;
             vertical-align: middle;
            }
          }
      }
    }
    .toggle{
      display: inline-block;
      width: 300px;
    }
  }
}
.setting-sound {
  .set-modal{
    .set-body{
      .sound-bar{
        .row{
          margin: 80px auto;
          .label{
            flex: 0 0 150px;
            width: 150px;
            margin-right: 30px;
          }
        }
      }
    }
  }  
  
}
</style>
