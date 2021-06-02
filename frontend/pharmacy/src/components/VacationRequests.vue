<template>
  <h3>All vacation requests</h3>
    <!-- Tabela sa podacima -->
    <table class="table table-striped">
        <thead class="card-header">
        <th>Doctor</th>
        <th>Pharmacy</th>
        <th>Start date</th>
        <th>End date</th>
        <th>Vacation status</th>
        <th></th>
        <th></th>
    </thead>
    <tbody>
        <tr :key="z" v-for="z in this.zahteviZaGodisnji">
            <td>{{z.doctor.name}} {{z.doctor.surname}}</td>
            <td>{{z.pharmacyName}}</td>
            <td>{{z.startDate[1]}}/{{z.startDate[2]}}/{{z.startDate[0]}}</td>
            <td>{{z.endDate[1]}}/{{z.endDate[2]}}/{{z.endDate[0]}}</td>
            <td>Waiting</td>
            <td><form v-on:click.prevent="postaviZahtev(z)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaciZahtev">View</button></form></td>
            <td><form v-on:click.prevent="postaviZahtev(z)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#prihvatiZahtev">Approve</button></form></td>
           
        </tr>
    </tbody>
    </table>

    <!-- Prihvatanje zahteva -->
  <div class="modal fade" id="prihvatiZahtev" tabindex="-1" role="dialog" aria-labelledby="poruka" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="poruka">Vacation request</h5>
          
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left">Doctor: {{this.odabraniZahtev?.doctor.name}} {{this.odabraniZahtev?.doctor.surname}} ({{this.odabraniZahtev?.doctor.phoneNumber}})</div>
        <div class="modal-body" align="left">Start date: {{this.odabraniZahtev?.startDate[1]}}/{{this.odabraniZahtev?.startDate[2]}}/{{this.odabraniZahtev?.startDate[0]}}</div>
        <div class="modal-body" align="left">End date: {{this.odabraniZahtev?.endDate[1]}}/{{this.odabraniZahtev?.endDate[2]}}/{{this.odabraniZahtev?.endDate[0]}}</div>
        <div class="modal-body" align="left">Approve: 
          <select id="potvrdaZahteva" style="width: 50%;" v-model="potvrdaZahteva" required="required">
              <option>Accept</option>
              <option>Deny</option>
          </select></div>
          <div v-if="potvrdaZahteva=='Deny'" class="modal-body" align="left">Why not: <input type="text" v-model="zastoNe"/></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="obradiZahtev()" data-dismiss="modal">Save</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

      <!-- obavestenja -->
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
import VacationDataService from '../service/VacationDataService.js';
import AuthService from "../service/AuthService.js";

export default {
    name: 'MorePharmacyInfo',
    data() {
        return {
            poruka: "Wait... Your require is in processing", 
            zahteviZaGodisnji: [], odabraniZahtev: null, potvrdaZahteva: null, zastoNe: null,
        };
      
    },
    created() {
      this.id = AuthService.getCurrentUser().id;
      this.osveziZahteve();
    },
    mounted() {  
    },
    methods : {
      osveziZahteve() {
        VacationDataService.getAllVacations(this.id)
        .then(response => {
          this.zahteviZaGodisnji = response.data;
        });
      },

      inicijalizujPoruku(pk) { this.poruka = pk; },
      postaviZahtev(zahtev) { this.odabraniZahtev = zahtev; },
      obradiZahtev() {
        if (this.potvrdaZahteva == null) { this.poruka = "You must choose to accept or deny request."; return; }
        if (this.potvrdaZahteva == "Deny" && (this.zastoNe == null || this.zastoNe?.length == 0))
          { this.poruka = "You must input reason why do not accept vacation request."; return; }
        VacationDataService.saveVacationApproval(this.id, this.odabraniZahtev, this.potvrdaZahteva, this.zastoNe)
          .then(response => {
            this.poruka = "Vacation request is successfully saved.";
            this.osveziZahteve();
          });
      },

    }
}
</script>