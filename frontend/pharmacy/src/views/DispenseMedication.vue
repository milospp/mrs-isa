<template>
<div>
    <NavBar role="pharmacist"/>
    <h1 class="title">Dispense Medication</h1>
    <div class="d-flex justify-content-center">
      <form v-on:submit.prevent="getReservation()"> <!--v-on:submit.prevent="getReservation()"-->
        <input type="text" v-model="reservationDTO.code"> <!--v-model="code"-->
        <button class="btn btn-primary m-4" type="submit">Search reservation</button>
      </form>
    </div>
    <div class="container" v-if="searched && empty">
      <h2 class="text-danger">Reservation code is not valid!</h2>
    </div>
    <div class="container" v-if="!empty">
      <div class="text-center">
      <h1>Reservation</h1>
      <h2>Code: {{reservationDTO.code}}</h2>
        <h3>Reserved: {{UtilService.formatDate(reservationDTO.reservationDate)}}</h3>
        <h3>Expires: 
          <!-- It should never be shown if it's expired (that's how backend works)
             <span v-if="UtilService.lessThan24HoursLeft(reservationDTO.lastDate)" style="color: red;">
            {{UtilService.formatDate(reservationDTO.lastDate)}}
            </span>
            <span v-else> -->
            {{UtilService.formatDate(reservationDTO.lastDate)}}
            <!-- </span> -->
            </h3>
      </div>
      <table class="table table-striped">
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
      <button v-if="UtilService.lessThan24HoursLeft(reservationDTO.lastDate)" class="btn btn-error mx-auto d-block disabled" aria-disabled="true">Cannot dispense</button>
      <button v-else class="btn btn-primary mx-auto d-block" v-on:click="dispenseMed()">Dispense</button>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
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
          if (response.data)
                    this.empty = false;
                    this.reservationDTO = response.data;
                    console.log(response.data);
                });
      if (this.reservationDTO.code == "")
        this.searched = false;
      else
        this.searched = true;
      
    },
    dispenseMed(){
      MedicineDataService.giveMedicineToPatient(this.reservationDTO.code)
      .then(function(response){
        alert(response.data);
      });
    }
  },
  created(){
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>