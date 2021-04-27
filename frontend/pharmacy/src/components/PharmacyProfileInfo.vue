<template>
<div>
  <RatingModal modalId="rating-modal" v-model="rating" @rated="ratePharmacy"></RatingModal>


    <div v-if="pharmacy" class="row">
      <div class="col-md-4" align="left">
        <h2>{{pharmacy.name}}</h2>
        <h5>Address: {{UtilService.AddressToString(pharmacy.address)}}</h5>
        <p class="rating">Rating: {{pharmacy.rating}} / 5<br />
          Description: {{pharmacy.description}}</p>
      </div>

      <div class="col-md-6 text-left" align="center"></div>
      
      <div class="col-md-2">
      <button class="btn btn-block btn-primary">Subscribe</button>
      <button v-bind:disabled="!pharmacy.canVote" type="button" class="btn btn-block btn-primary" v-on:click="ratingModal(pharmacy)" data-toggle="modal" data-target="#rating-modal">
          {{getMyVote(pharmacy)>0 ? "Change Rate": "Rate"}}
      </button>
      </div>

    </div>
    

    <div class="row">
      <div class="col-md-12">

        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Medicines</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Pharmacists</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Dermatologists</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Appoitments</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Map</a></li>
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
                  <th>Price</th>
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
                      <th>{{l.currentPrice}}</th>
                      <td>{{l.inStock}}</td>
                      <td><form v-on:click.prevent="funkcija(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">Reserve</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <div id="menu1" class="tab-pane fade">
              <h3>Pharmacists</h3>
              <table>
                <tr>
                  <td> &emsp; </td>
                  <td align="left"><button type="button" class="btn btn-primary" data-toggle="modal" 
                    data-target="#filterFar">Filter</button></td>
                  <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  
                    <td> <input type="text" v-model="pharmaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretragaFarm()"> <input type="submit" value="Search"/></form></td>
                </tr>
                <tr>
                  <td> &emsp; </td>
                </tr>
              </table>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                </thead>
                <tbody>
                    <tr :key="f.name" v-for="f in this.sviZaposleniFarmaceuti">
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
              <table>
                <tr>
                  <td> &emsp; </td>
                  <td align="left"><button type="button" class="btn btn-primary" data-toggle="modal" 
                    data-target="#filter">Filter</button></td>
                  <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  
                    <td> <input type="text" v-model="dermaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretraga()"> <input type="submit" value="Search"/></form></td>
                </tr>
                <tr>
                  <td> &emsp; </td>
                </tr>
              </table>
                <table class="table table-striped">
                  <thead class="card-header">
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Address</th>
                  <th>Phone number</th>
                </thead>
                <tbody>
                    <tr :key="d.name" v-for="d in this.sviZaposleniDermatolozi">
                      <td>{{d.name}}</td>
                      <td>{{d.surname}}</td>
                      <td>{{UtilService.AddressToString(d.address)}}</td>
                      <td>{{d.phoneNumber}}</td>
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
            <div id="menu4" class="tab-pane fade active">
					          <Mapa/>
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
        <div class="modal-body" align="left">Name: {{lek_za_prikaz?.medicine.name}}</div>
        <div class="modal-body" align="left">Structure: {{lek_za_prikaz?.medicine.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: {{lek_za_prikaz?.medicine.manufacturer}}</div>
        <div class="modal-body" align="left">Note: {{lek_za_prikaz?.medicine.note}}</div>
        <div class="modal-body" align="left">Points: {{lek_za_prikaz?.medicine.points}}</div>
        <div class="modal-body" align="left">Type: {{lek_za_prikaz?.medicine.type}}</div>
        <div class="modal-body" align="left">Price: {{lek_za_prikaz?.currentPrice}}</div>
        <div class="modal-body" align="left">Quantity: <input type="text" v-model="kolicina"/> (max = {{lek_za_prikaz?.inStock}})</div>
        <div class="modal-body" align="left">Expiry date: <input type="date" v-model="datum"/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="provera(lek_za_prikaz)">Reserve</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

     <!-- Filter dermatologa -->
  <div class="modal fade" id="filter" tabindex="-1" role="dialog" aria-labelledby="Filter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Filtercic">Infomation for search</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">First name: <input type="text" v-model="filterIme"/></div>
        <div class="modal-body" align="left">Last name: <input type="text" v-model="filterPrez"/></div>
        <div class="modal-body" align="left">Phone number: <input type="text" v-model="filterBroj"/></div>
        <div class="modal-body" align="left">State: <input type="text" v-model="filterAdrD"/></div>
        <div class="modal-body" align="left">City: <input type="text" v-model="filterAdrG"/></div>
        <div class="modal-body" align="left">Street: <input type="text" v-model="filterAdrU"/></div>
        <div class="modal-body" align="left">Number: <input type="text" v-model="filterAdrB"/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="filter(true)">Seaarch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

 <!-- Filter farmaceut -->
  <div class="modal fade" id="filterFar" tabindex="-1" role="dialog" aria-labelledby="Filter" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Filtercic">Infomation for search</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">First name: <input type="text" v-model="filterIme"/></div>
        <div class="modal-body" align="left">Last name: <input type="text" v-model="filterPrez"/></div>
        <div class="modal-body" align="left">Phone number: <input type="text" v-model="filterBroj"/></div>
        <div class="modal-body" align="left">State: <input type="text" v-model="filterAdrD"/></div>
        <div class="modal-body" align="left">City: <input type="text" v-model="filterAdrG"/></div>
        <div class="modal-body" align="left">Street: <input type="text" v-model="filterAdrU"/></div>
        <div class="modal-body" align="left">Number: <input type="text" v-model="filterAdrB"/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="filter(false)">Seaarch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
    import MedicineDataService from '../service/MedicineDataService.js';
    import RatingModal from '@/components/RatingModal.vue';
    import Mapa from "../components/Maps.vue";

export default {
  components: {
    RatingModal,
    Mapa,
  },
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
            datum: null,
            kolicina: null,
            max_kolicina: 1,
            pharmaSearch: "", dermaSearch: "",
            filterIme: "", filterPrez: "", filterBroj: "",
            filterAdrD: "", filterAdrG: "", filterAdrU: "", filterAdrB: "",
            rating: {
              title: "",
              body: "",
              ratingValue: "0",
              oldValue: null,
              pharmacy: null,
            },
            userId: 1,
		}
	},
  methods: {
    filter(filtDermatologa) {
      var suma = this.filterIme.length + this.filterPrez.length + this.filterBroj 
        + this.filterAdrD.length + this.filterAdrG.length + this.filterAdrU.length + this.filterAdrB.length;
      if (suma == 0) {
        alert("You must enter some parameter for filter");
        return;
      }

      if (filtDermatologa) {            // za dermatologe
        DermatologistDataService.filterDermatologistPharmacy(this.id, this.dermaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
          this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
          .then(response => {
            this.sviZaposleniDermatolozi = response.data;});
      }
      else {                            // za farmaceute
        PharmacistDataService.filterPharmacistPharmacy(this.id, this.pharmaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
            this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
            .then(response => {
              this.sviZaposleniFarmaceuti = response.data;});
      }

    },
    pretraga() {
        if (this.dermaSearch.length == 0) {
          alert("Input someting for searching");
          return;
        }
        DermatologistDataService.searchDermatologistPharmacy(this.id, this.dermaSearch)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
      },
      loadPharmacyData() {
          let self = this;

          PharmacyDataService.getPharmacy(this.id) // HARDCODED
              .then(response => {
                  self.pharmacy = response.data; 
                  // console.log(this.id);
                  this.patients = response.data;
                  self.pharmacy.canVote = false;
                  // console.log(response.data);
                  this.canUserRatePharmacy()
              });
      },
      pretragaFarm() {
        if (this.pharmaSearch.length == 0) {
          alert("Input someting for searching");
          return;
        }
        PharmacistDataService.searchPharmacistPharmacy(this.id, this.pharmaSearch)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;});
      },
      DodajFarmaceuta() {
        window.location.href = "/addPharmacist/" + this.id;
      },
      funkcija(l) {
        this.lek_za_prikaz = l;
        this.max_kolicina = l.quantity;
      },
      provera(reserve_data) {
        var provera = 0;
        provera += this.proveri_broj(this.kolicina, "Quantity must be number.");
        provera += this.proveri_kolicinu(this.kolicina);
        if (provera != 0) return false;
        alert("Everything is okay");

        return this.reserveMedicine(reserve_data);
      },
      proveri_broj(unos, poruka) {
        if (this.kolicina == null) {
          alert("You must enter quantity.");
          return 1;
        }
        for (var karakter of unos) {
          if (this.datum == unos && karakter == '.') continue;
          if (karakter < '0' || karakter > '9') {
            alert(poruka);
            return 1;
          }
        }
        return 0;
      },
      proveri_kolicinu(unos) {
        var broj = parseInt(unos);
        if (broj < 0 || broj > this.max_kolicina) {
          alert("Quantity must be in interval [1, " + this.max_kolicina + "].");
          return 1;
        }
        return 0;
      },


      reserveMedicine(reserve_data) {

        let reserve_form = {
          medicineId: reserve_data.medicine.id,
          pharmacyId: this.pharmacy.id,
          patientId: null, // TODO: PATIENT FORM SESSION
          quantity: this.kolicina,
          expirityDate: this.datum
        };

        console.log(reserve_form);

        MedicineDataService.reserveMedicine(reserve_form)
        .then(response => {
          console.log("reserved");
          MedicineDataService.getMedicineForPharmacy(this.id)
            .then(response => {
                this.lekovi = response.data;
            });
          $('#podaci').modal('hide'); 

        });



      },
      ratingModal(p) {
          this.rating.pharmacy = p;
          this.rating.title = "Pharmacy Rating"
          this.rating.body = p.name;

          console.log()
          PharmacyDataService.getUserRating(this.userId, p.id).then(response => {
            if (response.data) {
              this.rating.ratingValue = "" + response.data.rating;

              console.log(response.data);
              if (response.data.rating > 0)
                this.rating.oldValue = this.rating.ratingValue;
              else
                this.rating.oldValue = null;
              console.log( this.rating.oldValue);

            }
          });

        },

        getMyVote(p){
          console.log(this.userId);
          PharmacyDataService.getUserRating(this.userId, p.id).then(response => {
            if (response.data) {
              p.voted = response.data.rating
            }
          });
          return p.voted;
        },

        canUserRatePharmacy() {
          this.pharmacy.canVote = false;
          PharmacyDataService.canUserRate(this.userId, this.pharmacy.id).then(response => {
            if (response.data) {
              console.log(response.data);
              this.pharmacy.canVote = response.data;
            }
          });
          return this.pharmacy;
        },

        ratePharmacy() {

          let pharmacy = this.rating.pharmacy;
          let rating = this.rating.ratingValue;

          if (rating < 1 || rating > 5) {
            alert("Wrong rate value");
            return
          }

          let rateObject = {
            user: this.userId,
            rating: rating
          }
        
          PharmacyDataService.ratePharmacy(pharmacy, rateObject).then(response => {
            if (response.data) {
              this.loadPharmacyData();
              $("#rating-modal").modal('hide');
              alert("Successfully voted!");
            }
          });
        },

  },
  created() {
      this.id = this.$route.params.id;
      
      // TODO: Auth
      this.userId = prompt("User id");

      PharmacistDataService.getAllPharmacistPharmacy(this.id)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;
        });
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
          1
  },
}
</script>