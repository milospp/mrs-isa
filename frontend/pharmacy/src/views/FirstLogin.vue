<template>
  <h1 class="title" align="center">Password change</h1>
  <FirstLoginForm/>
</template>

<script>
// @ is an alias to /src
import FirstLoginForm from '@/components/FirstLoginForm.vue'


export default {
  name: 'Home',
  components: {
    FirstLoginForm
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>