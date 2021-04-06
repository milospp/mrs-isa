<template>
<div>
  <h3>Subscription</h3>
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Medicine Code</th>
        <th>Medicine Name</th>
        <th>Quantity</th>
        <th>Reservation Date</th>
        <th>Last Date</th>
        <th>Pharmacy</th>
        <th>Taken</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="r in reservations" v-bind:key="r.id" v-bind:style="{ background: r.canceled ? '#aaa' : ''}">
        <td>{{r.code}}</td>
        <td>{{r.medicine.name}}</td>
        <td>{{r.quantity}}</td>
        <td>{{UtilService.formatDate(r.reservationDate)}}</td>
        <td>{{UtilService.formatDate(r.lastDate)}}</td>
        <td>{{r.pharmacy.name}}</td>
        <td>{{r.pharmacy.name}}</td>
        <td><input type="checkbox" name="taken" id="taken" v-bind:checked="r.taken"></td>
        <!-- <td><input type="checkbox" name="canceled" id="canceled" v-bind:checked="r.canceled"></td> -->
        
        <td>
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <button v-bind:disabled="s.unsubscribed" class="btn btn-danger" v-on:click="unsubscribe(s)">Cancel</button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</template>

<script>
    import PatientDataService from '@/service/PatientDataService.js';
    import UtilService from '@/service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      reservations: {},
		}
	},
  methods: {
        loadPatientReservations() {
            PatientDataService.getPatientReservations(this.id) // HARDCODED
                .then(response => {
                    console.log("Load subscriptions");
                    console.log(response.data);

                    this.subscriptions = response.data;
                });
        },

        cancel(obj) {
            console.log(obj);
        },


    },
    mounted() {
        this.loadPatientReservations();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>