<template>
<div>
    <NavBar role="pharmacist"/>
    <h1 class="title">Dispense Medication</h1>
    <div class="d-flex justify-content-center">
      <form v-on:submit.prevent="getReservation()"> <!--v-on:submit.prevent="getReservation()"-->
        <input type="text"> <!--v-model="code"-->
        <button class="btn btn-primary m-4" type="submit">Search reservation</button>
      </form>
    </div>
    <div class="container">
      <div class="text-center">
      <h1>Reservation</h1>
      <h2>Code: {{reservationDTO.code}}</h2> <!---->
        <h3>Reserved: {{UtilService.formatDate(reservationDTO.reservationDate)}}</h3><!--{{UtilService.formatDate(reservationDTO.reservationDate)}}-->
        <h3>Expires: 
          <span v-if="UtilService.lessThan24HoursLeft(reservationDTO.lastDate)" style="color: red;">
            {{UtilService.formatDate(reservationDTO.lastDate)}}
            </span>
            <span v-else>
            {{UtilService.formatDate(reservationDTO.lastDate)}}
            </span>
            </h3> <!--<span v-if="UtilService.lessThan24HoursLeft(reservationDTO.lastDate)" style="color: red;">{{UtilService.formatDate(this.reservationDTO.lastDate)}}</span>-->
      </div>
      <table class="table table-striped">
        <thead>
          <th>Medicine code</th>
          <th>Medicine name</th>
          <th>Amount</th>
        </thead>
        <tbody>
          <tr>
            <td>{{reservationDTO.medicineInPharmacy.code}}</td>
            <td>{{reservationDTO.medicineInPharmacy.name}}</td>
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
          id: 1,
          code: "1234",
          reservationDate: "2021-04-16T22:38:09.002714",
          lastDate: "2021-04-13T22:38:09.002714",
          medicineInPharmacy: {
            code: "med1",
            name: "Lek za testiranje"
          },
          quantity: 2
        },
        empty: false
      }
  },
  methods: {
    getReservation(){
      console.log(this.reservationDTO.code);
      MedicineDataService.getReservation(this.reservationDTO.code)
        .then(function(response){
          if (response.data)
            this.reservationDTO = response.data;
        });
    },
    dispenseMed(){
      MedicineDataService.giveMedicineToPatient(this.reservationDTO.code)
      .then(function(response){
        alert(response.data);
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