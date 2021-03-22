<template>
  <NavBar/>
  <div class="container">
    <h1>Patients</h1>
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
  components: {
    NavBar,
    PatientList,
  }
}
</script>