<template>

    <div v-if="doctor" class="row">
      <div class="col-md-12">
        <h2>{{doctor.name}} {{doctor.surname}}</h2>
        <p class="rating">Rating: <span>TODO: 9.7/10</span></p>

        <p>Address: {{UtilService.AddressToString(doctor.address)}}</p>
        <p>Phone: {{doctor.phoneNumber}}</p>
      </div>
      
      <div class="col-md-12">
        <div class="d-inline-flex">
            <button class="btn btn-block btn-warning">Edit</button>
        </div>
      </div>

    </div>

    <div class="col-md-12">
      <div class="card m-4 box-shadow d-inline-flex">
        <div class="card-header">
            <h4 class="my-0 font-weight-normal">Apoteka u kojoj radi (TODO)</h4> <!--{{p.name}}-->
          </div>
        <div class="card-body">
          <p class="card-text">
            Opis apoteke u kojoj radi (TODO)
          </p><!--{{p.description}}-->
          <hr>
          <h6>
            Adresa apoteke u kojoj radi (TODO)
          </h6><!-- {{UtilService.AddressToString(p.address)}} -->
          <div class="d-flex justify-content-between align-items-center">
            <router-link class="btn btn-block btn-primary" :to="{ name: 'PharmacyPage', params: { id: 1/*p.id*/  }}">View</router-link>

          </div>
        </div>
      </div>
    </div>

</template>

<script>
    import DataService from '@/service/PharmacistDataService.js';
    import UtilService from '@/service/UtilService.js';

export default {
    name: "DoctorProfileInfo",
    //props: ['id'],
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      doctor: null
		}
	},
    methods: {
        loadDoctorData() {

            DataService.getPharmacist(this.id) // HARDCODED this.id
                .then(response => {
                    console.log(this.id);
                    this.doctor = response.data;
                    console.log(response.data);
                });
        }
    },
    mounted() {
        this.loadDoctorData();
    },
	created() {
		  this.id = this.$route.params.id;
	}
}
</script>