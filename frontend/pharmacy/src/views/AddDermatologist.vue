<template>
  <NavBar/>
  <div class="container">
    <h1 align="left">Register a dermatologist</h1>
    <p>
    <AddDermatologistForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddDermatologistForm from '@/components/AddDermatologistForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'Register Dermatologist',
  components: {
    NavBar, AddDermatologistForm
  }
}
</script>