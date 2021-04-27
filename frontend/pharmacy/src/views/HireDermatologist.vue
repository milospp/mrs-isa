<template>
  <NavBar/>
  <div class="container">
    <h1>Hire dermatologist</h1>
    <HireDermatologistForm/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import HireDermatologistForm from '@/components/HireDermatologistForm.vue'
export default {
  name: 'AddPharmacist',
  components: {
    NavBar,
    HireDermatologistForm,
  }
}
</script>