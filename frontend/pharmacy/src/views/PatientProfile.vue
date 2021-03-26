<template>
  <NavBar/>
  
  <div class="container pt-5">
    <PatientProfileInfo/>


    <div class="row">
      <div class="col-md-12">

        <div>
          <h2>TODO More info</h2>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-alergies">Alergies</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Subscription</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Penalities</a></li>
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
            <div id="menu3" class="tab-pane fade">
            </div>
          </div>
        </div>
        

        
      </div>
    </div>

  </div>
</template>

<style>
  .in {
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

// @ is an alias to /src
export default {
  name: 'PatientProfile',
  components: {
    NavBar,
    PatientProfileInfo,
    PatientAlergies,
    PatientSubscription,
    PatientPenalities,
  }
}
</script>
