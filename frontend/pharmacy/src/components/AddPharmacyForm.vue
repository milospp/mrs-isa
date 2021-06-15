<template style="width: 100%; height: 100%">


    <div class="d-flex justify-content-center">
        <form v-on:submit.prevent="formCheck(this)" class="col-md-8">
            <div class="form-group row">
                <label for="inputName" class="col-sm-2 col-form-label">Name of pharmacy</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="pharmacyName" v-model="registerData.name" placeholder="Name" 
                      required="required" pattern="[A-Z][a-zA-Z ]*" title="Name must start with capital letter">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputStrName" class="col-sm-2 col-form-label">Street name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="street" v-model="registerData.address.street" placeholder="Street"
                      required="required" pattern="[A-Z][a-zA-Z ]*" title="Name of street must start with capital letter">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputBNumber" class="col-sm-2 col-form-label">Building Nr.</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="number" v-model="registerData.address.number" placeholder="Number"
                      required="required" pattern="[0-9]/?[0-9a-z]*" title="Must be an actual number (slash is allowed as well)">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputCity" class="col-sm-2 col-form-label">City</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="city" v-model="registerData.address.city" placeholder="City"
                      required="required" pattern="[A-Z][a-zA-Z ]*" title="City's name must start with capital letter">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputCountry" class="col-sm-2 col-form-label">Country</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="state" v-model="registerData.address.state" placeholder="Country"
                      required="required" pattern="[A-Z][a-zA-Z ]*" title="Country must start with capital letter">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputDesc" class="col-sm-2 col-form-label">Description</label>
                <div class="col-sm-10">
                    <textarea style="resize: none;width: 100%; height : 200px;" class="form-control" placeholder="Description..."
                      id="description" v-model="registerData.description"></textarea>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPrice" class="col-sm-2 col-form-label">Price of consulting (per hour)</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="pricePerHour" v-model="registerData.pricePerHour" v-bind:min="0"
                      required="required" title="Price must be a number">
                </div>
            </div>
            <button type="submit" id="dugme" class="btn btn-primary">Register</button>


        </form>
    </div>



    <!-- <form v-on:submit.prevent="proveraForme(this)" >
        <table align="left">
          <tr><td align="right">Name of pharmacy: </td><td align="left">
          </td></tr>
          <tr><td align="right">Street name: </td><td align="left">
          </td></tr>
          <tr><td align="right">Building Nr.: </td><td align="left">
          </td></tr>
          <tr><td align="right">City: </td><td align="left">
          </td></tr>
          <tr><td align="right">Country: </td><td align="left">
          </td></tr>
          <tr><td align="right">Description: </td><td colspan="2">
          </td></tr>
          <tr><td align="right">Consulting price (hour): </td><td align="left">
          </td></tr>

          <tr><td></td><td align="left"><input  type="submit" value="Register" style="width: 50.66%;">
          </td></tr>
          
        </table>
    </form> -->
  
    
</template>

<script>
    import PharmacyDataService from '../service/PharmacyDataService.js';
    
    export default {
        name: 'AddPharmacyForm',
        data(){
           return {
            message: null,
            registerData: {
                name : "",
                address : {
                  street : "",
                  number : "",
                  city : "",
                  state : ""
                },
                description : "",
                pricePerHour: 0,
            }

            
        };
        },
        
        methods: {
            formCheck(e){
                //if (!povratna) return;
                PharmacyDataService.SendPharmacy(this.registerData).then(response => {
                  if (response.data) {
                    this.$toast.show(
                        "Pharmacy has been successfully added!",
                        {
                            position: "top", type: "success",
                        }
                    );
                }
                else{
                    this.$toast.show(
                        "This pharmacy already exists!",
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
            }
            
            
        }
        
    }


    
    
</script>

<style>



.columns {
  height: 100%;
  display: grid;
  grid-template-columns: 100vh;
  grid-auto-rows: auto;
  grid-gap: 1rem;
  padding: 1rem;
  box-sizing: content-box;
}

.c {
    border-radius: 4px;
    background-color: lightgrey;
    height: 100%;
}


.columnMap {
    width: 100%;
    height: 180%;
    grid-column: 1;
    grid-row-start: 1;
    grid-row-end: 3;
  }

  .columnForm {
    height: 200%;
    grid-column: 2;
    grid-row: 1;
  }

  .columnLinks {
    grid-column: 2;
    grid-row: 2;
  }

</style>