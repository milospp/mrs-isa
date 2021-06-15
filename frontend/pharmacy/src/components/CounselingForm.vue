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

        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="sortPharmacy">SortBy</span>
          </div>
          <select type="text" class="form-control" id="basic-url" aria-describedby="sortPharmacy" v-model="sortMethod">
            <option value="name" selected>Name</option>
            <option value="price">Price</option>
            <option value="rating">Rating</option>
          </select>
        </div>

        <div class="row">
            <div class="col-md-3 py-2" v-for="p in sortedPharmacies" :key="p.id">
                <div class="card h-100 box-shadow">
                    <div class="card-header">
                        <h4 class="my-0 font-weight-normal">{{p.name}} {{UtilService.formatRatingBracket(p.rating)}}</h4>
                        </div>
                    <div class="card-body">
                        <p class="card-text">
                        {{p.description}}
                        </p>
                        <p v-if="p.rating >= 1" class="rating">Rating: {{p.rating}} / 5</p>
                        <p class="rating">Price (hour): ${{p.pricePerHour}}</p>

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
        <p class="mb-1">Price per hour: ${{selectedPharmacy.pricePerHour}}</p>
        <p class="mb-1"><strong>Total: ${{selectedPharmacy.pricePerHour / 60 * appointmentRequest.durationInMins}}</strong></p>
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
                        <p v-else>No rating</p>

                        <hr>
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
        startTime: "2021-06-20T11:00",
        durationInMins: 30
      },
      workList: null,
      availablePharmacies: {},
      availablePharmacists: {},
      selectedPharmacy: null,
      sortMethod: "name",
    }
  },

  computed: {
    sortedPharmacies() {
      let pharmacies = Object.values(this.getPharmacies());
      
      if (this.sortMethod == 'name') pharmacies = pharmacies.sort((a, b) => (a.name > b.name) ? 1 : -1)
      if (this.sortMethod == 'price') pharmacies = pharmacies.sort((a, b) => (a.pricePerHour > b.pricePerHour) ? 1 : -1)
      if (this.sortMethod == 'rating') pharmacies = pharmacies.sort((a, b) => (a.rating > b.rating) ? 1 : -1)

      return pharmacies;
    },

  },


  methods: {
    findAvalibaleEmployes() {
      AppointmentDataService.getAvailableEmployes(this.appointmentRequest).then(response => {
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

            alert("Zakazano");
        })
    }

  }


}

</script>