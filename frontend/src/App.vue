<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import AddRound from './components/AddRound.vue'
import HomePage from './components/HomePage.vue'
import ChartComponent from './components/PieChartComponent.vue';

export default {
  name: 'App',
  components: {
    AddRound,
    HomePage,
    ChartComponent,
  },
}
</script>