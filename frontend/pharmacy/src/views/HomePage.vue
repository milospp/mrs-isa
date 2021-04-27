<template>
  <NavBar/>
  <h1 class="title">Home</h1>
  <div class="container">
    <Pharmacies limit="8"/>
    <router-link class="btn btn-secondary text-left" :to="{ name: 'Pharmacies'}">View all</router-link>

  </div>
  <div class="container py-5">
    <Medicines limit="6"/>
    <router-link class="btn btn-secondary text-left" :to="{ name: 'Medicines'}">View all</router-link>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import Medicines from '@/components/MedicinesList.vue'
import Pharmacies from '@/components/PharmaciesList'


export default {
  name: 'Home',
  components: {
    NavBar,
    Pharmacies,
    Medicines
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>