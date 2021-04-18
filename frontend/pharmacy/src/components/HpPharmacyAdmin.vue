<template>
    <table>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make appointment"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
             <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make pricelist"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
           <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make action and promotion"></form> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="DodajFarmaceuta()">
                    <input type="submit" class="btn btn-primary" value="Add pharmacist"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Add dermatologist"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Add medicine"></form> </td>
        </tr>
        <tr> 
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Get report"></form> </td>
            <td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
            <td align="left"><button type="button" class="btn btn-primary" data-toggle="modal" 
                data-target="#pharmacy">See pharmacy</button></td>
        </tr>
    </table> 
    <!-- Tabela sa podacima -->
    <div class="row">
      <div class="col-md-12">
        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Medicines</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Pharmacists</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu2">Dermatologists</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu3">Invalid receipts</a></li>
            <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu4">Map</a></li>
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines</h3>
              <table class="table table-striped">
                 <thead class="card-header">
                  <th>Code</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Price</th>
                  <th>Quantity</th>
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="l" v-for="l in this.lekovi">
                      <td>{{l.medicine.code}}</td>
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.medicine.type}}</td>
                      <th>{{l.currentPrice}}</th>
                      <td>{{l.inStock}}</td>
                      <td><form v-on:click.prevent="funkcija(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
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
                    <tr :key="f.username" v-for="f in this.sviZaposleniFarmaceuti">
                      <td>{{f.name}}</td>
                      <td>{{f.surname}}</td>
                      <td>{{f.address["state"]}}, {{f.address["city"]}}, {{f.address["street"]}}, {{f.address["number"]}}</td>
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
                    data-target="#filterDer">Filter</button></td>
                  <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                       &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                  
                    <td> <input type="text" v-model="dermaSearch"  size="50"/></td>
                    <td> <form v-on:click.prevent="pretragaDerm()"> <input type="submit" value="Search"/></form></td>
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
                    <tr :key="d.username" v-for="d in this.sviZaposleniDermatolozi">
                      <td>{{d.name}}</td>
                      <td>{{d.surname}}</td>
                      <td>{{d.address["state"]}}, {{d.address["city"]}}, {{d.address["street"]}}, {{d.address["number"]}}</td>
                      <td>{{d.phoneNumber}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu3" class="tab-pane fade">
              <h3>All invalid receipts</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Receipt</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Doctor 1</td>
                    <td>Receipt 1</td>
                  </tr>
                  <tr>
                    <td>Doctior 2</td>
                    <td>Receipt 2</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- mora na kraj jer inace ne radi kako valja -->
            <div id="menu4" class="tab-pane fade active">
					          <Mapa/>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Info o apoteci -->
  <div class="modal fade" id="pharmacy" tabindex="-1" role="dialog" aria-labelledby="Pharmacy info" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="PharmacyInfo">Pharmacy information</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Name: <input type="text" v-model="pharmacyName" placeholder=pharmacyName/></div>
        <div class="modal-body" align="left">Description: <input type="text" v-model="pharmacyDesc" placeholder=pharmacyDesc/></div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click.prevent="proveraApoteka()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
        <div class="modal-body" align="left">Name: <input type="text" v-model="name" placeholder=name/></div>
        <div class="modal-body" align="left">Structure: <input type="text" v-model="structure" placeholder=structure/></div>
        <div class="modal-body" align="left">Manufacturer: <input type="text" v-model="manufacturer" placeholder=manufacturer/></div>
        <div class="modal-body" align="left">Note: <input type="text" v-model="note" placeholder=note/></div>
        <div class="modal-body" align="left">Points: <input type="text" v-model="points" placeholder=points/></div>
        <div class="modal-body" align="left">Type: <input type="text" v-model="type" placeholder=type/></div>
        <div class="modal-body" align="left">Price: <input type="text" v-model="price" placeholder=quantity/></div>
        <div class="modal-body" align="left">Quantity: <input type="text" v-model="quantity" placeholder=quantity/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click.prevent="provera()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

      <!-- Filter dermatolog -->
  <div class="modal fade" id="filterDer" tabindex="-1" role="dialog" aria-labelledby="Filter" aria-hidden="true">
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
          <button type="button" class="btn btn-primary" v-on:click.prevent="filter(true)">Seaarch</button>
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
          <button type="button" class="btn btn-primary" v-on:click.prevent="filter(false)">Seaarch</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import DermatologistDataService from '../service/DermatologistDataService.js';
import PharmacistDataService from '../service/PharmacistDataService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
import Mapa from "../components/Maps.vue";
export default {
    name: 'HpPharmacyAdmin',
    components: {
      Mapa
    },
    data() {
        return {
            sviZaposleniFarmaceuti : [],
            sviZaposleniDermatolozi : [],
            pharmacy: null,
            pharmacyName: null, pharmacyDesc: null,
            lekovi: [],
            name: null, structure: null, manufacturer: null, price: null,
            note: null, points: null, type: null, quantity: null,
            dermaSearch: "", pharmaSearch: "",
            filterIme: "", filterPrez: "", filterBroj: "",
            filterAdrD: "", filterAdrG: "", filterAdrU: "", filterAdrB: "", 
        };
    },
    created() {
	    this.id = this.$route.params.id;
      PharmacyDataService.getPharmacyByIDAdmin(this.id)
        .then(response => {
          this.pharmacy = response.data;
          this.pharmacyName = this.pharmacy.name;
          this.pharmacyDesc = this.pharmacy.description;
        });
      PharmacistDataService.getAllPharmacistAdmin(this.id)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;});
      DermatologistDataService.getAllDermatologistAdmin(this.id)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
      MedicineDataService.getMedicineForPharmacyAdmin(this.id)
      .then(response => {
          this.lekovi = response.data;
      });
    },
    mounted() {
      PharmacistDataService.getAllPharmacistAdmin(this.id)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;});
          
      DermatologistDataService.getAllDermatologistAdmin(this.id)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
    },
    methods : {
      filter(filtDermatologa) {
        var suma = this.filterIme.length + this.filterPrez.length + this.filterBroj 
          + this.filterAdrD.length + this.filterAdrG.length + this.filterAdrU.length + this.filterAdrB.length;
        if (suma == 0) {
          alert("You must enter some parameter for filter");
          return;
        }

        if (filtDermatologa) {            // za dermatologe
          DermatologistDataService.filterDermatologistAdmin(this.id, this.filterIme, this.filterPrez, this.filterBroj, 
            this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
            .then(response => {
              this.sviZaposleniDermatolozi = response.data;});
        }
        else {                            // za farmaceute
          PharmacistDataService.filterPharmacistAdmin(this.id, this.filterIme, this.filterPrez, this.filterBroj, 
            this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
            .then(response => {
              this.sviZaposleniFarmaceuti = response.data;});
        }

      },
      pretragaDerm() {
        if (this.dermaSearch.length == 0) {
          alert("Input someting for searching");
          return;
        }
        DermatologistDataService.searchDermatologistAdmin(this.id, this.dermaSearch)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
      },
      pretragaFarm() {
        if (this.pharmaSearch.length == 0) {
          alert("Input someting for searching");
          return;
        }
        PharmacistDataService.searchPharmacistAdmin(this.id, this.pharmaSearch)
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;});
      },
      DodajFarmaceuta() {
        window.location.href = "/addPharmacist/" + this.id;
      },
      proveraApoteka() {
        if (this.pharmacyName.length == 0 || this.pharmacyDesc.length == 0) {
          alert("Name and description can't be empty.")
          return false;
        }
        this.pharmacy.name = this.pharmacyName;
        this.pharmacy.description = this.pharmacyDesc;
        PharmacyDataService.setPharmacy(this.pharmacy)
          .then(response => {alert("You successfaly changed pharmacy info.");});
      },
      funkcija(l) {
        this.name = l.medicine.name;
        this.structure =  l.medicine.structure;
        this.manufacturer = l.medicine.manufacturer;
        this.note = l.medicine.note;
        this.points = l.medicine.points;
        this.type = l.medicine.type;
        this.price = l.currentPrice;
        this.quantity = l.inStock;
      },
      provera() {
        var provera = 0;
        provera += this.provera_prazan(this.name, "You must enter name.");
        provera += this.provera_prazan(this.structure, "You must enter structure.");
        provera += this.provera_prazan(this.manufacturer, "You must enter manufacturer.");
        provera += this.provera_prazan(this.note, "You must enter note.");
        provera += this.provera_prazan(this.points, "You must enter points.");
        provera += this.provera_prazan(this.type, "You must enter type.");
        provera += this.provera_prazan(this.quantity, "You must enter quantity.");
        provera += this.provera_prazan(String(this.price), "You must enter price.");
        provera += this.proveri_broj(String(this.quantity), "Quantity must be number.");
        provera += this.proveri_broj(String(this.points), "Points must be number.");
        provera += this.proveri_cenu();
        if (provera != 0) return false;
        alert("Everything is okay");
        return true;
      },
      proveri_broj(unos, poruka) {
        for (var karakter of unos) {
          if (karakter < '0' || karakter > '9') {
            alert(poruka);
            return 1;
          }
        }
        return 0;
      },
      proveri_cenu() {
        var lista = this.price.split('.')
        if (lista.length > 2) {
          alert("Price must be in form 5.2");
          return 1;
        }
        for (var elem of lista) {
          var rez = this.proveri_broj(elem, "Price must be number in form 5.3") 
          if (rez == 1) return 1;
        }
        return 0;
      },
      provera_prazan(unos, poruka) {
        if (unos == null) {
          alert(poruka);
          return 1;
        }
        if (unos.length == 0) {
          alert(poruka);
          return 1;
        }
        return 0;
      }
    }
}
</script>