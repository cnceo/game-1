<template>
    <div class="bar-box" name="bar" style="width: 250px" 
    @click="setVoice($event)" ref="soundbox">
      <div class="bar-dot" name="dot" draggable="true" style="width: 40px;" 
      @touchmove="moveDot($event)" ref="soundbar">
        <div class="inner-dot"></div>
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
      // 初始化音量进度条
      let rate = val.cur / val.max
      let [dotElem, barWidth, dotWidth] = this.initSound()
      dotElem.style.left = barWidth * rate - (dotWidth / 2) + 'px'
    }
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
    moveDot (event) {
      if (event.target.getAttribute('name') !== 'dot') {
        return false
      }
      let barElem = this.$refs.soundbox
      let barWidth = parseInt(barElem.style.width)
      let dotSite = event.changedTouches[0].screenX
      // let startSite = this.getBodyOffset(barElem)
      let clientWidth = document.documentElement.clientWidth
      let startSite = (clientWidth - (barWidth + 60)) / 2 + 60
      let site = ''
      if (dotSite < startSite) {
        site = 0
      } else if (dotSite > (startSite + barWidth)) {
        site = startSite + barWidth
      } else {
        site = dotSite - startSite
      }
      this.calcVolume(site)
    },
    calcVolume (site) {
      let [dotElem, barWidth, dotWidth] = this.initSound()
      // 进度条对应显示
      if (site < (dotWidth / 2)) {
        return
        // dotElem.style.left = 0
      } else if (site > (barWidth - (dotWidth / 2))) {
        return
       // dotElem.style.left = (barWidth - dotWidth) + 'px'
      } else {
        dotElem.style.left = site - (dotWidth / 2) + 'px'
      }
      // 获取当前的位置
      // let curSite = site <= 0 ? 0 : (site >= barWidth) ? barWidth : site
      let curSite = site
      console.log(curSite)
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
  height: 20px;
  border-radius: 12px;
  background: #ace93c;
}
.bar-dot{
  position: absolute;
  top: -50%;
  left: 31px;
  width: 40px;
  height: 30px;
  padding: 5px;
  border-radius: 20px 20px 20px 20px;
  background: #eed23a;
  cursor: pointer;
}
.inner-dot{
  width: 40px;
  height: 30px;
  border-radius: 20px 20px 20px 20px;
  background: #c78c29;
}
</style>
