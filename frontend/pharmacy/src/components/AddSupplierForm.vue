<template>
     <form v-on:submit.prevent="formCheck(this)" >
        <table>
            <tr>
                <td align="right">Name:</td>
                <td colspan="2">
                    <input 
                        type="text" id="name" v-model="registerData.name" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Name must start with capital letter" size="31"
                    ></td>
            </tr>

            <tr>
                <td align="right">Surname:</td>
                <td colspan="2">
                    <input 
                        type="text" id="surname" v-model="registerData.surname" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Surname must start with capital letter" size="31"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">Email:</td>
                <td colspan="2">
                    <input 
                        type="text" id="email" v-model="registerData.email" required="required" size="31"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">Password:</td>
                <td colspan="2">
                    <input 
                        type="text" id="password" v-model="registerData.password" required="required"
                        pattern="[a-zA-Z0-9]{6,}" size="31" title="Password must have minimum 6 symbols"
                    ></td>
            </tr>

            <tr>
                <td align="right">Country:</td>
                <td colspan="2">
                    <input 
                        type="text" id="address" v-model="registerData.address.state" required="required"
                            pattern="[A-Z][a-zA-Z| ]*" size="31" title="State must start with capital letter"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">City:</td>
                <td colspan="2">
                    <input 
                        type="text" id="address" v-model="registerData.address.city" required="required"
                            pattern="[A-Z][a-zA-Z| ]*" size="31" title="City must start with capital letter"
                    ></td>
            </tr>

            <tr>
                <td align="right">Address and number:</td>
                <td>
                    <input 
                        type="text" id="address" v-model="registerData.address.street" required="required"
                            pattern="[A-Z][a-zA-Z0-9 ]*" title="Address must start with capital letter"
                    ></td>
                <td>
                    <input 
                        type="text" id="address" v-model="registerData.address.number" required="required" size="5" 
                        pattern="[0-9][0-9a-zA-Z/ ]*" title="Address number can have number and /"
                    ></td>
            </tr>

            <tr>
                <td align="right">Phone number:</td>
                <td colspan="2"><input 
                    type="text" id="phoneNumber" v-model="registerData.phoneNumber" required="required"
                    pattern="[0-9]*" size="31" title="Phone number must be a number"
                ></td>
            </tr>
            <tr>
                <td></td>
                <td colspan="2"><input type="submit" id="dugme" value="Register" style="width: 100%;"></td>
            </tr>
        </table>
    </form>
</template>

<script>
import SupplierDataService from '../service/SupplierDataService.js';
export default {
    name: 'AddSupplierForm',
    data() {
        return {
            message: null,
            registerData: {
                name : "Dostavljac",
                surname : "Dost",
                email : "dos@maildrop.cc",
                password : "password",
                phoneNumber : "0123123123",
                address : {
                    state: "State",
                    city: "City",
                    street: "Street",
                    number: "123",
                }
            }
        };
    },
    methods: {      
        formCheck(e) {
            SupplierDataService.SendSupplier(this.registerData)
				.catch(function (error) {
					if (error.response) {
						console.log(error.response.data);
					} else if (error.request) {
						console.log(error.request);
					}
					console.log("error.config");
					console.log(error.config);
				});
        },
    }
}
</script>