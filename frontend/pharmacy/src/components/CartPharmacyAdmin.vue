<template>
    <!-- Tabela sa podacima -->
    <div class="row">
      <div class="col-md-12">
        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Your cart</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">All medicines</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines in cart</h3>
              <div v-if="this.korpaLekova.length != 0"> 
                <table><tr>
                    <td><button type="button" align="center" class="btn btn-primary" data-toggle="modal" 
                      data-target="#potvrda">Delete all</button></td>
                    <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  <td><button type="button" align="center" class="btn btn-primary" data-toggle="modal" 
                    data-target="#podesiDatume">Order</button></td>
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
                    <tr :key="l" v-for="l in this.korpaLekova">
                      <td>{{l?.medicine.code}}</td>
                      <td>{{l?.medicine.name}}</td>
                      <td>{{l?.medicine.type}}</td>
                      <td><input type="number" size="10"  v-on:change.prevent="sacuvajKorpu()" v-model="l.quantity" placeholder=l?.quantity></td>
                      <td><form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
                      <td><form v-on:click.prevent="postaviLek(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#brisanje">Delete</button></form></td>
                  </tr>
                </tbody>
              </table>
            </div>
          <!-- </div> -->

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
        <div class="modal-body" align="left">In cart: <input type="number" v-model="quantity" placeholder=quantity/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary"  data-dismiss="modal" v-on:click.prevent="promeniKolicinu()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

<!-- brisanje svega -->
   <div class="modal fade" id="potvrda" tabindex="-1" role="dialog" aria-labelledby="Brisanje" aria-hidden="true">
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

    <!-- unos datuma -->
  <div class="modal fade" id="podesiDatume" tabindex="-1" role="dialog" aria-labelledby="datumi" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="dati">Enter period:</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">End date: <input type="datetime-local" v-model="krajnjiDatum"/></div>
        <div class="modal-footer">
          <button type="button" v-on:click.prevent="poruci()" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#obavestenje">Make order</button>
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

</template>

<script>
import MedicineDataService from '../service/MedicineDataService.js';
import OrderDataService from '../service/OrderDataService.js';
import AuthService from "../service/AuthService.js";

export default {
    name: 'CartPharmacyAdmin',
    data() {
        return {
            sviLekovi: [], poruka: "Wait... Your require is in processing", 
            korpaLekova: [], odabraniLek: null, 
            krajnjiDatum: null, quantity: 0, 
        };
    },
    created() {
      this.id = AuthService.getCurrentUser().id; 
      MedicineDataService.getAllMedicines()
      .then(response => {
          this.sviLekovi = response.data;
          this.prevediKorpu();
      });
    },
    mounted() {  
      MedicineDataService.getAllMedicines()
      .then(response => {
          this.sviLekovi = response.data;
          this.prevediKorpu();
      });
    },
    methods : {
      inicijalizujPoruku(pk) { 
        this.poruka = pk;
      },
        osveziLekove() {
           MedicineDataService.getAllMedicines()
            .then(response => {
                this.sviLekovi = response.data;
                this.prevediKorpu();
          });        
        },
        postaviLek(l) {
            this.odabraniLek = l;
            this.quantity = this.odabraniLek.quantity;
        },
        promeniKolicinu() { 
          this.odabraniLek.quantity = this.quantity;
          this.sacuvajKorpu();
        },
        dodajUKorpu(lek) {
            var nadjen = false;
            for (var l of this.korpaLekova)
                if (l.medicine.code == lek.code) {
                    nadjen = true;
                    break;
                }
            if (nadjen) {
                this.poruka = "You already add this medicine to your cart";
                return;
            }

            this.korpaLekova[this.korpaLekova.length] = {"medicine": lek, "quantity": 1};
            this.poruka = "You successfully added medicine to your cart";
            this.sacuvajKorpu();
        },
        obrisiLekove() {
          this.korpaLekova = [];
          this.poruka = "You successfully clean your cart";
          this.sacuvajKorpu();
        },
        obrisiLek() {
            var korpica = [];
            var brojac = 0;
            for (var l of this.korpaLekova)
                if (l.medicine.code != this.odabraniLek.medicine.code) {
                    korpica[brojac] = l;
                    brojac++;
                }
            this.korpaLekova = korpica;
            this.poruka = "You successfully deleted medicine from your cart";
            this.sacuvajKorpu();
        },
        poruci() {
          if (!this.proveri_datum()) return false;
          if (!this.proveri_kolicinu()) return false;
          OrderDataService.addOrder(this.id, this.korpaLekova, this.krajnjiDatum)
            .then(response => { 
              if (response.data == 0) {
                this.poruka = "You successfully made order";
                this.korpaLekova = [];
                this.sacuvajKorpu();
              }
              else {
                this.poruka = "End date must be in feature";
              }
            });
        },
        proveri_kolicinu() {
          for (var l in this.korpaLekova) if (l.quantity <= 0) {
            this.poruka = "Quantity in cart must be positive number."
            return false;
          }
          return true;
        },
        proveri_datum(){
          if (!this.krajnjiDatum) {
            this.poruka = "You must enter end date";
            return false;}
          return true;
        },
        sacuvajKorpu() {
          var stringic = "[";
          for (var lek of this.korpaLekova) {
            stringic += '{"medicine": "';
            stringic += lek.medicine.code;
            stringic += '", "quantity": "';
            stringic += lek.quantity;
            stringic += '"}'
            if (lek != this.korpaLekova[this.korpaLekova.length -1]) stringic += ", ";
          }
          stringic += "]";
          localStorage.setItem("korpa", stringic);
        },
        prevediKorpu() {
          var kodKolicina = JSON.parse(localStorage.getItem("korpa"));
          if (kodKolicina == null) return;
          var brojac = 0;
          for (var elem of kodKolicina)
            for (var lek of this.sviLekovi)
              if (elem.medicine == lek.code) {
                this.korpaLekova[brojac] = {"medicine": lek, "quantity": elem.quantity};
                brojac++;
                break;
                }
        },
    }
}
</script>