<template>
<div>
  <RatingModal modalId="rating-modal" v-model="rating" @rated="rateMedicine"></RatingModal>

  <h3>Reservations</h3>
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
        <td>{{r.medicineInPharmacy.medicine.name}} {{UtilService.formatRatingBracket(r.medicineInPharmacy.medicine.rating)}}</td>
        <td>{{r.quantity}}</td>
        <td>{{UtilService.formatDate(r.reservationDate)}}</td>
        <td>{{UtilService.formatDate(r.lastDate)}}</td>
        <td>{{r.medicineInPharmacy.pharmacy.name}}</td>
        <!-- <td>{{UtilService.formatReservationStatus(r.status)}}</td> -->
        <!-- <td><input type="checkbox" name="taken" id="taken" v-bind:checked="r.status"></td> -->
        <!-- <td><input type="checkbox" name="canceled" id="canceled" v-bind:checked="r.canceled"></td> -->
        
        <td v-if="r.status == 0">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <span class="badge badge-primary">PENDING</span><br>
          <button v-bind:disabled="r.canceled" class="btn btn-danger" v-on:click="cancel(r)">Cancel</button>
        </td>
        <td v-if="r.status == 1">
          <!-- <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link> -->
          <button type="button" class="btn btn-primary" v-on:click="ratingModal(r)" data-toggle="modal" data-target="#rating-modal">
            {{getMyVote(r)>0 ? "Change Rate": "Rate"}}
          </button>
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
        
        <!-- <td v-if="r.status == 0">
          <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link>
        </td> -->
      </tr>
    </tbody>
  </table>
</div>
</template>

<script>
    import PatientDataService from '@/service/PatientDataService.js';
    import UtilService from '@/service/UtilService.js';
    import RatingModal from '@/components/RatingModal.vue';
    import MedicineDataService from '@/service/MedicineDataService.js';

export default {
    setup() {
      return { UtilService }
    },

  components: {
    RatingModal,
  },

	data: function () {
		return {
      reservations: {},
      rating: {
        title: "",
        body: "",
        ratingValue: "0",
        oldValue: null,
        medicine: null,
      }
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

        ratingModal(r) {
          this.rating.medicine = r.medicineInPharmacy.medicine;
          this.rating.title = "Medicine Rating"
          this.rating.body = "[" +r.medicineInPharmacy.medicine.code+ "] " + r.medicineInPharmacy.medicine.name + " - (" + r.medicineInPharmacy.medicine.manufacturer + ")";
          console.log (this.rating.body);

          MedicineDataService.getUserRating(this.id, r.medicineInPharmacy.medicine.id).then(response => {
            if (response.data) {
              this.rating.ratingValue = "" + response.data.rating;

              console.log(response.data);
              if (response.data.rating > 0)
                this.rating.oldValue = this.rating.ratingValue;
              else
                this.rating.oldValue = null;
              console.log( this.rating.oldValue);

            }
          });

        },

        cancel(obj) {
          PatientDataService.cancelReservation(obj.id).then( response => {
            obj.canceled = true;
            obj.status = 2;
            console.log("Success");
          });
          console.log(obj);
        },

        getMyVote(r){
          MedicineDataService.getUserRating(this.id, r.medicineInPharmacy.medicine.id).then(response => {
            if (response.data) {
              r.voted = response.data.rating
            }
          });
          return r.voted;
        },

        rateMedicine() {

          let medicine = this.rating.medicine;
          let rating = this.rating.ratingValue;

          if (rating < 1 || rating > 5) {
            alert("Wrong rate value");
            return
          }

          let rateObject = {
            user: this.id,
            rating: rating
          }
        
          MedicineDataService.rateMedicine(medicine, rateObject).then(response => {
            if (response.data) {
              this.loadPatientReservations();
              $("#rating-modal").modal('hide');
              alert("Successfully voted!");
            }
          });
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