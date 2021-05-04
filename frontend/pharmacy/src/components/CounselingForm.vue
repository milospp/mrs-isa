<template>
<div>


<div class="d-flex justify-content-center">
  <div class="col-md-4">
    <form v-on:submit.prevent="findAvalibaleEmployes">
      <div class="form-group">
        <label for="startTime">Start Time</label>
        <input type="datetime-local" class="form-control" id="startTime" aria-describedby="emailHelp" placeholder="Enter email" v-model="appointmentRequest.startTime" @change="findAvalibaleEmployes">
      </div>
      <div class="form-group">
        <label for="durationTime">Duration of counseling</label>
        <input type="number" class="form-control" id="durationTime" placeholder="30min" v-model="appointmentRequest.durationInMins"  @change="findAvalibaleEmployes">
        <small id="emailHelp" class="form-text text-muted">Duration in minutes.</small>

      </div>

      <button type="submit" class="btn btn-primary">Check time</button>
    </form>

  </div>

</div>
<hr>

    <div class="pharmacySelection" v-if="workList != null && !selectedPharmacy">
        <h3>Select Pharmacy</h3>
        <div class="row">
            <div class="col-md-3 py-2" v-for="p in getPharmacies()" :key="p.id">
                <div class="card h-100 box-shadow">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">{{p.name}} {{UtilService.formatRatingBracket(p.rating)}}</h4>
                        </div>
                    <div class="card-body">
                        <p class="card-text">
                        {{p.description}}
                        </p>
                        <p v-if="p.rating >= 1" class="rating">Rating: {{p.rating}} / 5</p>

                        <hr>
                        <h6>
                        {{UtilService.AddressToString(p.address)}}
                        </h6>
                        <div class="d-flex justify-content-between align-items-center">
                        <button class="btn btn-block btn-primary" v-on:click="selectPharmacy(p.id)">Select</button>

                        </div>
                    </div>
                </div>
            </div>      

        </div>
    </div>

<div v-if="selectedPharmacy" class="doctorSelection">

    <a v-on:click="clearPharmacySelection" class="mb-3 list-group-item list-group-item-action flex-column align-items-start">
        <div class="d-flex w-100 justify-content-between">
        <h5 class="mb-1">{{selectedPharmacy.name}}</h5>
        <h2>x</h2>
        </div>
        <p class="mb-1">{{selectedPharmacy.description}}</p>
        <small>{{UtilService.AddressToString(selectedPharmacy.address)}}</small>
    </a>

    <h3>Select Pharmacist</h3>

    <div class="row">
            <div class="col-md-3 py-2" v-for="p in getPharmacists()" :key="p.id">
                <div class="card h-100 box-shadow">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">{{p.name}} {{p.surname}}{{UtilService.formatRatingBracket(p.rating)}}</h4>
                        </div>
                    <div class="card-body">
                        <p class="card-text">
                        {{p.email}}
                        </p>
                        <p v-if="p.rating >= 1" class="rating">Rating: {{p.rating}} / 5</p>

                        <hr>
                        <h6>
                            <!-- TODO -->
                            Price: $444
                        </h6>
                        <div class="d-flex justify-content-between align-items-center">
                        <button class="btn btn-block btn-primary" v-on:click="bookAppointment(p.id)">Book Appointment</button>

                        </div>
                    </div>
                </div>
            </div>      

        </div>


</div>


</div>
</template>

<script>
import AppointmentDataService from '@/service/AppointmentDataService.js';
import UtilService from '@/service/UtilService.js';

export default {
  name: 'Counseling',
  setup() {
      return { UtilService }
  },
  data: function () {
    return {
      appointmentRequest: {
        startTime: "2021-05-04T03:00",
        durationInMins: 30
      },
      workList: null,
      availablePharmacies: {},
      availablePharmacists: {},
      selectedPharmacy: null,
    }
  },


  methods: {
    findAvalibaleEmployes() {
      AppointmentDataService.getAvailableEmployes(this.appointmentRequest).then(response => {
        console.log(response);
        this.workList = response.data;
      })
    },

    getPharmacies() {
        let pharmacies = {};

        this.workList.forEach(element => {
            pharmacies[element.pharmacyDTO.id] = element.pharmacyDTO;
        });
        this.availablePharmacies = pharmacies
        return pharmacies;
    },

    selectPharmacy(id) {
        this.selectedPharmacy = this.availablePharmacies[id];
    },

    clearPharmacySelection() {
        this.selectedPharmacy = null;
    },

    getPharmacists() {
        let pharmacists = [];

        this.workList.forEach(element => {
            if (element.pharmacyDTO.id == this.selectedPharmacy.id){
                pharmacists.push(element.doctorDTO);
            }
        });

        this.availablePharmacists = pharmacists
        return pharmacists;
    },

    bookAppointment(pharmacistId) {
        this.appointmentRequest.pharmacyId = this.selectedPharmacy.id;
        this.appointmentRequest.pharmacistId = pharmacistId;
        AppointmentDataService.bookCounseling(this.appointmentRequest).then(response => {
            console.log(response);

            alert("Zakazano");
        })
    }

  }


}

</script>