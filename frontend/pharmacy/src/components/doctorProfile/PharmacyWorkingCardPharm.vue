<template>
<div class="row">
    <div class="col-md-12 d-flex justify-content-center" v-for="j in jobs" :key="j.id">
      <div class="card m-4 box-shadow" style="width: 25%">
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

export default {
    setup() {
      return { UtilService }
    },
    name: 'PharmacyCardPharm',
    data() {
        return {
            jobs: [],
            message: null,
        };
    },
    props: ['doctorId'],
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
    }
}

</script>