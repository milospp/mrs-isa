<template>

    <div v-if="pharmacy" class="row">
      <div class="col-md-4">
        <h2>{{pharmacy.name}}</h2>
        <p class="rating">Rating: <span>TODO: 9.7/10</span></p>

        <h5>Address: {{UtilService.AddressToString(pharmacy.address)}}</h5>
      </div>

      <div class="col-md-6 text-left">
        <p>{{pharmacy.description}}</p>
        
      </div>
      
      <div class="col-md-2">
        <button class="btn btn-block btn-primary">Subscribe</button>
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

	data: function () {
		return {
            pharmacy: null,
		}
	},
    methods: {
        loadPharmacyData() {
            let self = this;

            PharmacyDataService.getPharmacy(this.id) // HARDCODED
                .then(response => {
                    self.pharmacy = response.data; 
                    console.log(this.id);
                    this.patients = response.data;
                    console.log(response.data);
                });
        },
        patientInfo(patient){
            alert(patient);
        }


    },
    mounted() {
        this.loadPharmacyData();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>