<template>
  <div class="container">
    <!-- Add Round Form -->
    
     <!-- Need to change the logic so when I select 9 or 18 I can only fill in 9 or 18 holes -->
    <nav class="nav-bar">
     <router-link to="/" class="nav-link">Logout</router-link>
     <router-link to="/home" class="nav-link">Home</router-link>
      <router-link to="/AddRound" class="nav-link">Add Round</router-link>
      <router-link to="/ViewRound" class="nav-link">View Round</router-link>
      <router-link to="/ViewStats" class="nav-link">View Stats</router-link>
      <router-link to="/ViewInsights" class="nav-link">View Insights</router-link>

    </nav>
   <div class="progress-container" v-if="holesToPlay">
  <div class="progress-bar" :style="{ width: progressPercentage + '%' }"></div>
  <div class="progress-text">
    <span v-if="roundData.length < parseInt(holesToPlay)">
      Hole {{ currentHole.holeNumber }} of {{ holesToPlay }}
    </span>
    <span v-else>
      Round complete!
    </span>
  </div>
</div>

    <div class="home-container">
      <header class="hero">
        <h1>Adam's Golf Stats Tracker - Add Round</h1>
        <p>Enter your score </p>
      </header>
    </div>
    <form @submit.prevent="submitHole">

  <p class="hole-title-text">Golf Day Course Inputs </p>

 <div class="form-group">
      <label for="coursename">Course Name</label>
      <input type="text" v-model="courseName" id="coursename" placeholder="Course Name" required />

      <label for="date">Date</label>
      <input type="date" v-model="date" id="date"> 

      
      <h4 class="section-title">Weather Conditions</h4>
      <div class="radio-group-par">

      <label for="good">Good </label>
      <input type="radio" id="good" value="Good" v-model="weatherConditions" name="weather">

      <label for="bad">Bad </label>
      <input type="radio" id="bad" value="Bad" v-model="weatherConditions" name="weather">

      <label for="fair">Fair </label>
      <input type="radio" id="fair" value="Fair" v-model="weatherConditions" name="weather">
      </div>

      <div class="radio-group">
      <label for="9Holes">9 Holes </label>
      <input type="radio" id="9Holes" value="9" v-model="holesToPlay" name="holes">

      <label for="18Holes">18 Holes </label>
      <input type="radio" id="18Holes" value="18" v-model="holesToPlay" name="holes">
      </div>

      <p class="hole-title-text">Hole By Hole Data</p>


     

      <label for="holeNumber">Hole Number</label>
      <input type="number" v-model="currentHole.holeNumber" id="holeNumber" disabled> 


      <h3 class="section-title">Par for hole</h3>
      <div class="radio-group-par">
      
      <label for="par3">Par 3 </label>
      <input type="radio" id="par3" value="3" v-model.number="currentHole.par" name="par">

      <label for="par4">Par 4 </label>
      <input type="radio" id="par4" value="4" v-model.number="currentHole.par" name="par">

      <label for="par5">Par 5 </label>
      <input type="radio" id="par5" value="5" v-model.number="currentHole.par" name="par">
      </div>
      <div class="form-group">
        <label for="tee-club-used">Tee Club Used</label>
          <select id="tee-club-used" v-model="currentHole.teeClubUsed">
          <option>Driver</option>
          <option>3 - Wood</option>
          <option>7 - Wood</option>
          <option>5 - Iron</option>
          <option>6 - Iron</option>
          <option>7 - Iron</option>
          <option>8 - Iron</option>
          <option>9 - Iron</option>
          <option>P - Wedge</option>
          <option>A - Wedge</option>
          <option>50d - Wedge</option>
          <option>56d - Wedge</option>
        </select>
      </div>
      

      <label for="totalstrokes">Total Strokes</label>
      <input type="number" v-model="currentHole.strokes" id="totalstrokes"> 

      <label for="totalstrokes">Penalties</label>
      <div class="radio-group-par">
      <label for="none">None</label>
      <input type="radio" id="none" value="0" v-model.number="currentHole.penalties" name="penalties">

      <label for="one">One</label>
      <input type="radio" id="one" value="1" v-model.number="currentHole.penalties" name="penalties">

      <label for="two">Two </label>
      <input type="radio" id="two" value="2" v-model.number="currentHole.penalties" name="penalties">

      </div>
   
      



      <h3 class="section-title" v-show="currentHole.par!=3">Fairway Accuracy</h3>

      <div class="radio-group-fairway" v-show="currentHole.par!=3">
      <label for="fairwayleft">Fairway Left </label>
      <input type="radio" v-model="currentHole.fairway" id="fairwayleft" value="Left" name="fairway">

      <label for="fairwayhit">Fairway Hit </label>
      <input type="radio" v-model="currentHole.fairway" id="fairwayhit" value="Hit" name="fairway">

      <label for="fairwayright">Fairway Right </label>
      <input type="radio" v-model="currentHole.fairway" id="fairwayright" value="Right" name="fairway">
      </div>


      <h3 class="section-title">Total Putts</h3>
      <div class="radio-group-putts">

      <label for="0">0</label>
      <input type="radio" id="0" value="0" v-model="currentHole.putts" name="putt">

      <label for="1">1</label>
      <input type="radio" id="1" value="1" v-model="currentHole.putts" name="putt">

      <label for="2">2</label>
      <input type="radio" id="2" value="2"v-model="currentHole.putts" name="putt">

      <label for="3">3</label>
      <input type="radio" id="3" value="3" v-model="currentHole.putts" name="putt">

      <label for="4">4</label>
      <input type="radio" id="4" value="4" v-model="currentHole.putts" name="putt">
      </div>

     <div class="form-group">
      <label>Green in Regulation</label>
        <div class="gir-status" :class="{ yes: isGIR, no: !isGIR }">{{ isGIR ? 'Yes' : 'No' }}</div>
    </div>

    <h3 class="section-title"> Up and Down</h3>
    <div class ="radio-group-putts">
      <label for="yes">Yes</label>
      <input type="radio" id="yes" value="yes" v-model="currentHole.upAndDown" name="upanddown">

      <label for="no">No</label>
      <input type="radio" id="no" value="no" v-model="currentHole.upAndDown" name="upanddown">

      <label for="n/a">N/A</label>
      <input type="radio" id="n/a" value="n/a" v-model="currentHole.upAndDown" name="upanddown">

    </div>
   
    



      <div class="button-row">
      <input type="submit" class="submit-button" :disabled="roundData.length>=parseInt(holesToPlay)" value="Submit Hole"> 
      <button type="button" @click="undoLastHole" class="undo-button" :disabled="roundData.length === 0">Undo Last Hole</button>

      </div>

    
    </div>

    </form>

    
    

      <div>
</div>
       

      
    
  </div>
</template>

<script>

export default {
  data() {
    return {
      courseName: '',
      date: '',
      weatherConditions:'',
      holesToPlay: null,
      userId: '',
      roundData: [],
      currentHole: {
        holeNumber: null,
        par: null,
        strokes: null,
        fairway: null,
        putts: null,
        penalties:null,
        teeClubUsed:null,
        upAndDown:'n/a',
        isGIR:null,
      }
    };
  },
  methods: {
    async submitHole() {
    if (this.roundData.length >= parseInt(this.holesToPlay)) {
        alert(`You've already entered ${this.holesToPlay} holes. The round is complete.`);
        return;
    }

    // Validate all required fields
    if (!this.currentHole.par || !this.currentHole.strokes || !this.currentHole.putts || 
        !this.currentHole.penalties || !this.currentHole.teeClubUsed) {
        alert("Please complete all fields before submitting.");
        return;
    }

     const userId = localStorage.getItem('userId');
     console.log(userId);


    // Set isGIR explicitly before pushing
    this.currentHole.isGIR = this.isGIR;

    // Push current hole to roundData
    this.roundData.push({ ...this.currentHole });


    // Reset hole fields
    this.currentHole = {
        holeNumber: this.roundData.length + 1,
        par: null,
        strokes: null,
        fairway: null,
        putts: null,
        penalties: null,
        teeClubUsed: null,
        upAndDown: 'n/a',
        isGIR: null,
    };

    // Check if round is complete
    if (this.roundData.length === parseInt(this.holesToPlay)) {
        const round = {
            courseName: this.courseName,
            weatherConditions: this.weatherConditions,
            date: this.date,
            holes: this.roundData,
            userId: userId
        };

        try {
            const response = await fetch('http://localhost:8080/round/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(round),
                credentials: 'include' // Important for cookies/sessions if using them
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            console.log('Success:', data);
            alert('Round complete and submitted!');

            // Reset everything
            this.courseName = '';
            this.weatherConditions = '';
            this.date = '';
            this.holesToPlay = null;
            this.roundData = [];
            this.currentHole = {
                holeNumber: null,
                par: null,
                strokes: null,
                fairway: null,
                putts: null,
                penalties: null,
                isGIR: null,
                teeClubUsed: '',
                upAndDown: null,
            };
        } catch (error) {
            console.error('Error submitting round:', error);
            alert('There was an error submitting your round.');
        }
    }
},
    
    undoLastHole() {
  if (this.roundData.length > 0) {
    const lastHole = this.roundData.pop();
    this.currentHole = { ...lastHole }; // Refill the form with the last hole data
  }
},

 
  },
  
  watch: {
    holesToPlay(newValue) {
    if (newValue) {
      this.currentHole.holeNumber = 1;
    }
  },
 'currentHole.par': function(newPar, oldPar) {
    if (this.currentHole.strokes === oldPar || !this.currentHole.strokes) {
      this.currentHole.strokes = newPar;
    }
  }

},


computed: {

  userId(){
    return localStorage.getItem('userId');
  },
  progressPercentage() {
    const total = parseInt(this.holesToPlay) || 0;
    const current = this.currentHole.holeNumber || 1;
    return total ? ((current - 1) / total) * 100 : 0;
  },
   isGIR() {
    const strokes = this.currentHole.strokes;
    const putts = this.currentHole.putts;
    const par = parseInt(this.currentHole.par);

    if (isNaN(strokes) || isNaN(putts) || isNaN(par)) {
      return false;
    }

    return (strokes - putts) <= (par - 2);
  },
}
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
  max-width: 1000px;
  margin: 2rem auto;
  text-align: center;
  font-family: 'Arial', sans-serif;
}

.hero h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}
.hole-title-text {
  font-weight: bold;
  font-size: 1.1rem;
  text-align: center;
  text-decoration: underline #e74c3c;


}

.hero p {
  font-size: 1.2rem;
  color: #555;
}
.form-group {
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


.radio-group {
    display: flex;
    justify-content: flex-end;
    align-items:center;
    gap: 10px; /* space between buttons */
    margin-top: 10px;
  }
  

.radio-group label {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  cursor: pointer;
  background-color: #f9f9f9;
  transition: background-color 0.2s ease;
}
.section-title {
  text-align: center;
  font-weight: bold;
}
.radio-group-par {
    display: flex;
    justify-content:center;
    align-items:center;
    gap: 10px; /* space between buttons */
    margin-top: 10px;
  }
  .radio-group-par label {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  cursor: pointer;
  background-color: #f9f9f9;
  transition: background-color 0.2s ease;
}
.radio-group-fairway {
    display: flex;
    justify-content: center;
    align-items:center;
    gap: 10px; /* space between buttons */
    margin-top: 10px;
  }
  .radio-group-fairway label {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  cursor: pointer;
  background-color: #f9f9f9;
  transition: background-color 0.2s ease;
}
.radio-group-putts {
    display: flex;
    justify-content: center;
    align-items:center;
    gap: 10px; /* space between buttons */
    margin-top: 10px;
  }
  .radio-group-putts label {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  cursor: pointer;
  background-color: #f9f9f9;
  transition: background-color 0.2s ease;
}
.progress-container {
  position: relative;
  width: 100%;
  height: 30px;
  background-color: #e0e0e0;
  border-radius: 10px;
  margin: 1rem auto;
  max-width: 600px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background-color: #2ecc71;
  transition: width 0.3s ease;
}

.progress-text {
  position: absolute;
  width: 100%;
  text-align: center;
  top: 0;
  line-height: 30px;
  font-weight: bold;
  color: #333;
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
.undo-button {
  margin: 1rem auto;
  display: block;
  padding: 0.75rem 1.5rem;
  background-color: #fd6500;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.undo-button:hover {
  background-color: #27ae60;
}
.disabled {
  opacity: 0.5;
  pointer-events: none;
}
.button-row {
  display: flex;
  gap: 10px; /* space between buttons */
  align-items: center; /* vertically align if buttons differ in height */
}
select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  background-color: #fff;
  transition: border-color 0.3s ease;
  text-align: center;
}

select:focus {
  outline: none;
  border-color: #2ecc71;
  box-shadow: 0 0 0 2px rgba(46, 204, 113, 0.2);
}
.gir-status {
  font-size: 1rem;
  font-weight: bold;
  text-align: center;
  padding: 0.5rem;
  border-radius: 8px;
  max-width: 120px;
  margin: 0 auto;
  background-color: #eee;
  color: #333;
}

.gir-status.yes {
  background-color: #2ecc71;
  color: white;
}

.gir-status.no {
  background-color: #e74c3c;
  color: white;
}



</style>
