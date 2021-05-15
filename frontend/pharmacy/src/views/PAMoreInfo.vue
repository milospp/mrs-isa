<template>
  <NavBar/>
  <div class="container">
    <MoreInfo/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import MoreInfo from '@/components/PAMorePharmacyInfo.vue'
export default {
  name: 'PAMoreInfo',
  components: {
    NavBar,
    MoreInfo,
  }
}
</script>