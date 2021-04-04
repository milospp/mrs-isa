<template>
    <table>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make appointment"></form> </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Get report"></form> </td>
            <td></td>
            <td></td>
       </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="DodajFarmaceuta()">
                    <input type="submit" class="btn btn-primary" value="Add pharmacist"></form> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Add dermatologist"></form> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="Make action and promotion"></form> </td>
            <td> </td>
            <td></td>
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
                    <tr :key="l" v-for="l in this.lekovi">
                      <td>{{l.medicine.code}}</td>
                      <td>{{l.medicine.name}}</td>
                      <td>{{l.medicine.type}}</td>
                      <td>{{l.quantity}}</td>
                      <td><form v-on:click.prevent="funkcija(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View</button></form></td>
                  </tr>
                </tbody>
              </table>
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
        <div class="modal-body" align="left">Quantity: <input type="text" v-model="quantity" placeholder=quantity/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" v-on:click.prevent="provera()">Save changes</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DermatologistDataService from '../service/DermatologistDataService.js';
import PharmacistDataService from '../service/PharmacistDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
export default {
    name: 'HpPharmacyAdmin',
    data() {
        return {
            sviZaposleniFarmaceuti : [],
            sviZaposleniDermatolozi : [],
            lekovi: [],
            name: null, structure: null, manufacturer: null,
            note: null, points: null, type: null, quantity: null
        };
    },
    created() {
	    this.id = this.$route.params.id; 
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
      DodajFarmaceuta() {
        window.location.href = "/addPharmacist/" + this.id;
      },
      funkcija(l) {
        this.name = l.medicine.name;
        this.structure =  l.medicine.structure;
        this.manufacturer = l.medicine.manufacturer;
        this.note = l.medicine.note;
        this.points = l.medicine.points;
        this.type = l.medicine.type;
        this.quantity = l.quantity;
      },
      provera() {
        var provera = 0;
        provera += this.provera_prazan(this.name, "You must enter name");
        provera += this.provera_prazan(this.structure, "You must enter structure");
        provera += this.provera_prazan(this.manufacturer, "You must enter manufacturer");
        provera += this.provera_prazan(this.note, "You must enter note");
        provera += this.provera_prazan(this.points, "You must enter points");
        provera += this.provera_prazan(this.type, "You must enter type");
        provera += this.provera_prazan(this.quantity, "You must enter quantity");
        provera += this.proveri_broj(String(this.quantity), "Quantity must be number.");
        provera += this.proveri_broj(String(this.points), "Points must be number.");
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