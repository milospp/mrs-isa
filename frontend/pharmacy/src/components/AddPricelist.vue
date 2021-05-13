<template>
    <div id="tab-medicines" class="tab-pane in fade active in">
        <table>
            <tr>
            <td> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</td>
                <td> <form v-on:click.prevent="sacuvajCenovnik()"> <button type="button" class="btn btn-primary"
                    data-toggle="modal" data-target="#obavestenje">Save changes</button></form></td>
            </tr>
            <tr>
            <td> &emsp; </td>
            </tr>
        </table>
        <table class="table table-striped">
            <thead class="card-header">
                <th>Code</th>
                <th>Name</th>
                <th>Type</th>
                <th>Price</th>
            </thead>
            <tbody>
                <tr :key="l" v-for="l in this.cenovnik?.medicines">
                <td>{{l.medicine.code}}</td>
                <td>{{l.medicine.name}}</td>
                <td>{{l.medicine.type}}</td>
                <td><input type="number" min="1" v-on:change="promenaCene(l.currentPrice, l.code)" v-model="l.currentPrice"/></td>
                </tr>
            </tbody>
        </table>
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
            <div v-if="this.porukaOk == true">
                 <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="promeniProzor()">OK</button>
            </div>
            <div v-else>
                 <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="inicijalizujPoruku('Wait... Your require is in processing')">OK</button>
            </div>
        </div>
      </div>
    </div>
  </div>
 
</template>

<script>
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
import AuthService from "../service/AuthService.js";

export default {
    name: 'MorePharmacyInfo',
    data() {
        return {
            poruka: "Wait... Your require is in processing", 
            cenovnik: {medicines: []},
            porukaOk: false,
        };
      
    },
    created() {
      this.id = AuthService.getCurrentUser().id;
      MedicineDataService.getMedicineForPharmacyAdmin(this.id)
      .then(response => {
          this.cenovnik.medicines = response.data;
          for (var lek of this.cenovnik.medicines) lek.currentPrice = 0;
          });
    },
    mounted() {  
    },
    methods : {
      inicijalizujPoruku(pk) { this.poruka = pk; },

      sacuvajCenovnik() {
        for (var med of this.cenovnik.medicines) {
          if (med.currentPrice <= 0) {
            this.poruka = "All prices must be positive numbers."
            return;
          }
        }
        PharmacyDataService.savePricelist(this.id, this.cenovnik)
          .then(response => {
            this.poruka = "Pricelist is successfully edited";
            this.porukaOk = true;
          });
      },
      promenaCene() {this.postojiPromena = true; },
      promeniProzor() {window.location.href = "/homePagePharmacyAdmin";},
    }
}
</script>