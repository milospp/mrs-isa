<template>
<div>
  <NavBar/>
  
  <div class="container pt-5">
    <PharmacyProfileInfo/>
  </div>
</div>
</template>

<style scoped>
  .in {
    opacity: 1 !important;
  }
  .nav-item.active > .nav-link {
    color: #495057;
    background-color: #fff;
    border-color: #dee2e6 #dee2e6 #fff;
  }
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
