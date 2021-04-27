<template>

  <div class="row">
    <h3>All Pharmacies</h3>
  </div>
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

    },
    mounted() {
        this.getPharmacies();
    },
    created() {
    }
}

</script>