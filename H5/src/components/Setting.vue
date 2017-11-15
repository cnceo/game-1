<template>
  <div class="setting-sound">
    <Modal :showModal="showSetModal"
    @on-close="closeSetModal"
    class="set-modal">
      <div slot="title" class="set-title">
        <div class="set-tabs">
          <span v-for="(item, index) in setTabs" @click="changeSet(index)" :key="index" 
          class="title-active" :class="{'set-active': item.select}">{{item.label}}</span>
        </div>
      </div>
      <div slot="body" class="set-body">
        <div class="set-box">
          <div v-show="selectSet === 0">
            <div class="row">
              <span class="label">桌面：</span>
              <selectType :types="ds0_1" class="bar" @on-select="selectDesktop"></selectType>
            </div>
            <div class="row">
              <span class="label">牌面：</span>
              <selectType :types="ds0_2" class="bar" @on-select="selectCard"></selectType>
            </div>
          </div>
          <div v-show="selectSet === 1">
            <ul>
              <li>
                <span class="label">音效：</span>
                <SoundBar :sound="sound" class="bar" @on-change="changeSound"></SoundBar>
              </li>
              <li>
                <span class="label">音乐：</span>
                <SoundBar :sound="music" class="bar" @on-change="changeMusic"></SoundBar>
              </li>
            </ul>
            <span class="toggle" @click="changeAccount" v-show="account">切换账号</span> 
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import img1 from '../assets/card1.png'
import img2 from '../assets/card2.png'
import img3 from '../assets/card3.png'

export default {
  name: 'app',
  props: {
    sounds: {
    },
    account: {
      type: Boolean,
      default: true
    }
  },
  watch: {
    sounds: {
      handler: function (val) {
        console.log(val)
        this.music = val
      },
      deep: true
    }
  },
  data () {
    return {
      showSetModal: false,
      sound: {max: 1, cur: 0.5},
      music: {},
      setTabs: [
        {
          label: '系统设置',
          select: true
        },
        {
          label: '游戏设置',
          select: false
        }
      ],
      selectSet: 0,
      ds0: [
        {
          img: img1,
          select: true
        },
        {
          img: img2,
          select: false
        },
        {
          img: img3,
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
      this.showSetModal = true
    },
    closeSetModal () {
      this.showSetModal = false
    },
    changeSet (index) {
      this.setTabs.forEach((item, ids) => {
        if (ids === index) {
          item.select = true
        } else {
          item.select = false
        }
      })
      this.selectSet = index
    },
    selectDesktop (data) {
      console.log(data)
    },
    selectCard (data) {
      console.log(data)
    },
    // 音效设置
    changeSound (val) {
      this.$audio.setvol(val)
     // window.android.setSound(val)
    },
    // 音乐设置
    changeMusic (val) {
      // 调用android原生内部方法
      window.android.setSound(val)
    },
    changeAccount () {
      // 调用android原生内部方法
      window.android.changeAccount('正在返回')
    }
  }
}
</script>

<style scoped lang="less">
.title-active{
  display: inline-block;
  width: 300px;
  height: 120px;
  background: #fad700;
}
.setting-sound{
  .set-modal{
    .set-tabs{
      .set-active{
        background: #ddd;
      }
    }
    .set-body{
      margin-top: 120px;
      .set-box{
        ul{
          li {
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
        }
      }
    }
    .toggle{
      display: inline-block;
      width: 240px;
      height: 80px;
      background: yellow;
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
