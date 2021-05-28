<template>
  <NavBar/>
  <div class="container">
    <h1 class="title" align="center">Register</h1>
    <p>
    <AddPatientForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddPatientForm from '@/components/AddPatientForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'Register',
  components: {
    NavBar, AddPatientForm
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>