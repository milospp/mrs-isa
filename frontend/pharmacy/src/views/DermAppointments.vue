<template>
  <div>
    <NavBar/>
    <div class="container">
      <h1>Appointments</h1>
      <Appointments/>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import Appointments from '@/components/Appointments'
export default {
  name: 'Pharmacies',
  components: {
    NavBar,
    Appointments,
  }
}
</script>