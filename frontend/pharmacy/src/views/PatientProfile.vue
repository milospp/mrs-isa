<template>
  <div>
    <NavBar/>
    
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>


    <div class="container pt-5">
      <PatientProfileInfo/>


      <div class="row subsection">
        <div class="col-md-12">

          <div>
            <h2>TODO AUTH!! More info</h2>
            <ul class="nav nav-tabs">
              <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-alergies">Alergies</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Subscription</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Penalities</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#appointments">Appointments</a></li>
              <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#history">Hisotry</a></li>
              <!-- <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Map</a></li> -->
            </ul>
          
            <div class="tab-content">
              <div id="tab-alergies" class="tab-pane in fade active">
                <PatientAlergies/>
              </div>

              <div id="menu1" class="tab-pane in fade">
                <PatientSubscription/>

              </div>
              <div id="menu2" class="tab-pane in fade">

                <PatientPenalities/>        
              </div>
              <div id="appointments" class="tab-pane fade">
                <PatientAppointments/>
              </div>
              
              <div id="history" class="tab-pane fade">
                <PatientHistory/>
              </div>
            </div>
          </div>
          

          
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
  .subsection .in {
    opacity: 1 !important;
  }
  .nav-item.active > .nav-link {
    color: #495057;
    background-color: #fff;
    border-color: #dee2e6 #dee2e6 #fff;
  }
</style>

<script>
import NavBar from '@/components/NavBar.vue'
import PatientProfileInfo from '../components/patientProfile/PatientProfileInfo.vue'
import PatientAlergies from '../components/patientProfile/PatientAlergies.vue'
import PatientSubscription from '../components/patientProfile/PatientSubscription.vue'
import PatientPenalities from '../components/patientProfile/PatientPenalities.vue'
import PatientAppointments from '../components/patientProfile/PatientAppointments.vue'
import PatientHistory from '../components/patientProfile/PatientHistory.vue'

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
  }
}
</script>
