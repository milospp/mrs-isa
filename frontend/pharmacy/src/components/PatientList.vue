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
                <table id="patientsTable" class="table table-hover table-striped box-shadow"><!-- data-toggle="table" ;;;; table-sortable sortable
  data-height="460"
  data-url="json/data1.json" -->
                    <thead class="card-header">
                      <tr>
                        <th id="name" >
                          First name
                          <button style="border: none; background-color: inherit;" v-on:click="changeOrder('name')">
                            <span v-if="reverse === -1">△</span>
                            <span v-if="reverse === 1">▽</span>
                          </button>
                        </th> <!--data-type="string" data-field="name" data-sortable="true" ;;; data-field="name" data-sortable="true"-->
                        <th id="surname">
                          Last name
                          <button style="border: none; background-color: inherit;" v-on:click="changeOrder('surname')">
                            <span v-if="reverse === -1">△</span>
                            <span v-if="reverse === 1">▽</span>
                          </button>
                        </th>
                        <th id="address">Address</th>
                        <th>Phone number</th>
                        <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr :key="p.username" v-for="p in patients" v-on:dblclick="patientInfo(Object.values(p))" class="clickable" data-index="{{p.id}}">
                            <td scope="row">{{p.name}}</td>
                            <td>{{p.surname}}</td>
                            <td>{{UtilService.AddressToString(p.address)}}</td>
                            <td>{{p.phoneNumber}}</td>
                            <td>
                                <button class="btn btn-primary" v-on:click="showModal(p)">Examination history</button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <!-- <nav aria-label="Page navigation">
                  <ul class="pagination justify-content-center">
                    <li class="page-item" id="previous">
                      <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                      </a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item">
                      <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                      </a>
                    </li>
                  </ul>
                </nav> -->


                <!-- <div id="page-selection">Pagination goes here</div> -->

                <pagination v-model="currentPage" :records="totalPatients" :per-page="perPage" @paginate="refreshPatients($event)"/>

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
</template>

<script>
import PatientDataService from '../service/PatientDataService.js';
import UtilService from '../service/UtilService.js';
import AppointmentDataService from '@/service/AppointmentDataService.js';
import $ from 'jquery';
//import 'bootpag/lib/jquery.bootpag.min';
import Pagination from 'v-pagination-3';

export default {
    name: 'PatientList',
    setup() {
      return {UtilService}
    },
    components: {
        //ExamHistory
        Pagination
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

            currentPage: 1,
            perPage: 2,
            totalPatients: 6, // hardcoded
            sortBy: 'name',
            doctorId: 2,

            reverse: 1,

        };
    },
    methods: {
        refreshPatients(e) {
          console.log(this.currentPage-1);
          if (!this.currentPage) this.currentPage = 0;
          console.log(this.currentPage);
            PatientDataService.retrieveAllPatients(this.currentPage - 1, this.perPage, this.sortBy, this.doctorId, this.reverse) // HARDCODED
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
        },

        changeOrder(sort) {
          this.reverse = this.reverse * -1;
          this.sortBy = sort;
          this.refreshPatients();
          // var newItems = self.patients.slice().sort(function (a, b) { 
          //   var result;
          //   if (a.name < b.name) {
          //     result = 1
          //   }
          //   else if (a.name > b.name) {
          //     result = -1
          //   }
          //   else {
          //     result = 0
          //   }
          //   return result * self.reverse
          // })
          // newItems.forEach(function (item, index) {
          //   item.position = index;
          // });
        }
      },

      mounted() {
          //this.refreshPatients(0, 1, 'name', 2);
          this.refreshPatients();
      },

      created() {
        this.refreshPatients();
          //this.refreshPatients(0, 1, 'name', 2);
          // $(document).ready(function () {
          //     $('#patientsTable').DataTable();
          //     $('.dataTables_length').addClass('bs-select');
          // });
          //$('patientsTable').DataTable();

          // init bootpag
        // $('#page-selection').bootpag({
        //     total: 2
        // }).on("page", function(event, /* page number here */ num){
        //      this.refreshPatients(num, 1, 'name', 2); // some ajax content loading...
        //      $(this).bootpag({total: 10, maxVisible: 10});
        // });
//         $('#page-selection').bootpag({
//        total: 53,
//        page: 2,
//        maxVisible: 5,
//        leaps: true,
//        firstLastUse: true,
//        first: '←',
//        last: '→',
//        wrapClass: 'pagination',
//        activeClass: 'active',
//        disabledClass: 'disabled',
//        nextClass: 'next',
//        prevClass: 'prev',
//        lastClass: 'last',
//        firstClass: 'first'
// }).on('page', function(event, num)
// {
//      this.refreshPatients(num, 1, 'name', 2); // some ajax content loading...
// });
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
    /* table.dataTable thead .sorting:after,
table.dataTable thead .sorting:before,
table.dataTable thead .sorting_asc:after,
table.dataTable thead .sorting_asc:before,
table.dataTable thead .sorting_asc_disabled:after,
table.dataTable thead .sorting_asc_disabled:before,
table.dataTable thead .sorting_desc:after,
table.dataTable thead .sorting_desc:before,
table.dataTable thead .sorting_desc_disabled:after,
table.dataTable thead .sorting_desc_disabled:before {
bottom: .5em;
} */
.table-sortable > thead > tr > th {
    cursor: pointer;
    position: relative;
}

.table-sortable > thead > tr > th:after,
.table-sortable > thead > tr > th:after,
.table-sortable > thead > tr > th:after {
    content: ' ';
    position: absolute;
    height: 0;
    width: 0;
    right: 10px;
    top: 16px;
}
.table-sortable > thead > tr > th:after {
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-top: 5px solid #ccc;
    border-bottom: 0px solid transparent;
}

.table-sortable > thead > tr > th:hover:after {
    border-top: 5px solid #888;
}

.table-sortable > thead > tr > th.asc:after {
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-top: 0px solid transparent;
    border-bottom: 5px solid #333;
}
.table-sortable > thead > tr > th.asc:hover:after {
    border-bottom: 5px solid #888;
}

.table-sortable > thead > tr > th.desc:after {    
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-top: 5px solid #333;
    border-bottom: 5px solid transparent;
}
</style>