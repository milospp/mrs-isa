<template>
<div>
  <h3 class="mt-3">History Appointments</h3>

  <div class="radio-container">
    <div class="form-check form-check-inline btn-info">
      <input class="form-check-input" v-model="historyFilter" type="radio" name="inlineRadioOptions" id="examinations" value="EXAMINATION">
      <label class="form-check-label" for="examinations">Examinations</label>
    </div>
    <div class="form-check form-check-inline btn-info">
      <input class="form-check-input" v-model="historyFilter" type="radio" name="inlineRadioOptions" id="counseling" value="COUNSELING">
      <label class="form-check-label" for="counseling">Counseling</label>
    </div>
    <div class="form-check form-check-inline btn-info">
      <input class="form-check-input" v-model="historyFilter" type="radio" name="inlineRadioOptions" id="all" value="all" checked>
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
        <td>{{a.doctor.name}} {{a.doctor.surname}}</td>
        <td><router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: a.pharmacy.id  }}">{{a.pharmacy.name}}</router-link></td>
        <td>{{UtilService.formatDateTime(a.startTime)}}</td>
        <td>{{a.durationInMins}}min</td>
        <td>${{a.price}}</td>

        <td>
          
          <button class="btn btn-primary" v-on:click="cancelAppointment(a)">Details</button>
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
  }

  .radio-container input:checked + label {
    color: #fff;
    background-color: #0069d9;
    border-color: #0062cc;
  }
  
  .radio-container input {
    margin-left: 10px !important;
  }
</style>

<script>
    import AppointmentDataService from '@/service/AppointmentDataService.js';
    import UtilService from '@/service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      historyFilter: "all",
      appointments: [],
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
  },
  computed: {
    filteredHistory(){
      let selected = this.historyFilter;
      var filteredList = this.appointments;

      if (selected === "all")
        return filteredList;

      filteredList = filteredList.filter(appointment => appointment.type === selected)

      return filteredList;
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