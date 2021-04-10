<template>
    <div class="container">
        <div class="row">
            <div class="container" style="padding: 0px;">
                <form v-on:submit.prevent="searchPatients(this)" style="float: right;">
                    <div class="form-row form-inline mb-2" >
                        <div class="form-group col-auto">
                            <input v-model="searchName" type="text" class="form-control" id="name" placeholder="First Name">
                        </div>
                        <div class="form-group col-auto">
                            <input v-model="searchSurname" type="text" class="form-control" id="surname" placeholder="Last Name">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="container" style="padding: 0px;">
                <table class="table table-hover table-striped box-shadow">
                    <thead class="card-header">
                        <th style="">First name</th>
                        <th>Last name</th>
                        <th>Address</th>
                        <th>Phone number</th>
                        <th></th>
                    </thead>
                    <tbody>
                        <tr :key="p.username" v-for="p in patients" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
                            <td>{{p.name}}</td>
                            <td>{{p.surname}}</td>
                            <td>{{UtilService.AddressToString(p.address)}}</td>
                            <td>{{p.phoneNumber}}</td>
                            <td>
                                <button class="btn btn-primary" v-on:click="showModal(p)">Examination history</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <!--MODAL-->
        <div>
          <div class="modal fade" id="appointmentDetailsModal" tabindex="-1" role="dialog" aria-labelledby="appointmentDetailsModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div v-if="selectedAppointment" class="modal-content">
                <div class="modal-header">
                  <span v-bind:class="{ 'badge-info': selectedAppointment.type == 'COUNSELING', 'badge-primary': selectedAppointment.type == 'EXAMINATION' }" class="badge">{{selectedAppointment.type}}
                  <span v-if="false" class="badge badge-danger">CALCELED</span>
                  </span>
                  <h5 class="modal-title" id="appointmentDetailsModalLabel">Appointment</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <table class="table">
                    <tr>
                      <th>{{selectedAppointment.type}}</th>
                      <td>Type</td>
                    </tr>
                    <tr>
                      <th>Patient</th>
                      <td>{{selectedAppointment.examination.patient.name}} {{selectedAppointment.examination.patient.surname}}</td>
                    </tr>
                    <tr>
                      <th>Doctor</th>
                      <td>{{selectedAppointment.doctor.name}} {{selectedAppointment.doctor.surname}}</td>
                    </tr>
                    <tr>
                      <th>Start Time</th>
                      <td>{{UtilService.formatDateTime(selectedAppointment.startTime)}}</td>
                    </tr>
                    <tr>
                      <th>Duration</th>
                      <td>{{selectedAppointment.durationInMins}}min</td>
                    </tr>
                    <tr>
                      <th>Price</th>
                      <td>${{selectedAppointment.price}}</td>
                    </tr>
                    <tr>
                      <th>Examination Status</th>
                      <td>{{selectedAppointment.examination.status}}</td>
                    </tr>
                    <tr>
                      <th>Examination Info</th>
                      <td>{{selectedAppointment.examination.examinationInfo}}</td>
                    </tr>
                    <tr>
                      <th>Dignose</th>
                      <td>{{selectedAppointment.examination.diagnose}}</td>
                    </tr>
                  </table>
                  <hr>
                  <h4>Therapy</h4>
                  <h5 class="table" v-if="!Object.keys(selectedAppointment.examination.therapy).length">
                    No therapy
                  </h5>
                  <table class="table" v-if="Object.keys(selectedAppointment.examination.therapy).length">
                    <thead>
                      <tr>
                        <th>Medicine code</th>
                        <!-- <th>Medicine name</th> -->
                        <th>Days</th>
                      </tr>
                      <tr v-for="t in selectedAppointment.examination.therapy">
                        <td>{{t.medicine.code}}</td>
                        <!-- <td>m.name</td> -->
                        <td>{{t.days}}</td>
                      </tr>
                    </thead>
                  </table>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
              </div>
            </div>
          </div>

          <div class="modal fade" id="appointmentsModal" tabindex="-1" role="dialog" aria-labelledby="appointmentsModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="appointmentsModalLabel">Examination history</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">

                    <!-- <div class="radio-container">
                      <div class="form-check form-check-inline btn-info">
                        <input v-model="historyFilter" type="radio" name="inlineRadioOptions" id="examinations" value="EXAMINATION">
                        <label class="form-check-label" for="examinations">Examinations</label>
                      </div>
                      <div class="form-check form-check-inline btn-info">
                        <input v-model="historyFilter" type="radio" name="inlineRadioOptions" id="counseling" value="COUNSELING">
                        <label class="form-check-label" for="counseling">Counseling</label>
                      </div>
                      <div class="form-check form-check-inline btn-info">
                        <input v-model="historyFilter" type="radio" name="inlineRadioOptions" id="all" value="all" checked>
                        <label class="form-check-label" for="all">All</label>
                      </div>
                    </div> -->
                  <table class="table table-striped">

                    <thead>
                      <tr>
                        <th>Patient Name</th>
                        <th>TYPE</th>
                        <th>Doctor Name</th>
                        <th>Pharmacy</th>
                        <th>Start Time</th>
                        <th>Duration</th>
                        <th>Price</th>
                        <th></th>
                      </tr>
                    </thead>

                    <tbody>
                      <tr class="v-middle" v-for="a in appointments" v-bind:style="{ background: isCanceled(a) ? '#aaa' : ''}" v-bind:key="a.id">
                        <td>{{a.examination.patient.name}} {{a.examination.patient.surname}}</td>
                        <td v-bind:class="{ 'badge-info': a.type == 'COUNSELING', 'badge-primary': a.type == 'EXAMINATION' }" class="badge">{{a.type}}
                          <br><span v-if="isCanceled(a)" class="badge badge-danger">CALCELED</span>
                        </td>
                        <td>{{a.doctor.name}} {{a.doctor.surname}}</td>
                        <td>{{a.pharmacy.name}}</td> <!--<router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: a.pharmacy.id  }}">{{a.pharmacy.name}}</router-link>-->
                        <td>{{UtilService.formatDateTime(a.startTime)}}</td>
                        <td>{{a.durationInMins}}min</td>
                        <td>${{a.price}}</td>
                        <td>
                          <button class="btn btn-primary" v-on:click="showModalDetails(a)">Details</button>
                        </td>
                      </tr>
                      <!-- dummy podaci -->
                      <!-- <tr class="v-middle">
                        <td>name Surname</td>
                        <td>EXAMINATION
                        </td>
                        <td>Imenko Prezimic</td>
                        <td>Appotekaa</td>
                        <td>01-02-2021</td>
                        <td>20min</td>
                        <td>100RSD</td>
                        <td>
                          <button class="btn btn-primary" v-on:click="showModalDetails(a)">Details</button>
                        </td>
                      </tr> -->
                    </tbody>
                  
                  </table>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
              </div>

            
          </div>
        </div>
      </div>
    </div>
    


    
<!-- Modal -->


    <!-- <ExamHistory :role="role" :id="selectedPatient.id"/> -->
</template>

<script>
import PatientDataService from '../service/PatientDataService.js';
import UtilService from '../service/UtilService.js';
import AppointmentDataService from '@/service/AppointmentDataService.js';
import $ from 'jquery';
//import ExamHistory from '@/components/ExamHistory';

export default {
    name: 'PatientList',
    setup() {
      return {UtilService}
    },
    components: {
        //ExamHistory
    },
    data() {
        return {
            patients: [],
            message: null,
            searchName: "",
            searchSurname: "",
            selectedPatient: null,
            historyFilter: "all",
      appointments: [],
      selectedAppointment: null,
        };
    },
    methods: {
        refreshPatients() {
            PatientDataService.retrieveAllPatients() // HARDCODED
                .then(response => {
                    this.patients = response.data;
                    console.log(response.data);
                });
        },
        patientInfo(patient) {
            alert(patient);
        },
        searchPatients(e) {
            if (this.searchName || this.searchSurname){
                PatientDataService.searchPatients(this.searchName, this.searchSurname)
                .then(response => {
                        this.patients = response.data;
                        console.log(response.data);
                    })
                .catch(function (error) {
                    if (error.response) {
                    console.log(error.response.data);
                    } else if (error.request) {
                    console.log(error.request);
                    }
                    console.log("error.config");
                    console.log(error.config);
                });
            }
            else {
                this.refreshPatients();
            }
        },
        showModal(patient) {
          this.selectedPatient = patient;
          console.log(patient);
          this.loadPatientSubscriptions(patient.id);
          $('#appointmentsModal').modal();
          $('#appointmentsModal').modal('handleUpdate');
        },
        loadPatientSubscriptions(patientId) {
            AppointmentDataService.getAllUserPastAppointments(patientId) //this.id
                .then(response => {
                    console.log("Load Appointments");
                    console.log(response.data);

                    this.appointments = response.data;
                });
        },

        isCanceled(obj) {
          if (obj.examination != undefined){
            if (obj.examination.status == "CANCELED") {
              return true;
            }
          }
          return false;
        },

        showModalDetails(appointment) {
          this.selectedAppointment = appointment;
          $('#appointmentDetailsModal').modal();
        },

        filteredHistory() {
          let selected = this.historyFilter;
          var filteredList = this.appointments;

          // if (selected === "all")
          //   return filteredList;

          filteredList = filteredList.filter(appointment => appointment.type === selected)

          return filteredList;
        }
      },

      mounted() {
          this.refreshPatients();
      },

      created() {
          this.refreshPatients();
      }
}
</script>

<style scoped>
    .table {
        color: inherit;
        overflow: auto;
        position: relative;
    }
    .clickable {
        cursor: pointer;
    }
    .active {
        color: #42b983;
    }
    /*th {
        position: sticky;
        top: 0;
        color:  #007bff;
        text-decoration: none;
    }*/
</style>