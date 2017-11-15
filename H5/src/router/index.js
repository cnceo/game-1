import Vue from 'vue'
import Router from 'vue-router'
import UserInterface from '@/pages/UserInterface'
import GameInterface from '@/pages/GameInterface'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'UserInterface',
      component: UserInterface
    },
    {
      path: '/game',
      name: 'GameInterface',
      component: GameInterface
    }
  ]
})
