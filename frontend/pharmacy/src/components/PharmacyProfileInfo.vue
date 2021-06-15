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
      <button v-bind:disabled="this.user.role!='PATIENT'" class="btn btn-block btn-primary" v-on:click="subUnsub()">{{setSubBtnText()}}</button>
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
                  <th>Action/promotion</th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.lekovi">
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.medicine.specification.structure}}</td>
                      <td>{{l.medicine.manufacturer}}</td>
                      <td>{{l.medicine.note}}</td>
                      <td>{{l.medicine.points}}</td>
                      <td>{{l.medicine.type}}</td>
                       <!-- prava cena -->
                      <td v-if="l.priceType=='NORMAL'">{{l.currentPrice}}</td>
                      <td v-else>{{l.oldPrice}}</td>

                      <td>{{l.inStock}}</td>
                      <!-- popust/akcija -->
                      <td v-if="l.priceType=='NORMAL'"></td>
                      <td v-else-if="l.priceType=='ACTION'">{{l.currentPrice}} ({{100-l.currentPrice*100/l.oldPrice}}%)</td>
                      <td v-else>{{l.currentPrice}}</td>

                      <td v-if="user.role=='PATIENT' && l.medicine.perscription == 'WITHOUT_RECEIPT'"><form v-on:click.prevent="funkcija(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">Reserve</button></form></td>
                      <td v-else></td>
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
                  
                    <td> <input class="form-control" type="text" v-model="pharmaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretragaFarm()"> <input type="submit" data-toggle="modal" data-target="#obavestenje" value="Search"/></form></td>
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
                  <th>Start time</th>
                  <th>End time</th>
                </thead>
                <tbody>
                    <tr :key="f.name" v-for="f in this.sviZaposleniFarmaceuti">
                      <td>{{f.name}}</td>
                      <td>{{f.surname}}</td>
                      <td>{{UtilService.AddressToString(f.address)}}</td>
                      <td>{{f.phoneNumber}}</td>
                      <td>{{(f.pharmacyWork.startHour[0] < 10 ? "0" + f.pharmacyWork.startHour[0] : f.pharmacyWork.startHour[0])}}:{{(f.pharmacyWork.startHour[1] < 10 ? "0" + f.pharmacyWork.startHour[1] : f.pharmacyWork.startHour[1])}}</td>
                      <td>{{(f.pharmacyWork.endHour[0] < 10 ? "0" + f.pharmacyWork.endHour[0] : f.pharmacyWork.endHour[0])}}:{{(f.pharmacyWork.endHour[1] < 10 ? "0" + f.pharmacyWork.endHour[1] : f.pharmacyWork.endHour[1])}}</td>
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
                  
                    <td> <input type="text"  class="form-control" v-model="dermaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretraga()"> <input type="submit" data-toggle="modal" data-target="#obavestenje" value="Search"/></form></td>
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
                  <th>Start time</th>
                  <th>End time</th>
                </thead>
                <tbody>
                    <tr :key="d.name" v-for="d in this.sviZaposleniDermatolozi">
                      <td>{{d.name}}</td>
                      <td>{{d.surname}}</td>
                      <td>{{UtilService.AddressToString(d.address)}}</td>
                      <td>{{d.phoneNumber}}</td>
                      <td>{{(d.pharmacyWork.startHour[0] < 10 ? "0" + d.pharmacyWork.startHour[0] : d.pharmacyWork.startHour[0])}}:{{(d.pharmacyWork.startHour[1] < 10 ? "0" + d.pharmacyWork.startHour[1] : d.pharmacyWork.startHour[1])}}</td>
                      <td>{{(d.pharmacyWork.endHour[0] < 10 ? "0" + d.pharmacyWork.endHour[0] : d.pharmacyWork.endHour[0])}}:{{(d.pharmacyWork.endHour[1] < 10 ? "0" + d.pharmacyWork.endHour[1] : d.pharmacyWork.endHour[1])}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu3" class="tab-pane in fade">

              <Appointments class="col-md-12 mt-3" :filterPharmacyId="id" />
              
            </div>
            <div id="menu4" align-v="start" class="tab-pane fade active">
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
        <div class="modal-body" align="left">Structure: {{lek_za_prikaz?.medicine.specification.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: {{lek_za_prikaz?.medicine.manufacturer}}</div>
        <div class="modal-body" align="left">Note: {{lek_za_prikaz?.medicine.note}}</div>
        <div class="modal-body" align="left">Points: {{lek_za_prikaz?.medicine.points}}</div>
        <div class="modal-body" align="left">Type: {{lek_za_prikaz?.medicine.type}}</div>
        <div class="modal-body" align="left">Price: {{lek_za_prikaz?.currentPrice}}</div>
        <div class="modal-body" align="left">Action/promotion:
          <label v-if="lek_za_prikaz?.priceType=='NORMAL'">No</label>
          <label v-else-if="lek_za_prikaz?.priceType=='ACTION'">Action {{100-lek_za_prikaz?.currentPrice*100/lek_za_prikaz?.oldPrice}}% (original price = {{lek_za_prikaz?.oldPrice}})</label>
          <label v-else>Promotion (original price = {{lek_za_prikaz?.oldPrice}})</label></div>
          <hr>
        <div class="modal-body" align="left">Quantity: <input class="form-control d-inline w-auto" type="text" v-model="kolicina"/> (max = {{lek_za_prikaz?.inStock}})</div>
        <div class="modal-body" align="left">Expiry date: <input class="form-control d-inline w-auto" type="date" v-model="datum"/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" data-dismiss="modal" v-on:click.prevent="provera(lek_za_prikaz)">Reserve</button>
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
          <button type="button" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="filter(true)">Seaarch</button>
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
          <button type="button" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="filter(false)">Seaarch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</div>

    <!-- obavestenje -->
  <div class="modal fade" id="obavestenje" tabindex="-1" role="dialog" aria-labelledby="poruka" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="poruka">Message</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left"><label>{{this.poruka}}</label></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="inicijalizujPoruku('Wait... Your require is in processing', false)">OK</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
    import PharmacyDataService from '../service/PharmacyDataService.js';
    import PharmacistDataService from '../service/PharmacistDataService.js';
    import DermatologistDataService from '../service/DermatologistDataService';
    import AuthService from '../service/AuthService.js';
    import UtilService from '../service/UtilService.js';
    import MedicineDataService from '../service/MedicineDataService.js';
    import RatingModal from '@/components/RatingModal.vue';
    import Mapa from "../components/Maps.vue";
    import Appointments from '@/components/Appointments'


export default {
  components: {
    RatingModal,
    Mapa,
    Appointments,
  },
    setup() {
      return { UtilService}
    },

	data: function () {
		return {
            test: null,
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
            poruka: "Please wait... Your requirement is in processing", 
            subscriptions: [],
		}
	},
  methods: {
    inicijalizujPoruku(pk) { 
      this.poruka = pk;
    },
    filter(filtDermatologa) {
      var suma = this.filterIme.length + this.filterPrez.length + this.filterBroj 
        + this.filterAdrD.length + this.filterAdrG.length + this.filterAdrU.length + this.filterAdrB.length;
      if (suma == 0) {
        this.poruka = "You must enter some parameter for filter";
        return;
      }

      if (filtDermatologa) {            // za dermatologe
        DermatologistDataService.filterDermatologistPharmacy(this.id, this.dermaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
          this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
          .then(response => {
            this.sviZaposleniDermatolozi = response.data;
            this.poruka = "Find " + this.sviZaposleniDermatolozi.length + " results";});
      }
      else {                            // za farmaceute
        PharmacistDataService.filterPharmacistPharmacy(this.id, this.pharmaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
            this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
            .then(response => {
              this.sviZaposleniFarmaceuti = response.data;
              this.poruka = "Find " + this.sviZaposleniFarmaceuti.length + " results";});
      }

    },
    pretraga() {
        if (this.dermaSearch.length == 0) {
          this.poruka = "Input someting for searching";
          return;
        }
        DermatologistDataService.searchDermatologistPharmacy(this.id, this.dermaSearch)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;
          this.poruka = "Find " + this.sviZaposleniDermatolozi.length + " results";});
      },
      loadPharmacyData() {
          let self = this;

          PharmacyDataService.getPharmacy(this.id) // HARDCODED
              .then(response => {
                  self.pharmacy = response.data; 
                  //za mapu
                  localStorage.setItem("adresa", '[' + self.pharmacy.address.longitude + ', ' + self.pharmacy.address.latitude + ']');
                  // console.log(this.id);
                  this.patients = response.data;
                  self.pharmacy.canVote = false;
                  // console.log(response.data);
                  this.canUserRatePharmacy();
              });
      },
      pretragaFarm() {
        if (this.pharmaSearch.length == 0) {
          this.poruka = "Input someting for searching";
          return;
        }
        PharmacistDataService.searchPharmacistPharmacy(this.id, this.pharmaSearch)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;
          this.poruka = "Find " + this.sviZaposleniFarmaceuti.length + " results";});
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

        return this.reserveMedicine(reserve_data)
      },
      proveri_broj(unos, poruka) {
        if (this.kolicina == null) {
          this.poruka = "You must enter quantity.";
          return 1;
        }
        for (var karakter of unos) {
          if (this.datum == unos && karakter == '.') continue;
          if (karakter < '0' || karakter > '9') {
            this.poruka = poruka;
            return 1;
          }
        }
        return 0;
      },
      proveri_kolicinu(unos) {
        var broj = parseInt(unos);
        if (broj < 0 || broj > this.max_kolicina) {
          this.poruka = "Quantity must be in interval [1, " + this.max_kolicina + "].";
          return 1;
        }
        return 0;
      },


      reserveMedicine(reserve_data) {

        let reserve_form = {
          medicineId: reserve_data.medicine.id,
          pharmacyId: this.pharmacy.id,
          patientId: this.userId, // TODO: PATIENT FORM SESSION
          quantity: this.kolicina,
          expirityDate: this.datum
        };

        console.log(reserve_form);

        MedicineDataService.reserveMedicineAsPatient(reserve_form)
        .then(response => {
          console.log("reserved");
          MedicineDataService.getMedicineForPharmacy(this.id)
            .then(response => {
                this.lekovi = response.data;
            })


          $('#podaci').modal('hide'); 

        }).catch(error => {
          // TODO: DODATI OSTALE PROVERE!!!!
          this.poruka = "Patient is alergic";
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
          // TODO: check if this works...
          if (p.voted == undefined) {p.voted = null; return};
          
          if (p.voted) alert("ee");
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
            this.poruka = "Wrong rate value";
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
              this.poruka = "Successfully voted!";
            }
          });
        },
        isPatientSubscribed(){
          // for(let subscription of this.user.subscriptions){
          //   if(subscription == this.pharmacy.id){
          //     return true;
          //   }
          // }
          for(let subscription of this.subscriptions){
            if(subscription.id == this.pharmacy.id) return true;
          }


          return false;
        },
        subUnsub(){
          PharmacyDataService.subscribeUnsubscribeToPharmacy(this.pharmacy, this.user.id).then(response => {
            PharmacyDataService.getSubscriptions(this.user.id).then(response => {
              this.subscriptions = response.data;
            });
          });
        },
        setSubBtnText(){
          if(this.isPatientSubscribed()) return "Unsubscribe";
          else return "Subscribe";
        }

  },
  created() {
      this.id = this.$route.params.id;
      this.user = AuthService.getCurrentUser();

      this.userId = AuthService.getLoggedIdOrLogout();
      if (this.userId == null) return;
      
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
      PharmacyDataService.getSubscriptions(this.user.id).then(response => {
        this.subscriptions = response.data;
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
  },
}
</script>