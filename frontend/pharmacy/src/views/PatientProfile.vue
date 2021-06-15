<template>
  <div>
    <NavBar/>


    <div class="container pt-5">
      <PatientProfileInfo :userId="userId"/>




      <div class="row subsection">
        <div class="col-md-12">
<br>
<br>
          <div>
            <ul class="nav nav-tabs">
              <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#tab-alergies">Alergies</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#tab-reservations">Reservations</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Subscription</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Penalities</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#appointments">Appointments</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#history">History</a></li>
              <!-- <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Map</a></li> -->
            </ul>
          
            <div class="tab-content">
              <div id="tab-alergies" class="tab-pane in fade active">
                <PatientAlergies :userId="userId"/>
              </div>
              <div id="tab-reservations" class="tab-pane in fade">
                <PatientReservations :userId="userId"/>
              </div>

              <div id="menu1" class="tab-pane in fade">
                <PatientSubscription :userId="userId"/>

              </div>
              <div id="menu2" class="tab-pane in fade">

                <PatientPenalities :userId="userId"/>        
              </div>
              <div id="appointments" class="tab-pane fade">
                <PatientAppointments :userId="userId"/>
              </div>
              
              <div id="history" class="tab-pane fade">
                <PatientHistory :userId="userId"/>
              </div>
            </div>
          </div>
          

          
        </div>
      </div>

    </div>
  </div>
</template>

<style>


</style>

<script>
import NavBar from '@/components/NavBar.vue'
import PatientProfileInfo from '../components/patientProfile/PatientProfileInfo.vue'
import PatientAlergies from '../components/patientProfile/PatientAlergies.vue'
import PatientSubscription from '../components/patientProfile/PatientSubscription.vue'
import PatientPenalities from '../components/patientProfile/PatientPenalities.vue'
import PatientAppointments from '../components/patientProfile/PatientAppointments.vue'
import PatientHistory from '../components/patientProfile/PatientHistory.vue'
import PatientReservations from '../components/patientProfile/PatientReservations.vue'
import AuthService from '../service/AuthService.js'

// @ is an alias to /src
export default {
  name: 'PatientProfile',
  components: {
    NavBar,
    PatientProfileInfo,
    PatientAlergies,
    PatientSubscription,
    PatientPenalities,
    PatientAppointments,
    PatientHistory,
    PatientReservations,
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
