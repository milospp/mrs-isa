<template>

    <div v-if="patient" class="row">
      <div class="col-md-4">
        <h2>{{patient.name}} {{patient.surname}}</h2>

        <!-- <h5>Address: {{UtilService.AddressToString(patient.address)}}</h5> -->
        <h5>Address: {{patient.address}}</h5>
      </div>

      <div class="col-md-6 text-left">
        <p>Points: TODO</p>
        <p>Penalities: 2</p>
        
      </div>
      
      <div class="col-md-2">
        <button class="btn btn-block btn-warning">Edit</button>
      </div>

    </div>

</template>

<script>
    // import PharmacyDataService from '@/service/PharmacyDataService.js';
    import UtilService from '@/service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      patient: {"id":1,"name":"Isidora","surname":"Stanic","address":"ulica broj, grad, drzava","phoneNumber":"123-456-789"},
		}
	},
    methods: {
        // loadPatientData() {
        //     let self = this;

        //     PharmacyDataService.getPharmacy(this.id) // HARDCODED
        //         .then(response => {
        //             self.pharmacy = response.data; 
        //             console.log(this.id);
        //             this.patients = response.data;
        //             console.log(response.data);
        //         });
        // },


    },
    mounted() {
        // this.loadPatientData();
    },
	created() {
		  this.username = this.$route.params.username; 
	},
}
</script>