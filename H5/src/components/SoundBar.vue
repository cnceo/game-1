<template>
    <div class="bar-box" name="bar" style="" 
    @click="setVoice($event)" ref="soundbox">
      <div class="cur-box" ref="cur" name="bar" @touchstart="setVoice($event)">
        <div class="bar-dot" name="dot" draggable="true" style="width: 40px;" 
      @touchstart="startMove($event)" @touchmove="moveDot($event)" ref="soundbar">
        <!-- <img src="../assets/imgs/img_Setuphualun.png" alt="" width="100%" height="100%"> -->
      </div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'app',
  props: {
    sound: {
    }
  },
  data () {
    return {
    }
  },
  watch: {
    sound (val) {
     // console.log(val)
      let width = document.documentElement.clientWidth * 0.6 - 80
      this.$refs.soundbox.style.width = width + 'px'
      // 初始化音量进度条
      let rate = val.cur / val.max
      let [dotElem, barWidth, dotWidth] = this.initSound()
      dotElem.style.left = barWidth * rate - (dotWidth / 2) + 'px'
      this.$refs.cur.style.width = barWidth * rate - 18 + 'px'
    }
  },
  created () {
    this.$nextTick(() => {
      let rate = this.sound.cur / this.sound.max
      let [dotElem, barWidth, dotWidth] = this.initSound()
      dotElem.style.left = barWidth * rate - (dotWidth / 2) + 'px'
      this.$refs.cur.style.width = barWidth * rate - (dotWidth / 2) + 'px'
      console.log(this.$refs.cur)
    })
  },
  mounted () {
    let width = document.documentElement.clientWidth * 0.6 - 80
    this.$refs.soundbox.style.width = width + 'px'
    this.$nextTick(() => {
      let rate = this.sound.cur / this.sound.max
      let [dotElem, barWidth, dotWidth] = this.initSound()
      dotElem.style.left = barWidth * rate - (dotWidth / 2) + 'px'
      this.$refs.cur.style.width = barWidth * rate - 18 + 'px'
    })
  },
  methods: {
    initSound () {
      let barElem = this.$refs.soundbox
      let dotElem = this.$refs.soundbar
      let barWidth = parseInt(barElem.style.width)
      let dotWidth = parseInt(dotElem.style.width)
      return [dotElem, barWidth, dotWidth]
    },
    setVoice (event) {
      if (event.target.getAttribute('name') !== 'bar') {
        return false
      }
      let site = event.offsetX
      this.calcVolume(site)
    },
    startMove (event) {
    //  event.preventDefault()
    },
    moveDot (event) {
    //  event.preventDefault()
      if (event.target.getAttribute('name') !== 'dot') {
        return false
      }
      let barElem = this.$refs.soundbox
      let barWidth = parseInt(barElem.style.width)
      let dotSite = event.changedTouches[0].screenX
      // let startSite = this.getBodyOffset(barElem)
      let clientWidth = document.documentElement.clientWidth
      let startSite = (clientWidth - (barWidth + 100)) / 2 + 100
      let site = ''
      if (dotSite < startSite) {
        site = 0
      } else if (dotSite > (startSite + barWidth)) {
        site = barWidth
      } else {
        site = dotSite - startSite
      }
      setTimeout(() => {
        this.calcVolume(site)
      }, 2)
    },
    calcVolume (site) {
      let [dotElem, barWidth, dotWidth] = this.initSound()
     // this.$refs.cur.style.width = site + 'px'
      // 进度条对应显示
      if (site <= (dotWidth)) {
       // return
        dotElem.style.left = '0px'
        this.$refs.cur.style.width = '0px'
      } else if (site >= (barWidth - (dotWidth) - 30)) {
       // return
        dotElem.style.left = (barWidth - dotWidth) - 60 + 'px'
        this.$refs.cur.style.width = (barWidth - dotWidth) - 20 + 'px'
      } else {
        dotElem.style.left = site - (dotWidth / 2) + 'px'
        this.$refs.cur.style.width = site - (dotWidth / 2) + 10 + 'px'
      }
      // 获取当前的位置
      // let curSite = site <= 0 ? 0 : (site >= barWidth) ? barWidth : site
      let curSite = site
     // console.log(this.sound)
      // 计算此时的音量
      let maxSound = this.sound.max
      let rate = (curSite / barWidth)
     // console.log(rate)
      let curSound = 0
      if (maxSound === 1) {
        curSound = (maxSound * rate).toFixed(1)
      } else {
        curSound = parseInt(maxSound * rate)
      }
      console.log(curSound)
      // 将最终值传给父组件
      this.$emit('on-change', curSound)
    },
    // 计算声音条相对屏幕的左偏移量
    getBodyOffset (elem) {
      let offsetLeft = 0
      let flag = 0
      while (elem.tagName.toString().toLowerCase() !== 'body') {
        if (flag !== elem.offsetLeft) {
          offsetLeft += elem.offsetLeft
          flag = elem.offsetLeft
        }
        elem = elem.parentNode
      }
      return offsetLeft
    }
  }
}
</script>

<style scoped lang="less">
.bar-box{
  position: relative;
  width: 76%;
  height: 20px;
  border-radius: 12px;
  background: #FFF;
}
.cur-box{
  position: absolute;
  top: 0;
  left: 0;
  width: 0;
  height: 20px;
  border-radius: 12px;
  background: #ace93c;
}
.bar-dot{
  position: absolute;
  top: -60%;
  left: 0;
  width: 50px;
  height: 40px;
  //padding: 5px;
  border-radius: 50%;
  // background: #eed23a;
 // background: -webkit-linear-gradient(#cc9900, #996600);
  background: url('../assets/imgs/img_Setuphualun.png');
  background-size: 100% 100%;
  cursor: pointer;
}

// .inner-dot{
//   position: absolute;
//   top: 50%;
//   left: 50%;
//   width: 40px;
//   height: 30px;
//   border-radius: 50%;
//   transform: translate(-50%, -50%);
//   background: #c78c29; //eed23a
//   background: -webkit-linear-gradient(#996600, #cc9900);
// }
</style>
