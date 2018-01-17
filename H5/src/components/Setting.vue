<template>
  <div class="setting-sound" ref="slider">
    <Modal :showModal="showSetModal"
    @on-close="closeSetModal"
    class="set-modal">
      <div slot="title" class="set-title">
        <div class="set-tabs">
          <span v-for="(item, index) in setTabs" @touchstart="changeSet(index)" :key="index"
          class="title-active">
             <img :src="item.img" alt="" width="100%">
          </span>
        </div>
      </div>
      <div slot="body" class="set-body">
        <div class="set-box">
          <div v-if="selectSet === 0">
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
          <div v-if="selectSet === 1">
            <ul class="sound-bar">
              <li class="row">
                <span class="label" style="width: 100px">
                  <img src="../assets/imgs/img_Setup_Sound.png" alt="" width="100%">
                </span>
                <!-- <SoundBar :sound="sound" class="bar" @on-change="changeSound"></SoundBar> -->
                <div class="bar-box">
                  <vue-slider v-model="soundValue" :width="barWidth" :height="12" :dotSize="20" tooltip="false"
                :processStyle="processStyle" @callback="changeSound" ></vue-slider>
                </div>
              </li>
              <li class="row">
                <span class="label" style="width: 100px">
                  <img src="../assets/imgs/img_Setup_Music.png" alt="" width="100%">
                </span>
                <!-- <SoundBar :sound="music" class="bar" @on-change="changeMusic"></SoundBar> -->
                <div class="bar-box">
                  <vue-slider v-model="musicValue" :width="barWidth" :height="12" :dotSize="20" :tooltip="false"
                :processStyle="processStyle" @callback="changeMusic" ></vue-slider>
                </div>
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
import vueSlider from 'vue-slider-component'
import {mapGetters} from 'vuex'
import tabImgs from '../pages/tabImgs'

export default {
  name: 'app',
  components: {
    vueSlider
  },
  props: {
    account: {
      type: Boolean,
      default: true
    }
  },
  watch: {
    sounds: {
      handler: function (val) {
        console.log('hhh')
        console.log(val)
        // console.log(val)
        let local = window.localStorage
        let soundSize = local.getItem('soundSize')
      //  let musicSize = local.getItem('musicSize')
        if (soundSize) {
          val.sound.cur = soundSize
        }
        local.setItem('soundSize', val.sound.cur)
        // if (musicSize) {
        //   val.music.cur = musicSize
        // }
      //  local.setItem('musicSize', val.music.cur)
        this.music = val.music
        this.sound = val.sound
        this.musicValue = (val.music.cur * 100) / (val.music.max)
        this.soundValue = (val.sound.cur * 100) / (val.sound.max)
      },
      deep: true
    },
    musicValue (val) {
      // this.$JsBridge.callHandler(
      //   'setSound' // 原生的方法名
      //   , {'param': val.toString()} // 带个原生方法的参数
      //   , function (responseData) { // 响应原生回调方法

      //   }
      // )
    }
  },
  computed: mapGetters({
    'sounds': 'listenMusic'
  }),
  data () {
    return {
      cur: 0.5,
      max: 1,
      showSetModal: false,
      barWidth: 'auto',
      soundValue: '',
      musicValue: '',
      processStyle: {'backgroundColor': '#ace93c'},
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
      // ds0: [
      //   {
      //     img: tabImgs.cards[0],
      //     select: true
      //   },
      //   {
      //     img: tabImgs.cards[1],
      //     select: false
      //   },
      //   {
      //     img: tabImgs.cards[2],
      //     select: false
      //   }
      // ],
      ds0_1: [
        {
          img: tabImgs.cards[0],
          select: true,
          value: tabImgs.cards[0]
        },
        {
          img: tabImgs.cards[1],
          select: false,
          value: tabImgs.cards[1]
        },
        {
          img: tabImgs.cards[2],
          select: false,
          value: tabImgs.cards[2]
        }
      ],
      ds0_2: [
        {
          img: tabImgs.cards[3],
          select: true,
          value: tabImgs.cards[3]
        },
        {
          img: tabImgs.cards[4],
          select: false,
          value: tabImgs.cards[4]
        },
        {
          img: tabImgs.cards[5],
          select: false,
          value: tabImgs.cards[5]
        }
      ]
    }
  },
  created () {
    // 初始化数据
   // this.handleArray([this.ds0_1, this.ds0_2], this.ds0)
    // let local = window.localStorage
    // let soundSize = local.getItem('soundSize')
    // let musicSize = local.getItem('musicdSize')
    // this.music = musicSize == null ? this.sounds.music : musicSize
    // this.sound = soundSize == null ? this.sounds.sound : soundSize
  },
  mounted () {
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
    // 设置弹窗（由父组件调用）
    openSetModal () {
      this.$audio.play(this.$audio.ui)
      this.showSetModal = true
    },
    // 关闭设置弹窗
    closeSetModal () {
      this.showSetModal = false
    },
    // 游戏设置和系统设置切换
    changeSet (index) {
      this.$audio.play(this.$audio.ui)
      this.setTabs.forEach((item, ids) => {
        if (ids === index) {
          item.img = tabImgs.hoverTabs[index]
        } else {
          if (index === 0) {
            item.img = tabImgs.tabs[1]
          } else {
            item.img = tabImgs.tabs[0]
          }
        }
      })
      this.selectSet = index
      this.$nextTick(() => {
        let dots = this.$refs.slider.getElementsByClassName('vue-slider-dot')
        for (let i = 0, len = dots.length; i < len; i++) {
          dots[i].style.width = '30px'
        }
        let modal = this.$refs.slider.getElementsByClassName('set-modal')[0]
        console.log(modal.style.width)
      })
    },
    // 选择桌面
    selectDesktop (data) {
      this.$audio.play(this.$audio.ui)
     // console.log(data)
    },
    // 选择牌面
    selectCard (data) {
      this.$audio.play(this.$audio.ui)
      this.$emit('on-select', data)
    // console.log(data)
    },
    // 音效设置
    changeSound (val) {
      let value = (this.sound.max * val) / 100
      let local = window.localStorage
      local.setItem('soundSize', value)
      this.$audio.setvol(val)
      // 保存设置的音效
      this.$store.dispatch('getMusic', {
        music: this.music,
        sound: {
          max: this.sound.max,
          cur: value
        }
      })
     // let local = window.localStorage
    //  local.setItem('soundSize', val)
    },
    // 音乐设置
    changeMusic (val) {
      let value = (this.music.max * val) / 100
   //   let local = window.localStorage
    //  local.setItem('musicSize', value)
       // 调用android原生内部方法
      this.$JsBridge.callHandler(
        'setSound' // 原生的方法名
        , {'param': value.toString()} // 带个原生方法的参数
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
    // 切换账号
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
            width: 25%;
            margin-top: 10px;
            vertical-align: middle;
          }
        }
        .row:last-child {
          .label{
            width: 23%;
          }
        }
      }
    }
    .toggle{
      display: inline-block;
      width: 250px;
    }
  }
}
.setting-sound {
  .set-modal{
    .set-body{
      .sound-bar{
        .row{
          margin: 62px auto 0;
          .label{
            flex: 0 0 16%;
            width: 16%;
            margin-right: 30px;
          }
          .bar-box{
            flex: 1;
            width: 76%;
          }
        }
      }
    }
    .toggle{
      margin-top: 50px;
    }
  }
}

</style>
