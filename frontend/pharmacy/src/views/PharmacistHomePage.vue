<template>
  <NavBar/>
  <div class="title containter">
      <WorkingCalendar/>
  </div>
</template>

<script>
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
}
</style>