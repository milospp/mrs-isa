<template>

  <h3>Penalities</h3>
  <table class="table table-striped">
    <thead>
      <tr>
        <!-- <th>ID</th> -->
        <th>Reason</th>
        <th>Date</th>
        <th>Expire</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="p in penalities" v-bind:style="{ background: UtilService.isPastDate(UtilService.getFirstInNextMounth(p.date)) ? '#aaa' : ''}">
        <!-- <td>{{p.id}}</td> -->
        <td>{{p.reason}}</td>
        <td>{{UtilService.formatDate(p.date)}}</td>
        <td>{{UtilService.formatDate(UtilService.getFirstInNextMounth(p.date))}}</td>
      </tr>

    </tbody>
  </table>
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
      penalities: {},
		}
	},
    methods: {
        loadPatientPenalities() {

            PatientDataService.getPatientPenalities(this.id) // HARDCODED
                .then(response => {
                    this.penalities = response.data;
                    console.log("Penalities")
                    console.log(response.data);
                });
        },


    },
    mounted() {
        this.loadPatientPenalities();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>