<template>
    <div class="container">
        <div class="row">
            <div class="container" style="padding: 0px;">
                <form v-on:submit.prevent="searchPatients(event)" style="float: right;">
                    <div class="form-row form-inline mb-2" >
                      <div class="form-group col-auto">
                        <label for="sortSelect" class="mx-4">Sort</label>
                        <select class="form-control" id="sortSelect" v-model="sortParams" @change="sort()">
                          <option :key="sortOption.desc" :value="sortOption"  v-for="sortOption in sortOptions">{{sortOption.desc}}</option>
                        </select>       
                      </div>
                      <div class="form-group col-auto">
                      </div>
                      <div class="form-group col-auto">
                          <input type="text" class="form-control" id="nameInput" placeholder="First Name">
                      </div>
                      <div class="form-group col-auto">
                          <input type="text" class="form-control" id="surnameInput" placeholder="Last Name">
                      </div>
                      <div class="col-auto">
                          <button type="submit" class="btn btn-primary">Search</button>
                      </div>
                    </div>
                </form>
            </div>
            <div class="container" style="padding: 0px;">
                <table style="text-align: left; table-layout: fixed;" id="patientsTable" class="table table-hover table-striped box-shadow"><!-- data-toggle="table" ;;;; table-sortable sortable
  data-height="460"
  data-url="json/data1.json" -->
                    <thead class="card-header">
                      <tr>
                        <th id="name" >
                          First name
                        </th> <!--data-type="string" data-field="name" data-sortable="true" ;;; data-field="name" data-sortable="true"-->
                        <th id="surname">
                          Last name
                        </th>
                        <th id="address">Address</th>
                        <th>Phone number</th>
                        <th>Last Appointment</th>
                        <th></th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                        <tr :key="p.id" v-for="p in patients" v-on:dblclick="patientInfo(Object.values(p))" class="clickable" data-index="{{p.id}}">
                            <td scope="row">{{p.name}}</td>
                            
                            <td>{{p.surname}}</td>
                            <!-- <td>{{UtilService.formatDateTime(p.lastAppointmentDate)}}</td> -->
                            <td>{{UtilService.AddressToString(p.address)}}</td>
                            <td>{{p.phoneNumber}}</td>
                            <td>{{p.last}}</td>
                            <td>
                                <button class="btn btn-secondary" v-on:click="showModal(p)">Examination history</button>
                            </td>
                            <td> <!-- UtilService.isTimeForAppointment(p.lastDate.startTime, p.lastDate.durationInMins) && -->
                                <button v-if="UtilService.isTimeForAppointment(p.last) && p.status == 'PENDING'" class="btn btn-primary" v-on:click="startAppointment(p.aid)">Start 
                                  <span v-if="role == 'DERMATOLOGIST'">Examination</span>
                                  <span v-else>Counseling</span>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div class="d-flex row">
                  <div class="col-sm">
                  <label for="pageSizeSelect" class="mt-2 mr-2">Show per page:</label>
                  <select id="pageSizeSelect" v-model="refreshData.pageSize" class="form-select mr-5" aria-label="Default select example" @change="onChange($event)">
                    <option selected :value="2">2</option>
                    <option :value="5">5</option>
                    <option :value="10">10</option>
                    <option :value="15">15</option>
                    <option :value="20">20</option>
                  </select>
                  </div>
                  <div class="col-xl">
                  <pagination v-model="refreshData.pageNo" :records="totalPatients" :per-page="refreshData.pageSize" @paginate="refreshPatients()"/>
                  </div>
                </div>
                

            </div>
        </div>
        <!--MODALS-->
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
                      <tr v-for="t in selectedAppointment.examination.therapy" :key="t">
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
</template>

<script>
import PatientDataService from '../service/PatientDataService.js';
import UtilService from '../service/UtilService.js';
import AppointmentDataService from '@/service/AppointmentDataService.js';
import $ from 'jquery';
import Pagination from 'v-pagination-3';
import AuthService from '../service/AuthService.js';

export default {
    name: 'PatientList',
    setup() {
      return {UtilService}
    },
    components: {
        Pagination
    },
    data() {
        return {
          role: "",
            patients: [],
            message: null,
            lastAppointmentDates: [],

            selectedPatient: null,
            historyFilter: "all",
            appointments: [],
            selectedAppointment: null,

            totalPatients: 0,
            refreshData: {
              pageNo: 1,
              pageSize: 2,
              sortBy: 'id',
              searchParams: {
                doctorId: 0,
                name: "",
                surname: ""
              },
              ascending: true
            },
            sortParams: { sortBy: 'id', ascending: true, desc: 'By Default' },

            sortOptions: [
              { sortBy: 'id', ascending: true, desc: 'By Default' },
              { sortBy: 'name', ascending: true, desc: 'By First Name Asc' },
              { sortBy: 'name', ascending: false, desc: 'By First Name Desc' },
              { sortBy: 'surname', ascending: true, desc: 'By Last Name Asc' },
              { sortBy: 'surname', ascending: false, desc: 'By Last Name Desc' },
              { sortBy: 'last', ascending: true, desc: 'By Last Appointment Asc' },
              { sortBy: 'last', ascending: false, desc: 'By Last Appointment Desc' }
            ],

        };
    },
    methods: {
        refreshPatients() {
          this.refreshData.searchParams.doctorId = AuthService.getCurrentUser().id;
          console.log("page size: ", this.refreshData.pageSize);
          if (!this.refreshData.pageNo) this.refreshData.pageNo = 1;

          console.log(this.sortParams.sortBy);
        
          console.log(this.refreshData.pageNo);
          PatientDataService.retrieveAllPatients(this.refreshData) // HARDCODED
              .then(response => {
                  this.patients = response.data.patients;
                  //this.lastAppointments = response.data.lastAppointmentsByDoctor;
                  this.totalPatients = response.data.count;
                  //this.addDatesToPatients();
                  console.log(response.data);
              });
        },
        addDatesToPatients(){
          for (let i = 0; i < this.patients.length; i++){
            this.patients[i].lastDate = this.lastAppointments[i];
          }
        },
        patientInfo(patient) {
            alert(patient);
        },
        searchPatients(e) {
          this.refreshData.searchParams.name = $('#nameInput').val();
          console.log($('#nameInput').val());
          this.refreshData.searchParams.surname = $('#surnameInput').val();
          console.log($('#surnameInput').val());
          this.refreshData.pageNo = 1;
          this.refreshPatients();
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
        onChange(e){
          this.refreshData.pageNo = 1;
          this.refreshPatients(e);
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

          filteredList = filteredList.filter(appointment => appointment.type === selected)

          return filteredList;
        },

        sort() {
          this.refreshData.ascending = this.sortParams.ascending;
          this.refreshData.sortBy = this.sortParams.sortBy;
          this.refreshData.pageNo = 1;
          this.refreshPatients();
        },

        startAppointment(lastAppointmentId){
          this.$router.push('/appointment/'+lastAppointmentId);
        }
      },

      mounted() {
          this.refreshData.searchParams.doctorId = AuthService.getCurrentUser().id;
          this.role = AuthService.getCurrentUser().role;
          this.refreshPatients();

      },

      created() {
        this.refreshData.searchParams.doctorId = AuthService.getCurrentUser().id;
        console.log(this.refreshData.searchParams.doctorId);
        this.role = AuthService.getCurrentUser().role;
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
    select{
      height: 38px;
    }
</style>