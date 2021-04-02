<template>

    <div v-if="pharmacy" class="row">
      <div class="col-md-4">
        <h2>{{pharmacy.name}}</h2>
        <p class="rating">Rating: <span>TODO: 9.7/10</span></p>

        <h5>Address: {{UtilService.AddressToString(pharmacy.address)}}</h5>
      </div>

      <div class="col-md-6 text-left">
        <p>{{pharmacy.description}}</p>
        
      </div>
      
      <div class="col-md-2">
        <button class="btn btn-block btn-primary">Subscribe</button>
      </div>

    </div>
    

    <div class="row">
      <div class="col-md-12">

        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Home</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Pharmacists</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Dermatologists</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Appoitments</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Map</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                    <th>Example</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                    <td>Med 1</td>
                  </tr>
                  <tr>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                    <td>Med 2</td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <div id="menu3" class="tab-pane in fade">

              <h3>Appointments</h3>

              <div class="appointment card mb-4">
                <div class="card-header">
                  <h5>Free apointment at 08:00 (30min)</h5>
                </div>
                <div class="card-body text-left">
                  <div class="row">
                    <div class="col-sm-6 h4">
                      <table>
                        <tr><td>Pharmacist: </td><td class="pl-5"><strong>Dr.Mr.Phill</strong></td></tr>
                        <tr><td>Pharmacy: </td><td class="pl-5"><strong>Phara</strong></td></tr>
                        <tr><td>Price per hour: </td><td class="pl-5"><strong>$20.00 USD</strong></td></tr>
                      </table>
                    </div>
                    <div class="col-sm-6 h5">
                      <table>
                        <tr><td>Starting at: </td><td class="pl-5"><strong>20/5/2021 08:00</strong></td></tr>
                      </table>
                      <br>
                      <button class="btn btn-block btn-primary">Book a appointment</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="menu4" class="tab-pane fade">
            </div>
            
            <div id="menu1" class="tab-pane fade">
              <h3>Pharmacists</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                </thead>
                <tbody>
                    <tr :key="f.username" v-for="f in this.sviZaposleniFarmaceuti" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
                      <td>{{f.name}}</td>
                      <td>{{f.surname}}</td>
                      <td>{{UtilService.AddressToString(f.address)}}</td>
                      <td>{{f.phoneNumber}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu2" class="tab-pane fade">
              <h3>Dermatologists</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                </thead>
                <tbody>
                    <tr :key="d.username" v-for="d in this.sviZaposleniDermatolozi" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
                      <td>{{d.name}}</td>
                      <td>{{d.surname}}</td>
                      <td>{{UtilService.AddressToString(d.address)}}</td>
                      <td>{{d.phoneNumber}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

</template>

<script>
    import PharmacyDataService from '../service/PharmacyDataService.js';
    import PharmacistDataService from '../service/PharmacistDataService.js';
    import DermatologistDataService from '../service/DermatologistDataService';
    import UtilService from '../service/UtilService.js';

export default {
    setup() {
      return { UtilService}
    },

	data: function () {
		return {
            pharmacy: null,
            sviZaposleniFarmaceuti : [] ,
            sviZaposleniDermatolozi : []
		}
	},
  methods: {
      loadPharmacyData() {
          let self = this;

          PharmacyDataService.getPharmacy(this.id) // HARDCODED
              .then(response => {
                  self.pharmacy = response.data; 
                  console.log(this.id);
                  this.patients = response.data;
                  console.log(response.data);
              });
      },
      patientInfo(patient){
          alert(patient);
      }
  },
  created() {
      this.id = this.$route.params.id; 
      PharmacistDataService.getAllPharmacistPharmacy(this.id)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;});
      DermatologistDataService.getAllDermatologistsPharmacy(this.id)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
  },
  mounted() {
    this.loadPharmacyData();
      PharmacistDataService.getAllPharmacistPharmacy(this.id)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;});
      DermatologistDataService.getAllDermatologistsPharmacy(this.id)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
    }
}
</script>