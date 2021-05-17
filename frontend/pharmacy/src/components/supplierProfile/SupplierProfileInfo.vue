<template>

    <div v-if="supplier" class="row">
      <div class="col-md-12">
        <h2>{{supplier.name+" "+supplier.surname}}</h2>

        <p>Address: {{UtilService.AddressToString(supplier.address)}}</p>
        <p>Phone: {{supplier.phoneNumber}}</p>
      </div>
      
        <div class="col-md-12">
            <div class="d-inline-flex">
                <button class="btn btn-block btn-warning" v-on:click="showModal()">Edit</button>
        </div>
    </div>

<!-- MODAL -->

    <div class="modal fade" id="editDataModal" tabindex="-1" role="dialog" aria-labelledby="editDataModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editDataModalTitle">Edit Personal Data</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            <form v-on:submit.prevent="editPersonalData()">
                <div class="modal-body">

                    <div id="update-data">

                        <div class="form-group">
                            <label name="product_name">First name</label>
                            <input type="text" class="form-control" v-model="supplier.name" id="name" required>
                        </div>

                        <div class="form-group">
                            <label name="product_price">Last name</label>
                            <input type="text" class="form-control" v-model="supplier.surname" id="surname" required>
                        </div>

                        <div class="d-inline-flex row" id="street_and_number">
                            <div class="form-group col">
                                <label name="product_price">Street</label>
                                <input type="text" class="form-control" v-model="supplier.address.street" id="street" required>
                            </div>
                            <div class="form-group col-5 ml-1">
                                <label name="product_price">Number</label>
                                <input type="text" class="form-control" v-model="supplier.address.number" id="number" required>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label name="product_price">City</label>
                            <input type="text" class="form-control" v-model="supplier.address.city" id="city" required>
                        </div>

                        <div class="form-group">
                            <label name="product_price">State</label>
                            <input type="text" class="form-control" v-model="supplier.address.state" id="state" required>
                        </div>
                        
                        <div class="form-group">
                            <label name="product_price">Phone Number</label>
                            <input type="text" class="form-control" v-model="supplier.phoneNumber" id="phoneNumber" required
                            pattern="[0-9]*" title="Phone number must number" size="31">
                        </div>

                        <div class="form-group">
                            <label name="product_price">Email</label>
                            <input type="text" class="form-control" v-model="supplier.email" id="email" required
                            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <div class="form-group">
                            <button class="btn btn-primary" type="submit">Update</button>
                    </div>
                </div>
            </form>
                </div>      
            </div>

        </div>
    </div>
    <br>
    <h3>My medicines</h3>
    <div style="height: 250px; overflow-y: scroll;">
    <form>
        <table class="table table-striped">
            <thead class="card-header">
                <th>Code</th>
                <th>Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th></th>
                <th></th>
            </thead>
            <tbody>
                <tr v-for="(item, key, index) in this.myMedicines">
                    <td>{{key}}</td>
                    <td>{{item.medicine.name}}</td>
                    <td>{{item.inStock}}</td>
                    <td>{{item.currentPrice}}</td>
                    <td><form v-on:click.prevent="setEdittedMedicine(item)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editMedicineModal">Edit</button></form></td>
                    <td><form v-on:click.prevent="setEdittedMedicine(item)"><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button></form></td>
                </tr>
            </tbody>
        </table>
    </form>
    </div>

    <button class="btn btn-primary" data-toggle="modal" data-target="#newMedModal">Add a new medicine</button>

    <div class="modal fade" id="editMedicineModal" tabindex="-1" role="dialog" aria-labelledby="editMedicineModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="Potvrdica">{{this.edittedMedicine.medicine.name}} </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
            </div>
            <div>
                <form>
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <td>Price: </td>
                                <td><input type="number" class="form-control" id="edittedPrice" v-model="edittedMedicine.currentPrice" v-bind:min="0"></td>
                                <td>Quantity: </td>
                                <td><input type="number" class="form-control" id="edittedQuantity" v-model="edittedMedicine.inStock" v-bind:min="0"></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit" v-on:click="updateMedicine(this.edittedMedicine)" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
            </div>
            <div>
                <form>
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <td>Are you sure you wish to delete {{this.edittedMedicine.medicine.name}}?</td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit" v-on:click="removeMedicine(this.edittedMedicine)" data-dismiss="modal">Confirm</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="newMedModal" tabindex="-1" role="dialog" aria-labelledby="newMedModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="Potvrdica">Add a new medicine</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
            </div>
            <div>
                <form>
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <td colspan="2">Select a medicine: </td>
                                <td colspan="2">
                                    <select id="newMedicineSelect" style="width: 100%;" v-model="newMedicine">
                                    <option v-for="(item, key, index) in this.otherMedicines" v-bind:value="item">{{item.medicine.name}}</option>
                                    </select>   
                                </td>
                            </tr>
                            <tr>
                                <td>Price: </td>
                                <td><input type="number" class="form-control" id="newPrice" v-model="newMedicine.currentPrice" v-bind:min="0"></td>
                                <td>Quantity: </td>
                                <td><input type="number" class="form-control" id="newQuantity" v-model="newMedicine.inStock" v-bind:min="0"></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit" v-on:click="addMedicine(this.edittedMedicine)" data-dismiss="modal">Confirm</button>
                </div>
            </div>
        </div>
    </div>
</div>


</template>

<script>
import DataService from '@/service/SupplierDataService.js';
import UtilService from '@/service/UtilService.js';
import AuthService from '@/service/AuthService.js';
import MedicineDataService from '@/service/MedicineDataService.js';

export default {
    name: "SupplierProfileInfo",
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
            supplier: null,
            myMedicines: [],
            otherMedicines: [], //as in, not in supplier's list right now
            edittedMedicine: {medicine: {name:""}},
            newMedicine: {},
		}
	},
    methods: {
        loadSupplierData() {

            DataService.getSupplier(this.id)
                .then(response => {
                    console.log(this.id);
                    this.supplier = response.data;
                    console.log(response.data);
                });
        },
        showModal() {
          $('#editDataModal').modal();
        },
        editPersonalData: function()
        {
            DataService.editPersonalData(this.supplier)
            .then((response) => {
                if (response.data)
                    alert("You have successfuly changed personal data!");
            });
            $('#editDataModal').modal('hide');
        },
        setEdittedMedicine(medicine){
            this.edittedMedicine = medicine;
            console.log(this.edittedMedicine);
        },
        updateMedicine(medicine){
            MedicineDataService.updateSuppliersMedicine(medicine);
        },
        removeMedicine(medicine){
            MedicineDataService.removeMedicineOfSupplier(medicine).then(response => {
               this.$router.go("/supplier/profile"); 
            });
        },
        getOtherMedicines(){
            MedicineDataService.getAllMedicines().then(response => {
                this.otherMedicines = {};
                for(var med of response.data){
                    if(this.myMedicines.[med.code]){
                        continue;
                    }
                    this.otherMedicines[med.code] = {id: 0, currentPrice: 0, medicine: med, inStock: 0, supplier: {}};
                }

                console.log("OTHER MEDS");
                console.log(this.otherMedicines);
            });
        },
        addMedicine(medicine){
            this.newMedicine.currentPrice = parseFloat(this.newMedicine.currentPrice);
            this.newMedicine.inStock = parseInt(this.newMedicine.inStock);
            this.newMedicine.supplier = this.supplier;
            console.log(this.newMedicine);
            MedicineDataService.addMedicineToSupplier(medicine).then(response => {
               this.$router.go("/supplier/profile"); 
            }); 
        }
       
    },
    mounted() {
        this.loadSupplierData();
    },
	created() {
        this.loadSupplierData();
		this.id = AuthService.getCurrentUser().id;
        MedicineDataService.getSuppliersMedicines(this.id).then(response => {
            this.myMedicines = response.data;
            console.log(this.myMedicines);
            this.getOtherMedicines();
        });
        
	}
}
</script>