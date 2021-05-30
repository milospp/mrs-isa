<template>
  <NavBar/>
  <div class="container">
    
    <h1 class="title" align="center">Login</h1>
    <LoginForm/>
    
  </div>
</template>

<script>
// @ is an alias to /src
import LoginForm from '@/components/LoginForm.vue'
import NavBar from '@/components/NavBar.vue'


export default {
  name: 'Home',
  components: {
    LoginForm,
    NavBar,
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>