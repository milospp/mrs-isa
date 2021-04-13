<template>
<div>
  <div class="row">
    <h3>Appointments</h3>
  </div>
  <div class="row">

    <!-- <div class="col-md-4" v-for="a in appointments"> -->
    <div class="w-100" v-for="a in appointments">
      <div class="appointment card mb-4">
        <div class="card-header">
          <h5>Free apointment at {{UtilService.formatDateTime( a.startTime )}} ({{a.durationInMins}}min)</h5>
        </div>
        <div class="card-body text-left">
          <div class="row">
            <div class="col-sm-6 h5">
              <table>
                <tr><td>Dermatologist: </td><td class="pl-5"><strong>{{a.doctor.name}}</strong></td></tr>
                <tr><td>Pharmacy: </td><td class="pl-5"><strong>{{a.pharmacy.name}}</strong></td></tr>
                <tr><td>Price per hour: </td><td class="pl-5"><strong>${{a.price}} USD</strong></td></tr>
              </table>
            </div>
            <div class="col-sm-6 h5">
              <table>
                <tr>
                  <td>Starting at: </td><td class="pl-5"><strong>{{UtilService.formatDateTime( a.startTime )}}</strong></td>
                </tr>
                <tr>
                  <td>Duration: </td><td class="pl-5"><strong>{{a.durationInMins}}min</strong></td>
                </tr>
              </table>
              <br/>
              <button class="btn btn-block btn-primary" v-bind:disabled="a.booked" v-on:click="bookAppointment(a)">Book a appointment</button>
            </div>
          </div>
        </div>
      </div> 
    </div>

  </div>
</div>
</template>

<script>
import AppointmentDataService from '../service/AppointmentDataService.js';
import UtilService from '@/service/UtilService.js';

export default {
    setup() {
      return { UtilService }
    },

    name: 'Appointments',
    data() {
        return {
            appointments: [],
            // limit: 0,
        };
    },
    props: ['limit'],
    computed:{
      // medicinesSlice(){
      //   return this.limit ? this.medicines.slice(0,this.limit) : this.medicines
      // },
    },

    methods: {
        getDermatologAppointments() {
          AppointmentDataService.getFreeDermAppointments() // HARDCODED
                .then(response => {
                    this.appointments = response.data;
                    console.log(response.data);
                });
        },

        bookAppointment(a) {
          // TODO: Getloggedin user id
          let userId = 1;

          AppointmentDataService.bookAppointment(a.id, userId).then(response => {
            a.booked = true;
            alert("Succesfully reserved");
          });
        }
    },
    mounted() {
        this.getDermatologAppointments();
    },
    created() {
    }
}
</script>