<template>
  <NavBar/>
  <div class="container">
    <h1>File a complaint</h1>
    
        <ul class="nav nav-tabs">
              <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#tab-pharmacies">Pharmacies</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-pharmacists">Pharmacists</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-dermatologists">Dermatologists</a></li>
        </ul>
          
            <div class="tab-content">
              <div id="tab-pharmacies" class="tab-pane in fade active">
                <PharmacyComplaint :userId="userId"/>
              </div>
              <div id="tab-pharmacists" class="tab-pane in fade">
                <PharmacistComplaint :userId="userId"/>
              </div>
               <div id="tab-dermatologists" class="tab-pane in fade">
                <DermatologistComplaint :userId="userId"/>
              </div>
            </div>
    
  </div>
</template>
<script>
import PharmacyComplaint from '@/components/PharmacyComplaint.vue'
import PharmacistComplaint from '@/components/PharmacistComplaint.vue'
import DermatologistComplaint from '@/components/DermatologistComplaint.vue'
import NavBar from '@/components/NavBar.vue'
import AuthService from '../service/AuthService.js'
export default {
  name: 'ComplaintPage',
  components: {
    NavBar, PharmacyComplaint, PharmacistComplaint, DermatologistComplaint
  },

  beforeMount() {
      let user = AuthService.getCurrentUser();
      if (user == null) {
        AuthService.logout();
        this.$router.replace("/login");
        return;
      }

      if (user.role != "PATIENT"){
          this.$router.replace("/");
      }

      this.userId = user.id;
  }
}
</script>