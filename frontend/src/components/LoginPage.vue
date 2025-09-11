
<template>
  <div>
    <div class="home-container">
      <header class="loginForm">
        <h1>Adam's Golf Stats Tracker - {{ isRegistering ? 'Register' : 'Login' }}</h1>
        
        <!-- Login/Register Toggle -->
        <div class="auth-toggle">
          <button 
            @click="isRegistering = false" 
            :class="{ active: !isRegistering }"
          >
            Login
          </button>
          <button 
            @click="isRegistering = true" 
            :class="{ active: isRegistering }"
          >
            Register
          </button>
        </div>
        <!-- Additional Register Fields (only show when registering) -->
         <div v-if="isRegistering" class="form-group">
          <label>Email</label>
          <input type="email" v-model="email" required placeholder="Please enter your email"/>
        </div>

        <!-- Username Field (always visible) -->
        <div class="form-group">
          <label>Username</label>
          <input type="text" v-model="userName" required placeholder="Enter your username"/>
        </div>
        
        <!-- Password Field (always visible) -->
        <div class="form-group">
          <label>Password</label>
          <input type="password" v-model="passWord" required placeholder="Enter your password"/>
        </div>

        <!-- Additional Register Fields (only show when registering) -->
        <div v-if="isRegistering" class="form-group">
          <label>Confirm Password</label>
          <input type="password" v-model="confirmPassword" required placeholder="Confirm your password"/>
        </div>
        
      <!-- Want to add some validation maybe some functions that checks whats inputted to see it has required length, and chars  -->  
       

        <!-- Submit Button -->
        <button 
          @click.prevent="submit" 
          class="submit" 
          :disabled="!isFormValid"
        >
          {{ loading ? 'Processing...' : isRegistering ? 'Register' : 'Login' }}
        </button>

        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
        <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
      </header>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      userName: '',
      passWord: '',
      email:'',
      confirmPassword: '',
      isRegistering: false,
      loading: false,
      errorMessage: '',
      successMessage: ''
    };
  },

  computed: {
     isFormValid() {
    if (this.isRegistering) {
      return (
        this.validateEmail(false) &&
        this.validateUsername(false) &&
        this.validatePassword(false) &&
        this.validateConfirmPassword(false)
      );
    } else {
      return (
        this.validateUsername(false) &&
        this.validatePassword(false)
      );
    }
  },
  },
  
  watch: {
    // Clear messages when switching between login/register
    isRegistering() {
      this.errorMessage = '';
      this.successMessage = '';
      this.confirmPassword = '';
   
    }
  },
  
  methods: {
    //implement functions that validate each field 
    validateEmail(setError = true){
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if(!this.email.trim()){
         if(setError) this.errorMessage = "Enter an email address";
        return false;
      }
      if(this.email.length<4){
         if(setError) this.errorMessage = "Enter an email address over 4 chars";
        return false;
      }
      if(!emailRegex.test(this.email)){
        if(setError) this.errorMessage = "Enter an valid email address"
        return false;
      }
      return true;

    },

    validateUsername(setError = true){
      if(this.userName.length<4){
         if(setError) this.errorMessage = "Enter a username greater than four characters"; 
        return false; 
      }
      if(!this.userName.trim()){
         if(setError) this.errorMessage = "Enter text in the username input";
        return false;
      }
      return true;

    },

    validatePassword(setError = true){
      if(!this.passWord){
        if(setError) this.errorMessage = "Enter a password"; 
        return false; 
      }
      if(this.passWord.length<6){
         if(setError) this.errorMessage = "Password must be longer than 6 characters"; 
        return false;
      }
      if(!/[A-Z]/.test(this.passWord)){
         if(setError) this.errorMessage = "Please enter an uppercase or lowercase"; 
        return false; 
      }
      if(!/[0-9]/.test(this.passWord)){
         if(setError) this.errorMessage ="Please a number between 0-9";
        return false; 
      }
      this.errorMessage =" "; 
      return true;

    },
    validateConfirmPassword(setError = true){
      if(this.passWord!==this.confirmPassword){
         if(setError) this.errorMessage="Passwords need to match";
        return false;
      }else{
        return true; 
      }


    },

    async submit() {
      if (this.loading) return;
      
      this.loading = true;
      this.errorMessage = '';
      this.successMessage = '';

      try {
        if (this.isRegistering) {
          await this.register();
        } else {
          await this.login();
        }
      } catch (error) {
        console.error('Auth error:', error);
        this.errorMessage = error.message || `${this.isRegistering ? 'Registration' : 'Login'} failed. Please try again.`;
      } finally {
        this.loading = false;
      }
    },

    async login() {
      const loginData = {
        userName: this.userName,
        passWord: this.passWord,
      }; 
      
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || 'Login failed');
      }

      const data = await response.json();
      console.log('Login successful', data);
      this.$router.push('/home');
      const userId = data.user.id;
      console.log('User ID:', userId);

    // Store for later use
    localStorage.setItem('userId', userId);
    },

    async register() {
      // Validate passwords match
      if (this.passWord !== this.confirmPassword) {
        throw new Error('Passwords do not match');
      }

      const registerData = {
        userName: this.userName,
        passWord: this.passWord,
        email: this.email
       
      }; 
      
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(registerData)
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || 'Registration failed');
      }

      const data = await response.json();
      console.log('Registration successful', data);
      window.alert('Registration successful!');
      this.$router.push('/home');
      

      
      // Switch back to login form after successful registration
      setTimeout(() => {
        this.isRegistering = false;
        this.successMessage = '';
      }, 2000);
    }
  }
};
</script>
<style scoped>
/* Add these styles for better form layout */
.form-group {
  margin-bottom: 1rem;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

/* Auth Toggle Styles */
.auth-toggle {
  display: flex;
  margin-bottom: 1.5rem;
  border-radius: 4px;
  overflow: hidden;
  border: 1px solid #2ecc71;
}

.auth-toggle button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  background: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.auth-toggle button.active {
  background-color: #2ecc71;
  color: white;
}

.auth-toggle button:hover:not(.active) {
  background-color: #f0f0f0;
}

.error-message {
  color: #e74c3c;
  margin-top: 1rem;
}

.success-message {
  color: #27ae60;
  margin-top: 1rem;
}

.submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.submit {
  background-color: #2ecc71;
  color: white;
  padding: 0.75rem 2rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 1rem;
}

.submit:hover:not(:disabled) {
  background-color: #27ae60;
}
</style>

