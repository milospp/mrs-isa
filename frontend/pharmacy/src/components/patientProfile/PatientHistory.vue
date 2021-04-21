<template>
<div>
  <h3 class="mt-3">History Appointments</h3>


    
<!-- Button trigger modal -->
<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#appointmentModal">
  Launch demo modal
</button> -->

<!-- Modal -->
<div class="modal fade" id="appointmentModal" tabindex="-1" role="dialog" aria-labelledby="appointmentModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div v-if="selectedAppointment" class="modal-content">
      <div class="modal-header">
        <span v-bind:class="{ 'badge-info': selectedAppointment.type == 'COUNSELING', 'badge-primary': selectedAppointment.type == 'EXAMINATION' }" class="badge">{{selectedAppointment.type}}
        <span v-if="false" class="badge badge-danger">CALCELED</span>
        </span>
        <h5 class="modal-title" id="appointmentModalLabel">Appointment</h5>
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
            <td>{{selectedAppointment.doctor.name}} {{selectedAppointment.doctor.surname}} ({{selectedAppointment.doctor.rating}})</td>
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
              <th>Quantity</th>
            </tr>
            <tr v-for="v,k in selectedAppointment.examination.therapy">
              <td>{{k}}</td>
              <!-- <td>m.name</td> -->
              <td>{{v}}</td>
            </tr>
          </thead>
        </table>
        
        <hr>
        <h2>Rate doctor {{ratingValue}}</h2>
        <RatingStars v-model="ratingValue" />
        <button  class="btn btn-primary" v-bind:disabled="!isRatingValid" v-on:click="rateDoctor(selectedAppointment ,ratingValue)" >{{selectedAppointment.voted > 0? "Resubmit Vote": "Submit Vote"}}</button>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>


  <div class="radio-container">
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
  </div>
  <table class="table table-striped">
    <thead>
      <tr>
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
      <tr class="v-middle" v-for="a in filteredHistory" v-bind:style="{ background: isCanceled(a) ? '#aaa' : ''}">
        <td v-bind:class="{ 'badge-info': a.type == 'COUNSELING', 'badge-primary': a.type == 'EXAMINATION' }" class="badge">{{a.type}}
          <br><span v-if="isCanceled(a)" class="badge badge-danger">CALCELED</span>
        </td>
        <td>{{a.doctor.name}} {{a.doctor.surname}} ({{a.doctor.rating}})</td>
        <td><router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: a.pharmacy.id  }}">{{a.pharmacy.name}}</router-link></td>
        <td>{{UtilService.formatDateTime(a.startTime)}}</td>
        <td>{{a.durationInMins}}min</td>
        <td>${{a.price}}</td>

        <td>
          
          <button class="btn btn-primary" v-on:click="showModal(a)">Details</button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</template>
<style>
  [type="checkbox"]:checked, 
  [type="checkbox"]:not(:checked), 
  [type="radio"]:checked, 
  [type="radio"]:not(:checked) {
    position: absolute;
    left: -9999px;
    width: 0;
    height: 0;
    visibility: hidden;
  }

  .v-middle td {
    vertical-align: baseline !important;
  }

  .radio-container {
    transition: 1s ease-in-out all;
    margin: 3rem 0;
    
    font-weight: bold;
  }

  .radio-container .btn-info {
    border-radius: 0.25rem;
  }

  .radio-container label {
    padding: .5rem 1rem;
    user-select: none;

  }

  .radio-container input:checked + label {
    color: #fff;
    background-color: #0069d9;
    border-color: #0062cc;
    border-radius: 0.25rem;
  }
  
  .radio-container input {
    margin-left: 10px !important;
  }
</style>

<script>
    import AppointmentDataService from '@/service/AppointmentDataService.js';
    import PatientDataService from '@/service/PatientDataService.js';
    import UtilService from '@/service/UtilService.js';
    import RatingStars from '@/components/FiveStars.vue';
    import $ from 'jquery';


export default {
  setup() {
    return { UtilService }
  },

  components: {
    RatingStars,
  },

	data: function () {
		return {
      historyFilter: "all",
      appointments: [],
      selectedAppointment: null,
      ratingValue: null,
		}
	},
  methods: {
        loadPatientSubscriptions() {
            AppointmentDataService.getAllUserPastAppointments(this.id)
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

        showModal(appointment) {
          this.selectedAppointment = appointment;
          $('#appointmentModal').modal();
        },

        rateDoctor(appointment, rating) {
          if (rating < 1 || rating > 5) {
            alert("Wrong rate value");
            return
          }
          appointment.voted = rating;

          let rateObject = {
            user: appointment.examination.patient.id,
            rating: rating
          }
        
          PatientDataService.rateDoctor(appointment.doctor, rateObject).then(response => {
            if (response.data) {
              alert("Successfully voted!");
            }
          });
        }
  },
  computed: {
    filteredHistory(){
      let selected = this.historyFilter;
      var filteredList = this.appointments;

      if (selected === "all")
        return filteredList;

      filteredList = filteredList.filter(appointment => appointment.type === selected)

      return filteredList;
    },
    isRatingValid(){
      if (this.ratingValue < 1 || this.ratingValue > 5)
        return false;
      else return true;
    }
  },

  mounted() {
      this.loadPatientSubscriptions();
  },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>