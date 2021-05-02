<template>
  <NavBar/>
  <div class="container">
    <h1>Home page</h1>
    <Card/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import Card from '@/components/CardPharmacyAdmin.vue'
export default {
  name: 'CardPharmacyAdmin',
  components: {
    NavBar,
    Card,
  }
}
</script>