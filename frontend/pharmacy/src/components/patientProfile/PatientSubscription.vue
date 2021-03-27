<template>

  <h3>Subscription</h3>
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Address</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="s in subscriptions">
        <td>{{s.name}}</td>
        <td>{{s.description}}</td>
        <td>{{UtilService.AddressToString(s.address)}}</td>
        <td><button class="btn btn-danger">Unsubscribe</button></td>
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
      subscriptions: {},
		}
	},
  methods: {
        loadPatientSubscriptions() {
            PatientDataService.getPatientSubscriptions(this.id) // HARDCODED
                .then(response => {
                    console.log("Load subscriptions");
                    console.log(response.data);

                    this.subscriptions = response.data;
                });
        },


    },
    mounted() {
        this.loadPatientSubscriptions();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>