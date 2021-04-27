<template>
  <NavBar/>
  <div class="container">
    <h1>Home page</h1>
    <HpPharmacyAdmin/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import HpPharmacyAdmin from '@/components/HpPharmacyAdmin.vue'
export default {
  name: 'HomePagePharmacyAdmin',
  components: {
    NavBar,
    HpPharmacyAdmin,
  }
}
</script>