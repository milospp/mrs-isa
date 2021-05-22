<template>
<div class="row d-flex justify-content-center">
    <div class="col-md-3" v-for="j in jobs" :key="j.id">
      <div class="card mx-2 mt-4 box-shadow">
        <div class="card-header">
            <h4 class="my-0 font-weight-normal">{{j.pharmacyName}}</h4> <!---->
          </div>
        <div class="card-body">
          <p class="card-text">
            {{j.pharmacyDesc}}
          </p><!---->
          <hr>
          <p class="card-text">
            Shift: {{UtilService.FormatTime(j.startHour)}} - {{UtilService.FormatTime(j.endHour)}}
          </p><!---->
          <hr>
          <h6>
            {{UtilService.AddressToString(j.pharmacyAddr)}}
          </h6><!--  -->
          <div class="d-flex justify-content-between align-items-center">
            <router-link class="btn btn-block btn-primary" :to="{ name: 'PharmacyPage', params: { id: j.pharmacyId  }}">View</router-link>

          </div>
        </div>
      </div>
    </div>
</div>
</template>

<script>
import PharmacyDataService from '../../service/PharmacyDataService.js';
import UtilService from '../../service/UtilService.js';
import AuthService from '@/service/AuthService.js';

export default {
    setup() {
      return { UtilService }
    },
    name: 'PharmacyCard',
    data() {
        return {
            jobs: [],
            message: null,
        };
    },
    methods: {
      getJobs() {
        PharmacyDataService.getDoctorsJobs(this.doctorId) // HARDCODED
                .then(response => {
                    this.jobs = response.data;
                    console.log("poslovi");
                    console.log(response.data);
                });
      },
      getPharmacies() {
        for (j in this.jobs){
          PharmacyDataService.getPharmacy(j.pharmacyId)
            .then(response => {
              j['pharmacy'] = response.data;
              console.log("apoteka");
              console.log(response.data);
          });
        }
      }
    },
    mounted() {
        this.getJobs();
        this.getPharmacies();
    },
    created() {
        this.doctorId = AuthService.getCurrentUser().id;
    }
}

</script>