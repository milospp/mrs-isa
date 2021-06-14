<template>
    <NavBar/>
    <div class="container">
      <LoyaltyProgramForm/>
    </div>
</template>
<script>
import NavBar from '@/components/NavBar.vue'
import LoyaltyProgramForm from '@/components/LoyaltyProgramForm.vue'
import AuthService from '../service/AuthService.js'

export default {
    name: 'LoyaltyProgramView',
    components: {
        NavBar, LoyaltyProgramForm,
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