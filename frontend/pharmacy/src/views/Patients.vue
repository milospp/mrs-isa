<template>
  <NavBar/>
  <div class="container">
    <h1 class="title">My Patients</h1>
    <PatientList/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import PatientList from '@/components/PatientList'
export default {
  name: 'Patients',
  //props: ['role'],
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