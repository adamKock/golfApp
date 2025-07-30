<template>
  <div class="container">
    <!-- Add Round Form -->
    
    <nav class="nav-bar">
        <router-link to="/" class="nav-link">Home</router-link>
      <router-link to="/AddRound" class="nav-link">Add Round</router-link>
      <router-link to="/ViewRound" class="nav-link">View Round</router-link>
      <router-link to="/ViewStats" class="nav-link">View Stats</router-link>
      <router-link to="/ViewInsights" class="nav-link">View Insights</router-link>

    </nav>

    <div class="home-container">
      <header class="hero">
        <h1>Adam's Golf Stats Tracker - View Stats</h1>
        <p>Enter your score </p>
      </header>
    </div>

    <!-- Change from a total round to hole by hole UI --> 
    <!-- Initally input the course and the day then submit the hole by hole info-->
    <!-- What hole, what is par for the hole, what did I score on the hole -->  
    <!-- Fairway either hit, missed left or missed right or n-->
    <!-- Total Putts -->    
    

      <div>
  <label>
  </label>
</div>
       

      
    
  </div>
</template>

<script>

</script>

<style scoped>
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
  max-width: 800px;
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

</style>
