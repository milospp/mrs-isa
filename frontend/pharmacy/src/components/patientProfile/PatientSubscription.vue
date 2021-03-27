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
      <tr v-for="s in subscriptions" v-bind:style="{ background: s.unsubscribed ? '#aaa' : ''}">
        <td>{{s.name}}</td>
        <td>{{s.description}}</td>
        <td>{{UtilService.AddressToString(s.address)}}</td>

        <td>
          <router-link class="btn btn-primary" :to="{ name: 'PharmacyPage', params: { id: s.id  }}">View</router-link>
          <button v-bind:disabled="s.unsubscribed" class="btn btn-danger" v-on:click="unsubscribe(s)">Unsubscribe</button>
        </td>
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

        unsubscribe(obj) {
            PatientDataService.unsubscribePatient(this.id, obj.id)
                .then(response => {
                    console.log("Unsubscribe " + obj.id);
                    obj.unsubscribed = true;
                    //this.subscriptions = response.data;
                });
        }


    },
    mounted() {
        this.loadPatientSubscriptions();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>