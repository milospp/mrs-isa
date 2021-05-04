<template>

  <div class="row">
    <h3>All Pharmacies</h3>
  </div>
		<div class="row mb-3">
			<div class="col-md-12">
				<a class="btn btn-primary float-right" data-toggle="collapse" href="#searchCollapse" role="button" aria-expanded="false" aria-controls="searchCollapse">
					Filter
				</a>
			</div>
		</div>
	<form v-on:submit.prevent="searchPharmacies" class="collapse" id="searchCollapse">
      <div class="form-row">
          <div class="form-group col-md-4">
            <label for="inputEmail4">Name</label>
            <input type="text" class="form-control" id="inputName" v-model="searchParams.name">
          </div>
          <div class="form-group col-md-4">
            <label for="inputAddress">Address</label>
            <input type="text" class="form-control" id="inputAddress" v-model="searchParams.addressString">
          </div>
          <div class="form-group col-md-2">
            <label for="inputAddress">Rating</label>
            <select class="form-control" id="exampleFormControlSelect1" v-model="searchParams.rating">
              <option>>4*</option>
              <option>4-5</option>
              <option>3-4</option>
              <option>2-3</option>
              <option>1-2</option>
            </select>          
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



  <div class="row">


      
    <div class="col-md-3 py-2" v-for="p in pharmaciesSlice">
      <div class="card h-100 box-shadow">
        <div class="card-header">
            <h4 class="my-0 font-weight-normal">{{p.name}}</h4>
          </div>
        <div class="card-body">
          <p class="card-text">
            {{p.description}}
          </p>
          <p v-if="p.rating >= 1" class="rating">Rating: {{p.rating}} / 5</p>

          <hr>
          <h6>
            {{UtilService.AddressToString(p.address)}}e
          </h6>
          <div class="d-flex justify-content-between align-items-center">
            <router-link class="btn btn-block btn-primary" :to="{ name: 'PharmacyPage', params: { id: p.id  }}">View</router-link>

          </div>
        </div>
      </div>
    </div>      

  </div>

</template>

<script>
import PharmacyDataService from '../service/PharmacyDataService.js';
import UtilService from '../service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },
    name: 'PharmaciesList',
    data() {
        return {
            pharmacies: [],
            message: null,
            searchParams: {},
        };
    },
    props: ['limit'],
    computed:{
      pharmaciesSlice(){
        return this.limit ? this.pharmacies.slice(0,this.limit) : this.pharmacies
      },
    },

    methods: {
        getPharmacies() {
          PharmacyDataService.getAllPharmacies() // HARDCODED
                .then(response => {
                    this.pharmacies = response.data;
                    console.log(response.data);
                });
        },
        searchPharmacies() {
          PharmacyDataService.searchPharmacies(JSON.stringify(this.searchParams)) // HARDCODED
                .then(response => {
                    this.pharmacies = response.data;
                    console.log(response.data);
                });
        },

    },
    mounted() {
        this.getPharmacies();
    },
    created() {
    }
}

</script>