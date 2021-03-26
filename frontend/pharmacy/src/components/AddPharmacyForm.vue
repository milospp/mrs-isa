<template style="width: 100%; height: 100%">

    <form v-on:submit.prevent="proveraForme(this)" >
        <table align="left">
          <tr><td align="right">Name of pharmacy: </td><td align="left"><input type="text" id="pharmacyName" v-model="registerData.name" 
            required="required" pattern="[A-Z][a-zA-Z ]*" title="Name must start with capital letter">
          </td></tr>
          <tr><td align="right">Street name: </td><td align="left"><input type="text" id="street" v-model="registerData.address.street" 
            required="required" pattern="[A-Z][a-zA-Z ]*" title="Name of street must start with capital letter">
          </td></tr>
          <tr><td align="right">Building Nr.: </td><td align="left"><input type="text" id="number" v-model="registerData.address.number" 
            required="required" pattern="[0-9]/?[0-9a-z]*" title="Must be an actual number (slash is allowed as well)">
          </td></tr>
          <tr><td align="right">City: </td><td align="left"><input type="text" id="city" v-model="registerData.address.city" 
            required="required" pattern="[A-Z][a-zA-Z ]*" title="City's name must start with capital letter">
          </td></tr>
          <tr><td align="right">Country: </td><td align="left"><input type="text" id="state" v-model="registerData.address.state" 
            required="required" pattern="[A-Z][a-zA-Z ]*" title="Country must start with capital letter">
          </td></tr>
          <tr><td align="right">Description: </td><td colspan="2"><textarea style="resize: none;width: 400px; height : 200px;"
            id="description" v-model="registerData.description"></textarea>
          </td></tr>
          <tr><td></td><td align="left"><input  type="submit" value="Register" style="width: 50%;">
          </td></tr>
          
        </table>
    </form>
  
    
</template>

<script>
    import PharmacyDataService from '../service/PharmacyDataService.js';
    
    console.log("Salji broj salji salji broj");
    
    export default {
        name: 'AddPharmacyForm',
        data(){
           return {
            message: null,
            registerData: {
                name : "Apotekica",
                address : {
                  street : "Ulica",
                  number : "br",
                  city : "grad",
                  state : "drzava"
                },
                description : "Nema leka bez recepta",
            }

            
        };
        },
        
        methods: {
            proveraForme(e){
                let povratna = true;
                let sviRedovi = document.getElementsByTagName("tr");
                for (let red of sviRedovi) {                
                    if (red.cells.length == 3) red.removeChild(red.cells[2]);
                }

                

                if (!povratna) return;
                PharmacyDataService.SendPharmacy(this.registerData)
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