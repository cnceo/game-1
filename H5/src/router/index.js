import Vue from 'vue'
import Router from 'vue-router'
import UserInterface from '@/pages/UserInterface'
import QtGame from '@/pages/QtGame'
import HtGame from '@/pages/HtGame'
import DjGame from '@/pages/DjGame'
// import Voice from '@/pages/Voice'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'UserInterface',
      component: UserInterface
    },
    {
      path: '/qt',
      name: 'QtGame',
      component: QtGame
    },
    {
      path: '/ht',
      name: 'HtGame',
      component: HtGame
    },
    {
      path: '/dj',
      name: 'DjGame',
      component: DjGame
    }
  ]
})
