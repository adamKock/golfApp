import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AddRound from '@/components/AddRound.vue'
import ViewRound from '@/components/ViewRound.vue'
import ViewStats from '@/components/ViewStats.vue'
import ViewInsights from '@/components/ViewInsights.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/AddRound',
      name: 'AddRound',
      component: AddRound,
    },
    {
      path: '/ViewRound',
      name: 'ViewRound',
      component: ViewRound,
    },
    {
      path: '/ViewStats',
      name: 'ViewStats',
      component: ViewStats,
    },
     {
      path: '/ViewInsights',
      name: 'ViewInsights',
      component: ViewInsights,
    },
  ],
})

export default router
