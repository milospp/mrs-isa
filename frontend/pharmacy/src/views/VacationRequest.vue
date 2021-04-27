<template>
  <NavBar/>
  <h1 class="title">Vacation Request</h1>
  <div class="containter">
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'

export default {
  name: 'PharmacistHomePage',
  //props: ['role'],
  components: {
    NavBar
  },
  data() {
      return {
          email: "test#test"
      }
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>