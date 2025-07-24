<template>
    <div>
      <h2>Add Golf Round</h2>
      <form @submit.prevent="submitRound">
        <label>
          Course Name:
          <input v-model="round.course" required />
        </label>
        <br />
        <label>
          Score:
          <input type="number" v-model.number="round.score" required />
        </label>
        <br />
        <button type="submit">Add Round</button>
      </form>
      <p v-if="message">{{ message }}</p>
    </div>
  
    <div>
      <h2>All Golf Rounds</h2>
      <button @click="getRounds">Get Rounds</button>
      <ul v-if="rounds.length">
        <li v-for="(r, index) in rounds" :key="index">
          Course: {{ r.course }} – Score: {{ r.score }}
        </li>
      </ul>
      <p v-if="!rounds.length && fetched">No rounds found.</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        round: {
          course: '',
          score: null,
        },
        message: '',
        rounds: [],
        fetched: false
      };
    },
    methods: {
      async submitRound() {
        try {
          const res = await fetch('http://localhost:8080/round/create', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.round),
          });
          if (res.ok) {
            this.message = 'Round added successfully!';
            this.round.course = '';
            this.round.score = null;
          } else {
            this.message = 'Failed to add round.';
          }
        } catch (error) {
          this.message = 'Error: ' + error.message;
        }
      },
      async getRounds() {
        try {
          const res = await fetch('http://localhost:8080/round/get');
          if (res.ok) {
            const data = await res.json();
            this.rounds = data;
            this.fetched = true;
            this.message = '';
          } else {
            this.message = 'Failed to fetch rounds.';
          }
        } catch (error) {
          this.message = 'Error: ' + error.message;
        }
      }
    }
  };
  </script>
  