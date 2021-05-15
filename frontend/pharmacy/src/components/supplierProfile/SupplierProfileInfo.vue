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
    <form v-on:submit.prevent="">
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
                    <td><form v-on:click.prevent=""><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">Edit</button></form></td>
                    <td><form v-on:click.prevent=""><button type="button" class="btn btn-danger">Delete</button></form></td>
                </tr>
            </tbody>
        </table>
    </form>
    </div>

    <div>
        <button type="button" class="btn btn-primary">Add medicine</button>
    </div>

</template>

<script>
import DataService from '@/service/SupplierDataService.js';
import UtilService from '@/service/UtilService.js';
import AuthService from '@/service/AuthService.js';
import MedicineDataService from '@/service/MedicineDataService.js';

// import DermatologistDataService from '../../service/DermatologistDataService';

export default {
    name: "SupplierProfileInfo",
    //props: ['id'],
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
            supplier: null,
            myMedicines: [],
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
    },
    mounted() {
        this.loadSupplierData();
    },
	created() {
		this.id = AuthService.getCurrentUser().id;
        MedicineDataService.getSuppliersMedicines(this.id).then(response => {
            this.myMedicines = response.data;
            console.log(this.myMedicines);
        });
	}
}
</script>