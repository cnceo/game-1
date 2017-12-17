import Modal from './Modal'
import SoundBar from './SoundBar'
import Setting from './Setting'
import SelectType from './SelectType'
import DjGame from '../pages/DjGame'
import QtGame from '../pages/QtGame'
import HtGame from '../pages/HtGame'

// 公共组件全局注册
const Global = {
  install: function (Vue) {
    // 注册弹出窗
    Vue.component('Modal', Modal)

    // 注册声音条
    Vue.component('SoundBar', SoundBar)

    // 注册设置
    Vue.component('Setting', Setting)

    // 类型选择(单选)
    Vue.component('SelectType', SelectType)

     // 大九
    Vue.component('dj-game', DjGame)
     // 清推
    Vue.component('qt-game', QtGame)
      // 混推
    Vue.component('ht-game', HtGame)
  }
}

export default Global
