<template>
<div>
  <h3 class="mt-3">Dermatologist Appointments</h3>
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
      <tr class="v-middle" v-for="a in appointments" v-bind:style="{ background: isCanceled(a) ? '#aaa' : ''}">
        <td v-bind:class="{ 'badge-info': a.type == 'COUNSELING', 'badge-primary': a.type == 'EXAMINATION' }" class="badge">{{a.type}}
          <br><span v-if="isCanceled(a)" class="badge badge-danger">CALCELED</span>
        </td>
        <td>{{a.doctor.name}} {{a.doctor.surname}}</td>
        <td><router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: a.pharmacy.id  }}">{{a.pharmacy.name}}</router-link></td>
        <td>{{UtilService.formatDateTime(a.startTime)}}</td>
        <td>{{a.durationInMins}}min</td>
        <td>${{a.price}}</td>

        <td>
          
          <button v-bind:disabled="isCanceled(a)" class="btn btn-danger" v-on:click="cancelAppointment(a)">Cancel</button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</template>
<style>
  .v-middle td {
    vertical-align: baseline !important;
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
      appointments: {},
		}
	},
  methods: {
        loadPatientSubscriptions() {
            AppointmentDataService.getAllUpcomingAppointments(this.id)
                .then(response => {
                    console.log("Load Appointments");
                    console.log(response.data);

                    this.appointments = response.data;
                });
        },

        cancelAppointment(obj) {
            AppointmentDataService.cancelAppointment(this.id, obj.id)
                .then(response => {
                    console.log("Cancel " + obj.id);
                    obj.examination.status = "CANCELED";
                    //this.subscriptions = response.data;
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
    mounted() {
        this.loadPatientSubscriptions();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>