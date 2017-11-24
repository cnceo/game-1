<template>
  <div id="app">
    <router-view/>
    
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      userInfo: {},
      music: null,
      timer: null,
      screenWidth: '',
      screenHeight: ''
    }
  },
  created () {
    // var vm = this
    // this.$$.JsBridge.registerHandler('getUserInfo', function (data, responseCallback) {
    //   // vm.id = JSON.parse(data).id;
    //   // vm.token = JSON.parse(data).token
    //   window.android.read(typeof data)
    //   window.android.read(data)
    //   responseCallback(data)
    // })
    // 获取Android传来的数据
    window.user = '{"max": "31", "cur": "15"}'
    window.music = '{"max": "31", "cur": "15"}'
    let total = 0
    this.timer = setInterval(() => {
      if (total >= 2) {
        clearInterval(this.timer)
        this.timer = null
      }
      if (window.user) { // object类型
        total++
        this.userInfo = window.JSON.parse(window.user)
        this.$store.dispatch('getWxUserInfo', this.userInfo)
      }
      if (window.music) {
        total++
        this.music = Object.assign({}, this.handleJSON(window.music))
        this.$store.dispatch('getMusic', {
          sound: {
            max: this.$audio.max,
            cur: this.$audio.volume
          },
          music: this.music
        })
      }
    }, 500)
    // 获取消息信息
    // this.$store.dispatch('indexPublic', {})
    // function createCORS (method, url) {
    //   var xhr = new XMLHttpRequest()
    //   if ('withCredentials' in xhr) {
    //     xhr.open(method, url, true)
    //   } else if (typeof XDomainRequest !== 'undefined') {
    //     xhr = new XDomainRequest()
    //     xhr.open(method, url)
    //   } else {
    //     xhr = null
    //   }
    //   return xhr
    // }
    // var request = createCORS('post', 'http://www.syhpgkj.com:8080/app/get/notice')
    // if (request) {
    //   request.onload = function (data) {
    //     console.log(request.responseText)
    //   }
    //   request.send()
    // }
  },
  methods: {
    handleJSON (json) {
      let reg = /[0-9a-zA-Z_]+/g
      let data = json.replace(reg, function (str) {
        return "'" + str + "'"
      })
      return window.JSON.parse(data)
    }
  },
  mounted () {
    this.screenWidth = document.documentElement.clientWidth + 'px'
    this.screenHeight = document.documentElement.clientHeight + 'px'
    document.getElementById('app').style.width = this.screenWidth
    document.getElementById('app').style.height = this.screenHeight
    document.getElementById('app').style.overflow = 'hidden'
  }
}
</script>

<style>

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0;
  padding: 0;
}
h1, h2 {
  font-weight: normal;
  margin: 20px 0;
}

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

li{
  display: inline-block;
  margin: 0;
}

p{
  margin: 0;
}

a {
  color: #42b983;
}
</style>
