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
  watch: {
    sound (val) {
      // 初始化音量进度条
      let rate = this.sound.cur / this.sound.max
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
      let [dotElem, barWidth, dotWidth] = this.initSound()
      let site = event.offsetX
      // 进度条对应显示
      if (site <= 0) {
        dotElem.style.left = 0
      } else if (site >= (barWidth - dotWidth)) {
        dotElem.style.left = barWidth - dotWidth + 'px'
      } else {
        dotElem.style.left = site + 'px'
      }
      // 获取当前的位置
      let curSite = parseInt(dotElem.style.left)
      // 计算此时的音量
      let maxSound = this.sound.max
      let rate = (curSite / barWidth)
      let curSound = Math.round(maxSound * rate)
      // 将最终值传给父元素
      this.$emit('on-change', curSound)
    },
    moveDot (event) {
      let [dotElem, barWidth, dotWidth] = this.initSound()
      let barElem = this.$refs.soundbox
      let dotSite = event.changedTouches[0].clientX
      let startSite = this.getBodyOffset(barElem)
      let site = dotSite - (startSite - barWidth)
      // 进度条对应显示
      if (dotSite <= (startSite - barWidth)) {
        dotElem.style.left = 0
      } else if (dotSite >= (startSite - dotWidth)) {
        dotElem.style.left = barWidth - dotWidth + 'px'
      } else {
        dotElem.style.left = site - (barWidth / 2) + 'px'
      }
      // 获取当前的位置
      let curSite = parseInt(dotElem.style.left)
      // 计算此时的音量
      let maxSound = this.sound.max
      let rate = (curSite / barWidth)
      let curSound = Math.round(maxSound * rate)
      // 将最终值传给父元素
      this.$emit('on-change', curSound)
    },
    getBodyOffset (elem) {
      let offsetLeft = 0
      while (elem.tagName.toString().toLowerCase() !== 'body') {
        offsetLeft += elem.offsetLeft
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
