<template>
  <NavBar/>
  <div class="container">
    <h1 class="title">Vacation Request</h1>
    <div v-if="jobs && doctorsRole === 'DERMATOLOGIST'" class="form-group">
      <label for="pharmacy">Pharmacy</label>
      <select class="form-control" v-model="vacation.pharmacyId" name="pharmacy" id="pharmacy" required>
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
    <!-- <div class="row"> -->
      <!-- <div class="form-group col"> -->
          <!-- <label for="start">Start date</label> -->
          <!-- <input id="start" class="form-control" type="date" v-model="vacation.startDate"/> -->
      <!-- </div> -->
      <!-- <div class="form-group col"> -->
          <!-- <label for="end">End date</label> -->
          <!-- <input id="end" class="form-control" type="date" v-model="vacation.endDate"/> -->
      <!-- </div> -->
    <!-- </div> -->

    <DatePicker :min-date='minDate' v-model="range" is-range>
          <template v-slot="{ inputValue, inputEvents }">
            <div class="form-group row">
              <div class="col">
                <label for="start">Start date</label>
                <input id="start"
                  :value="inputValue.start"
                  v-on="inputEvents.start"
                  class="border form-control px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                />
              </div>
              <div class="col">
                <label for="end">End date</label>
                <input id="end"
                  :value="inputValue.end"
                  v-on="inputEvents.end"
                  class="border form-control px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                />
              </div>
            </div>
          </template>
        </DatePicker>
    <div class="form-group">
        <label for="reason">Why should I take a leave?</label>
        <textarea class="form-control" v-model="vacation.reason" rows="5" placeholder="Reason" id="reason"/>
        
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
import VacationDataService from "@/service/VacationDataService.js";

const today = new Date()
const tomorrow = new Date(today)
tomorrow.setDate(tomorrow.getDate() + 1)

export default {
  name: 'PharmacistHomePage',
  components: {
    NavBar
  },
  data() {
      return {
          minDate: tomorrow,
          range: {
            start: tomorrow,
            end: tomorrow
          },
          jobs: null,
          vacation: {
            type: 'REST',
            startDate: tomorrow.toISOString(),
            endDate: tomorrow.toISOString(),
            reason: '',
            status: null,
            whyNot: null,
            doctorId: null,
            pharmacyId: null,
            pharmacyAdminId: null
          }
      }
  },
  mounted() {
    this.getJobs();
    this.vacation.doctorId = this.doctor.id;
  },
  methods: {
    sendRequest(){
      this.vacation.startDate = this.range.start;
      this.vacation.endDate = this.range.end;
      if (this.doctorsRole === 'PHARMACIST'){
        this.vacation.pharmacyId = this.jobs[0].pharmacyId;
      }
      if (this.vacation.pharmacyId == null){
        this.$toast.show(
              "Please select pharmacy.",
              {
                  position: "top", type: "warning",
              }
            );
        return;
      }
      VacationDataService.sendRequest(this.vacation)
        .then(response => {
          if (response.data){
            this.$toast.show(
              "Vacation request sucessfully sent!",
              {
                  position: "top", type: "success",
              }
            );
          }
          else {
            this.$toast.show(
              "Invalid time!",
              {
                  position: "top", type: "error",
              }
            );
          }
        })
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