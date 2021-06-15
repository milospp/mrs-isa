<template>
  <NavBar/>
  <div class="container">
    <PharmacyQRCPage/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import PharmacyQRCPage from '@/components/PharmacyQRCPage.vue'
export default {
  name: 'PharmacyQRcode',
  components: {
    NavBar,
    PharmacyQRCPage,
  }
}
</script>