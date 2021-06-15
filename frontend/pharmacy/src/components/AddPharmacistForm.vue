<template>
     <form v-on:submit.prevent="proveraForme(this)">
        <table>
            <tr>
                <td align="left">Name:</td>
                <td colspan="2">
                    <input 
                        type="text" class="form-control" id="name" v-model="info.registerData.name" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Name must start with capital letter"  size="31"
                    ></td>
            </tr>

            <tr>
                <td align="left">Surname:</td>
                <td colspan="2">
                    <input 
                        type="text" class="form-control" id="surname" v-model="info.registerData.surname" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Surname must start with capital letter"  size="31"
                    ></td>
            </tr>
            
            <tr>
                <td align="left">Email:</td>
                <td colspan="2">
                    <input 
                        type="left" class="form-control" id="email" v-model="info.registerData.email" required="required"  size="31"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com"
                    ></td>
            </tr>
            
            <tr>
                <td align="left">Initial password:</td>
                <td colspan="2">
                    <input 
                        type="left" class="form-control" id="password" v-model="info.registerData.password" required="required"
                        pattern="[a-zA-Z0-9]{6,}" title="Password must have minimum 6 symbols"  size="31"
                    ></td>
            </tr>

            <tr>
                <td align="left">Country:</td>
                <td colspan="2">
                    <input 
                        type="text" class="form-control" id="address" v-model="info.registerData.address.state" required="required"
                            pattern="[A-Z][a-zA-Z| ]*" title="State must start with capital letter"  size="31"
                    ></td>
            </tr>
            
            <tr>
                <td align="left">City:</td>
                <td colspan="2">
                    <input 
                        type="text" class="form-control" id="address" v-model="info.registerData.address.city" required="required"
                            pattern="[A-Z][a-zA-Z| ]*"  title="City must start with capital letter"  size="31"
                    ></td>
            </tr>

            <tr>
                <td align="left">Address and number:</td>
                <td>
                    <input 
                        type="text" class="form-control" id="address" v-model="info.registerData.address.street" required="required"
                            pattern="[A-Z][a-zA-Z0-9| ]*" title="Address must start with capital letter"
                    ></td>
                <td>
                    <input 
                        type="text" class="form-control" id="address" v-model="info.registerData.address.number" required="required" size="5" 
                        pattern="[0-9][0-9a-zA-Z|/| ]*" title="Address number can have number, letters and /"
                    ></td>
            </tr>

            <tr>
                <td align="left">Phone numer:</td>
                <td colspan="2"><input 
                    type="text" id="phoneNumber" class="form-control" v-model="info.registerData.phoneNumber" required="required"
                    pattern="[0-9]*" title="Phone number must number" size="31"
                ></td>
            </tr>
            
            <tr>
                <td align="left">Working hours:</td>
                <td colspan="2" align="left">
                    <span>&ensp; from &nbsp;</span>
                    <input 
                        type="time" id="startHour" v-model="info.startHour" required="required"
                    >
                    <span>&ensp; to &ensp;</span>
                     <input 
                        type="time" id="endHour" v-model="info.endHour" required="required"
                    ></td>
            </tr>

            <tr>
                <td></td>
                <td colspan="2"><input type="submit" data-toggle="modal" data-target="#obavestenje" id="dugme" value="   Add   "></td>
            </tr>
        </table>
    </form>

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
import PharmacistDataService from '../service/PharmacistDataService.js';
export default {
    name: 'AddPharmacistForm',
    data() {
        return {
            info: {
                registerData: {
                    name : "",
                    surname : "",
                    email : "",
                    username : "",
                    password : "",
                    address : {
                        state: "",
                        city: "",
                        street: "",
                        number: "",
                    },
                    phoneNumber : "",
                    
                },
                startHour: "",
                endHour: "",
            },
            poruka: "Wait... Your require is in processing", 
            porukaOk: false,
        };
    },
    created() {
        this.id = this.$route.params.id; 
    },
    methods: {
        inicijalizujPoruku(pk) { this.poruka = pk; },
        promeniProzor() { window.location.href = "/homePagePharmacyAdmin"; },

        proveraForme(e) {
            if (!this.provera_vremena()) {
                this.poruka = "Start hour must be smaller than end hour";
                return false;
            }
            PharmacistDataService.SendPharmacist(this.id, this.$data.info)
            .then(response => {
                if (response.data == 0) { 
                    this.poruka = "You successfully hired a pharmacist"; 
                    this.porukaOk = true;
                    return true;
                } 
                this.poruka = "Email is not unique!";
                return false;
            });
		},
        provera_vremena() {
            var splitStart = this.info.startHour.split(':');
            var splitEnd = this.info.endHour.split(':');
            if (parseInt(splitStart[0]) > parseInt(splitEnd[0])) return false;
            else if (parseInt(splitStart[0]) == parseInt(splitEnd[0]) 
                && parseInt(splitStart[1]) > parseInt(splitStart[1])) return false;
            return true;
        }
    }
}
</script>