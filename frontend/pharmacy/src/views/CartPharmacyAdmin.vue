<template>
  <NavBar/>
  <div class="container">
    <h1>Home page</h1>
    <Cart/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import Cart from '@/components/CartPharmacyAdmin.vue'
export default {
  name: 'CartPharmacyAdmin',
  components: {
    NavBar,
    Cart,
  }
}
</script>