<template>
  <NavBar/>
  <div class="container">
    <h1>My offers</h1>
    <SuppliersOffers/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import SuppliersOffers from '@/components/SuppliersOffers.vue'
export default {
  name: 'SuppliersOffersPage',
  components: {
    NavBar,
    SuppliersOffers,
  }
}
</script>