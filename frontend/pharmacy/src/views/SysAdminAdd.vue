<template>
  <NavBar/>
  <div class="container">
    <h1>Add a new...</h1>
    
        <ul class="nav nav-tabs">
              <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#tab-pharmacy">Pharmacy</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-supplier">Supplier</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-dermatologist">Dermatologist</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-medicine">Medicine</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-phAdmin">Pharmacy Admin.</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-sysAdmin">System Admin.</a></li>
        </ul>
            <br>
            <div class="tab-content">
                <div id="tab-pharmacy" class="tab-pane in fade active">
                    <AddPharmacyForm/>
                </div>
                <div id="tab-supplier" class="tab-pane in fade">
                    <AddSupplierForm/>
                </div>
                <div id="tab-dermatologist" class="tab-pane in fade">
                    <AddDermatologistForm/>
                </div>
                <div id="tab-medicine" class="tab-pane in fade">
                    <AddMedicineForm/>
                </div>
                <div id="tab-phAdmin" class="tab-pane in fade">
                    <AddPharmacyAdminForm/>
                </div>
                <div id="tab-sysAdmin" class="tab-pane in fade">
                    <AddSysAdminForm/>
                </div>
            </div>
    
  </div>
</template>
<script>
import AddPharmacyForm from '@/components/AddPharmacyForm.vue';
import AddSupplierForm from '@/components/AddSupplierForm.vue';
import AddDermatologistForm from '@/components/AddDermatologistForm.vue';
import AddMedicineForm from '@/components/AddMedicineForm.vue';
import AddPharmacyAdminForm from '@/components/AddPharmacyAdminForm.vue';
import AddSysAdminForm from '@/components/AddSysAdminForm.vue';
import NavBar from '@/components/NavBar.vue'
import AuthService from '../service/AuthService.js'
export default {
  name: 'SysAdminAdd',
  components: {
    NavBar, AddPharmacyForm, AddSupplierForm, AddDermatologistForm, AddMedicineForm, AddPharmacyAdminForm, AddSysAdminForm
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