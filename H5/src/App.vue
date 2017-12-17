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
      music: {},
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
  },
  mounted () {
    // 获取Android传来的数据
    let vm = this
    // 获取系统音量信息
    this.$JsBridge.registerHandler('getSound', function (data, responseCallback) {
      // 将原生带来的参数，显示在show标签位置
      let max = data.split('&')[0].split('=')[1]
      let cur = data.split('&')[1].split('=')[1]
      vm.music.cur = cur
      vm.music.max = max
      vm.$store.dispatch('getMusic', {
        sound: {
          max: vm.$audio.max,
          cur: vm.$audio.volume
        },
        music: vm.music
      })
      var responseData = '当前音量：' + max + '&' + cur
      // 调用responseCallback方法可以带传参数到原生
      responseCallback(responseData)
    })
  },
  methods: {
    handleJSON (json) {
      let reg = /[0-9a-zA-Z_]+/g
      let data = json.replace(reg, function (str) {
        return "'" + str + "'"
      })
      return data
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
  height: 100vh;
  overflow: hidden;
}
h1, h2 {
  font-weight: normal;
  margin: 20px 0;
  line-height: 48px;
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
  margin: 0 0 15px 0;
  line-height: 45px;
}

a {
  color: #42b983;
}
</style>
