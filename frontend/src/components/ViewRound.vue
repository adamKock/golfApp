<template>
  <div class="container">
    <!-- Add Round Form -->
    
    <nav class="nav-bar">
   <router-link to="/" class="nav-link">Logout</router-link>
     <router-link to="/home" class="nav-link">Home</router-link>
      <router-link to="/AddRound" class="nav-link">Add Round</router-link>
      <router-link to="/ViewRound" class="nav-link">View Round</router-link>
      <router-link to="/ViewStats" class="nav-link">View Stats</router-link>
      <router-link to="/ViewInsights" class="nav-link">View Insights</router-link>

    </nav>

    <div class="home-container">
      <header class="hero">
        <h1>Adam's Golf Stats Tracker - View Round</h1>
      </header>
    </div>

    <div class="viewRoundInputs"><label for="coursename">Course Name</label>
      <input type="text" v-model="courseName" id="coursename" placeholder="Course Name" required/>

      <label for="date">Date</label>
      <input type="date" v-model="date" id="date"/>
    </div>

<div>
  <button class="submit-button" id="getRoundBtn" @click="getRound"> Get Round</button>
</div>
 
<div v-if="roundData" class="round-summary">
  <h3>Summary Stats Round</h3>
  <p><strong>Course Name:</strong> {{ courseName }}</p>
  <p><strong>Date:</strong> {{ formattedDate }}</p>
  <p><strong>Weather Conditions:</strong> {{ weatherConditions }}</p>
  <p><strong>Total Score:</strong> {{ totalStrokes }}</p>
  <p><strong>Total Par:</strong> {{ totalPar }}</p>
  <p><strong>Total Putts:</strong> {{ totalPutts }}</p>
  <p><strong>1-Putts:</strong> {{ onePutts }}</p>
  <p><strong>2-Putts:</strong> {{ twoPutts }}</p>
  <p><strong>3+ Putts:</strong> {{ threePlusPutts }}</p>
  <p><strong>Fairways Hit:</strong> {{ fairwaysHit }} ({{ fairwayPercentage }}%)</p>
  <p><strong>Total Fairways:</strong> {{ fairwayAttempted }}</p>
  <p><strong>Green In Regulation :</strong> ({{ greensInRegulationPercentage }}%)</p>


</div>

<div v-if="roundData" class="charts-row">
  <div class="chart-wrapper">
    <h3>Putts Breakdown</h3>
    <ChartComponent :chartData="puttsData" :chartOptions="options" chartType="pie" />
  </div>

  <div class="chart-wrapper">
    <h3>Fairway Accuracy</h3>
    <ChartComponent :chartData="fairwayAccuracy" :chartOptions="options" chartType="pie" />
  </div>

    <div class="chart-wrapper">
    <h3>Green In Regulation Accuracy</h3>
    <ChartComponent :chartData="girComputed" :chartOptions="options" chartType="pie" />
  </div>

  <div class="chart-wrapper">
    <h3>Score vs Par</h3>
    <BarChartComponent :chartData="scoreVsPar" :chartOptions="options" chartType="bar" />
  </div>
</div>

<div v-if="roundData && roundData.holes" class="hole-data-table">
  <h3>Hole By Hole Breakdown</h3>
  <table>
    <thead>
      <tr>
        <th>Hole</th>
        <th>Par</th>
        <th>Total Strokes</th>
        <th>Putts</th>
        <th>Fairway</th>
        <th>Penalties</th>
        <th>Tee Club Used</th>
        <th>Green In Reg</th>
        <th>Up and Down</th>

      </tr>
    </thead>
    <tbody>
      <tr v-for="(hole, index) in roundData.holes" :key="index">
        <td>{{ index + 1 }}</td>
        <td>{{ hole.par }}</td>
        <td>{{ hole.strokes }}</td>
        <td>{{ hole.putts }}</td>
        <td>{{ hole.fairway }}</td>
        <td>{{ hole.penalties }}</td>
        <td>{{ hole.teeClubUsed }}</td>
        <td>{{ hole.gir ? 'Yes' : 'No' }}</td>
        <td>{{ hole.upAndDown}}</td>




      </tr>
    </tbody>
  </table>
 
</div>



        <!-- Simple Form that you click a date and input the course name maybe a drop down ?? --> 
        <!-- Then that performs a get and then you get the data on screen --> 



     <!-- Currently the data gets returned onto the screen.
      Need to figure out how I want the round data to be displayed
      Maybe a -->
    

      
  

       

      
    
  </div>
</template>
<script>
import { ref, computed } from 'vue';
import { Pie } from 'vue-chartjs';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import ChartComponent from './PieChartComponent.vue'
import BarChartComponent from './BarChartComponent.vue';

Chart.register(ArcElement, Tooltip, Legend);

export default {
  components: {
    ChartComponent, // ✅ add this line!
    BarChartComponent,
  },
  setup() {
    const courseName = ref('');
    const date = ref('');
    const roundData = ref(null);

    async function getRound() {
      if (!courseName.value || !date.value) {
        alert('Please fill in both Course Name and Date');
        return;
      }

       // Get userId from localStorage here
  const userId = localStorage.getItem('userId');
  console.log('User ID:', userId); // Debug log

      const data = {
        courseName: courseName.value.trim(),
        date: date.value,
        userId: userId,
      };

      try {
        const response = await fetch('http://localhost:8080/round/find', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(data),
        });

        if(response.status===404){
          const errorData = await response.json();
      alert(errorData.message || 'No round found for the specified criteria');
      roundData.value = null;
      return;

        }

        if (!response.ok) {
          throw new Error('Network response was not OK');
        }

        

        const result = await response.json();
        roundData.value = result;
        console.log('Round Holes:', roundData.value.holes);

      } catch (error) {
        console.error('Error fetching round:', error);
      }
    }

    // --- Summary Computed Properties ---
    const totalStrokes = computed(() => {
      return roundData.value ? roundData.value.holes.reduce((sum, h) => sum + h.strokes, 0) : 0;
    });

  

 

    const weatherConditions = computed(() => {
  return roundData.value ? roundData.value.weatherConditions : '';
});

    const formattedDate = computed(() => {
  if (!date.value) return '';
  const [year, month, day] = date.value.split('-');
  return `${day}-${month}-${year}`;
});

    const totalPar = computed(() => {
  if (!roundData.value) return 0;
  return roundData.value.holes.reduce((sum, hole) => sum + Number(hole.par), 0);
});

    const totalPutts = computed(() => {
      return roundData.value ? roundData.value.holes.reduce((sum, h) => sum + h.putts, 0) : 0;
    });

    const onePutts = computed(() => {
      return roundData.value ? roundData.value.holes.filter(h => h.putts === 1).length : 0;
    });

    const twoPutts = computed(() => {
      return roundData.value ? roundData.value.holes.filter(h => h.putts === 2).length : 0;
    });

    const threePlusPutts = computed(() => {
      return roundData.value ? roundData.value.holes.filter(h => h.putts >= 3).length : 0;
    });

 const fairwaysHit = computed(() => {
  if (!roundData.value) return 0;
  return roundData.value.holes.filter(h => Number(h.par) !== 3 && (h.fairway === true || h.fairway === 'Hit')).length;
});

const fairwaysMissedLeft = computed(() => {
  if (!roundData.value) return 0;
  return roundData.value.holes.filter(h => Number(h.par) !== 3 && h.fairway === 'Left').length;
});

const fairwaysMissedRight = computed(() => {
  if (!roundData.value) return 0;
  return roundData.value.holes.filter(h => Number(h.par) !== 3 && h.fairway === 'Right').length;
});

const fairwayAttempted = computed(() => {
  if (!roundData.value) return 0;
  // Count holes where par != 3 AND fairway is not null or undefined
  return roundData.value.holes.filter(h => Number(h.par) !== 3 && h.fairway != null).length;
});

    const fairwayPercentage = computed(() => {
      return fairwayAttempted.value > 0 ? Math.round((fairwaysHit.value / fairwayAttempted.value) * 100) : 0;
    });

    const greensInRegulationHit = computed(() => {
      return roundData.value ? roundData.value.holes.filter(h => h.gir === true).length : 0;
    });

      const greensInRegulationMissed = computed(() => {
      return roundData.value ? roundData.value.holes.filter(h => h.gir === false).length : 0;
    });

    const greensInRegulationPercentage = computed(() => {
      return roundData.value ? Math.round((greensInRegulationHit.value / roundData.value.holes.length) * 100) : 0;
    });

const puttsData = computed(() => {
  if (!roundData.value) {
    return {
      labels: ['1-Putts', '2-Putts', '3+ Putts'],
      datasets: [{
        label: 'Putts Breakdown',
        backgroundColor: ['#4caf50', '#ff9800', '#f44336'],
        data: [0, 0, 0],
      }],
    };
  }

  return {
    labels: ['1-Putts', '2-Putts', '3+ Putts'],
    datasets: [
      {
        backgroundColor: ['#4caf50', '#ff9800', '#f44336'],
        data: [onePutts.value, twoPutts.value, threePlusPutts.value],
      },
    ],
  };
});

const fairwayAccuracy = computed(() => {
  if (!roundData.value) {
    return {
      labels: ['Left', 'Right', 'Hit'],
      datasets: [{
        label: 'Fairway Accuracy Breakdown',
        backgroundColor: ['#4caf50', '#ff9800', '#f44336'],
        data: [0, 0, 0],
      }],
    };
  }

  return {
    labels: ['Left', 'Right', 'Hit'],
    datasets: [
      {
        backgroundColor: ['#4caf50', '#ff9800', '#f44336'],
        data: [fairwaysMissedLeft.value, fairwaysMissedRight.value, fairwaysHit.value],
      },
    ],
  };
});

const scoreVsPar = computed(() => {
  if (!roundData.value) {
    return {
      labels: ['Par', 'Score'],
      datasets: [{
        label: 'Score vs Par',
        backgroundColor: ['#4caf50', '#ff9800', ],
        data: [0, 0, ],
      }],
    };
  }

  return {
     labels: ['Par', 'Score'],
    datasets: [
      {
       label: 'Score vs Par',
        backgroundColor: ['#4caf50', '#ff9800'],
        data: [totalPar.value, totalStrokes.value],
      },
    ],
  };
});

const girComputed = computed(() => {
  if (!roundData.value) {
    return {
      labels: ['Hit', 'Missed'],
      datasets: [{
        label: 'Green In Reg Hit vs Missed',
        backgroundColor: ['#4caf50', '#ff9800', ],
        data: [0, 0, ],
      }],
    };
  }


  return {
     labels: ['Hit', 'Missed'],
    datasets: [
      {
       label: 'Green In Reg Hit vs Missed',
        backgroundColor: ['#4caf50', '#ff9800'],
        data: [greensInRegulationHit.value, greensInRegulationMissed.value],
      },
    ],
  };
});





const options = {
  responsive: true,
  plugins: {
    legend: {
      position: 'bottom',
    },
  },
};

    // Add more stats as needed...

    return {
      courseName,
      date,
      getRound,
      roundData,
      totalStrokes,
      totalPutts,
      onePutts,
      twoPutts,
      threePlusPutts,
      fairwaysHit,
      fairwayPercentage,
      greensInRegulationHit,
      greensInRegulationMissed,
      greensInRegulationPercentage,
      options,
      puttsData,
      fairwayAttempted,
      fairwaysMissedRight,
      fairwaysMissedLeft,
      fairwayAccuracy,
      totalPar,
      scoreVsPar,
      formattedDate,
      weatherConditions,
      girComputed,
    
    };
  },
};
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
  margin: 2rem auto;
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
.viewRoundInputs {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  max-width: 500px;
  margin: 1rem auto;
}
label {
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #333;
  font-size: 1rem;
  text-align: center;
  
}
input {
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  background-color: #fff;
  transition: border-color 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #2ecc71;
  box-shadow: 0 0 0 2px rgba(46, 204, 113, 0.2);
}
.submit-button {
  margin: 1rem auto;
  display: block;
  padding: 0.75rem 1.5rem;
  background-color: #2ecc71;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.submit-button:hover {
  background-color: #27ae60;
}

.charts-row {
  display: flex;
  justify-content: space-between; /* Distribute space evenly */
  gap: 1rem; /* Reduced gap between charts */
  flex-wrap: nowrap; /* Prevent wrapping */
  margin-top: 2rem;
  overflow-x: auto; /* Allow horizontal scrolling if needed */
  padding-bottom: 1rem; /* Space for scrollbar */
}

.chart-wrapper {
  flex: 1; /* Each chart takes equal space */
  min-width: 22%; /* Minimum width for each chart */
  max-width: 24%; /* Maximum width for each chart */
  text-align: center;
  padding: 0.5rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}
.hole-data-table {
  margin: 2rem auto;
  max-width: 1420px; /* Increased from 800px */
  overflow-x: auto;
}

.hole-data-table table {
  width: 100%;
  table-layout: fixed; /* Ensures consistent column widths */
  border-collapse: collapse;
  font-family: Arial, sans-serif;
}

.hole-data-table th,
.hole-data-table td {
  border: 1px solid #ddd;
  padding: 1rem; /* Increased padding for better spacing */
  text-align: center;
  word-wrap: break-word;
}

.hole-data-table th {
  background-color: #f4f4f4;
  font-weight: bold;
  font-size: 1rem;
}

.hole-data-table tr:nth-child(even) {
  background-color: #f9f9f9;
}
.round-summary{
  text-align: left;
  padding: 1rem;
}


</style>
