<template>
<div>
    <NavBar/>
    <h1 class="title">Dispense Medication</h1>
    <div class="container">
      <div class="d-flex justify-content-center">
        <form class="form-inline form-group" v-on:submit.prevent="getReservation()">
          <!--<div class="form-group">-->
            <label class="mr-2" for="codeInput">Reservation code</label>
            <input type="text" class="form-control mr-4" placeholder="Code" id="codeInput" v-model="reservationDTO.code">
            <button class="btn btn-primary" type="submit">Search reservation</button>
          <!--</div>-->
        </form>
      </div>
      
      <div class="container" v-if="!empty">
        <div class="text-center mx-4">
        <h1>Reservation</h1>
        <h2>Code: {{reservationDTO.code}}</h2>
          <h3>Reserved: {{UtilService.formatDate(reservationDTO.reservationDate)}}</h3>
          <h3>Expires: {{UtilService.formatDate(reservationDTO.lastDate)}}</h3>
        </div>
        <table class="table table-striped" style="text-align: left; table-layout: fixed;">
          <thead>
            <th>Medicine code</th>
            <th>Medicine name</th>
            <th>Amount</th>
          </thead>
          <tbody>
            <tr>
              <td>{{reservationDTO.medicineInPharmacy.medicine.code}}</td>
              <td>{{reservationDTO.medicineInPharmacy.medicine.name}}</td>
              <td>{{reservationDTO.quantity}}</td>
            </tr>
          </tbody>
        </table>
        <button v-if="UtilService.lessThan24HoursLeft(reservationDTO.lastDate)" class="btn btn-danger mx-auto d-block disabled form-control" :disabled="true" aria-disabled="true">Cannot dispense</button>
        <button v-else class="btn btn-success mx-auto d-block form-control" v-on:click="dispenseMed()">Dispense</button>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import MedicineDataService from '@/service/MedicineDataService.js'
import UtilService from '@/service/UtilService.js';

export default {
  name: 'DispenseMedication',
  setup() {
      return {UtilService}
    },
  components: {
    NavBar
  },
  data() {
      return {
        reservationDTO: {
          id: 0,
          code: "",
          reservationDate: "",//"2021-04-16T22:38:09.002714",
          lastDate: "",//"2021-04-13T22:38:09.002714",
          medicineInPharmacy: {
            code: "",
            name: ""
          },
          quantity: 0
        },
        empty: true,
        searched: false,
      }
  },
  methods: {
    getReservation(){
      console.log(this.reservationDTO.code);
      MedicineDataService.getReservation(this.reservationDTO.code)
        .then(response => {
          if (response.data){
            this.empty = false;
            this.reservationDTO = response.data;
            console.log(response.data);
          }})
        .catch(error => {
          this.empty = true;
          this.$toast.show(
            "Reservation code is not valid!",
            {
                position: "top", type: "error",
            }
          );}
        );
      if (this.reservationDTO.code == "")
        this.searched = false;
      else
        this.searched = true;
    },
    dispenseMed(){
      MedicineDataService.giveMedicineToPatient(this.reservationDTO.code)
      .then(response => {
        if (response.data){
          this.$toast.show(
            "Successfullty dispensed medicine!",
            {
                position: "top", type: "success",
            }
          );
          this.empty = true;
        } else {
          this.$toast.show(
            "Reservation is already taken!",
            {
                position: "top", type: "error",
            }
          );
          this.empty = true;
        }
        
      });
    }
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>