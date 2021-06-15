<template>

    <div class="d-flex justify-content-center">
        <form v-on:submit.prevent="formCheck(this)" class="col-md-8">
            <div class="form-group row">
                <label for="inputName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                <input 
                        type="text" class="form-control" id="inputName" v-model="registerData.name" required
                        pattern="[A-Z][a-zA-Z]*" title="Name must start with capital letter" size="31" placeholder="Name"
                >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputSurname" class="col-sm-2 col-form-label">Surname</label>
                <div class="col-sm-10">
                <input 
                        type="text" class="form-control" id="inputSurname" v-model="registerData.surname" required
                        pattern="[A-Z][a-zA-Z]*" title="Surname must start with capital letter" size="31" placeholder="Surname"
                    >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-10">
                <input 
                        type="text" class="form-control" id="email" v-model="registerData.email" required size="31"
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com" placeholder="example@mail.com"
                    >
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
                <div class="col-sm-10">
                <input 
                        type="password" class="form-control" id="password" v-model="registerData.password" required
                        pattern="[a-zA-Z0-9]{6,}" size="31" title="Password must have minimum 6 symbols" placeholder="Password"
                    >
                </div>
            </div>

            <div class="form-group row">
                <label for="inputState" class="col-sm-2 col-form-label">State</label>
                <div class="col-sm-10">
                <input 
                        type="text" class="form-control" id="inputState" v-model="registerData.address.state" required
                            pattern="[A-Z][a-zA-Z| ]*" size="31" title="State must start with capital letter" placeholder="State"
                    >
                </div>
            </div>

            <div class="form-group row">
                <label for="inputCity" class="col-sm-2 col-form-label">City</label>
                <div class="col-sm-10">
                <input 
                        type="text" class="form-control" id="inputCity" v-model="registerData.address.city" required
                            pattern="[A-Z][a-zA-Z| ]*" size="31" title="City must start with capital letter" placeholder="City"
                    >
                </div>
            </div>

            <div class="form-group row">
                <label for="inputAddress" class="col-sm-2 col-form-label">Address and number</label>
                <div class="col-sm-10">
                <input 
                        type="text" class="form-control d-inline col-9" id="inputAddress" v-model="registerData.address.street" required
                            pattern="[A-Z][a-zA-Z0-9| ]*" title="Address must start with capital letter" placeholder="Address"
                    >
                <input 
                    type="text" class="form-control d-inline col-3" id="inputAddressNumber" v-model="registerData.address.number" required size="5"
                    pattern="[0-9][0-9a-zA-Z|/| ]*" title="Address number can have number, letters and /" placeholder="Number"
                >

                </div>
            </div>

            <div class="form-group row">
                <label for="inputPhoneNumber" class="col-sm-2 col-form-label">Phone Number</label>
                <div class="col-sm-10">
                <input 
                    type="text" class="form-control" id="inputPhoneNumber" v-model="registerData.phoneNumber" required
                    pattern="[0-9]*" size="31" title="Phone number must be number" placeholder="Phone Number"
                >
                </div>
            </div>
            <button type="submit" id="dugme" class="btn btn-primary">Register</button>


        </form>
    </div>

     <!-- <form v-on:submit.prevent="formCheck(this)" >
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
    </form> -->
</template>

<script>
import DermatologistDataService from '../service/DermatologistDataService.js';
export default {
    name: 'AddDermatologistForm',
    data() {
        return {
            message: null,
            registerData: {
                name : "",
                surname : "",
                email : "",
                password : "",
                phoneNumber : "",
                address : {
                    state: "",
                    city: "",
                    street: "",
                    number: "",
                }
            }
        };
    },
    methods: {      
        formCheck(e) {
            DermatologistDataService.SendDermatologist(this.registerData).then(response => {
                if (response.data) {
                    this.$toast.show(
                        "Dermatologist has been successfully added!",
                        {
                            position: "top", type: "success",
                        }
                    );
                }
                else{
                    this.$toast.show(
                        "This e-mail is already taken!",
                        {
                            position: "top", type: "error",
                        }
                    );
                }
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
    }
}
</script>