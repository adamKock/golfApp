<template>
  <div>
    <!-- Full-width navigation bar -->
      <nav class="nav-bar">
      <router-link to="/" class="nav-link">Home</router-link>
      <router-link to="/AddRound" class="nav-link">Add Round</router-link>
      <router-link to="/ViewRound" class="nav-link">View Round</router-link>
      <router-link to="/ViewStats" class="nav-link">View Stats</router-link>
       <router-link to="/ViewInsights" class="nav-link">View Insights</router-link>
    </nav>

    <!-- Page content in a centered container -->
    <div class="home-container">
      <header class="hero">
        <h1>Adam's Golf Stats Tracker - Home  </h1>
        <p>Track your golf rounds, stats, and performance — one hole at a time.</p>
        <img src ="/images/Miramar15th.jpg" class="img-navBar"></img>
      </header>
      <ChartComponent/>
    </div>

    <body class="img-home">
      
    </body>
  </div>
</template>

<script>

import ChartComponent from './PieChartComponent.vue';
export default {
  name: 'HomePage',
  components: {
    ChartComponent
  }
};

</script>

<style scoped>
/* Nav bar across the screen */
.nav-bar {
  width: 100%;
  background-color: #2ecc71;
  display: flex;
  justify-content: center;
  gap: 2rem;
  padding: 1rem;
  position: sticky;
  top: 0;
  z-index: 10;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: bold;
  font-size: 1.1rem;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  transition: background-color 0.3s ease;
}

.nav-link:hover {
  background-color: #27ae60;
}

/* Content stays centered */
.home-container {
  max-width: 900px;
  margin: 3rem auto;
  padding: 2rem;
  text-align: center;
  font-family: 'Arial', sans-serif;
}

.hero h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.hero p {
  font-size: 1.2rem;
  color: #555;
}


h1 {
  width: 100%;
  text-align: center; /* Optional: centers the text */
  padding: 20px; /* Optional: adds spacing */
  margin: 0; /* Optional: removes default margin */
}
.img-navBar{
  display: block;
  margin: 0 auto;
  max-width: 100%;
  height: auto;
}

</style>