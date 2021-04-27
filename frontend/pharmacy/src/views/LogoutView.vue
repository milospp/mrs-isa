<template>
  <div class="logout-template">
      <h1>Logout</h1>
  </div>
</template>

<script>

    import AuthService from '../service/AuthService.js';

export default {
    beforeMount() {
        AuthService.logout();
        this.$router.push("/");
    }
}
</script>

<style>

</style>