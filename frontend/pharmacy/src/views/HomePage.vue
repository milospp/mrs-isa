<template>
  <NavBar/>
  <FirstLoginWatcher/>
  <div class="container mt-3">
    <Pharmacies limit="8"/>
    <div class="text-center">
      <router-link class="btn btn-info" :to="{ name: 'Pharmacies'}">View all</router-link>
    </div>

  </div>
  <div class="container py-5">
    <Medicines limit="6"/>
    <div class="text-center">
      <router-link class="btn btn-info" :to="{ name: 'Medicines'}">View all</router-link>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import Medicines from '@/components/MedicinesList.vue'
import Pharmacies from '@/components/PharmaciesList'
import FirstLoginWatcher from '@/components/FirstLoginWatcher.vue'


export default {
  name: 'Home',
  components: {
    NavBar,
    Pharmacies,
    Medicines,
    FirstLoginWatcher
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>