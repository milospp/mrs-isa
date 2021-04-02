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
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Doctors</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Appoitments</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Map</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Pharmacists</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu5">Dermatologists</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines</h3>
              <table class="table table-striped">
                <thead class="card-header">
                  <th>Name</th>
                  <th>Structure</th>
                  <th>Manufacturer</th>
                  <th>Note</th>
                  <th>Points</th>
                  <th>Type</th>
                  <th>Quantity</th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.lekovi">
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.medicine.structure}}</td>
                      <td>{{l.medicine.manufacturer}}</td>
                      <td>{{l.medicine.note}}</td>
                      <td>{{l.medicine.points}}</td>
                      <td>{{l.medicine.type}}</td>
                      <td>{{l.quantity}}</td>
                      <td><form v-on:click.prevent="funkcija(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">Reserve</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu1" class="tab-pane fade">
              <h3>Staff</h3>
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
            
            <div id="menu2" class="tab-pane in fade">

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
            <div id="menu3" class="tab-pane fade">
            </div>
            
            <div id="menu4" class="tab-pane fade">
              <h3>Pharmacists</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                </thead>
                <tbody>
                    <tr :key="f.username" v-for="f in this.sviZaposleniFarmaceuti">
                      <td>{{f.name}}</td>
                      <td>{{f.surname}}</td>
                      <td>{{UtilService.AddressToString(f.address)}}</td>
                      <td>{{f.phoneNumber}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu5" class="tab-pane fade">
              <h3>Dermatologists</h3>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                </thead>
                <tbody>
                    <tr :key="d.username" v-for="d in this.sviZaposleniDermatolozi">
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

  <!-- Info o leku -->
  <div class="modal fade" id="podaci" tabindex="-1" role="dialog" aria-labelledby="About medicine" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="AboutMedicine">About medicine</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Name: {{lek_za_prikaz?.medicine.name}}<br />Nanananna</div>
        <div class="modal-body" align="left">Structure: {{lek_za_prikaz?.medicine.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: {{lek_za_prikaz?.medicine.manufacturer}}</div>
        <div class="modal-body" align="left">Note: {{lek_za_prikaz?.medicine.note}}</div>
        <div class="modal-body" align="left">Points: {{lek_za_prikaz?.medicine.points}}</div>
        <div class="modal-body" align="left">Type: {{lek_za_prikaz?.medicine.type}}</div>
        <div class="modal-body" align="left">Quantity: ... (max = {{lek_za_prikaz?.quantity}})</div>
        <div class="modal-body" align="left">Expiry date: <input type="text" v-model="date"/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click.prevent="provera()">Reserve</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
    import MedicineDataService from '../service/MedicineDataService.js';

export default {
    setup() {
      return { UtilService}
    },

	data: function () {
		return {
            pharmacy: null,
            sviZaposleniFarmaceuti : [] ,
            sviZaposleniDermatolozi : [],
            lekovi: [],
            lek_za_prikaz: null,
            date: null
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
      funkcija(l) {
        this.lek_za_prikaz = l;
      },
      provera() {
        if (this.date == null) {
          alert("You must enter date.\nDate must be in one of form \n17.03.2021.\n7.3.2021.");
          return false;
        }
        var splitovano = this.date.split('.');
        if (splitovano.lenght != 4) {
          alert("You forget dot.\nDate must be in one of form \n17.03.2021.\n7.3.2021.");
          return false;
        }
        if (splitovano[0].length == 0 | splitovano[1].lenght == 0 | splitovano[2].lenght == 0 | splitovano[3] != 0 ) {
          alert("Date must be in one of form \n17.03.2021.\n7.3.2021.");
          return false;
        }
        for (var karakter in this.date) {
          if (karakter == '.') continue;
          if (karakter <= '0' | karakter >= '9') {
            alert("You wrote the letter.\nDate must be in one of form \n17.03.2021.\n7.3.2021.");
            return false;
          }
        }
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
      MedicineDataService.getMedicineForPharmacy(this.id)
        .then(response => {
            this.lekovi = response.data;
        });
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