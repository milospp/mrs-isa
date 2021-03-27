<template>
  <NavBar role="pharmacist"/>
  <h1 class="title">Dispense Medication</h1>
  <div class="containter">
      <!-- <h1 class="title">Dispense Medication</h1> -->
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'

export default {
  name: 'DispenseMedication',
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