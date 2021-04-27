<template>
     <form v-on:submit.prevent="formCheck(this)" >
        <table>
            <tr>
                <td align="right">Code:</td>
                <td>
                    <input 
                        type="text" id="code" v-model="registerData.code" required="required"
                        pattern="[a-zA-Z0-9-]+" title="Code may contain letters or numbers, dash is also allowed" size="31"
                    ></td>
            </tr>

            <tr>
                <td align="right">Name:</td>
                <td>
                    <input 
                        type="text" id="name" v-model="registerData.name" required="required"
                        pattern="[a-zA-Z0-9][a-zA-Z0-9 ]*" title="Name must contain letters or numbers, space is allowed" size="31"
                    ></td>
            </tr>
            
            <tr>
                <td align="right">Structure:</td>
                <td>
                    <textarea style="resize: none;width: 100%; height : 200px;" id="structure" 
                    v-model="registerData.structure" required="required">
                    </textarea>
                </td>
            </tr>
            
            <tr>
                <td align="right">Manufacturer:</td>
                <td>
                    <input 
                        type="text" id="manufacturer" v-model="registerData.manufacturer" required="required"
                        pattern="[a-zA-Z0-9][a-zA-Z0-9 -]*" size="31" title="Manufacturer's name must contain letters, numbers and spacing is also allowed"
                    ></td>
            </tr>

            <tr>
                <td align="right">Note:</td>
                <td>
                    <textarea style="resize: none;width: 100%; height : 200px;" id="note" v-model="registerData.note">
                    </textarea>
                </td>
            </tr>
            
            <tr>
                <td align="right">Points:</td>
                <td>
                    <input type="number" id="points" min="0" max="99"  v-model="registerData.points" 
                        required="required" title="You have to select a quantity for points">
                </td>
            </tr>

            <tr>
                <td align="right">Form:</td>
                <td>
                    <select id="shape" style="width: 100%;" v-model="registerData.shape" required="required">
                        <option value="tablets">Tablets</option>
                        <option value="pills">Pills</option>
                        <option value="powder">Powder</option>
                        <option value="syrup">Syrup</option>
                        <option value="saline">Saline</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td align="right">Type:</td>
                <td><input 
                    type="text" id="type" v-model="registerData.type" required="required"
                    pattern="[A-Za-z][A-Za-z 0-9-]*" size="31" title="Type of medicine must contain letters, numbers, space and dash is allowed"
                ></td>
            </tr>
            <tr>
                <td align="right">Prescription:</td>
                <td>
                    <select id="perscription" style="width: 100%;" v-model="registerData.perscription" required="required">
                        <option value="0">With receipt</option>
                        <option value="1">Without receipt</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">Select replacement medicines:</td>
                <td>
                    <select id="replacementMedicationIds" style="width: 100%;" v-model="registerData.replacementMedicationIds" 
                     multiple>
                        <option v-for="medicine in this.allMedicines" v-bind:value=medicine.id>{{medicine.name}}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" id="dugme" value="Register" style="width : 100% ;"></td>
            </tr>
        </table>
    </form>
</template>

<script>
import MedicineDataService from '../service/MedicineDataService.js';
export default {
    name: 'AddMedicineForm',
    data() {
        return {
            allMedicines: [],
            message: null,
            registerData: {
                code : "CD132",
                name : "Lek Lek",
                structure : "Samo igla",
                manufacturer : "ppp",
                note : "Nemoj voziti",
                points : "3",
                form: 0,
                shape : "saline",
                type : "Tip",
                perscription : 1,
                replacementMedicationIds : []
            }
        };
    },
    methods: {      
        formCheck(e) {
            MedicineDataService.sendMedicine(this.registerData)
				.catch(function (error) {
					if (error.response) {
						console.log(error.response.data);
					} else if (error.request) {
						console.log(error.request);
					}
					console.log("error.config");
					console.log(error.config);
				});
            console.log(this.registerData)
        },
        
    },
    created() {
        MedicineDataService.getAllMedicines()
        .then(
            response => {
                this.allMedicines = response.data;
            }
        );
    }
    
}
</script>