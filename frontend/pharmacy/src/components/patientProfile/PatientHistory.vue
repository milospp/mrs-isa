<template>
<div>


    
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
            <td>{{selectedAppointment.doctor.name}} {{selectedAppointment.doctor.surname}} ({{selectedAppointment.doctor.rating}} / 5)</td>
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
        
        <div v-if="selectedAppointment.examination.status == 'HELD' && selectedAppointment.voted != null" class="rating-section">
          <hr>
          <h4>Rate Doctor: <strong>{{selectedAppointment.doctor.name}} {{selectedAppointment.doctor.surname}}</strong></h4>
          <RatingStars v-model="ratingValue" inputId="p-h-"/>
          <button  class="btn btn-primary" v-bind:disabled="!isRatingValid || selectedAppointment.voted==ratingValue" v-on:click="rateDoctor(selectedAppointment ,ratingValue)" >{{voteButtonText}} {{ratingBracketValue}}</button>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>




<!-- FILTER -->
<div class="row mb-3 mt-5">
    <div class="radio-container col-md-6">
    <div class="form-check form-check-inline btn-info">
      <input class="hidden-cb" v-model="historyFilter" type="radio" name="inlineRadioOptions" id="examinations" value="EXAMINATION">
      <label class="form-check-label" for="examinations">Examinations</label>
    </div>
    <div class="form-check form-check-inline btn-info">
      <input class="hidden-cb" v-model="historyFilter" type="radio" name="inlineRadioOptions" id="counseling" value="COUNSELING">
      <label class="form-check-label" for="counseling">Counseling</label>
    </div>
    <div class="form-check form-check-inline btn-info">
      <input class="hidden-cb" v-model="historyFilter" type="radio" name="inlineRadioOptions" id="all" value="all" checked>
      <label class="form-check-label" for="all">All</label>
    </div>
  </div>
			<div class="col-md-6">
				<a class="btn btn-primary float-right" data-toggle="collapse" href="#searchCollapse" role="button" aria-expanded="false" aria-controls="searchCollapse">
					Filter
				</a>
			</div>
	</div>
  <div class="searchForm collapse mb-2 bg-light" id="searchCollapse">
    <form v-on:submit.prevent="searchPastAppointments" class="p-3">
          <h4>Filter</h4>

        <div class="form-row">
            <div class="form-group col-md-4">
              <label for="inputDoctor">Doctor Name</label>
              <input type="text" class="form-control" id="inputDoctor" v-model="searchParams.doctorName">
            </div>
            <div class="form-group col-md-4">
              <label for="inputPharmacy">Pharmacy Name</label>
              <input type="text" class="form-control" id="inputPharmacy" v-model="searchParams.pharmacyName">
            </div>

            <div class="form-group col-md-4">
              <label for="inputPerscription">Status</label>
              <select class="form-control" id="inputPerscription" v-model="searchParams.status">
                <option value="ALL">All</option>
                <option value="HELD">Held</option>
                <option value="NOT_HELD">Not Held</option>
                <option value="CANCELED">Canceled</option>
              </select>          
            </div>

            <div class="form-group col-md-6">
              <label for="inputDuration">Start Time</label>

              <div class="input-group">

                  <input type="datetime-local" class="form-control" id="inputShape" min="0" :max="Math.min(9999,searchParams.endTime)" v-model="searchParams.startTime">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroupPrepend2">-</span>
                  </div>
                  <input type="datetime-local" class="form-control" id="inputShape" :min="Math.max(0,searchParams.startTime)" max="999" v-model="searchParams.endTime">
              </div>
            </div>



            <div class="form-group col-md-2">
              <label for="inputDuration">Duration</label>

              <div class="input-group">

                  <input type="number" class="form-control" id="inputDurationMin" min="0" :max="Math.min(9999,searchParams.maxDuration)" v-model="searchParams.minDuration">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputDuration">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputDurationMax" :min="Math.max(0,searchParams.minDuration)" max="9999" v-model="searchParams.maxDuration">
              </div>
            </div>

            <div class="form-group col-md-2">
              <label for="inputPrice">Price</label>

              <div class="input-group">

                  <input type="number" class="form-control" id="inputPriceMin" min="0" :max="searchParams.maxPrice" v-model="searchParams.minPrice">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputPrice">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputPriceMax" :min="searchParams.minPrice" v-model="searchParams.maxPrice">
              </div>
            </div>


            <div class="form-group col-md-2">
              <label for="sortSelect">Sort</label>
              <select class="form-control" id="sortSelect" v-model="searchParams.sort">
                <option value="DATE_ASC">By Date Asc</option>
                <option value="DATE_DES" selected>By Date Des</option>
                <option value="DOCTOR_ASC">By Doctor Asc</option>
                <option value="DOCTOR_DES">By Doctor Des</option>
                <option value="PHARMACY_ASC">By Pharmacy Asc</option>
                <option value="PHARMACY_DES">By Pharmacy Des</option>
                <option value="STATUS_ASC">By Status Asc</option>
                <option value="STATUS_DES">By Status Des</option>
                <option value="DURATION_ASC">By Duration Asc</option>
                <option value="DURATION_DES">By Duration Des</option>

                <option value="PRICE_ASC">By Price Asc</option>
                <option value="PRICE_DES">By Price Des</option>
              </select>          
            </div>

        </div>
        <button type="submit" class="btn btn-primary">Search</button> 
    </form>
  </div>
<!-- END OF FILTER -->

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
        <td>{{a.doctor.name}} {{a.doctor.surname}} ({{a.doctor.rating}} / 5)</td>
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
  .hidden-cb  {
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
    /* margin: 3rem 0; */
    
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
    import AuthService from '@/service/AuthService.js';
    import $ from 'jquery';


export default {
  props: ['userId'],
  
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
      searchParams: {},
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
          this.ratingValue = "0";  
        
          PatientDataService.getUserRating(appointment.examination.patient, appointment.doctor).then(response => {
            if (response.data) {
              this.ratingValue = "" + response.data.rating;
              appointment.voted = response.data.rating;
            }
          });

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
              this.loadPatientSubscriptions();
              alert("Successfully voted!");
            }
          });
        },

        searchPastAppointments() {
          AppointmentDataService.searchAllUserPastAppointments(this.id, JSON.stringify(this.searchParams)) // HARDCODED
                .then(response => {
                    this.appointments = response.data;
                });
        },


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
    },
    voteButtonText() {
      if (this.selectedAppointment.voted <= 0)
        return "Submit Vote";

      if (this.selectedAppointment.voted == this.ratingValue){
        return "Already voted";
      }
      
      return "Resubmit Vote";

    },

        ratingBracketValue(){
            if (this.ratingValue>0) return " ( "+ this.ratingValue +" )";
            return "";
        }
  },

  mounted() {
      this.loadPatientSubscriptions();
  },
	created() {
		  this.id = this.userId; 

	},
}
</script>