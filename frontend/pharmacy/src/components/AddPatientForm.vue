<template>
    <form v-on:submit.prevent="proveraForme(this)"> <!--v-on:submit.prevent="send" -->
        <table>
            <tr>
                <td align="left">Input name:</td>
                <td id="ime" >
                    <input 
                        type="text" id="name" v-model="registerData.name" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Name must start with capital letter"
                    ></td>
            </tr>

            <tr>
                <td align="left">Input surname:</td>
                <td id="prez" >
                    <input 
                        type="text" id="surname" v-model="registerData.surname" required="required"
                        pattern="[A-Z][a-zA-Z]*" title="Surname must start with capital letter"
                    ></td>
            </tr>
            
            <tr>
                <td align="left">Input email:</td>
                <td id="mejl" >
                    <input 
                        type="text" id="email" v-model="registerData.email" required="required"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com"
                    ></td>
            </tr>
            
            <tr>
                <td align="left">Input initial password:</td>
                <td id="lozinka" >
                    <input 
                        type="text" id="password" v-model="registerData.password" required="required"
                        pattern="[a-zA-Z0-9]{6,}" title="Password must have minimum 6 symbols"
                    ></td>
            </tr>

            <tr>
                <td align="left">Input address:</td>
                <td id="adresa" >
                    <input 
                        type="text" id="address" v-model="registerData.address.street" required="required"
                            pattern="[A-Z][a-zA-Z0-9 ]*" title="Address must start with capital letter"
                    ></td>
            </tr>
            <tr>
                <td align="left">Input address number:</td>
                <td id="adresa" >
                    <input 
                        type="text" id="address" v-model="registerData.address.number" required="required"></td>
            </tr>

            <tr>
                <td align="left">Input phone number:</td>
                <td id="broj" ><input 
                    type="text" id="phoneNumber" v-model="registerData.phoneNumber" required="required"
                    pattern="[0-9]*" title="Phone number must be a number"
                ></td>
            </tr>

            <tr>
                <td></td>
                <td><input type="submit" id="dugme" value="Dodaj"></td>
            </tr>
        </table>
    </form>
</template>

<script>
import PatientDataService from '../service/PatientDataService.js';
import PharmacistDataService from '../service/PharmacistDataService.js';
console.log("Majmun");
export default {
    name: 'AddPatientForm',
    data() {
        return {

            message: null,
            registerData: {
                name : "",
                surname : "",
                email : "",
                password : "",
                address : {
                    state: "",
                    city: "",
                    street: "",
                    number: "",
                },
                phoneNumber : "",
            }
        };
    },
    methods: {     
        proveraForme(e) {
            let povratna = true;
            let sviRedovi = document.getElementsByTagName("tr");
            for (let red of sviRedovi) {                // brisem stare komentare
                if (red.cells.length == 3) red.removeChild(red.cells[2]);
            }

            if (!povratna) return;
            PatientDataService.SendPatient(this.registerData)
				.catch(function (error) {
					if (error.response) {
						console.log(error.response.data);
					} else if (error.request) {
						console.log(error.request);
					}
					console.log("error.config");
					console.log(error.config);
				});
        }
    }
}
</script>