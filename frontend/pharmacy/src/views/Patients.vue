<template>
  <NavBar/>
  <div class="container">
    <h1 class="title">My Patients</h1>
    <PatientList/>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import PatientList from '@/components/PatientList'
export default {
  name: 'Patients',
  components: {
    NavBar,
    PatientList,
  },
  data() {
    return {
      
    }
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>