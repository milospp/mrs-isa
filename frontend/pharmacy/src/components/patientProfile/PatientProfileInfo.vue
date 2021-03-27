<template>

    <div v-if="patient" class="row">
      <div class="col-md-4">
        <h2>{{patient.name}} {{patient.surname}}</h2>

        <p>Address: {{UtilService.AddressToString(patient.address)}}</p>
        <p>Phone: {{patient.phoneNumber}}</p>
      </div>

      <div class="col-md-6 text-left">
        <p>Points: {{patient.points}}</p>
        <p>Penalities: TODO</p>
        
      </div>
      
      <div class="col-md-2">
        <button class="btn btn-block btn-warning">Edit</button>
      </div>

    </div>

</template>

<script>
    import PatientDataService from '@/service/PatientDataService.js';
    import UtilService from '@/service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      patient: null,
		}
	},
    methods: {
        loadPatientData() {
            let self = this;

            PatientDataService.getPatient(this.id) // HARDCODED
                .then(response => {
                    self.pharmacy = response.data; 
                    console.log(this.id);
                    this.patient = response.data;
                    console.log(response.data);
                });
        },


    },
    mounted() {
        this.loadPatientData();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>