<template>
  <NavBar/>
  <div class="container">
    <h1 align="left">Register a new pharmacy</h1>
    <p>
    <AddPharmacyForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddPharmacyForm from '@/components/AddPharmacyForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'Register pharmacy',
  components: {
    NavBar, AddPharmacyForm
  }
}
</script>

