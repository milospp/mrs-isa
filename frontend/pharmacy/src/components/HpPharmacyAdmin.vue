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
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" class="btn btn-primary" value="See profile"></form> </td>
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
                    <tr :key="f.key" v-for="f in this.lekovi">
                      <td>{{f.medicine.code}}</td>
                      <td>{{f.medicine.name}}</td>
                      <td>{{f.medicine.type}}</td>
                      <td>{{f.quantity}}</td>
                      <td><form ><input type="submit" class="btn btn-primary" value="View"></form></td>
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
            lekovi: []
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
      }
    }
}
</script>