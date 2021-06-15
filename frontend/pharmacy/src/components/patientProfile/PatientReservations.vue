<template>
<div>
  <RatingModal modalId="rating-modal" v-model="rating" @rated="rateMedicine"></RatingModal>

  <h3 style="padding:20px 0px">Reservations</h3>



<div class="row mb-3">
			<div class="col-md-12">
				<a class="btn btn-outline-secondary" data-toggle="collapse" href="#searchMedCollapse" role="button" aria-expanded="false" aria-controls="searchMedCollapse">
					Filter
				</a>
			</div>
	</div>
  <div class="searchForm collapse mb-2 bg-light" id="searchMedCollapse">
    <form v-on:submit.prevent="searchMedicines" class="p-3">
          <h4>Filter</h4>

        <div class="form-row">
            <div class="form-group col-md-4">
              <label for="inputMedName">Name</label>
              <input type="text" class="form-control" id="inputMedName" v-model="searchParams.name">
            </div>
            <div class="form-group col-md-3">
              <label for="inputPharmacy">Pharmacy</label>
              <input type="text" class="form-control" id="inputPharmacy" v-model="searchParams.pharmacy">
            </div>
            

            <div class="form-group col-md-2">
              <label for="inputMedPointsMin">Quantity</label>

              <div class="input-group">

                  <input type="number" class="form-control" id="inputMedPointsMin" min="0" :max="Math.min(999,searchParams.maxQuantity)" v-model="searchParams.minQuantity">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputMedPointsMax">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputMedPointsMax" :min="Math.max(0,searchParams.minQuantity)" v-model="searchParams.maxQuantity">
              </div>
            </div>
            <div class="form-group col-md-6">
              <label for="inputMedMinResDate">Reservation Date</label>

              <div class="input-group">

                  <input type="date" class="form-control" id="inputMedMinResDate" :max="searchParams.maxResDate" v-model="searchParams.minResDate">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputMedMaxResDate">-</span>
                  </div>
                  <input type="date" class="form-control" id="inputMedMaxResDate" :min="searchParams.minResDate" v-model="searchParams.maxResDate">
              </div>
            </div>
            
            <div class="form-group col-md-6">
              <label for="inputMedMinLastDate">Last Date</label>

              <div class="input-group">

                  <input type="date" class="form-control" id="inputMedMinLastDate" :max="searchParams.maxLastDate" v-model="searchParams.minLastDate">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputMedMaxResDate">-</span>
                  </div>
                  <input type="date" class="form-control" id="inputMedMaxLastDate" :min="searchParams.minLastDate" v-model="searchParams.maxLastDate">
              </div>
            </div>


        
                <div class="form-group col-md-3">
              <label for="sortMedSelect">Sort</label>
              <select class="form-control" id="sortMedSelect" v-model="searchParams.sort">
                <option value="NAME_ASC">By Name Asc</option>
                <option value="NAME_DES">By Name Des</option>
                <option value="RES_DATE_ASC">By Reserved Date Asc</option>
                <option value="RES_DATE_DES">By Reserved Date Des</option>
                <option value="LAST_DATE_ASC">By Last Date Asc</option>
                <option value="LAST_DATE_DES">By Last Date Des</option>
                <option value="QUANTITY_ASC">By Quantity Asc</option>
                <option value="QUANTITY_DES">By Quantity Des</option>
              </select>          
            </div>

                <div class="form-group col-md-3">

            <label for="btn-check-outlined">Only e-recepies</label><br>
            <input style="width:35px; height:35px;" type="checkbox" class="btn-check" id="btn-check-outlined" autocomplete="off" v-model="searchParams.onlye">

                </div>

        </div>
        <button type="submit" class="btn btn-primary">Search</button> 
        <a v-on:click="resetFormMedicines" class="ml-3 btn btn-warning">Reset</a> 

    </form>
  </div>




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
    import AuthService from '@/service/AuthService.js';

export default {
  props: ['userId'],

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
      },
      
      searchParams: {
        minQuantity: 0,
        maxQuantity: undefined,
      },

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

        resetFormMedicines() {
          this.searchParams = {
            minQuantity: 0,
            maxQuantity: undefined,
            minLastDate: undefined,

          }
        },
        searchMedicines() {

              PatientDataService.getPatientReservationsFilter(this.id, JSON.stringify(this.searchParams)) // HARDCODED
                .then(response => {

                    this.reservations = response.data;
                });
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
		  this.id = this.userId; 
	},
}
</script>