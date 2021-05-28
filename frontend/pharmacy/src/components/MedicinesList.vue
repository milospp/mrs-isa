<template>

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
            <div class="form-group col-md-4">
              <label for="inputMedStructure">Structure</label>
              <input type="text" class="form-control" id="inputMedStructure" v-model="searchParams.structure">
            </div>
            <div class="form-group col-md-4">
              <label for="inputMedManufacturer">Manufacturer</label>
              <input type="text" class="form-control" id="inputMedManufacturer" v-model="searchParams.manufacturer">
            </div>

            <div class="form-group col-md-3">
              <label for="inputMedShape">Shape</label>
              <input type="text" class="form-control" id="inputMedShape" v-model="searchParams.shape">
            </div>



            <div class="form-group col-md-3">
              <label for="inputMedType">Type</label>
              <input type="text" class="form-control" id="inputMedType" v-model="searchParams.type">
            </div>

            <div class="form-group col-md-2">
              <label for="inputMedPerscription">Perscription</label>
              <select class="form-control" id="inputMedPerscription" v-model="searchParams.perscription">
                <option value="ALL">All</option>
                <option value="WITH_RECEIPT">With Receipt</option>
                <option value="WITHOUT_RECEIPT">Without Receipt</option>
              </select>          
            </div>

            <div class="form-group col-md-2">
              <label for="inputMedPointsMin">Points</label>

              <div class="input-group">

                  <input type="number" class="form-control" id="inputMedPointsMin" min="0" :max="Math.min(999,searchParams.maxPoints)" v-model="searchParams.minPoints">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputMedPointsMax">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputMedPointsMax" :min="Math.max(0,searchParams.minPoints)" max="999" v-model="searchParams.maxPoints">
              </div>
            </div>

            <div class="form-group col-md-2">
              <label for="inputMedMinRating">Rating</label>

              <div class="input-group">

                  <input type="number" class="form-control" id="inputMedMinRating" min="0" :max="Math.min(5,searchParams.maxRating)" v-model="searchParams.minRating">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputMedMaxRating">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputMedMaxRating" :min="Math.max(0,searchParams.minRating)" max="5" v-model="searchParams.maxRating">
              </div>
            </div>


            <div class="form-group col-md-2">
              <label for="sortMedSelect">Sort</label>
              <select class="form-control" id="sortMedSelect" v-model="searchParams.sort">
                <option value="NAME_ASC">By Name Asc</option>
                <option value="NAME_DES">By Name Des</option>
                <option value="SHAPE_ASC">By Shape Asc</option>
                <option value="SHAPE_DES">By Shape Des</option>
                <option value="TYPE_ASC">By Type Asc</option>
                <option value="TYPE_DES">By Type Des</option>
                <option value="POINTS_ASC">By Points Asc</option>
                <option value="POINTS_DES">By Points Des</option>
                <option value="RAING_ASC">By Rating Asc</option>
                <option value="RAING_DES">By Rating Des</option>
              </select>          
            </div>

        </div>
        <button type="submit" class="btn btn-primary">Search</button> 
        <a v-on:click="resetFormMedicines" class="ml-3 btn btn-warning">Reset</a> 

    </form>
  </div>
  
  <div class="modal fade" id="specsView" tabindex="-1" role="dialog" aria-labelledby="specsView" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Specification of {{this.viewedMedicine.name}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div>
                    <table class="table table-striped">
                        <tbody>
                            <tr><td>Structure: </td><td>{{this.viewedMedicine.specification.structure}}</td></tr>
                            <tr><td>Daily intake: </td><td>{{this.viewedMedicine.specification.dailyIntake}}</td></tr>
                            <tr><td>Side effects: </td><td>{{this.viewedMedicine.specification.sideEffects}}</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

  <reserve-med-modal v-if="userId && userId.role == 'PATIENT'" modalId="reserve-med-modal" v-model="selectedMed" :patientId="userId.id" @rated="reserveMedicine"></reserve-med-modal>


  <div class="row">

    <div class="col-6 col-lg-2 col-md-3 py-2 card-group" v-for="m in medicinesSlice">
      <div class="card">
        <a href="#" v-on:click="fetchMedicine(m)" data-toggle="modal" data-target="#specsView">
          <img class="card-img-top pl-4 pr-4 pt-2" src="@/assets/medicinelogo.png" alt="Card image cap">
        </a>
        <div class="card-body">
          <h5 class="card-title mb-0">{{m.name}}</h5>
          <div class="rating mb-3">
            <span v-for="i in m.rating">★</span>
          </div>
          <p class="card-text">{{m.type}}</p>
        </div>
        <!-- <ul class="list-group list-group-flush">
          <li class="list-group-item">{{m.manufacturer}}</li>
        
        </ul> -->

        <div class="card-footer">
            <button type="button" class="btn btn-block btn-outline-primary" v-on:click="fetchMedicine(m)" data-toggle="modal" data-target="#specsView">View specification</button>
            <button v-if="userId && userId.role == 'PATIENT'" type="button" class="btn btn-block btn-outline-primary" v-on:click="openReservationModal(m)" data-toggle="modal" data-target="#reserve-med-modal">Reserve</button>
          
        </div>
      </div>
    </div>



<!-- 
    <div class="col-md-4" v-for="m in medicinesSlice">
      <div class="card mb-4 box-shadow">
        <div class="card-header">
            <h4 class="my-0 font-weight-normal">{{m.name}}</h4><kbd>{{m.code}}</kbd>
          </div>
        <div class="card-body">
          <h6>{{m.type}}</h6>
          <h6>{{m.shape}}</h6>
          <h6>{{m.manufacturer}}</h6>
          <p class="card-text">
            {{m.note}}
          </p> 
          
          <div class="d-flex justify-content-between align-items-center">
            <button type="button" class="btn btn-primary" v-on:click="fetchMedicine(m)" data-toggle="modal" data-target="#specsView">View specification</button>
            <button v-if="userId && userId.role == 'PATIENT'" type="button" class="btn btn-primary" v-on:click="openReservationModal(m)" data-toggle="modal" data-target="#reserve-med-modal">Reserve</button>
          </div>
          
        </div>
      </div>
    </div> -->

    
  </div>
</template>

<script>
import MedicineDataService from '../service/MedicineDataService.js';
import AuthService from '../service/AuthService.js';
import ReserveMedModal from '@/components/ReserveMedModal.vue';



export default {
    name: 'MedicinesList',
    components: {
      ReserveMedModal
    },
    data() {
        return {
            medicines: [],
            message: null,
            searchParams: {
              minPoints: 0,
              maxPoints: 999,
              minRating: 0,
              maxRating: 5,
              userId: null,
            },
            selectedMed: null,
            viewedMedicine: { specification: {structure:"", dailyIntake:"", sideEffects:""}},
            // limit: 0,
        };
    },
    props: ['limit'],
    computed:{
      medicinesSlice(){
        return this.limit ? this.medicines.slice(0,this.limit) : this.medicines
      },
    },

    methods: {
        getMedicines() {
          MedicineDataService.getAllMedicines() // HARDCODED
                .then(response => {
                    this.medicines = response.data;
                    console.log(response.data);
                });
        },
        searchMedicines() {
          MedicineDataService.searchMedicines(JSON.stringify(this.searchParams))
                .then(response => {
                    this.medicines = response.data;
                });
        },
        fetchMedicine(medicine){
          this.viewedMedicine = medicine;
        },

        openReservationModal(med){
          this.selectedMed = med;
        },

        resetFormMedicines() {
          this.searchParams = {
              minPoints: 0,
              maxPoints: 999,
              minRating: 0,
              maxRating: 5,
              userId: null,
          }
        },

    },
    mounted() {
        this.getMedicines();
    },
    created() {
        this.userId = AuthService.getCurrentUser();
    }
}
</script>