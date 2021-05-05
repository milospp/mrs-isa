<template>
     <form v-on:submit.prevent="proveraForme(this)">
        <table>
            <tr>
                <td align="right">Name:</td>
                <td colspan="2">
                    <input 
                        type="text" id="name" v-model="registerData.name" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Name must start with capital letter"  size="31"
                    ></td>
            </tr>

            <tr>
                <td align="right">Surname:</td>
                <td colspan="2">
                    <input 
                        type="text" id="surname" v-model="registerData.surname" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Surname must start with capital letter"  size="31"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">Email:</td>
                <td colspan="2">
                    <input 
                        type="text" id="email" v-model="registerData.email" required="required"  size="31"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">Initial password:</td>
                <td colspan="2">
                    <input 
                        type="text" id="password" v-model="registerData.password" required="required"
                        pattern="[a-zA-Z0-9]{6,}" title="Password must have minimum 6 symbols"  size="31"
                    ></td>
            </tr>

            <tr>
                <td align="right">Country:</td>
                <td colspan="2">
                    <input 
                        type="text" id="address" v-model="registerData.address.state" required="required"
                            pattern="[A-Z][a-zA-Z| ]*" title="State must start with capital letter"  size="31"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">City:</td>
                <td colspan="2">
                    <input 
                        type="text" id="address" v-model="registerData.address.city" required="required"
                            pattern="[A-Z][a-zA-Z| ]*"  title="City must start with capital letter"  size="31"
                    ></td>
            </tr>

            <tr>
                <td align="right">Address and number:</td>
                <td>
                    <input 
                        type="text" id="address" v-model="registerData.address.street" required="required"
                            pattern="[A-Z][a-zA-Z0-9| ]*" title="Address must start with capital letter"
                    ></td>
                <td>
                    <input 
                        type="text" id="address" v-model="registerData.address.number" required="required" size="5" 
                        pattern="[0-9][0-9a-zA-Z|/| ]*" title="Address number can have number, letters and /"
                    ></td>
            </tr>

            <tr>
                <td align="right">Phone numer:</td>
                <td colspan="2"><input 
                    type="text" id="phoneNumber" v-model="registerData.phoneNumber" required="required"
                    pattern="[0-9]*" title="Phone number must number" size="31"
                ></td>
            </tr>
            
            <tr>
                <td align="right">Working hours:</td>
                <td colspan="2" align="left">
                    <span>&ensp; from &nbsp;</span>
                    <input 
                        type="time" id="startHour" v-model="startHour" required="required"
                    >
                    <span>&ensp; to &ensp;</span>
                     <input 
                        type="time" id="endHour" v-model="endHour" required="required"
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
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click.prevent="inicijalizujPoruku('Wait... Your require is in processing')">OK</button>
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
            registerData: {
                name : "Farnaceyt",
                surname : "Farm",
                email : "farm@maildrop.cc",
                username : "farm",
                password : "password",
                address : {
                    state: "Stat",
                    city: "City",
                    street: "Street",
                    number: "123",
                },
                phoneNumber : "111222333",
            },
            startHour: "10:00",
            endHour: "12:00",
            poruka: "Wait... Your require is in processing", 
        };
    },
    created() {
        this.id = this.$route.params.id; 
    },
    methods: {
        inicijalizujPoruku(pk) { 
            this.poruka = pk;
        },
        proveraForme(e) {
            if (!this.provera_vremena()) {
                this.poruka = "Start hour must be smaller than end hour";
                return false;
            }
            PharmacistDataService.SendPharmacist(this.id, this.$data)
            .then(response => {
                if (response.data == 0) { 
                    this.poruka = "You successfully hired a pharmacist"; 
                    return true;
                } 
                this.poruka = "Email is not unique!";
                return false;
            });
		},
        provera_vremena() {
            var splitStart = this.startHour.split(':');
            var splitEnd = this.endHour.split(':');
            if (parseInt(splitStart[0]) > parseInt(splitEnd[0])) return false;
            else if (parseInt(splitStart[0]) == parseInt(splitEnd[0]) 
                && parseInt(splitStart[1]) > parseInt(splitStart[1])) return false;
            return true;
        }
    }
}
</script>