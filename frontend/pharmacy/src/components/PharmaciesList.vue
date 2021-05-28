<template>

		<div class="row mb-3">
			<div class="col-md-12">
				<a class="btn btn-outline-secondary" data-toggle="collapse" href="#searchCollapse" role="button" aria-expanded="false" aria-controls="searchCollapse">
					Filter
				</a>
			</div>
		</div>
    <div class="searchForm collapse mb-2 bg-light" id="searchCollapse">

      <form v-on:submit.prevent="searchPharmacies" class="p-3">
          <h4>Filter</h4>

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
                <label for="inputMinRating">Rating</label>

                <div class="input-group">

                    <input type="number" class="form-control" id="inputMinRating" min="0" :max="Math.min(5,searchParams.maxRating)" v-model="searchParams.minRating">

                    <div class="input-group-prepend">
                      <span class="input-group-text" id="inputMaxRating">-</span>
                    </div>
                    <input type="number" class="form-control" id="inputMaxRating" :min="Math.max(0,searchParams.minRating)" max="5" v-model="searchParams.maxRating">
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
          <a v-on:click="resetFormPharmacies" class="ml-3 btn btn-warning">Reset</a> 
      </form>

    </div>


  <div class="row">

    <div class="col-md-3 py-2 card-group" v-for="p in pharmaciesSlice">
      <div class="card">
        <router-link :to="{ name: 'PharmacyPage', params: { id: p.id  }}">
          <img class="card-img-top p-1 pl-5 pr-5" src="@/assets/pharmacylogo.png" alt="Card image cap">
        </router-link>
        <div class="card-body">
          <router-link :to="{ name: 'PharmacyPage', params: { id: p.id  }}">
            <h5 class="card-title mb-0">{{p.name}}</h5>
          </router-link>
          <div class="rating mb-3">
            <span v-for="i in p.rating">★</span>
          </div>
          <p class="card-text">{{p.description}}</p>
        </div>
        <ul class="list-group list-group-flush">
          <li class="list-group-item">{{UtilService.AddressToString(p.address)}}</li>
        
        </ul>

        <!-- <div class="card-footer">
          <five-stars v-model="p.rating" v-bind:inputId="modalId" disableEdit/>
        </div> -->
      </div>
    </div>


      
    <!-- <div class="col-md-3 py-2" v-for="p in pharmaciesSlice">
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
    </div>       -->

  </div>

</template>

<script>
import PharmacyDataService from '../service/PharmacyDataService.js';
import FiveStars from "@/components/FiveStars.vue";
import UtilService from '../service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },

    components: { FiveStars },
    name: 'PharmaciesList',
    data() {
        return {
            pharmacies: [],
            message: null,
            searchParams: {
              minRating: 0,
              maxRating: 5,
              sort: 'NAME_ASC'
            },
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

        resetFormPharmacies(){
          this.searchParams = {
              minRating: 0,
              maxRating: 5,
              name: undefined,
              addressString: undefined,
              sort: 'NAME_ASC'
          };
        },

    },
    mounted() {
        this.getPharmacies();
    },
    created() {
    }
}

</script>