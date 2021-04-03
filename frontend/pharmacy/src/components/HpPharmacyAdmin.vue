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
          </ul>
        
          <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
              <h3>Medicines</h3>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Description</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Name 1</td>
                    <td>Description 1</td>
                  </tr>
                  <tr>
                    <td>Name 2</td>
                    <td>Description 2</td>
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
                    <tr :key="f.username" v-for="f in this.sviZaposleniFarmaceuti" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
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
                    <tr :key="d.username" v-for="d in this.sviZaposleniDermatolozi" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
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

    <!-- Info o adminu -->
  <div class="modal fade" id="pharmacy" tabindex="-1" role="dialog" aria-labelledby="Pharmacy info" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="PharmacyInfo">Pharmacy info</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
          <div class="modal-body" align="left">Name: <input type="text" v-model="pharmacyName" placeholder=pharmacyName/></div>
          <div class="modal-body" align="left">Description: <input type="text" v-model="pharmacyDesc" placeholder=pharmacyDesc/></div>
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
import PharmacyDataService from '../service/PharmacyDataService.js';
import AddPharmacistForm from '@/components/AddPharmacistForm.vue';
export default {
    name: 'HpPharmacyAdmin',
    data() {
        return {
            sviZaposleniFarmaceuti : [],
            sviZaposleniDermatolozi : [],
            pharmacy: null,
            pharmacyName: null,
            pharmacyDesc: null
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
      provera() {
        if (this.pharmacy.name.length == 0 || this.pharmacy.description.length == 0) {
          alert("Name and description can't be empty.")
          return false;
        }
        this.pharmacy.name = this.pharmacyName;
        this.pharmacy.description = this.pharmacyDesc;
        PharmacyDataService.setPharmacy(this.pharmacy)
          .then(response => {alert("You successfaly changed pharmacy info.");});
      }
    }
}
</script>