var Audio = {}
Audio.install = function (Vue, options) {
  Vue.prototype.$audio = {
    volume: 0.5,
    max: 1
  }
  Vue.prototype.$audio.ui = 'static/audios/ui_click.mp3'
  Vue.prototype.$audio.btn = 'static/audios/btn_click.mp3'
  Vue.prototype.$audio.play = function (src) {
    let elem = document.getElementById('audio')
    let audio = document.createElement('audio')
    audio.src = src
    elem.appendChild(audio)
    if (audio.paused || audio.ended) {
      setTimeout(() => {
        elem.removeChild(audio)
      }, 1000)
      audio.volume = this.volume
      audio.play()
      // window.android.read('当前音频大小为：' + audio.volume)
    }
  }
  Vue.prototype.$audio.setvol = function (val) {
    if (val <= 0) {
      this.volume = 0
      return
    } else if (val >= 1) {
      this.volume = 1
      return
    }
    this.volume = val
  }
}
module.exports = Audio
