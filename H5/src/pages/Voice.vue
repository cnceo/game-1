<template>
  <div class="test">
   <!-- <touch :src="src"></touch> -->
   <img src="../assets/imgs/img_Room_yuyin.png" alt=""  @touchstart="startRecording($event)" @touchend="endRecording">
   <div id="box"></div>
  </div>
</template>

<script >
import SRC from '../assets/imgs/img_Room_yuyin.png'
export default {
  data () {
    return {
      audioContext: null,
      recorder: null,
      src: SRC
    }
  },
  created () {
    console.log(window.AudioContext)
    this.init()
  },
  methods: {
    init () {
      let vm = this
      window.AudioContext = window.AudioContext || window.webkitAudioContext
      navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
      window.URL = window.URL || window.webkitURL
      this.audioContext = new AudioContext()
      if (navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({
          audio: true}).then(function (stream) {
            vm.startUserMedia(stream)
            /* use the stream */
          }).catch(function (err) {
            console.log(err)
            /* handle the error */
          })
      } else {
        console.log('getUserMedia not supported')
      }
    },
    startUserMedia (stream) {
      var input = this.audioContext.createMediaStreamSource(stream)
      this.recorder = new window.Recorder(input)
    },
    startRecording (e) {
      e.preventDefault()
      this.recorder && this.recorder.record()
    },
    endRecording (e) {
      this.recorder && this.recorder.stop()
      this.createDownloadLink()
    },
    createDownloadLink () {
      this.recorder && this.recorder.exportWAV(function (blob) {
        var url = URL.createObjectURL(blob)
        var li = document.createElement('li')
        var au = document.createElement('audio')
        var hf = document.createElement('a')
        au.controls = true
        au.src = url
        hf.href = url
        hf.download = new Date().toISOString() + '.wav'
        hf.innerHTML = hf.download
        li.appendChild(au)
        li.appendChild(hf)
        document.getElementById('box').appendChild(li)
      })
    }
  }
}
</script>
