<template>

  <h3>Penalities</h3>
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Reason</th>
        <th>Date</th>
        <th>Expire</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>Reasooooon 1</td>
        <td>20/10/1015</td>
        <td>Med 1</td>
      </tr>
      <tr>
        <td>Med 2</td>
        <td>Med 2</td>
        <td>Med 2</td>
      </tr>
    </tbody>
  </table>
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
      alergies: {},
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