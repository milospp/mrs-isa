<template>
  <NavBar/>
  <div class="container">
    <h1>Pharmacy's reports</h1>
    <br/>
    <h4>Report is about:</h4>
    <PAReports/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import PAReports from '@/components/PAReports.vue'
export default {
  name: 'PAReportsPage',
  components: {
    NavBar,
    PAReports,
  }
}
</script>