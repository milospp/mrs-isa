<template>
  <NavBar/>
  <div class="title containter">
      <WorkingCalendar/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import WorkingCalendar from '@/components/WorkingCalendar.vue'

export default {
  name: 'PharmacistHomePage',
  components: {
    NavBar,
    WorkingCalendar
  },
  data() {
      return {}
  }
}
</script>

<style scoped>
.title {
    margin: auto;
    margin-top: 1%;
    margin-bottom: 1%;
    /* width: 70%; */
    /* height: 90%; */
}
</style>