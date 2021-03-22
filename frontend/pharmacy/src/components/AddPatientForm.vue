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
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,}$" title="Email must be in form example@yahoo.com"
                    ></td>
            </tr>
            
            <tr>
                <td align="left">Input username:</td>
                <td id = "korIme">
                    <input 
                        type="text" id="username" v-model="registerData.username" required="required"
                        pattern="[a-zA-Z0-9]*" title="Username isn't valid"
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
                            pattern="[A-Z][a-zA-Z0-9]*" title="Address must start with capital letter"
                    ></td>
            </tr>
            <tr>
                <td align="left">Input address number:</td>
                <td id="adresa" >
                    <input 
                        type="text" id="address" v-model="registerData.address.number" required="required"></td>
            </tr>

            <tr>
                <td align="left">Input phone numer:</td>
                <td id="broj" ><input 
                    type="text" id="phoneNumber" v-model="registerData.phoneNumber" required="required"
                    pattern="[0-9]*" title="Phone number must number"
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
console.log("Majmun");
export default {
    name: 'AddPatientForm',
    data() {
        return {

            message: null,
            registerData: {
                name : "Ad",
                surname : "Sn",
                email : "test@test",
                username : "username",
                password : "password",
                address : {
                    state: "",
                    city: "",
                    street: "Temp ulica",
                    number: "123",
                },
                phoneNumber : "123123",
            }

            // pharmacist: [],
            // message: null,
            // name : null,
            // surname : null,
            // email : null,
            // username : null,
            // password : null,
            // address : null,
            // phoneNumber : null
        };
    },
    methods: {     
        proveraForme(e) {

            //e.preventDefault();
            let povratna = true;
            let sviRedovi = document.getElementsByTagName("tr");
            for (let red of sviRedovi) {                // brisem stare komentare
                if (red.cells.length == 3) red.removeChild(red.cells[2]);
            }

            //----- jedinstvenost -----
            // if (this.checkEmail()) {
            //     povratna = false;
            //     this.ispisPoruke(sviRedovi[2], "Email must be unque!");
            // }
            // if (this.checkUsername())  {
            //     povratna = false;
            //     this.ispisPoruke(sviRedovi[3], "Username must be unque!");
            // }

            if (!povratna) return;
            PatientDataService.SendPatient(this.registerData)
                .then(response => {
                    console.log("Majmun");
                })
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

        ispisPoruke(red, poruka) {
            if (red.cells.length > 2) return;
            let novaKolona = red.insertCell(2);
            novaKolona.innerHTML = poruka;
            novaKolona.style.color = "red";
        },

        checkUsername() {
            console.log("response");
            PatientDataService.checkUsername(this.username)
                .then(response => {
                    console.log(response);
                    if (response.data) {     // slobodno
                        document.getElementById("korIme").style.background = "white";
                        return true;
                    }
                    else {
                        document.getElementById("korIme").style.background = "red";
                        return false;
                    }
                });
        },

        checkEmail() {
            console.log("response");
            PatientDataService.checkEmail(this.email)
                .then(response => {
                    console.log(response);
                    return response.data;
                });
        }
    }
}
</script>