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
				<div class="navbar-nav mr-auto mt-2 mt-lg-0">
					<a class="navbar-brand" href="/">Navbar</a>

					<router-link :to="{ name: 'HomePage'}" class="nav-item nav-link">Home</router-link>
					<router-link :to="{ name: 'Pharmacies'}" class="nav-item nav-link">Pharmacies</router-link>
					<router-link :to="{ name: 'Medicines'}" class="nav-item nav-link">Medicines</router-link>
					
					<!-- <router-link v-if="user && user.userRole == 'ADMIN'" to="/users" class="nav-link">Users</router-link>  -->

				</div>
				<div v-if="!user" class="navbar-nav my-2 my-lg-0">
					<router-link to="/login" class="nav-link">Login</router-link>
					<router-link to="/register" class="nav-link">Register</router-link>
				</div>

			  <div v-if="user" class="navbar-nav my-2 my-lg-0">
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
  props: {},
  data: function () {
    return {
      // TO DOOO
			user: JSON.parse(window.localStorage.getItem('user'))
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