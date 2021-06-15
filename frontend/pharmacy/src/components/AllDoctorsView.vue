<template>
<div>
    <div class="row">
      <div class="col-md-12">

        <div>
          <ul class="nav nav-tabs">
            <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Pharmacists</a></li>
             <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">Dermatologists</a></li>
          </ul>

            <div class="tab-content">
            <div id="tab-medicines" class="tab-pane in fade active in">
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
                  <th>Pharmacy</th>
                  <th>Start time</th>
                  <th>End time</th>
                </thead>
                <tbody>
                    <tr :key="f.name" v-for="f in this.sviZaposleniFarmaceuti">
                      <td>{{f.name}}</td>
                      <td>{{f.surname}}</td>
                      <td>{{UtilService.AddressToString(f.address)}}</td>
                      <td>{{f.phoneNumber}}</td>
                      <td>{{f.pharmacyWork.pharmacyName}}</td>
                      <td>{{(f.pharmacyWork.startHour[0] < 10 ? "0" + f.pharmacyWork.startHour[0] : f.pharmacyWork.startHour[0])}}:{{(f.pharmacyWork.startHour[1] < 10 ? "0" + f.pharmacyWork.startHour[1] : f.pharmacyWork.startHour[1])}}</td>
                      <td>{{(f.pharmacyWork.endHour[0] < 10 ? "0" + f.pharmacyWork.endHour[0] : f.pharmacyWork.endHour[0])}}:{{(f.pharmacyWork.endHour[1] < 10 ? "0" + f.pharmacyWork.endHour[1] : f.pharmacyWork.endHour[1])}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div id="menu1" class="tab-pane fade">
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
                  <th>Pharmacy</th>
                  <th>Start time</th>
                  <th>End time</th>
                </thead>
                <tbody>
                    <tr :key="d.name" v-for="d in this.sviZaposleniDermatolozi">
                      <td>{{d.name}}</td>
                      <td>{{d.surname}}</td>
                      <td>{{UtilService.AddressToString(d.address)}}</td>
                      <td>{{d.phoneNumber}}</td>
                      <td>{{d.pharmacyWork.pharmacyName}}</td>
                      <td>{{(d.pharmacyWork.startHour[0] < 10 ? "0" + d.pharmacyWork.startHour[0] : d.pharmacyWork.startHour[0])}}:{{(d.pharmacyWork.startHour[1] < 10 ? "0" + d.pharmacyWork.startHour[1] : d.pharmacyWork.startHour[1])}}</td>
                      <td>{{(d.pharmacyWork.endHour[0] < 10 ? "0" + d.pharmacyWork.endHour[0] : d.pharmacyWork.endHour[0])}}:{{(d.pharmacyWork.endHour[1] < 10 ? "0" + d.pharmacyWork.endHour[1] : d.pharmacyWork.endHour[1])}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            </div>
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
    import PharmacistDataService from '../service/PharmacistDataService.js';
    import DermatologistDataService from '../service/DermatologistDataService';
    import UtilService from '../service/UtilService.js';

export default {
    setup() {
      return { UtilService}
    },
	data: function () {
        return {
            sviZaposleniFarmaceuti : [] ,
            sviZaposleniDermatolozi : [],
            pharmaSearch: "", dermaSearch: "",
            filterIme: "", filterPrez: "", filterBroj: "",
            filterAdrD: "", filterAdrG: "", filterAdrU: "", filterAdrB: "",
            poruka: "Wait... Your require is in processing", 
        }
	},
    mounted() {
      PharmacistDataService.getAllPharmacists()
        .then(response => {
          this.sviZaposleniFarmaceuti = response.data;
        });
      DermatologistDataService.getAllDermatologists()
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;});
    },
  methods: {
    inicijalizujPoruku(pk) { this.poruka = pk; },
    filter(filtDermatologa) {
      var suma = this.filterIme.length + this.filterPrez.length + this.filterBroj 
        + this.filterAdrD.length + this.filterAdrG.length + this.filterAdrU.length + this.filterAdrB.length;
      if (suma == 0) {
        this.poruka = "You must enter some parameter for filter";
        return;
      }

      if (filtDermatologa) {            // za dermatologe
        DermatologistDataService.filterAllDermatologists(this.dermaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
          this.filterAdrD, this.filterAdrG, this.filterAdrU, this.filterAdrB,)
          .then(response => {
            this.sviZaposleniDermatolozi = response.data;
            this.poruka = "Find " + this.sviZaposleniDermatolozi.length + " results";});
      }
      else {                            // za farmaceute
        PharmacistDataService.filterAllPharmacists(this.pharmaSearch, this.filterIme, this.filterPrez, this.filterBroj, 
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
        DermatologistDataService.searchAllDermatologists(this.dermaSearch)
        .then(response => {
          this.sviZaposleniDermatolozi = response.data;
          this.poruka = "Find " + this.sviZaposleniDermatolozi.length + " results";});
    },
    pretragaFarm() {
        if (this.pharmaSearch.length == 0) {
            this.poruka = "Input someting for searching";
            return;
        }
        PharmacistDataService.searchAllPharmacists(this.pharmaSearch)
        .then(response => {
            this.sviZaposleniFarmaceuti = response.data;
            this.poruka = "Find " + this.sviZaposleniFarmaceuti.length + " results";});
    },

  },

}
</script>