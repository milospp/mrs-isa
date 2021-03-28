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
				<div v-if="role === 'none'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'HomePage'}" class="nav-item nav-link">Home</router-link>
					<router-link :to="{ name: 'Pharmacies'}" class="nav-item nav-link">Pharmacies</router-link>
					<router-link to="/medicines?q=none" class="nav-item nav-link">Medicines</router-link>
					<router-link :to="{ name: 'DermAppointments'}" class="nav-item nav-link">Derm. Appointments</router-link>


				</div>
				<div v-else-if="role === 'pharmacist'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link v-if="role === 'pharmacist' || role === 'dermatologist'" :to="{ name: 'PharmacistHomePage'}" class="nav-item nav-link">Working Calendar</router-link>
					<router-link to="/medicines?q=pharmacist" class="nav-item nav-link">Medicines</router-link>
					<router-link v-if="role === 'pharmacist' || role === 'dermatologist'" :to="{ name: 'Patients'}" class="nav-item nav-link">My Patients</router-link>
					<router-link :to="{ name: 'DispenseMedication'}" class="nav-item nav-link">Dispense Medication</router-link>
					<router-link v-if="role === 'pharmacist' || role === 'dermatologist'" :to="{ name: 'VacationRequest'}" class="nav-item nav-link">Vacation Request</router-link>
					
				</div>

				<!-- desni deo -->
				<div v-if="role === 'none'" class="navbar-nav my-2 my-lg-0">
					<router-link to="/login" class="nav-link">Login</router-link>
					<router-link to="/register" class="nav-link">Register</router-link>
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
export default {
  name: 'NavBar',
  props: ['role'],
  data: function () {
    return {
      // TO DOOO
			// user: JSON.parse(window.localStorage.getItem('user'))
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#navbar a.router-link-exact-active {
  color: #42b983;
}
</style>