import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Teams from '../views/Teams.vue'
import TeamDetail from '../views/TeamDetail.vue'
import Athletes from '../views/Athletes.vue'
import AthleteDetail from '../views/AthleteDetail.vue'
import Races from '../views/Races.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/teams',
    name: 'Teams',
    component: Teams
  },
  {
    path: '/teams/:id',
    name: 'TeamDetail',
    component: TeamDetail,
    props: true
  },
  {
    path: '/athletes',
    name: 'Athletes',
    component: Athletes
  },
  {
    path: '/athletes/:id',
    name: 'AthleteDetail',
    component: AthleteDetail,
    props: true
  },
  {
    path: '/races',
    name: 'Races',
    component: Races
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
