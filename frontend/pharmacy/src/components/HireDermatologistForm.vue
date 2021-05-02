<template>
    <table class="table table-striped">
        <thead class="card-header">
            <th>First name</th>
            <th>Last name</th>
            <th>Address</th>
            <th>Phone number</th>
            <th>&emsp;</th>
        </thead>
        <tbody>
            <tr :key="d.name" v-for="d in this.sviDermatolozi">
                <td>{{d.name}}</td>
                <td>{{d.surname}}</td>
                <td>{{d.address["state"]}}, {{d.address["city"]}}, {{d.address["street"]}}, {{d.address["number"]}}</td>
                <td>{{d.phoneNumber}}</td>
                <td><form v-on:click.prevent="podesi(d)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#radnovreme">Hire</button></form></td>
            </tr>
        </tbody>
    </table>

 <div class="modal fade" id="radnovreme" tabindex="-1" role="dialog" aria-labelledby="radVr" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="vreme">Input working hours</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div>
            <label>Start hour: </label>
        <input 
            type="text" id="startHour" v-model="startHour" required="required" size="7"
            title="Start hour must be in form 17:17">
        </div>
        <div>
            <label>End end: </label>
        <input 
            type="text" id="endHour" v-model="endHour" required="required" size="7"
            title="End hour must be in form 17:17">
            </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="zaposli(true)" data-dismiss="modal">Hire</button>
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
</template>

<script>
import DermatologistDataService from '../service/DermatologistDataService.js';
export default {
    name: 'HireDermatologistForm',
    data() {
        return {
            zaposliRadnika: null,
            startHour: "",
            endHour: "",
            sviDermatolozi: [],
            poruka: "Wait... Your require is in processing", 
        };
    },
    created() {
	this.id = this.$route.params.id;
      DermatologistDataService.getDermAdminHire(this.id)
        .then(response => {
          this.sviDermatolozi = response.data;});
    },
    mounted() {
      DermatologistDataService.getDermAdminHire(this.id)
        .then(response => {
          this.sviDermatolozi = response.data;});
    },
    methods: {
        osveziKandidate() {
            DermatologistDataService.getDermAdminHire(this.id)
                .then(response => {
                    this.sviDermatolozi = response.data;});
        },
        inicijalizujPoruku(pk) { 
            this.poruka = pk;
        },
        zaposli() {
            if (!this.provera_vremena()) return false;
            DermatologistDataService.hireDermatologist(this.id, this.zaposliRadnika, this.startHour, this.endHour)
                .then(response => {
                if (response.data == 0) { 
                    this.inicijalizujPoruku("You successfully hired a dermatologist.");
                    this.osveziKandidate();
                    return true;
                } 
                else if (response.data == -1) { 
                    this.inicijalizujPoruku("Something goes wrong."); 
                    return false;
                }
                else if (response.data == -2) { 
                    this.inicijalizujPoruku("This dermatologist is already hired in your pharmacy."); 
                    return false;
                }
                this.inicijalizujPoruku("Doctor has job in that time in another pharmacy!");
                return false;});
        },
        podesi(rad) { this.zaposliRadnika = rad; },
        provera_vremena() {
            if ( this.startHour.length == 0 || this.endHour.length == 0) {
                this.poruka = "You must enter start and end hour";
                return false;
            }
            var splitStart = this.startHour.split(':');
            var splitEnd = this.endHour.split(':');
            if ( splitStart.length != 2 || splitStart.length != 2) {
                this.poruka = "You must enter time in form 17:37";
                return false;
            }
            try {
                if (parseInt(splitStart[0]) > parseInt(splitEnd[0])) {
                    this.poruka = "Start hour must be before end hour";
                    return false;
                    }
                else if (parseInt(splitStart[0]) == parseInt(splitEnd[0]) 
                    && parseInt(splitStart[1]) > parseInt(splitStart[1])) {
                    this.poruka = "Start hour must be before end hour";
                    return false;
                    }
                if (parseInt(splitEnd[0]) > 24) {
                    this.poruka = "Hours must be in interval 0-24";
                    return false;
                    }
                else if (parseInt(splitEnd[0]) > 60) {
                    this.poruka = "Minutes must be in interval 0-60";
                    return false;
                    }
                return true;
            }
            catch {
                this.poruka = "You must enter time in form 17:37";
            }
            
        }
    }
}
</script>
