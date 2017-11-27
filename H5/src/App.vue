<template>
  <div id="app">
    <router-view/>
    
  </div>
</template>

<script>
// import {mapGetters} from 'vuex'
export default {
  name: 'app',
  data () {
    return {
      userInfo: {},
      music: null,
      timer: null,
      room: '',
      screenWidth: '',
      screenHeight: ''
    }
  },
  created () {
    // 获取Android传来的数据
    let vm = this
    // 获取用户微信信息
    this.$JsBridge.registerHandler('getUserInfo', function (data, responseCallback) {
      // 将原生带来的参数，显示在show标签位置
      vm.userInfo = window.JSON.parse(data)
      vm.$store.dispatch('getWxUserInfo', vm.userInfo)
      var responseData = '微信用户信息：' + window.JSON.stringify(vm.userInfo)
      // 调用responseCallback方法可以带传参数到原生
      responseCallback(responseData)
    })
    // let vm = this
    //   this.$JsBridge.callHandler(
    //     'submitFromWeb' // 原生的方法名
    //     , {'param': 'aaaaa'} // 带个原生方法的参数
    //     , function (responseData) { // 响应原生回调方法
    //       vm.content2 = 'send get responseData from java, data = ' + responseData
    //     }
    //   )
    // window.user = '{"max": "31", "cur": "15"}'
    // window.music = '{"max": "31", "cur": "15"}'
    // let total = 0
    // this.timer = setInterval(() => {
    //   if (total >= 2) {
    //     clearInterval(this.timer)
    //     this.timer = null
    //   }
    //   if (window.user) { // object类型
    //     total++
    //     this.userInfo = window.JSON.parse(window.user)
    //     window.android.read(window.user)
    //     this.$store.dispatch('getWxUserInfo', this.userInfo)
    //   }
    //   if (window.music) {
    //     total++
    //     this.music = Object.assign({}, this.handleJSON(window.music))
    //     this.$store.dispatch('getMusic', {
    //       sound: {
    //         max: this.$audio.max,
    //         cur: this.$audio.volume
    //       },
    //       music: this.music
    //     })
    //   }
    // }, 500)
  },
  mounted () {
    // 获取Android传来的数据
    let vm = this
    // 获取系统音量信息
    this.$JsBridge.registerHandler('getSound', function (data, responseCallback) {
      // 将原生带来的参数，显示在show标签位置
      vm.music = Object.assign({}, vm.handleJSON(data))
      vm.$store.dispatch('getMusic', {
        sound: {
          max: vm.$audio.max,
          cur: vm.$audio.volume
        },
        music: vm.music
      })
      var responseData = '当前音量：' + window.JSON.stringify(vm.music)
      // 调用responseCallback方法可以带传参数到原生
      responseCallback(responseData)
    })
    this.screenWidth = document.documentElement.clientWidth + 'px'
    this.screenHeight = document.documentElement.clientHeight + 'px'
    document.getElementById('app').style.width = this.screenWidth
    document.getElementById('app').style.height = this.screenHeight
    document.getElementById('app').style.overflow = 'hidden'
  },
  // computed: mapGetters({
  //   roomId: 'listenRoom'
  // }),
  // watch: {
  //   roomId (val) {
  //     this.$store.dispatch('getRoom', val)
  //     this.$router.push({path: '/game', query: {}})
  //   }
  // },
  methods: {
    handleJSON (json) {
      let reg = /[0-9a-zA-Z_]+/g
      let data = json.replace(reg, function (str) {
        return "'" + str + "'"
      })
      return window.JSON.parse(data)
    }
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
