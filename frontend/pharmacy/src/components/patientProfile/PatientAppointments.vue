<template>

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
      <tr>
        <td class="badge badge-primary">Examination</td>
        <td>Johm</td>
        <td>Pharm</td>
        <td>22/1/2020 22:22</td>
        <td>39 min</td>
        <td>$ 200.00</td>

        <td>
          <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage'}">View</router-link>
          <button v-bind:disabled="true" class="btn btn-danger" v-on:cancelAppointment="unsubscribe()">Cancel</button>
        </td>
      </tr>
      <tr>
        <td class="badge badge-primary">Examination</td>
        <td>Johm</td>
        <td>Pharm</td>
        <td>22/1/2020 22:22</td>
        <td>39 min</td>
        <td>$ 200.00</td>

        <td>
          <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage'}">View</router-link>
          <button v-bind:disabled="true" class="btn btn-danger" v-on:cancelAppointment="unsubscribe()">Cancel</button>
        </td>
      </tr>
      <tr class="v-middle">
        <td class="badge badge-primary">Examination</td>
        <td>Johm</td>
        <td>Pharm</td>
        <td>22/1/2020 22:22</td>
        <td>39 min</td>
        <td>$ 200.00</td>

        <td>
          <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage'}">View</router-link>
          <button v-bind:disabled="true" class="btn btn-danger" v-on:cancelAppointment="unsubscribe()">Cancel</button>
        </td>
      </tr>
<!--       
      <tr class="v-middle" v-for="s in subscriptions" v-bind:style="{ background: s.canceled ? '#aaa' : ''}">
        <td>{{s.name}}</td>
        <td>{{s.description}}</td>
        <td>{{UtilService.AddressToString(s.address)}}</td>

        <td>
          <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link>
          <button v-bind:disabled="s.canceled" class="btn btn-danger" v-on:click="unsubscribe(s)">Unsubscribe</button>
        </td>
      </tr> -->
    </tbody>
  </table>


  <h3 class="mt-5">Consulting Appointments</h3>

</template>

<style>
  .v-middle td {
    vertical-align: baseline !important;
  }
</style>

<script>
    import PatientDataService from '@/service/PatientDataService.js';
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
            PatientDataService.getPatientAppointments(this.id) // HARDCODED
                .then(response => {
                    console.log("Load Appointments");
                    console.log(response.data);

                    this.subscriptions = response.data;
                });
        },

        cancelAppointment(obj) {
            PatientDataService.cancelAppointment(this.id, obj.id)
                .then(response => {
                    console.log("Cancel " + obj.id);
                    obj.canceled = true;
                    //this.subscriptions = response.data;
                });
        }


    },
    mounted() {
        // this.loadPatientSubscriptions();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>