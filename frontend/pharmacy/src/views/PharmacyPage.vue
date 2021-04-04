<template>
<div>
  <NavBar/>
  
  <div class="container pt-5">
    <PharmacyProfileInfo/>
  </div>
</div>
</template>

<style>

</style>

<script>
import NavBar from '@/components/NavBar.vue'
import PharmacyProfileInfo from '../components/PharmacyProfileInfo.vue'

// @ is an alias to /src
export default {
  name: 'PharmacyPage',
  components: {
    NavBar,
    PharmacyProfileInfo,
  }
}
</script>
