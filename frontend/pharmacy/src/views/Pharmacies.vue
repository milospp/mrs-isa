<template>
  <NavBar/>
  <div class="container mt-3">
    <PharmaciesList/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import PharmaciesList from '@/components/PharmaciesList'
export default {
  name: 'Pharmacies',
  components: {
    NavBar,
    PharmaciesList,
  }
}
</script>
