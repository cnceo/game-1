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
