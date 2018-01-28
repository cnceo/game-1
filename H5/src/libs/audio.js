var Audio = {}
Audio.install = function (Vue, options) {
  Vue.prototype.$audio = {
    volume: 0.6,
    max: 1
  }
  Vue.prototype.$audio.ui = 'static/audios/ui_click.mp3'
  Vue.prototype.$audio.btn = 'static/audios/btn_click.mp3'
  Vue.prototype.$audio.sg = 'static/audios/start_game.mp3'
  Vue.prototype.$audio.pk = 'static/audios/card_pk.mp3'
  Vue.prototype.$audio.gp = 'static/audios/card_gp.mp3'
  Vue.prototype.$audio.dx = 'static/audios/down_xz.mp3'
  Vue.prototype.$audio.px = 'static/audios/play_xz.mp3'
  Vue.prototype.$audio.play = function (src) {
    let elem = document.getElementById('audio')
    let audio = document.createElement('audio')
    let source = document.createElement('source')
    source.src = src
    audio.appendChild(source)
    elem.appendChild(audio)
    if (audio.paused || audio.ended) {
      let timer = setTimeout(() => {
        clearTimeout(timer)
        timer = null
        elem.removeChild(audio)
      }, 2000)
      audio.volume = this.volume
      audio.play()
      // window.android.read('当前音频大小为：' + audio.volume)
    }
  }
  Vue.prototype.$audio.play1 = function (src) {
    let elem = document.getElementById('audio1')
    let audio1 = document.createElement('audio')
    let source = document.createElement('source')
    source.src = src
    audio1.appendChild(source)
    elem.appendChild(audio1)
    if (audio1.paused || audio1.ended) {
      let timer = setTimeout(() => {
        clearTimeout(timer)
        timer = null
        elem.removeChild(audio1)
      }, 1100)
      audio1.volume = this.volume
      audio1.play()
      // window.android.read('当前音频大小为：' + audio.volume)
    }
  }
  Vue.prototype.$audio.play2 = function (src) {
    let elem = document.getElementById('audio2')
    let audio2 = document.createElement('audio')
    let source = document.createElement('source')
    source.src = src
    audio2.appendChild(source)
    elem.appendChild(audio2)
    if (audio2.paused || audio2.ended) {
      let timer = setTimeout(() => {
        clearTimeout(timer)
        timer = null
        elem.removeChild(audio2)
      }, 1100)
      audio2.volume = this.volume
      audio2.play()
      // window.android.read('当前音频大小为：' + audio.volume)
    }
  }
  Vue.prototype.$audio.play3 = function (src) {
    let elem = document.getElementById('audio3')
    let audio3 = document.createElement('audio')
    let source = document.createElement('source')
    source.src = src
    audio3.appendChild(source)
    elem.appendChild(audio3)
    if (audio3.paused || audio3.ended) {
      let timer = setTimeout(() => {
        clearTimeout(timer)
        timer = null
        elem.removeChild(audio3)
      }, 1000)
      audio3.volume = this.volume
      audio3.play()
      // window.android.read('当前音频大小为：' + audio.volume)
    }
  }
  Vue.prototype.$audio.play4 = function (src) {
    let elem = document.getElementById('audio4')
    let audio4 = document.createElement('audio')
    let source = document.createElement('source')
    source.src = src
    audio4.appendChild(source)
    elem.appendChild(audio4)
    if (audio4.paused || audio4.ended) {
      let timer = setTimeout(() => {
        clearTimeout(timer)
        timer = null
        elem.removeChild(audio4)
      }, 1100)
      audio4.volume = this.volume
      audio4.play()
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
