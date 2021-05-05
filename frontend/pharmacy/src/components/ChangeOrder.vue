<template>
    <div v-if="!this.narudzbenica">
        <label> Something goes wrong </label>
    </div>
    <div v-else-if="this.narudzbenica.allOffer.length == 0">
        <label> Start date: {{this.narudzbenica.startDate[1]}}/{{this.narudzbenica.startDate[2]}}/{{this.narudzbenica.startDate[0]}} 
        {{this.narudzbenica.startDate[3]}}:{{this.narudzbenica.startDate[4]}} <br />
             End date: <input type="datetime-local" v-model="this.narudzbenica.endDate" placeholder="this.narudzbenica.endDate"/>  <br />
             Chosen offer: /</label>

        <div class="row">
        <div class="col-md-12">
            <div>
            <ul class="nav nav-tabs">
                <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Order info</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">All medicines</a></li>
            </ul>
            
            <div class="tab-content">
                <div id="tab-medicines" class="tab-pane in fade active in">
                <h3>Medicines</h3>
                <div v-if="this.narudzbenica.allMedicines.length != 0"> 
                <table><tr>
                    <td><button type="button" align="center" class="btn btn-primary" data-toggle="modal" 
                      data-target="#potvrdaBrisanje">Delete all</button></td>
                    <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  <td><button type="button" align="center" class="btn btn-primary" data-toggle="modal" 
                    data-target="#potvrda">Save</button></td>
                </tr><tr><td>&emsp;</td></tr></table>
               </div>
              <table class="table table-striped">
                 <thead class="card-header">
                  <th>Code</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Quantity</th>
                  <th></th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.narudzbenica.allMedicines">
                      <td>{{l?.medicine.code}}</td>
                      <td>{{l?.medicine.name}}</td>
                      <td>{{l?.medicine.type}}</td>
                      <td><input type="number" size="10" min="1" v-on:change.prevent="proveri_broj(l, false)" v-model="l.quantity" placeholder=l?.quantity></td>
                      <td><form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
                      <td><form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#brisanje">Delete</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div id="menu1" class="tab-pane fade">
                <h3>All medicines</h3>
                    <table class="table table-striped">
                    <thead class="card-header">
                    <th>Code</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Structure</th>
                    <th></th>
                    </thead>
                    <tbody>
                        <tr :key="l" v-for="l in this.sviLekovi">
                        <td>{{l.code}}</td>
                        <td>{{l.name}}</td>
                        <td>{{l.type}}</td>
                        <td>{{l.structure}}</td>
                        <td><form v-on:click.prevent="dodajUKorpu(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje">Add to cart</button></form></td>
                    </tr>
                    </tbody>
                </table>
                </div>
            </div>
            </div>
        </div>
        </div>
    </div>         <!-- uslovni div -->
    <div v-else>
        <label> Start date: {{this.narudzbenica.startDate[1]}}/{{this.narudzbenica.startDate[2]}}/{{this.narudzbenica.startDate[0]}} 
        {{this.narudzbenica.startDate[3]}}:{{this.narudzbenica.startDate[4]}} <br />
         End date: {{this.narudzbenica.endDate[1]}}/{{this.narudzbenica.endDate[2]}}/{{this.narudzbenica.endDate[0]}} 
            {{this.narudzbenica.endDate[3]}}:{{this.narudzbenica.endDate[4]}} <br />
         Chosen offer: {{this.narudzbenica.chosenOffer?.supplier.name}} {{this.narudzbenica.chosenOffer?.supplier.surname}}</label>

        <div class="row">
        <div class="col-md-12">
            <div>
            <ul class="nav nav-tabs">
                <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Order info</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">All offer</a></li>
            </ul>
            
            <div class="tab-content">
                <div id="tab-medicines" class="tab-pane in fade active in">
                <h3>Medicines</h3>
              <table class="table table-striped">
                 <thead class="card-header">
                  <th>Code</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Quantity</th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.narudzbenica.allMedicines">
                      <td>{{l?.medicine.code}}</td>
                      <td>{{l?.medicine.name}}</td>
                      <td>{{l?.medicine.type}}</td>
                      <td>{{l?.quantity}}</td>
                      <td><form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div id="menu1" class="tab-pane fade">
                <h3>Offers</h3>
                    <table class="table table-striped">
                    <thead class="card-header">
                    <th>Supplier</th>
                    <th>Price</th>
                    <th>Start date</th>
                    <th>End date</th>
                    <th></th>
                    </thead>
                    <tbody>
                        <tr :key="o" v-for="o in this.narudzbenica.allOffer">
                        <td>{{o?.supplier.name}} {{o?.supplier.surname}}</td>
                        <td>{{o?.price}}</td>
                        <td>{{o?.startDate.startDate[1]}}/{{o?.startDate.startDate[2]}}/{{o?.startDate.startDate[0]}} 
                            {{o?.startDate.startDate[3]}}:{{o?.startDate.startDate[4]}} </td>
                        <td>{{o?.endDate.endDate[1]}}/{{o?.endDate.endDate[2]}}/{{o?.endDate.endDate[0]}} 
                            {{o?.endDate.endDate[3]}}:{{o?.endDate.endDate[4]}}</td>
                        <td><form v-on:click.prevent="postaviPonudu(o)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaciPonude">View</button></form></td>
                    </tr>
                    </tbody>
                </table>
                </div>
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
        <div class="modal-body" align="left">Code: {{this.odabraniLek?.medicine.code}}</div>
        <div class="modal-body" align="left">Name: {{this.odabraniLek?.medicine?.name}}</div>
        <div class="modal-body" align="left">Structure: {{this.odabraniLek?.medicine?.structure}}</div>
        <div class="modal-body" align="left">Manufacturer: {{this.odabraniLek?.medicine?.manufacturer}}</div>
        <div class="modal-body" align="left">Note: {{this.odabraniLek?.medicine?.note}}</div>
        <div class="modal-body" align="left">Type: {{this.odabraniLek?.medicine?.type}}</div>
        <div class="modal-body" align="left">Points: {{this.odabraniLek?.medicine?.points}}</div>
        <div class="modal-body" align="left">With receipt: {{this.odabraniLek?.medicine?.perscription}}</div>
        <div class="modal-body" align="left">Shape: {{this.odabraniLek?.medicine?.shape}}</div>
        <div v-if="this.narudzbenica?.allOffer.length == 0">
            <div class="modal-body" align="left">In order: <input type="number" min="1" v-model="kolicina" placeholder=kolicina/></div>
        </div><div v-else>
            <div class="modal-body" align="left">In order: {{this.odabraniLek?.quantity}}</div>
        </div>
        <div class="modal-footer">
          <div v-if="this.narudzbenica?.allOffer.length == 0">
            <button type="button" class="btn btn-primary"  data-dismiss="modal" v-on:click.prevent="proveri_broj(kolicina, true)">Save changes</button>
          </div>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

<!-- brisanje svega -->
   <div class="modal fade" id="potvrdaBrisanje" tabindex="-1" role="dialog" aria-labelledby="Brisanje" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Brisa">Are you sure you want to delete all medicine from cart?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="obrisiLekove()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- brisanje iz korpe -->
   <div class="modal fade" id="brisanje" tabindex="-1" role="dialog" aria-labelledby="Brisanje" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Bris">Are you sure you want to delete medicine {{this.odabraniLek?.medicine.name}}?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="obrisiLek()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="inicijalizujPoruku('Wait... Your require is in processing')">OK</button>
        </div>
      </div>
    </div>
  </div>

     <!-- potvrda -->
  <div class="modal fade" id="potvrda" tabindex="-1" role="dialog" aria-labelledby="Potvrda" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Potvrdica">Are you sure you want save changes on this order?</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="sacuvajIzmene()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

   <!-- Info o ponudi -->
  <div class="modal fade" id="podaciPonude" tabindex="-1" role="dialog" aria-labelledby="About offer" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="AboutOffer">About offer</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Supplier: {{this.odabranaPonuda?.supplier.name}} {{this.odabranaPonuda?.supplier.surname}}</div>
        <div class="modal-body" align="left">Supplier address: {{this.odabranaPonuda?.supplier.address.state}} {{this.odabranaPonuda?.supplier.address.city}} 
            {{this.odabranaPonuda?.supplier.address.street}} {{this.odabranaPonuda?.supplier.address.number}}</div>
        <div class="modal-body" align="left">Price: {{this.odabranaPonuda?.price}}</div>
        <div class="modal-body" align="left">Start date: {{this.odabranaPonuda?.startDate.startDate[1]}}/{{this.odabranaPonuda?.startDate.startDate[2]}}/{{this.odabranaPonuda?.startDate.startDate[0]}} 
            {{this.odabranaPonuda?.startDate.startDate[3]}}:{{this.odabranaPonuda?.startDate.startDate[4]}}</div>
        <div class="modal-body" align="left">End date: {{this.odabranaPonuda?.endDate.endDate[1]}}/{{this.odabranaPonuda?.endDate.endDate[2]}}/{{this.odabranaPonuda?.endDate.endDate[0]}} 
            {{this.odabranaPonuda?.endDate.endDate[3]}}:{{this.odabranaPonuda?.endDate.endDate[4]}}</div>
        <div class="modal-body" align="left">Offer description: {{this.odabranaPonuda?.supplier.offerDescription}}</div>
        <div class="modal-footer">
          <div v-if="this.narudzbenica?.chosenOffer == null">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#potvrdaPonude" data-dismiss="modal">Choose offer</button>
          </div>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

       <!-- potvrda -->
  <div class="modal fade" id="potvrda" tabindex="-1" role="dialog" aria-labelledby="Potvrda" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="Potvrdica">Are you sure you want choose this offer? <br/>
            Supplier: {{this.odabranaPonuda?.supplier.name}} {{this.odabranaPonuda?.supplier.surname}} <br/>
            Price: {{this.odabranaPonuda?.price}} <br/></h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="odaberiPonudu()" data-dismiss="modal">Yes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import OrderDataService from '../service/OrderDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';

export default {
    name: 'ChangeOrder',
    data() {
        return {
            narudzbenica: null, sviLekovi: [], odabraniLek: null, kolicina: null, datumi: null,
            poruka: "Wait... Your require is in processing", odabranaPonuda: null,
        };
    },
    created() {
        this.procitajNarudzbenicu();
    },
    mounted() {
    },
    deleted() {
        localStorage.removeItem("lekoviNarudzbenice");
    },
    methods : {
        sacuvajKorpu() {
          var stringic = '[';
          for (var lek of this.narudzbenica.allMedicines) {
            stringic += '{"medicine": "';
            stringic += lek.medicine.code;
            stringic += '", "quantity": "';
            stringic += lek.quantity;
            stringic += '"}'
            if (lek != this.narudzbenica.allMedicines[this.narudzbenica.allMedicines.length -1]) stringic += ", ";
          }
          stringic += "]";
          localStorage.setItem("lekoviNarudzbenice", stringic);
        },
        procitajKorpu() {
          var kodKolicina = JSON.parse(localStorage.getItem("lekoviNarudzbenice"));
          if (kodKolicina == null) return;
          var brojac = 0;
          for (var elem of kodKolicina)
            for (var lek of this.sviLekovi)
              if (elem.medicine == lek.code) {
                this.narudzbenica.allMedicines[brojac] = {"medicine": lek, "quantity": elem.quantity};
                brojac++;
                break;
                }
        },
        sacuvajNarudzbenicu() {
            var stringPonude = "{";
            stringPonude += '"startDate": [' + this.narudzbenica.startDate + "], ";
            stringPonude += '"endDate": [' + this.narudzbenica.endDate + "], ";
            stringPonude += '"author": {"name": "' + this.narudzbenica.author.name + '", "surname": "'
            + this.narudzbenica.author.surname + '"}';
            stringPonude += "}";
            localStorage.setItem("narudzbenica", stringPonude);
        },
        procitajNarudzbenicu() {
            var podaci = JSON.parse(localStorage.getItem("narudzbenica"));
            OrderDataService.findOneOrder(podaci)
                .then(response => {
                    this.narudzbenica = response.data;
                    if (this.narudzbenica == null) return;     // ne bi trebalo da se dogodi ikad

                    this.podesiDatume();

                    if (this.narudzbenica.allOffer.length == 0) {
                        this.narudzbenica.endDate = this.narudzbenica.endDate[0] + "-" + this.narudzbenica.endDate[1] + "-"
                        + this.narudzbenica.endDate[2] + "T" + this.narudzbenica.endDate[3] + ":" +
                        this.narudzbenica.endDate[4];
                        MedicineDataService.getAllMedicines()
                            .then(response => {
                                this.sviLekovi = response.data;
                                this.procitajKorpu();
                                });
                    } 
                    else {          // Kada ne sme izmena
                        this.narudzbenica.allMedicines = this.narudzbenica.allMedicines;
                        for (var o of this.narudzbenica.allOffer) this.podesiDvocifrene(o);
                    } 
                });

        },
        podesiDatume() {
            // sacuvamo originalni izgled datuma
            this.datumi = [[this.narudzbenica.startDate[1], this.narudzbenica.startDate[2], this.narudzbenica.startDate[3], this.narudzbenica.startDate[4]],
             [this.narudzbenica.endDate[0], this.narudzbenica.endDate[1], this.narudzbenica.endDate[2], this.narudzbenica.endDate[3], this.narudzbenica.endDate[4]]];
            
            this.podesiDvocifrene(this.narudzbenica);
        },
        podesiDvocifrene(objekat) {
            if (objekat.endDate[1] < 10) objekat.endDate[1] = "0" + objekat.endDate[1];
            if (objekat.endDate[2] < 10) objekat.endDate[2] = "0" + objekat.endDate[2];
            if (objekat.endDate[3] < 10) objekat.endDate[3] = "0" + objekat.endDate[3];
            if (objekat.endDate[4] < 10) objekat.endDate[4] = "0" + objekat.endDate[4];

            if (objekat.startDate[1] < 10) objekat.startDate[1] = "0" + objekat.startDate[1];
            if (objekat.startDate[2] < 10) objekat.startDate[2] = "0" + objekat.startDate[2];
            if (objekat.startDate[3] < 10) objekat.startDate[3] = "0" + objekat.startDate[3];
            if (objekat.startDate[4] < 10) objekat.startDate[4] = "0" + objekat.startDate[4]; 
        },
        postaviLek(l) {
            this.odabraniLek = l;
            this.kolicina = this.odabraniLek.quantity;
        },
        promeniKolicinu() { 
          this.odabraniLek.quantity = this.kolicina;
          this.sacuvajKorpu();
        },
        obrisiLekove() {
          this.narudzbenica.allMedicines = [];
          this.poruka = "You successfully clean your cart";
          this.sacuvajKorpu();
        },
        obrisiLek() {
            var korpica = [];
            var brojac = 0;
            for (var l of this.narudzbenica.allMedicines)
                if (l.medicine.code != this.odabraniLek.medicine.code) {
                    korpica[brojac] = l;
                    brojac++;
                }
            this.narudzbenica.allMedicines = korpica;
            this.poruka = "You successfully deleted medicine from your cart";
            this.sacuvajKorpu();
        },
        inicijalizujPoruku(pk) { 
            this.poruka = pk;
        },
        proveri_broj(broj, jesteBroj) {
            if (!jesteBroj) {       // kada mu prosledim lek
                this.odabraniLek = broj; 
                broj = broj.quantity;
            }
            if (broj > 0) {this.sacuvajKorpu(); return;}
            this.kolicina = 1;
            this.promeniKolicinu();
        },
        sacuvajIzmene() {
            if (this.narudzbenica.allMedicines.length == 0) {
                this.poruka = "You must have minimum 1 medicine in order";
                return;
            }

            // podesi datume
            this.narudzbenica.startDate[1] = this.datumi[0][0];
            this.narudzbenica.startDate[2] = this.datumi[0][1];
            this.narudzbenica.startDate[3] = this.datumi[0][2];
            this.narudzbenica.startDate[4] = this.datumi[0][3];
            // this.narudzbenica.endDate = this.datumi[1];

            OrderDataService.editOrder(this.narudzbenica)
                .then(response => {
                    if (response.data == 0) this.poruka = "You successfully edited order";
                    else this.poruka = "End date must be in future";});
        },
        dodajUKorpu(lek) {
            var nadjen = false;
            for (var l of this.narudzbenica.allMedicines)
                if (l.medicine.code == lek.code) {
                    nadjen = true;
                    break;
                }
            if (nadjen) {
                this.poruka = "You already add this medicine to your cart";
                return;
            }

            this.narudzbenica.allMedicines[this.narudzbenica.allMedicines.length] = {"medicine": lek, "quantity": 1};
            this.poruka = "You successfully added medicine to your order";
            this.sacuvajKorpu();
        },
        postaviPonudu(o) { this.odabranaPonuda = o; },
        odaberiPonudu() {
            OrderDataService.chooseOffer(this.odabranaPonuda)
                .then(response => {
                    if (response.data == 0) {
                        this.poruka = "You successfully chose offer";
                        this.procitajNarudzbenicu();
                    }
                    else this.poruka = "Offer is not available (end date is passed)";
                });
        },
    }
}
</script>