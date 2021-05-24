<template>
  <h1 class="title" align="center">Login</h1>
  <LoginForm/>
</template>

<script>
// @ is an alias to /src
import LoginForm from '@/components/LoginForm.vue'


export default {
  name: 'Home',
  components: {
    LoginForm
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>