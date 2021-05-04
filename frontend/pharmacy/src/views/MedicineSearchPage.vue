<template>
  <NavBar/>
  <div class="container">
    <h1>Search medicines...</h1> 
    <MedicineSearch/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import MedicineSearch from '@/components/MedicineSearch.vue'

export default {
  name: 'MedicinesSearchPage',
  components: {
    NavBar,
    MedicineSearch
  }
}
</script>