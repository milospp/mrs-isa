<template>
  <div class="row">
    <h3>All Medicines</h3>
  </div>


	<div class="row mb-3">
			<div class="col-md-12">
				<a class="btn btn-primary float-right" data-toggle="collapse" href="#searchCollapse" role="button" aria-expanded="false" aria-controls="searchCollapse">
					Filter
				</a>
			</div>
	</div>
  <div class="searchForm collapse mb-2 bg-light" id="searchCollapse">
    <form v-on:submit.prevent="searchMedicines" class="p-3">
          <h4>Filter</h4>

        <div class="form-row">
            <div class="form-group col-md-4">
              <label for="inputEmail4">Name</label>
              <input type="text" class="form-control" id="inputName" v-model="searchParams.name">
            </div>
            <div class="form-group col-md-4">
              <label for="inputStructure">Structure</label>
              <input type="text" class="form-control" id="inputStructure" v-model="searchParams.structure">
            </div>
            <div class="form-group col-md-4">
              <label for="inputManufacturer">Manufacturer</label>
              <input type="text" class="form-control" id="inputAddress" v-model="searchParams.manufacturer">
            </div>

            <div class="form-group col-md-3">
              <label for="inputShape">Shape</label>
              <input type="text" class="form-control" id="inputShape" v-model="searchParams.shape">
            </div>



            <div class="form-group col-md-3">
              <label for="inputType">Type</label>
              <input type="text" class="form-control" id="inputType" v-model="searchParams.type">
            </div>

            <div class="form-group col-md-2">
              <label for="inputPerscription">Perscription</label>
              <select class="form-control" id="inputPerscription" v-model="searchParams.perscription">
                <option value="WITH_RECEIPT">With Receipt</option>
                <option value="WITHOUT_RECEIPT">Without Receipt</option>
              </select>          
            </div>

            <div class="form-group col-md-2">
              <label for="inputPoints">Points</label>

              <div class="input-group">

                  <input type="text" class="form-control" id="inputShape" v-model="searchParams.minPoints">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroupPrepend2">-</span>
                  </div>
                  <input type="text" class="form-control" id="inputShape" v-model="searchParams.maxPoints">
              </div>
            </div>

            <div class="form-group col-md-2">
              <label for="inputMinRating">Rating</label>

              <div class="input-group">

                  <input type="text" class="form-control" id="inputMinRating" v-model="searchParams.minRating">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputMaxRating">-</span>
                  </div>
                  <input type="text" class="form-control" id="inputMaxRating" v-model="searchParams.maxRating">
              </div>
            </div>


            <div class="form-group col-md-2">
              <label for="sortSelect">Sort</label>
              <select class="form-control" id="sortSelect" v-model="searchParams.sort">
                <option value="NAME_ASC">By Name Asc</option>
                <option value="NAME_DES">By Name Des</option>
                <option value="ADDRESS_ASC">By Address Asc</option>
                <option value="ADDRESS_DES">By Address Des</option>
                <option value="RAING_ASC">By Rating Asc</option>
                <option value="RAING_DES">By Rating Des</option>
              </select>          
            </div>

        </div>
        <button type="submit" class="btn btn-primary">Search</button> 
    </form>
  </div>
  
  <div class="row">

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
            <!-- <router-link class="btn btn-block btn-primary" :to="{ name: 'MedicinePage', params: { id: m.id  }}">View</router-link> -->
            <button type="button" class="btn btn-block btn-primary">View TODO</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MedicineDataService from '../service/MedicineDataService.js';

export default {
    name: 'MedicinesList',
    data() {
        return {
            medicines: [],
            message: null,
            searchParams: {},
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
          MedicineDataService.searchMedicines(JSON.stringify(this.searchParams));
                // .then(response => {
                //     this.pharmacies = response.data;
                //     console.log(response.data);
                // });
        },

    },
    mounted() {
        this.getMedicines();
    },
    created() {
    }
}
</script>