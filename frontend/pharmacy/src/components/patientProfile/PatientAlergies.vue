<template>

<!-- Modal -->
<div class="modal fade" id="addAllergiesModal" tabindex="-1" role="dialog" aria-labelledby="addAllergiesModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="addAllergiesModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

          <h4>Choice an allergy</h4>
          <form v-on:submit.prevent="addAlergy(this)">
            <label for="cars">Choose an allergy:</label>
            <select v-model="addAlergySelected" name="medicines_allergy" id="medicines_allergy">
              <option v-for="m in medicines" v-bind:key="m.id" v-bind:value="m.id">[{{m.code}}] {{m.name}} ({{m.manufacturer}})</option>
            </select>
            <br><br>
            <input class="btn btn-primary" type="submit" value="Add">
          </form>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

  <h3 class="float-left mt-3">Alergies</h3>
  <button type="button" class="btn btn-primary mt-3 float-right" data-toggle="modal" data-target="#addAllergiesModal">
    Add allergies
  </button>
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
    import MedicineDataService from '@/service/MedicineDataService.js';
    import UtilService from '@/service/UtilService.js';
    import $ from 'jquery';
    import Multiselect from 'vue-multiselect'


export default {
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      alergies: {},
      medicines: null,
      addAlergySelected: null,
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

        loadMedicines() {
          MedicineDataService.getAllMedicines() // HARDCODED
                .then(response => {
                    this.medicines = response.data;
                    console.log(response.data);
                });
        },
        addAlergy(data) {
          var medicine = {id: this.addAlergySelected};
          PatientDataService.addPatientAllergy(medicine).then(response => {
            this.loadPatientAlergies();
          });
        }



    },
    mounted() {
        let self = this;
        this.loadPatientAlergies();
        $('#addAllergiesModal').on('show.bs.modal', function () {
          self.loadMedicines();
        })
        
    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>