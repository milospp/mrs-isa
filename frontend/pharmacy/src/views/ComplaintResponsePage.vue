<template>
  <NavBar/>
  <div class="container">
    <h1>Complaints</h1>
    <ComplaintResponse/>
  </div>
</template>
<script>
import NavBar from '@/components/NavBar.vue'
import ComplaintResponse from '@/components/ComplaintResponse.vue'
import AuthService from '../service/AuthService.js'
export default {
  name: 'ComplaintResponsePage',
  components: {
    NavBar, ComplaintResponse
  },

  beforeMount() {
      let user = AuthService.getCurrentUser();
      if (user == null) {
        AuthService.logout();
        this.$router.replace("/login");
        return;
      }

      if (user.role != "SYS_ADMIN"){
          this.$router.replace("/");
      }

      this.userId = user.id;
  }
}
</script>