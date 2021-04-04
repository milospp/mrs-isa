<template>

<!-- Modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div v-if="patientEdit" class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editModalLabel">Edit</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="email" class="col-form-label">Email:</label>
            <input type="email" class="form-control" v-model="patientEdit.email" id="email" disabled>
          </div>
          <div class="form-group">
            <label for="name" class="col-form-label">Name:</label>
            <input type="text" class="form-control" v-model="patientEdit.name" id="name">
          </div>
          <div class="form-group">
            <label for="surname" class="col-form-label">Surname:</label>
            <input type="text" class="form-control" v-model="patientEdit.surname" id="surname">
          </div>
          <div class="form-group">
            <label for="phone" class="col-form-label">Phonenumber:</label>
            <input type="text" class="form-control" v-model="patientEdit.phoneNumber" id="phone">
          </div>


          <h5>Address</h5>
          <div class="form-group">
            <label for="state" class="col-form-label">State:</label>
            <input type="text" class="form-control" v-model="patientEdit.address.state" id="state">
          </div>
          <div class="form-group">
            <label for="city" class="col-form-label">City:</label>
            <input type="text" class="form-control" v-model="patientEdit.address.city" id="city">
          </div>
          <div class="form-group">
            <label for="street" class="col-form-label">Street:</label>
            <input type="text" class="form-control" v-model="patientEdit.address.street" id="street">
          </div>
          <div class="form-group">
            <label for="number" class="col-form-label">Number:</label>
            <input type="text" class="form-control" v-model="patientEdit.address.number" id="number">
          </div>

        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" v-on:click="updatePatient()" >Save</button>
      </div>
    </div>
  </div>
</div>


    <div v-if="patient" class="row">




      <div class="col-md-4">
        <h2>{{patient.name}} {{patient.surname}}</h2>

        <p>Address: {{UtilService.AddressToString(patient.address)}}</p>
        <p>Phone: {{patient.phoneNumber}}</p>
      </div>

      <div class="col-md-6 text-left">
        <p>Points: {{patient.points}}</p>
        <p>Active Penalties: {{penalties}}</p>
        
      </div>
      
      <div class="col-md-2">
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-block btn-warning" data-toggle="modal" data-target="#editModal">
          Edit
        </button>
      </div>

    </div>

</template>

<script>
    import PatientDataService from '@/service/PatientDataService.js';
    import UtilService from '@/service/UtilService.js';
    import $ from 'jquery';

export default {
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      patient: null,
      patientEdit: null,
      penalties: 0,
		}
	},
    methods: {
        loadPatientData() {

            PatientDataService.getPatient(this.id) // HARDCODED
                .then(response => {
                    console.log(this.id);
                    this.patient = response.data;
                    console.log(response.data);
                });
        },

        getPatientPenalties() {
          PatientDataService.getPatientPenalitiesCount(this.id) // HARDCODED
                .then(response => {
                    console.log(this.id);
                    this.penalties = response.data;
                    console.log(response.data);
                });
        },

        editModalMethod() {
          let self = this;

          $('#editModal').on('show.bs.modal', function () {

            self.patientEdit = JSON.parse(JSON.stringify(self.patient));
          // console.log(self.patientEdit);

          });


        },

        updatePatient() {
          PatientDataService.updatePatientInfo(this.patientEdit)
                .then(response => {
                    $('#editModal').modal('hide');
                    this.patient = response.data;
                });
        }


    },
    mounted() {
        this.loadPatientData();
        this.getPatientPenalties();

        this.editModalMethod();

    },
	created() {
		  this.id = this.$route.params.id; 
	},
}
</script>