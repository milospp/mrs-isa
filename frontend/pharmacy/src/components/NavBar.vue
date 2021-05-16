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
					<router-link :to="{ name: 'MedicineSearchPage'}" class="nav-item nav-link">Search medicines</router-link>


				</div>
				<div v-else-if="user.role === 'PATIENT'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'HomePage'}" class="nav-item nav-link">Home</router-link>
					<router-link :to="{ name: 'Pharmacies'}" class="nav-item nav-link">Pharmacies</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					<router-link :to="{ name: 'Counseling'}" class="nav-item nav-link">Counseling</router-link>
					<router-link :to="{ name: 'DermAppointments'}" class="nav-item nav-link">Derm Appointments</router-link>

					
				</div>
				<div v-else-if="user.role === 'PHARMACIST'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'PharmacistHomePage'}" class="nav-item nav-link">Working Calendar</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					<router-link :to="{ name: 'Patients'}" class="nav-item nav-link">My Patients</router-link>
					<router-link :to="{ name: 'DispenseMedication'}" class="nav-item nav-link">Dispense Medication</router-link>
					<router-link :to="{ name: 'VacationRequest' }" class="nav-item nav-link">Vacation Request</router-link>
					
				</div>
				<div v-else-if="user.role === 'DERMATOLOGIST'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'DermatologistHomePage'}" class="nav-item nav-link">Working Calendar</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					<router-link :to="{ name: 'Patients'}" class="nav-item nav-link">My Patients</router-link>
					<router-link :to="{ name: 'VacationRequest' }" class="nav-item nav-link">Vacation Request</router-link>
					
				</div>

			<!-- Za administratora apotekee -->
				<div v-else-if="user.role === 'PHARMACY_ADMIN'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'HomePagePharmacyAdmin'}" class="nav-item nav-link">Dashboard</router-link>
					<router-link :to="{ name: 'PAMoreInfo'}" class="nav-item nav-link">More info</router-link>
					<router-link :to="{ name: 'CartPharmacyAdmin'}" class="nav-item nav-link">Cart</router-link>
					
				</div>
				<div v-else-if="user.role === 'SYS_ADMIN'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'AddPharmacy'}" class="nav-item nav-link">Add Pharmacy</router-link>
					<router-link :to="{ name: 'AddSupplier'}" class="nav-item nav-link">Add Supplier</router-link>
					<router-link :to="{ name: 'AddDermatologist' }" class="nav-item nav-link">Add Dermatologist</router-link>
					<router-link :to="{ name: 'AddMedicine' }" class="nav-item nav-link">Add Medicine</router-link>
					<router-link :to="{ name: 'AddPharmacyAdmin' }" class="nav-item nav-link">Add Pharmacy Admin.</router-link>
					<router-link :to="{ name: 'AddSysAdmin' }" class="nav-item nav-link">Add Sys. Admin.</router-link>
					
				</div>
				<div v-else-if="user.role === 'SUPPLIER'" class="navbar-nav mr-auto mt-2 mt-lg-0">

					<a class="navbar-brand" href="/">Navbar</a>
					<router-link :to="{ name: 'AddNewOffer'}" class="nav-item nav-link">New offer</router-link>
					<router-link :to="{ name: 'SuppliersOffersPage'}" class="nav-item nav-link">My offers</router-link>

				</div>

				<!-- desni deo -->
				<div v-if="!user" class="navbar-nav my-2 my-lg-0">
					<router-link to="/login" class="nav-link">Login</router-link>
					<router-link to="/register" class="nav-link">Register</router-link>
				</div>
			  	<div v-else-if="user.role === 'PHARMACIST'" class="navbar-nav my-2 my-lg-0">
					<router-link to="/pharmacist/profile" class="nav-link">Profile</router-link>
					<router-link to="/logout" class="nav-link">Logout</router-link>
				</div>
				<div v-else-if="user.role === 'DERMATOLOGIST'" class="navbar-nav my-2 my-lg-0">
					<router-link to="/dermatologist/profile" class="nav-link">Profile</router-link>
					<router-link to="/logout" class="nav-link">Logout</router-link>
				</div>
				<div v-else-if="user.role === 'SUPPLIER'" class="navbar-nav my-2 my-lg-0">
					<router-link to="/supplier/profile" class="nav-link">Profile</router-link>
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
	  this.user = AuthService.getCurrentUser();
	  if (this.user)
	   console.log(this.user.role);
	//   
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#navbar a.router-link-exact-active {
  color: #42b983;
}
</style>