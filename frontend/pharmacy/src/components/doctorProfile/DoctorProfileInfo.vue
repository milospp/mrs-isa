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