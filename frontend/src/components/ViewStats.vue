<template>
  <div class="container">
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
        <h1>Adam's Golf Stats Tracker - View All Rounds</h1>
      </header>

      <div class="controls">
        <div class="filter-controls" v-if="allRounds.length > 0">
          <select v-model="selectedCourse" @change="filterRounds">
            <option value="">All Courses</option>
            <option v-for="course in uniqueCourses" :value="course">{{ course }}</option>
          </select>
          <input type="date" v-model="dateFilter" @change="filterRounds" placeholder="Filter by date">
        </div>
      </div>

      <!-- Overall Statistics -->
      <div v-if="filteredRounds.length > 0" class="summary-section">
        <h2>Overall Statistics</h2>
        <div class="stats-grid">
          <div class="stat-card">
            <h3>Average Score</h3>
            <p>{{ averageScore }}</p>
          </div>
          <div class="stat-card">
            <h3>Average Putts</h3>
            <p>{{ averagePutts }}</p>
          </div>
          <div class="stat-card">
            <h3>Fairway Accuracy</h3>
            <p>{{ overallFairwayPercentage }}%</p>
          </div>
          <div class="stat-card">
            <h3>GIR Percentage</h3>
            <p>{{ overallGIRPercentage }}%</p>
          </div>
        </div>
      </div>

      <!-- Performance Trends -->
      <div v-if="filteredRounds.length > 0" class="trends-section">
        <h2>Performance Trends</h2>
        <div class="charts-row">
          <div class="chart-wrapper">
            <h3>Scores Over Time</h3>
            <LineChartComponent 
              :chartData="scoresOverTimeData" 
              :chartOptions="lineChartOptions" 
            />
          </div>
          <div class="chart-wrapper">
            <h3>Putts Over Time</h3>
            <LineChartComponent 
              :chartData="puttsOverTimeData" 
              :chartOptions="lineChartOptions" 
            />
          </div>
          <div class ="chart-wrapper"></div>
          <h3> Fairway Accuracy Over Time</h3>
          <LineChartComponent
          :chartData="fairwayAccuracyOverTimeData"
          :chartOptions="lineChartOptions" 
          />
          <div class="chart-wrapper"></div>
          <h3> Green in Regulation Over Time</h3>
          <LineChartComponent
          :chartData="GIRAccuracyOverTimeData"
          :chartOptions="lineChartOptions" 
          />
        </div>
      </div>

      <!-- Individual Round Details -->
      <div v-for="(round, index) in filteredRounds" :key="round.id || index" class="round-container">
        <div class="round-header" @click="toggleRoundDetails(index)">
          <h3>{{ round.courseName }} - {{ formatDate(round.date) }}</h3>
          <span class="score-badge">{{ calculateTotalStrokes(round.holes) }} ({{ calculateScoreToPar(round.holes) }})</span>
          <span class="toggle-icon">{{ showRoundDetails[index] ? '−' : '+' }}</span>
        </div>

        <div v-if="showRoundDetails[index]" class="round-details">
          <!-- Round Summary -->
          <div class="round-summary">
            <div class="summary-grid">
              <div>
                <p><strong>Weather:</strong> {{ round.weatherConditions }}</p>
                <p><strong>Total Putts:</strong> {{ calculateTotalPutts(round.holes) }}</p>
                <p><strong>Total Penalties:</strong> {{ calculatePenalties(round.holes) }}</p>
             


              </div>
              <div>
                <p><strong>Fairways:</strong> {{ calculateFairwaysHit(round.holes) }}/{{ calculateFairwaysAttempted(round.holes) }} ({{ calculateFairwayPercentage(round.holes) }}%)</p>
                <p><strong>GIR:</strong> {{ calculateGIR(round.holes) }}/{{ getRoundHoleCount(round.holes) }} {{(calculateGIRPercentage(round.holes))}}%</p>
                <p><strong>Up and Downs Per Round:</strong> {{ calculateUpAndDownsPerRound(round.holes) }}</p>

              </div>
            </div>
          </div>

          <!-- Round Charts -->
          <div class="charts-row">
            <div class="chart-wrapper">
              <h4>Putts Breakdown</h4>
              <ChartComponent 
                :chartData="getPuttsData(round.holes)" 
                :chartOptions="pieChartOptions" 
                chartType="pie" 
              />
            </div>
            <div class="chart-wrapper">
              <h4>Fairway Accuracy</h4>
              <ChartComponent 
                :chartData="getFairwayData(round.holes)" 
                :chartOptions="pieChartOptions" 
                chartType="pie" 
              />
            </div>
            <div class="chart-wrapper">
              <h4>Score vs Par</h4>
              <BarChartComponent 
                :chartData="getScoreVsParData(round.holes)" 
                :chartOptions="barChartOptions" 
              />
            </div>
             <div class="chart-wrapper">
              <h4>Green In Regulation Hit</h4>
              <ChartComponent 
                :chartData="getGIRData(round.holes)" 
                :chartOptions="pieChartOptions" 
                chartType="pie"
              />
            </div>
          </div>

          <!-- Hole-by-Hole Details -->
          <div class="hole-details">
            <h4>Hole by Hole Breakdown</h4>
            <div class="table-container">
              <table class="hole-data-table">
                <thead>
                  <tr>
                    <th>Hole</th>
                    <th>Par</th>
                    <th>Score</th>
                    <th>Putts</th>
                    <th>Fairway</th>
                    <th>GIR</th>
                    <th>Up & Down</th>
                    <th>Penalties</th>
                    <th>Tee Club</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(hole, holeIndex) in round.holes" :key="holeIndex" 
                      :class="{'highlight-birdie': hole.strokes < hole.par, 'highlight-bogey': hole.strokes > hole.par}">
                    <td>{{ holeIndex + 1 }}</td>
                    <td>{{ hole.par }}</td>
                    <td>{{ hole.strokes }}</td>
                    <td>{{ hole.putts }}</td>
                    <td :class="{'fairway-hit': hole.fairway === 'Hit', 'fairway-missed': hole.fairway && hole.fairway !== 'Hit'}">
                      {{ hole.fairway || '-' }}
                    </td>
                    <td :class="{'gir-hit': hole.gir, 'gir-missed': !hole.gir}">
                      {{ hole.gir ? 'Yes' : 'No' }}
                    </td>
                    <td>{{ hole.upAndDown || '-' }}</td>
                    <td>{{ hole.penalties || '0' }}</td>
                    <td>{{ hole.teeClubUsed || '-' }}</td>
                  </tr>
                </tbody>
                <tfoot>
                  <tr class="totals-row">
                    <td>Total</td>
                    <td>{{ calculateTotalPar(round.holes) }}</td>
                    <td>{{ calculateTotalStrokes(round.holes) }}</td>
                    <td>{{ calculateTotalPutts(round.holes) }}</td>
                    <td>{{ calculateFairwaysHit(round.holes) }}/{{ calculateFairwaysAttempted(round.holes) }}</td>
                    <td>{{ calculateGIR(round.holes) }}/18</td>
                    <td colspan="3"></td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div v-if="allRounds.length === 0 && !loading" class="empty-state">
        <p>No rounds found. Click "Get All Rounds" to load your data.</p>
      </div>
      
      <div v-if="loading" class="loading-state">
        <p>Loading your rounds...</p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import ChartComponent from './PieChartComponent.vue';
import BarChartComponent from './BarChartComponent.vue';
import LineChartComponent from './LineChartComponent.vue';
import PieChartComponent from './PieChartComponent.vue';

export default {
  components: {
    ChartComponent, 
    BarChartComponent,
    LineChartComponent
  },
  setup() {
    const allRounds = ref([]);
    const filteredRounds = ref([]);
    const loading = ref(false);
    const showRoundDetails = ref([]);
    const selectedCourse = ref('');
    const dateFilter = ref('');
    
    // Chart options
    const pieChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'bottom',
        },
      }
    };
    
    const barChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false,
        },
      },
      scales: {
        y: {
          beginAtZero: true
        }
      }
    };
    
    const lineChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false,
        },
      },
      scales: {
        y: {
          beginAtZero: false
        }
      }
    };

    // Computed properties
    const uniqueCourses = computed(() => {
      const courses = new Set();
      allRounds.value.forEach(round => courses.add(round.courseName));
      return Array.from(courses).sort();
    });
    
    const averageScore = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      const total = filteredRounds.value.reduce((sum, round) => {
        return sum + calculateTotalStrokes(round.holes);
      }, 0);
      return (total / filteredRounds.value.length).toFixed(1);
    });
    
    const averagePutts = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      const total = filteredRounds.value.reduce((sum, round) => {
        return sum + calculateTotalPutts(round.holes);
      }, 0);
      return (total / filteredRounds.value.length).toFixed(1);
    });
    
    const overallFairwayPercentage = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      let totalHit = 0;
      let totalAttempted = 0;
      
      filteredRounds.value.forEach(round => {
        totalHit += calculateFairwaysHit(round.holes);
        totalAttempted += calculateFairwaysAttempted(round.holes);
      });
      
      return totalAttempted > 0 ? Math.round((totalHit / totalAttempted) * 100) : 0;
    });
    
    const overallGIRPercentage = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      let totalGIR = 0;
      let totalHoles = 0;
      
      filteredRounds.value.forEach(round => {
        totalGIR += calculateGIR(round.holes);
        totalHoles += round.holes.length;
      });
      
      return Math.round((totalGIR / totalHoles) * 100);
    });
    
    const scoresOverTimeData = computed(() => {
      return {
        labels: filteredRounds.value.map(round => formatDate(round.date)),
        datasets: [{
          label: 'Score',
          data: filteredRounds.value.map(round => calculateTotalStrokes(round.holes)),
          borderColor: '#2ecc71',
          backgroundColor: 'rgba(46, 204, 113, 0.1)',
          tension: 0.3,
          fill: true
        }]
      };
    });
    
    const puttsOverTimeData = computed(() => {
      return {
        labels: filteredRounds.value.map(round => formatDate(round.date)),
        datasets: [{
          label: 'Putts',
          data: filteredRounds.value.map(round => calculateTotalPutts(round.holes)),
          borderColor: '#3498db',
          backgroundColor: 'rgba(52, 152, 219, 0.1)',
          tension: 0.3,
          fill: true
        }]
      };
    });

    const fairwayAccuracyOverTimeData = computed(() => {
  return {
    labels: filteredRounds.value.map(round => formatDate(round.date)),
    datasets: [{
      label: 'Fairway Accuracy %',
      data: filteredRounds.value.map(round => calculateFairwayPercentage(round.holes)),
      borderColor: '#16a085',
      backgroundColor: 'rgba(22, 160, 133, 0.1)',
      tension: 0.3,
      fill: true
    }]
  };
});

const GIRAccuracyOverTimeData = computed(() => {
  return {
    labels: filteredRounds.value.map(round => formatDate(round.date)),
    datasets: [{
      label: 'GIR %',
      data: filteredRounds.value.map(round => calculateGIRPercentage(round.holes)),
      borderColor: '#8e44ad',
      backgroundColor: 'rgba(142, 68, 173, 0.1)',
      tension: 0.3,
      fill: true
    }]
  };
});

    // Methods
    async function getRounds() {

    // Get userId from localStorage here
  const userId = localStorage.getItem('userId');
  console.log('User ID:', userId); // Debug log

      if(!userId){
        console.error("No User ID found"); 
        return; 
      }

      try {
        loading.value = true;
         const response = await fetch(`http://localhost:8080/round/allRoundsById?userId=${userId}`);
        
       console.log('Response status:', response.status); // Add this
    
    if (!response.ok) {
      // Get the error message from the response body
      const errorText = await response.text();
      console.error('Server error response:', errorText);
      throw new Error(`Server error: ${response.status} - ${errorText}`);
    }
        
        const data = await response.json();
        allRounds.value = data;
        filteredRounds.value = [...data];
        showRoundDetails.value = new Array(data.length).fill(false);
      } catch (error) {
        console.error("Error fetching rounds:", error);
      } finally {
        loading.value = false;
      }
    }
    
    function filterRounds() {
      filteredRounds.value = allRounds.value.filter(round => {
        const courseMatch = !selectedCourse.value || round.courseName === selectedCourse.value;
        const dateMatch = !dateFilter.value || round.date.includes(dateFilter.value);
        return courseMatch && dateMatch;
      });
    }
    
    function toggleRoundDetails(index) {
      showRoundDetails.value[index] = !showRoundDetails.value[index];
    }
    
    function formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
    }
    
    // Calculation functions
    function calculateTotalStrokes(holes) {
      return holes.reduce((sum, h) => sum + h.strokes, 0);
    }
    
    function calculateTotalPar(holes) {
      return holes.reduce((sum, h) => sum + Number(h.par), 0);
    }
    
    function calculateScoreToPar(holes) {
      const totalStrokes = calculateTotalStrokes(holes);
      const totalPar = calculateTotalPar(holes);
      const difference = totalStrokes - totalPar;
      return difference > 0 ? `+${difference}` : difference;
    }
    
    function calculateTotalPutts(holes) {
      return holes.reduce((sum, h) => sum + h.putts, 0);
    }
    
    function calculateFairwaysHit(holes) {
      return holes.filter(h => Number(h.par) !== 3 && (h.fairway === true || h.fairway === 'Hit')).length;
    }
    
    function calculateFairwaysAttempted(holes) {
      return holes.filter(h => Number(h.par) !== 3 && h.fairway != null).length;
    }
    
    function calculateFairwayPercentage(holes) {
      const attempted = calculateFairwaysAttempted(holes);
      return attempted > 0 ? Math.round((calculateFairwaysHit(holes) / attempted) * 100) : 0;
    }
    
    function calculateGIR(holes) {
      return holes.filter(h => h.gir === true).length;
    }
    
    function calculateGIRPercentage(holes) {
      return Math.round((calculateGIR(holes) / holes.length) * 100);
    }

  function getRoundHoleCount(holes) {
  return holes.length; // Returns 9 or 18
}

function calculateUpAndDownsPerRound(holes){
  return holes.filter(u=> u.upAndDown==="yes").length;
}

function calculatePenalties(holes){
  return holes.filter(p=> p.penalties==="1").length;
}

    function getGIRData(holes){
      const girHit = calculateGIR(holes); 
      const girMissed = holes.length-girHit; 

      return {
        labels: ['GIR Hit', 'GIR Missed'],
        datasets: [{
          backgroundColor: ['#4caf50', '#f44336'],
          data: [girHit, girMissed],
    }],
  };
    }
    
    function getPuttsData(holes) {
      const onePutts = holes.filter(h => h.putts === 1).length;
      const twoPutts = holes.filter(h => h.putts === 2).length;
      const threePlusPutts = holes.filter(h => h.putts >= 3).length;
      
      return {
        labels: ['1-Putts', '2-Putts', '3+ Putts'],
        datasets: [{
          backgroundColor: ['#4caf50', '#ff9800', '#f44336'],
          data: [onePutts, twoPutts, threePlusPutts],
        }],
      };
    }
    
    function getFairwayData(holes) {
      const fairwaysHit = calculateFairwaysHit(holes);
      const fairwaysMissedLeft = holes.filter(h => Number(h.par) !== 3 && h.fairway === 'Left').length;
      const fairwaysMissedRight = holes.filter(h => Number(h.par) !== 3 && h.fairway === 'Right').length;
      
      return {
        labels: ['Hit', 'Left', 'Right'],
        datasets: [{
          backgroundColor: ['#4caf50', '#ff9800', '#f44336'],
          data: [fairwaysHit, fairwaysMissedLeft, fairwaysMissedRight],
        }],
      };
    }
    
    function getScoreVsParData(holes) {
      const totalPar = calculateTotalPar(holes);
      const totalStrokes = calculateTotalStrokes(holes);
      
      return {
        labels: ['Par', 'Score'],
        datasets: [{
          backgroundColor: ['#3498db', '#2ecc71'],
          data: [totalPar, totalStrokes],
        }],
      };
      
    }

    // Load data when component mounts
    onMounted(getRounds);

    return {
      allRounds,
      filteredRounds,
      loading,
      showRoundDetails,
      selectedCourse,
      dateFilter,
      uniqueCourses,
      averageScore,
      averagePutts,
      overallFairwayPercentage,
      overallGIRPercentage,
      scoresOverTimeData,
      puttsOverTimeData,
      pieChartOptions,
      barChartOptions,
      lineChartOptions,
      getRounds,
      filterRounds,
      toggleRoundDetails,
      formatDate,
      calculateTotalStrokes,
      calculateTotalPar,
      calculateScoreToPar,
      calculateTotalPutts,
      calculateFairwaysHit,
      calculateFairwaysAttempted,
      calculateFairwayPercentage,
      calculateGIR,
      calculateGIRPercentage,
      getPuttsData,
      getFairwayData,
      getScoreVsParData,
      fairwayAccuracyOverTimeData,
      GIRAccuracyOverTimeData,
      getGIRData,
      getRoundHoleCount,
      calculateUpAndDownsPerRound,
      calculatePenalties,
    };
  }
};
</script>

<style scoped>
.container {
  max-width: 12000px;
  margin: 0 auto;
  padding: 0 1rem;
}

.controls {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
  align-items: center;
}

.filter-controls {
  display: flex;
  gap: 1rem;
  width: 100%;
  max-width: 600px;
}

.filter-controls select,
.filter-controls input {
  flex: 1;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
}

.summary-section {
  margin-bottom: 2rem;
  padding: 1.5rem;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-top: 1rem;
}

.stat-card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  text-align: center;
}

.stat-card h3 {
  margin-top: 0;
  color: #555;
  font-size: 1rem;
}

.stat-card p {
  margin-bottom: 0;
  font-size: 1.5rem;
  font-weight: bold;
  color: #2c3e50;
}

.round-container {
  margin-bottom: 2rem;
  border: 1px solid #eee;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.round-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  background-color: #f5f5f5;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.round-header:hover {
  background-color: #eee;
}

.score-badge {
  background-color: #2ecc71;
  color: white;
  padding: 0.3rem 0.8rem;
  border-radius: 20px;
  font-weight: bold;
  font-size: 0.9rem;
}

.toggle-icon {
  font-weight: bold;
  font-size: 1.2rem;
  margin-left: 1rem;
}

.round-details {
  padding: 1.5rem;
  background-color: white;
}

.summary-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
}

.highlight-birdie {
  background-color: rgba(46, 204, 113, 0.1) !important;
}

.highlight-bogey {
  background-color: rgba(231, 76, 60, 0.1) !important;
}

.fairway-hit {
  color: #2ecc71;
  font-weight: bold;
}

.fairway-missed {
  color: #e74c3c;
}

.gir-hit {
  color: #2ecc71;
  font-weight: bold;
}

.gir-missed {
  color: #e74c3c;
}

.totals-row {
  font-weight: bold;
  background-color: #f5f5f5 !important;
}

.empty-state,

.loading-state {
  text-align: center;
  padding: 2rem;
  color: #777;
  font-size: 1.1rem;
}.nav-bar {
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
  max-width: 1000px;
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







</style>