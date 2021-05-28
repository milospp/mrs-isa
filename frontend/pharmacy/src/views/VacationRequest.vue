<template>
  <NavBar/>
  <div class="container">
    <h1 class="title">Vacation Request</h1>
    <div v-if="jobs && doctorsRole === 'DERMATOLOGIST'" class="form-group">
      <label for="pharmacy">Pharmacy</label>
      <select class="form-control" v-model="pharmacy" name="pharmacy" id="pharmacy">
        <option :key="j" v-for="j in jobs" :value="j.pharmacyId">{{j.pharmacyName}}</option>
      </select>
    </div>
    <div class="form-group">
      <label for="typeOfRest">Type</label>
      <select class="form-control" v-model="vacation.type" name="type" id="typeOfRest">
        <option value="REST">Vacation</option>
        <option value="ABSENCE">Absence</option>
      </select>
    </div>
    <div class="row">
      <div class="form-group col">
          <label for="start">Start date</label>
          <input id="start" class="form-control" type="datetime-local" v-model="vacation.startDate"/>
      </div>
      <div class="form-group col">
          <label for="end">End date</label>
          <input id="end" class="form-control" type="datetime-local" v-model="vacation.endDate"/>
      </div>
    </div>
    <div class="form-group">
        <label for="reason">Why should I take a leave?</label>
        <input class="form-control" placeholder="Reason" id="reason">
    </div>
    <div class="form-group">
        <button class="form-control btn btn-success" id="send" @click="sendRequest()">Send Request</button>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import AuthService from "@/service/AuthService.js";
import PharmacyDataService from '@/service/PharmacyDataService.js';

export default {
  name: 'PharmacistHomePage',
  components: {
    NavBar
  },
  data() {
      return {
          jobs: null,
          vacation: {
            type: 'REST',
            startDate: new Date(),
            endDate: new Date(),
            reason: '',
          }
      }
  },
  mounted() {
    this.getJobs();
  },
  methods: {
    sendRequest(){
      alert('sending...');
    },
    getJobs(){
      PharmacyDataService.getDoctorsJobs(this.doctor.id)
      .then(response => {
                    this.jobs = response.data;
                    console.log("poslovi");
                    console.log(response.data);
                });
    }
  },
  computed: {
    doctor() {
      return AuthService.getCurrentUser()
    },
    doctorsRole() {
      return AuthService.getCurrentUser().role
    }
  }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>