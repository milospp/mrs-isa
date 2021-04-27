<template>
  <NavBar/>
  <h1 class="title">Working Calendar</h1>
  <div class="containter">
      <WorkingCalendar v-bind:email="email"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import WorkingCalendar from '@/components/WorkingCalendar.vue'

export default {
  name: 'DermatologistHomePage',
  components: {
    NavBar,
    WorkingCalendar
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