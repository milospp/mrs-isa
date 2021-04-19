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
        <th>Status</th>
        <!-- <th></th> -->
      </tr>
    </thead>
    <tbody>
      <tr v-for="r in reservations" v-bind:key="r.id" v-bind:style="{ background: r.canceled ? '#aaa' : ''}">
        <td>{{r.code}}</td>
        <td>{{r.medicineInPharmacy.medicine.name}}</td>
        <td>{{r.quantity}}</td>
        <td>{{UtilService.formatDate(r.reservationDate)}}</td>
        <td>{{UtilService.formatDate(r.lastDate)}}</td>
        <td>{{r.medicineInPharmacy.pharmacy.name}}</td>
        <!-- <td>{{UtilService.formatReservationStatus(r.status)}}</td> -->
        <!-- <td><input type="checkbox" name="taken" id="taken" v-bind:checked="r.status"></td> -->
        <!-- <td><input type="checkbox" name="canceled" id="canceled" v-bind:checked="r.canceled"></td> -->
        
        <td v-if="r.status == 0">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <span class="badge badge-primary">PENDING</span>
        </td>
        <td v-if="r.status == 1">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <span class="badge badge-default">TAKEN</span>
        </td>
        <td v-if="r.status == 2">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <span class="badge badge-warning">CANCELED</span>
        </td>
        <td v-if="r.status == 3">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <span class="badge badge-danger">EXPIRED</span>
        </td>
        
        <td v-if="r.status != 2">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <button v-bind:disabled="r.canceled" class="btn btn-danger" v-on:click="cancel(r)">Cancel</button>
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
                    console.log("Load RESERVAIONS");
                    console.log(response.data);

                    this.reservations = response.data;
                });
        },

        cancel(obj) {
          PatientDataService.cancelReservation(obj.id).then( response => {
            obj.canceled = true;
            console.log("Success");
          });
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