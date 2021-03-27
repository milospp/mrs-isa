<template>

  <h3>Alergies</h3>
  <table class="table table-striped">
    <thead>
      <tr>
        <th>Name</th>
        <th>Code</th>
        <th>Note</th>
        <th>Structure</th>
        <th>Shape</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="a in alergies">
        <td>{{a.name}}</td>
        <td>{{a.code}}</td>
        <td>{{a.note}}</td>
        <td>{{a.structure}}</td>
        <td>{{a.shape}}</td>
        <td>
          <button class="btn btn-primary">View</button>
    <!-- <router-link class="btn btn-block btn-primary" :to="{ name: 'MedicinePage', params: { id: a.id  }}">View</router-link> -->
          <button class="btn btn-danger ml-1">Delete</button>
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
      alergies: {},
		}
	},
    methods: {
        loadPatientAlergies() {
            let self = this;

            PatientDataService.getPatientAllergies(this.id) // HARDCODED
                .then(response => {
                    // TODO: TEST
                    self.alergies = response.data; 
                    console.log(this.id);
                    this.alergies = response.data;
                    console.log(response.data);
                });
        },


    },
    mounted() {
        this.loadPatientAlergies();
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>