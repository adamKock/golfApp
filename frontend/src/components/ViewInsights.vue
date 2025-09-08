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
        <h1>Adam's Golf Stats Tracker - Insights</h1>
      </header>


      <!-- Overall Statistics -->
      <div v-if="filteredRounds.length > 0" class="summary-section">
        <h2>Overall Statistics Comparison Against 3 - Handicap</h2>
        <div class="stats-grid">
          <!-- Score section-->

          <div class="stat-card">
          <div :class="{ 'green-box': averageScoreOnParThree <= 3, 'red-box': averageScoreOnParThree > 3 }">
          <h3>Average Score For Par 3</h3>
          <p>{{ averageScoreOnParThree }}</p>
          </div>
           
          </div>
          <div class="stat-card">
            <div :class="{ 'green-box': averageScoreOnParFour <= 3.9, 'red-box': averageScoreOnParFour > 4 }">
            <h3>Average Score For Par 4</h3>
            <p>{{ averageScoreOnParFour }}</p>
          </div>

            </div>
          
          <div class="stat-card">
            <div :class="{ 'green-box': averageScoreOnParFour <= 5, 'red-box': averageScoreOnParFour > 5 }">
            <h3>Average Score For Par 5</h3>
            <p>{{ averageScoreOnParFive }}</p>
          </div>
          </div>
           

      

          <!-- Putts section-->

          <div class="stat-card">
            <div :class="{ 'green-box': averagePuttsFor9 <= 15, 'red-box': averagePuttsFor9 > 15 }">
            <h3>Average Putts per round 9 holes</h3>
            <p>{{ averagePuttsFor9 }}</p>
            </div>
          </div>

          <div class="stat-card">
            <div :class="{ 'green-box': averagePuttsFor18 <30, 'red-box': averagePuttsFor18 >30, 'grey-box' : averagePuttsFor18 <5 }">
            <h3>Average Putts per round 18 holes</h3>
            <p>{{ averagePuttsFor18 }}</p>
            </div>
          </div>


            <div class="stat-card">
            <div :class="{ 'green-box': averagePuttsPerParThree <= 1.85, 'orange-box' : averagePuttsPerParThree >1.86, 'red-box': averagePuttsPerParThree >2  }">
            <h3>Average Putts Per Par 3</h3>
            <p>{{averagePuttsPerParThree  }}</p>
            </div>
          </div>

           <div class="stat-card">
            <div :class="{ 'green-box': averagePuttsPerParFour <= 1.72,'orange-box' : averagePuttsPerParFour >=1.73, 'red-box': averagePuttsPerParFour >=2  }">
            <h3>Average Putts Per Par 4</h3>
            <p>{{ averagePuttsPerParFour  }}</p>
             </div>
          </div>


           <div class="stat-card">
            <div :class="{ 'green-box': averagePuttsPerParFive <= 1.60,'orange-box' : averagePuttsPerParFive >=1.61, 'red-box': averagePuttsPerParFive >1.9  }">
            <h3>Average Putts Per Par 5</h3>
            <p>{{ averagePuttsPerParFive }}</p>
          </div>
           </div>

           <div class="stat-card">
            <div :class="{ 'green-box': threePuttFrequency <= 1.3, 'red-box': threePuttFrequency >1.31  }">
            <h3>3 -Putt Frequency Per Round</h3>
            <p>{{ threePuttFrequency }}</p>
          </div>
          </div>

            <div class="stat-card">    
              <div :class="{ 'green-box': onePuttPercentage => 28, 'red-box': onePuttPercentage <27  }">
            <h3>1 Putt Percentage </h3>
            <p>{{ onePuttPercentage }}</p>
          </div>
          </div>

            <div class="stat-card">
              <div :class="{ 'green-box': puttsPerGIR <= 1.7, 'orange-box' : puttsPerGIR >1.71, 'red-box': puttsPerGIR >1.9  }">
            <h3> Putts Per GIR  </h3>
            <p>{{ puttsPerGIR }}</p>
          </div>
          </div>

          <!-- Fairway  section-->

          <div class="stat-card">
              <div :class="{ 'green-box': overallFairwayPercentage >55, 'red-box': overallFairwayPercentage <40, 'orange-box': overallFairwayPercentage >= 40 && overallFairwayPercentage <= 54}">
            <h3>Fairway Accuracy</h3>
            <p>{{ overallFairwayPercentage }}%</p>
          </div>
           </div>

           <div class="stat-card">
              <div :class="{ 'green-box': averageParFromLeftRough >37, 'red-box': averageParFromLeftRough <37}">
            <h3>Average Chance of Par If left off the tee</h3>
            <p>{{ averageParFromLeftRough }}%</p>
          </div>
          </div>

          <div class="stat-card">
              <div :class="{ 'green-box': averageParFromRightRough >33, 'red-box': averageParFromRightRough <33}">
            <h3>Average Chance of Par If right off the tee</h3>
            <p>{{ averageParFromRightRough }}%</p>
          </div> 
           </div>
          

          <div class="stat-card">
            <div :class="{ 'green-box': averageParFromFairway >59, 'red-box': averageParFromFairway <59}">
            <h3>Average Chance of Par If fairway hit</h3>
            <p>{{ averageParFromFairway }}%</p>
          </div>
            </div>
            
          <!-- GIR section-->
          <div class="stat-card">
              <div :class="{ 'green-box': overallGIRPercentage >55, 'red-box': overallGIRPercentage <35, 'orange-box': overallGIRPercentage >= 36 && overallGIRPercentage <= 54}">
            <h3>GIR Percentage</h3>
            <p>{{ overallGIRPercentage }}%</p>
          </div>
            </div>

            <div class="stat-card">
              <div :class="{ 'green-box': overallGIRPercentagePar3 >49, 'red-box': overallGIRPercentagePar3 <48}">
            <h3>GIR By Par 3</h3>
            <p>{{ overallGIRPercentagePar3 }}%</p>
          </div>
          </div>


            <div class="stat-card">
              <div :class="{ 'green-box': overallGIRPercentagePar4 >54, 'red-box': overallGIRPercentagePar4 <=54}">
            <h3>GIR By Par 4</h3>
            <p>{{ overallGIRPercentagePar4 }}%</p>
          </div>
          </div>
             <div class="stat-card">
              <div :class="{ 'green-box': overallGIRPercentagePar4 >54, 'red-box': overallGIRPercentagePar4 <=54}">
            <h3>GIR By Par 5</h3>
            <p>{{ overallGIRPercentagePar5  }}%</p>
          </div>
          </div>

          <!-- Scrambling section-->
          <div class="stat-card">
              <div :class="{ 'green-box': averageUpAndDownsFor9 >3.5, 'red-box': averageUpAndDownsFor9 <=3.4}">
            <h3>Average Up and Downs Per Round 9 Holes </h3>
            <p>{{ averageUpAndDownsFor9 }}</p>
          </div>
           </div>
          

            <div class="stat-card">
              <div :class="{ 'green-box': averageUpAndDownsFor18 >8, 'red-box': averageUpAndDownsFor18 <=7}">

            <h3>Average Up and Downs Per Round 18 Holes </h3>
            <p>{{ averageUpAndDownsFor18 }}</p>
          </div>
           </div>

           <div>
          

  
            


           
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
    
    
    const averageScore = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      const total = filteredRounds.value.reduce((sum, round) => {
        return sum + calculateTotalStrokes(round.holes);
      }, 0);
      return (total / filteredRounds.value.length).toFixed(1);
    });

  
      const averageScoreOnParThree = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPar3Strokes =0; 
      let totalPar3Holes =0; 
      
     filteredRounds.value.forEach(round => {
    const par3Holes = round.holes.filter(hole => hole.par === "3" || hole.par === 3);
     totalPar3Strokes += par3Holes.reduce((sum, hole) => sum + Number(hole.strokes), 0);
    totalPar3Holes += par3Holes.length;
  });
  
  return totalPar3Holes > 0 ? (totalPar3Strokes / totalPar3Holes).toFixed(1) : 0;

    });

      const averageScoreOnParFour = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPar4Strokes =0; 
      let totalPar4Holes =0; 
      
     filteredRounds.value.forEach(round => {
     const par4Holes = round.holes.filter(hole => hole.par === "4" || hole.par === 4);
    totalPar4Strokes += par4Holes.reduce((sum, hole) => sum + Number(hole.strokes), 0);
    totalPar4Holes += par4Holes.length;
  });
  
  return totalPar4Holes > 0 ? (totalPar4Strokes / totalPar4Holes).toFixed(1) : 0;

    });

       const averageScoreOnParFive = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPar5Strokes =0; 
      let totalPar5Holes =0; 
      
     filteredRounds.value.forEach(round => {
     const par5Holes = round.holes.filter(hole => hole.par === "5" || hole.par === 5);
    totalPar5Strokes += par5Holes.reduce((sum, hole) => sum + Number(hole.strokes), 0);
    totalPar5Holes += par5Holes.length;
  });
  
  return totalPar5Holes > 0 ? (totalPar5Strokes / totalPar5Holes).toFixed(1) : 0;

    });


      const averagePuttsPerParThree = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPuttsPar3 =0; 
      let totalPar3Holes =0; 

        filteredRounds.value.forEach(round => {
          const par3Holes = round.holes.filter(hole => hole.par ==="3" || hole.par ===3);
          totalPuttsPar3 += par3Holes.reduce((sum, hole) => sum + Number (hole.putts),0);
          totalPar3Holes+= par3Holes.length; 
        }); 

        return totalPar3Holes >0 ? (totalPuttsPar3 / totalPar3Holes).toFixed(1):0; 
    
      });
    
      const averagePuttsPerParFour= computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPuttsPar4 =0; 
      let totalPar4Holes =0; 

        filteredRounds.value.forEach(round => {
          const par4Holes = round.holes.filter(hole => hole.par ==="4" || hole.par ===4);
          totalPuttsPar4 += par4Holes.reduce((sum, hole) => sum + Number (hole.putts),0);
          totalPar4Holes+= par4Holes.length; 
        }); 

        return totalPar4Holes >0 ? (totalPuttsPar4 / totalPar4Holes).toFixed(1):0; 
    
      });


     
      const averagePuttsPerParFive = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPuttsPar5 =0; 
      let totalPar5Holes =0; 

        filteredRounds.value.forEach(round => {
          const par5Holes = round.holes.filter(hole => hole.par ==="5" || hole.par ===5);
          totalPuttsPar5 += par5Holes.reduce((sum, hole) => sum + Number (hole.putts),0);
          totalPar5Holes+= par5Holes.length; 
        }); 

        return totalPar5Holes >0 ? (totalPuttsPar5 / totalPar5Holes).toFixed(1):0; 
    
      });


   const threePuttFrequency = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      const total = filteredRounds.value.reduce((sum, round) => {
        return sum + calculateThreePuttFrequency(round.holes);
      }, 0);
      return (total / filteredRounds.value.length).toFixed(1);
    });

    const onePuttPercentage = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
      const total = filteredRounds.value.reduce((sum, round) => {
        return sum + calculateOnePuttPercentage(round.holes);
      }, 0);
      return (total / filteredRounds.value.length).toFixed(1);
    });

     const puttsPerGIR = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let totalPutts =0; 
      let totalGirHoles =0; 

      filteredRounds.value.forEach(round => {
        const girHoles = round.holes.filter(hole => hole.gir ===true); 
        totalPutts += girHoles.reduce((sum, hole) => sum+ Number (hole.putts),0);
        totalGirHoles += girHoles.length; 
      });

        return totalGirHoles >0 ? (totalPutts / totalGirHoles).toFixed(1):0; 
  
    });

   const averagePuttsFor9 = computed(() => {
  const nineHoleRounds = filteredRounds.value.filter(round => round.holes.length === 9);
  if (nineHoleRounds.length === 0) return 0;

  const totalPutts = nineHoleRounds.reduce((sum, round) => {
    return sum + calculateTotalPutts(round.holes);
  }, 0);

  return (totalPutts / nineHoleRounds.length).toFixed(1);
});

const averagePuttsFor18 = computed(() => {
  const eighteenHoleRounds = filteredRounds.value.filter(round => round.holes.length === 18);
  if (eighteenHoleRounds.length === 0) return 0;

  const totalPutts = eighteenHoleRounds.reduce((sum, round) => {
    return sum + calculateTotalPutts(round.holes);
  }, 0);

  return (totalPutts / eighteenHoleRounds.length).toFixed(1);
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

       const overallGIRPercentagePar3 = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
  
      let totalGIR = 0;
      let totalPar3Holes = 0;
  
      filteredRounds.value.forEach(round => {
      const par3Holes = round.holes.filter(hole => hole.par == 3);
      totalGIR += countGIRForPar3(round.holes);
      totalPar3Holes += par3Holes.length;
  });
  
  return totalPar3Holes > 0 ? Math.round((totalGIR / totalPar3Holes) * 100) : 0;
});

      const overallGIRPercentagePar4 = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
  
      let totalGIR = 0;
      let totalPar4Holes = 0;
  
      filteredRounds.value.forEach(round => {
      const par4Holes = round.holes.filter(hole => hole.par == 4);
      totalGIR += countGIRForPar4(round.holes);
      totalPar4Holes += par4Holes.length;
  });
  
  return totalPar4Holes > 0 ? Math.round((totalGIR / totalPar4Holes) * 100) : 0;
});
    
       const overallGIRPercentagePar5 = computed(() => {
      if (filteredRounds.value.length === 0) return 0;
  
      let totalGIR = 0;
      let totalPar5Holes = 0;

      filteredRounds.value.forEach(round => {
      const par5Holes = round.holes.filter(hole => hole.par == 5);
      totalGIR += countGIRForPar5(round.holes);
      totalPar5Holes += par5Holes.length;
  });
  
  return totalPar5Holes > 0 ? Math.round((totalGIR / totalPar5Holes) * 100) : 0;
});

const averageUpAndDownsFor9 = computed(() => {
  const nineHoleRounds = filteredRounds.value.filter(round => round.holes.length === 9);
  if (nineHoleRounds.length === 0) return 0;

  const total = nineHoleRounds.reduce((sum, round) => {
    return sum + calculateUpAndDowns(round.holes);
  }, 0);

  return (total / nineHoleRounds.length).toFixed(1);
});

const averageUpAndDownsFor18 = computed(() => {
  const eighteenHoleRounds = filteredRounds.value.filter(round => round.holes.length === 18);
  if (eighteenHoleRounds.length === 0) return 0;

  const total = eighteenHoleRounds.reduce((sum, round) => {
    return sum + calculateUpAndDowns(round.holes);
  }, 0);

  return (total / eighteenHoleRounds.length).toFixed(1);
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

 const averageParFromLeftRough = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let parMade =0; 
      let timesHitLeftRough =0; 

      filteredRounds.value.forEach(round =>{
        const leftRoughHoles= round.holes.filter(hole => hole.fairway ==="Left");
        timesHitLeftRough += leftRoughHoles.length; 

        parMade += leftRoughHoles.filter(hole => hole.strokes <= hole.par).length; 
      })
     
      return timesHitLeftRough >0 ? ((parMade / timesHitLeftRough)*100).toFixed(1):0;
    
    });

    const averageParFromRightRough = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let parMade =0; 
      let timesHitRightRough =0; 

      filteredRounds.value.forEach(round =>{
        const rightRoughHoles= round.holes.filter(hole => hole.fairway ==="Right");
        timesHitRightRough += rightRoughHoles.length; 

        parMade += rightRoughHoles.filter(hole => hole.strokes <= hole.par).length; 
      })
      return timesHitRightRough >0 ? ((parMade / timesHitRightRough)*100).toFixed(1):0;
    });


     const averageParFromFairway = computed(() => {
      if (filteredRounds.value.length === 0) return 0;

      let parMade =0; 
      let timesHitFairway =0; 

      filteredRounds.value.forEach(round =>{
        const fairwayHoles= round.holes.filter(hole => hole.fairway ==="Hit");
        timesHitFairway += fairwayHoles.length; 

        parMade += fairwayHoles.filter(hole => hole.strokes <= hole.par).length; 
      })
      return timesHitFairway >0 ? ((parMade / timesHitFairway)*100).toFixed(1):0;
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
   function countGIRForPar3(holes) {
      const par3Holes = holes.filter(hole => hole.par == 3); // Loose equality handles both "4" and 4
      return par3Holes.filter(hole => hole.gir === true).length;
}

    // Calculate GIR count (not percentage) for Par 4s
    function countGIRForPar4(holes) {
      const par4Holes = holes.filter(hole => hole.par == 4); // Loose equality handles both "4" and 4
      return par4Holes.filter(hole => hole.gir === true).length;
}
    
     function countGIRForPar5(holes) {
      const par5Holes = holes.filter(hole => hole.par == 5); // Loose equality handles both "4" and 4
      return par5Holes.filter(hole => hole.gir === true).length;
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

function calculateAverageScoreForPar3(holes ){
    const par3Holes = holes.filter(hole => hole.par === "3" || hole.par === 3);
    if(par3Holes.length===0)return 0; 
    const totalStrokes = par3Holes.reduce((sum, hole) => sum + hole.strokes, 0);
    return (totalStrokes / par3Holes.length).toFixed(2);

}

function calculateAverageScoreForPar4(holes ){
    const par4Holes = holes.filter(hole => hole.par === "4" || hole.par === 4);
    if(par4Holes.length===0)return 0; 
    const totalStrokes = par4Holes.reduce((sum, hole) => sum + hole.strokes, 0);
    return (totalStrokes / par4Holes.length).toFixed(2);

}
function calculateAverageScoreForPar5(holes ){
    const par5Holes = holes.filter(hole => hole.par === "5" || hole.par === 5);
    if(par5Holes.length===0)return 0; 
    const totalStrokes = par5Holes.reduce((sum, hole) => sum + hole.strokes, 0);
    return (totalStrokes / par5Holes.length).toFixed(2);

}

function calculateAveragePuttsPar3(holes ){
    const par3Holes = holes.filter(hole => hole.par === "3" || hole.par === 3);
    if(par3Holes.length===0)return 0; 
    const totalPutts = par3Holes.reduce((sum, hole) => sum + hole.putts, 0);
    return (totalPutts / par3Holes.length).toFixed(2);
}
function calculateAveragePuttsPar4(holes ){
    const par4Holes = holes.filter(hole => hole.par === "4" || hole.par === 4);
    if(par4Holes.length===0)return 0; 
    const totalPutts = par4Holes.reduce((sum, hole) => sum + hole.putts, 0);
    return (totalPutts / par4Holes.length).toFixed(2);
}
function calculateAveragePuttsPar5(holes ){
    const par5Holes = holes.filter(hole => hole.par === "5" || hole.par === 5);
    if(par5Holes.length===0)return 0; 
    const totalPutts = par5Holes.reduce((sum, hole) => sum + hole.putts, 0);
    return (totalPutts / par5Holes.length).toFixed(2);
}

function calculateThreePuttFrequency(holes){
  const threePutts = holes.filter(hole=> hole.putts >=3).length; 
  if(holes.length===0)return 0; 
  return Math.round(threePutts/holes.length)*100; 
}

function calculateOnePuttPercentage(holes){
  const onePutts = holes.filter(hole=> hole.putts<2).length;
  if(holes.length===0)return 0;
  return Math.round((onePutts/holes.length)*100); 

}

function calculatePuttsPerGIR(holes){
  const girHit = holes.filter(hole=> hole.gir===true); 
  const totalPutts = girHit.reduce((sum, hole) => sum + hole.putts,0);
  if(girHit.length===0) return 0; 
  return (totalPutts/girHit.length).toFixed(2); 
}

function calculateAverageParFromLeftRough(holes){
  const allLeftRoughHoles = holes.filter(hole=> hole.fairway==="Left"); 
  const parOrBetter = allLeftRoughHoles.filter(hole => hole.strokes<=hole.par).length;
  if(allLeftRoughHoles.length===0)return 0;
  return ((parOrBetter / allLeftRoughHoles.length) * 100).toFixed(1);
}

function calculateAverageParFromRightRough(holes){
  const allRightRoughHoles = holes.filter(hole=> hole.fairway==="Right"); 
  const parOrBetter = allRightRoughHoles.filter(hole => hole.strokes<=hole.par).length;
  if(allRightRoughHoles.length===0)return 0;
  return ((parOrBetter / allRightRoughHoles.length) * 100).toFixed(1);
}

function calculateAverageParFromFairway(holes){
  const allFairwaysHit = holes.filter(hole=> hole.fairway==="Hit"); 
  const parOrBetter = allFairwaysHit.filter(hole => hole.strokes<=hole.par).length;
  if(allFairwaysHit.length===0)return 0;
  return ((parOrBetter / allFairwaysHit.length) * 100).toFixed(1);
}

function calculateUpAndDowns(holes) {
  const missedGIR = holes.filter(hole => !hole.gir);
  const successfulUpAndDowns = missedGIR.filter(hole => hole.strokes <= hole.par);
  return successfulUpAndDowns.length;
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
      averageScore,
      averagePuttsFor9,
      averagePuttsFor18,
      overallFairwayPercentage,
      overallGIRPercentage,
      scoresOverTimeData,
      puttsOverTimeData,
      fairwayAccuracyOverTimeData,
      GIRAccuracyOverTimeData,
      pieChartOptions,
      barChartOptions,
      lineChartOptions,
      averageScoreOnParThree,
      averageScoreOnParFour,
      averageScoreOnParFive,
      averagePuttsPerParThree,
      averagePuttsPerParFour,
      averagePuttsPerParFive,
      threePuttFrequency,
      onePuttPercentage,
      puttsPerGIR,
      averageParFromLeftRough,
      averageParFromRightRough,
      averageParFromFairway,
      overallGIRPercentagePar3,
      overallGIRPercentagePar4,
      overallGIRPercentagePar5,
      averageUpAndDownsFor9,
      averageUpAndDownsFor18,
      getRounds,
      filterRounds,
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
      getGIRData,
      getRoundHoleCount,
      calculateUpAndDownsPerRound,
      calculatePenalties,
      calculateAverageScoreForPar3,
      calculateAverageScoreForPar4,
      calculateAverageScoreForPar5,
      calculateAveragePuttsPar3,
      calculateAveragePuttsPar4,
      calculateAveragePuttsPar5,
      calculateThreePuttFrequency,
      calculateOnePuttPercentage,
      calculatePuttsPerGIR,
      calculateAverageParFromLeftRough,
      calculateAverageParFromRightRough,
      calculateAverageParFromFairway,
      countGIRForPar3,
      countGIRForPar4,
      countGIRForPar5,
      calculateUpAndDowns,

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


.summary-section {
  margin-bottom: 2rem;
  max-width:100%;
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
  max-width: 10000px;
  margin: 2rem auto;
  text-align: center;
  font-family: 'Arial', sans-serif;
}

.hero h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}
.green-box{
  background-color: #27ae60;
  color: white;
  padding: 10px;
  border-radius: 5px;
}
.red-box {
  background-color: red;
  color: white;
  padding: 10px;
  border-radius: 5px;
}
.orange-box {
  background-color: orange;
  color: white;
  padding: 10px;
  border-radius: 5px;
}
.grey-box {
  background-color: rgb(147, 146, 146);
  color: white;
  padding: 10px;
  border-radius: 5px;
}











</style>