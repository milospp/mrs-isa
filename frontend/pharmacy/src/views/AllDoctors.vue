<template>
  <NavBar/>
  <div class="container">
    <AllDoctorsView/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import AllDoctorsView from '@/components/AllDoctorsView.vue'
export default {
  name: 'CartPharmacyAdmin',
  components: {
    NavBar,
    AllDoctorsView,
  }
}
</script>