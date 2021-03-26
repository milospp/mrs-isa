<template>
  <div class="row">
    <h3>All Medicines</h3>
  </div>
  <div class="row">

    <div class="col-md-4" v-for="m in medicines">
      <div class="card mb-4 box-shadow">
        <div class="card-header">
            <h4 class="my-0 font-weight-normal">{{m.name}}</h4><kbd>{{m.code}}</kbd>
          </div>
        <div class="card-body">
          <h6>{{m.type}}</h6>
          <h6>{{m.shape}}</h6>
          <h6>{{m.manufacturer}}</h6>
          <p class="card-text">
            {{m.note}}
          </p>
          
          <div class="d-flex justify-content-between align-items-center">
            <button type="button" class="btn btn-block btn-primary">View TODO</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MedicineDataService from '../service/MedicineDataService.js';

export default {
    name: 'MedicinesList',
    data() {
        return {
            medicines: [],
            message: null,
        };
    },
    methods: {
        getMedicines() {
          MedicineDataService.getAllMedicines() // HARDCODED
                .then(response => {
                    this.medicines = response.data;
                    console.log(response.data);
                });
        },
        patientInfo(patient){
            alert(patient);
        }
    },
    mounted() {
        this.getMedicines();
    },
    created() {
    }
}
</script>