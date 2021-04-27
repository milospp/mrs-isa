<template>
  <nav id="navbar" class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">

				<!-- levi deo -->
				<div v-if="!user" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'HomePage'}" class="nav-item nav-link">Home</router-link>
					<router-link :to="{ name: 'Pharmacies'}" class="nav-item nav-link">Pharmacies</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					<!-- <router-link :to="{ name: 'DermAppointments'}" class="nav-item nav-link">Derm. Appointments</router-link> -->


				</div>
				<div v-else-if="user.role === 'PHARMACIST'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/pharmacist">Navbar</a>

					<router-link :to="{ name: 'PharmacistHomePage'}" class="nav-item nav-link">Working Calendar</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					<router-link to="/patients" class="nav-item nav-link">My Patients</router-link>
					<router-link :to="{ name: 'DispenseMedication'}" class="nav-item nav-link">Dispense Medication</router-link>
					<router-link to="/vacation-request" class="nav-item nav-link">Vacation Request</router-link>
					
				</div>
				<div v-else-if="user.role === 'DERMATOLOGIST'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/dermatologist">Navbar</a>

					<router-link :to="{ name: 'DermatologistHomePage'}" class="nav-item nav-link">Working Calendar</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					<router-link to="/patients" class="nav-item nav-link">My Patients</router-link>
					<router-link to="/vacation-request" class="nav-item nav-link">Vacation Request</router-link>
					
				</div>

				<!-- desni deo -->
				<div v-if="!user" class="navbar-nav my-2 my-lg-0">
					<router-link to="/login" class="nav-link">Login</router-link>
					<router-link to="/register" class="nav-link">Register</router-link>
				</div>
			  	<div v-else-if="user.role === 'PHARMACIST'" class="navbar-nav my-2 my-lg-0">
					<router-link to="/pharmacist/profile/15" class="nav-link">Profile</router-link>
					<router-link to="/logout" class="nav-link">Logout</router-link>
				</div>
				<div v-else-if="user.role === 'DERMATOLOGIST'" class="navbar-nav my-2 my-lg-0">
					<router-link to="/dermatologist/profile/16" class="nav-link">Profile</router-link>
					<router-link to="/logout" class="nav-link">Logout</router-link>
				</div>
				<div v-else class="navbar-nav my-2 my-lg-0">
					<router-link to="/profile" class="nav-link">Profile</router-link>
					<router-link to="/logout" class="nav-link">Logout</router-link>
				</div>
			</div>
		</div>
	</nav>

</template>

<script>
import AuthService from '../service/AuthService.js';

export default {
  name: 'NavBar',
  data: function () {
    return {
		user: null,
      // TO DOOO
			// user: JSON.parse(window.localStorage.getItem('user'))
    }
  },
  created(){
	  this.user = JSON.parse(AuthService.getCurrentUser());
	  console.log(this.user);
	  console.log(this.user.role);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#navbar a.router-link-exact-active {
  color: #42b983;
}
</style>