import Vue from 'vue'
import Router from 'vue-router'
import UserInterface from '@/components/UserInterface'
import GameInterface from '@/components/GameInterface'

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
