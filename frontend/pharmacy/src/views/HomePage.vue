<template>
  <NavBar/>
  <h1>Home</h1>
  <div class="container">
    <Pharmacies/>
  </div>
  <div class="container py-5">
    <Medicines/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import Pharmacies from '@/components/PharmaciesMinList.vue'
import Medicines from '@/components/MedicinesMinList.vue'

export default {
  name: 'Home',
  components: {
    NavBar,
    Pharmacies,
    Medicines,
  }
}
</script>