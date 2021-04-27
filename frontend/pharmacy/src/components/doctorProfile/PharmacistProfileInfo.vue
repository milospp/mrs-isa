<template>

    <div v-if="doctor" class="row">
      <div class="col-md-12">
        <h2>{{doctor.name}} {{doctor.surname}}</h2>
        <!-- <p class="rating">Rating: <span>5</span></p> -->

        <p>Address: {{UtilService.AddressToString(doctor.address)}}</p>
        <p>Phone: {{doctor.phoneNumber}}</p>
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
                    <!-- <h1>Edit Personal Data</h1> -->

                        <div class="form-group">
                            <label name="product_name">First name</label>
                            <input type="text" class="form-control" v-model="doctor.name" id="name" required>
                        </div>

                        <div class="form-group">
                            <label name="product_price">Last name</label>
                            <input type="text" class="form-control" v-model="doctor.surname" id="surname" required>
                        </div>

                        <div class="d-inline-flex row" id="street_and_number">
                            <div class="form-group col">
                                <label name="product_price">Street</label>
                                <input type="text" class="form-control" v-model="doctor.address.street" id="street" required>
                            </div>
                            <div class="form-group col-5 ml-1">
                                <label name="product_price">Number</label>
                                <input type="text" class="form-control" v-model="doctor.address.number" id="number" required>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label name="product_price">City</label>
                            <input type="text" class="form-control" v-model="doctor.address.city" id="city" required>
                        </div>

                        <div class="form-group">
                            <label name="product_price">State</label>
                            <input type="text" class="form-control" v-model="doctor.address.state" id="state" required>
                        </div>
                        
                        <div class="form-group">
                            <label name="product_price">Phone Number</label>
                            <input type="text" class="form-control" v-model="doctor.phoneNumber" id="phoneNumber" required
                            pattern="[0-9]*" title="Phone number must number" size="31">
                        </div>

                        <div class="form-group">
                            <label name="product_price">Email</label>
                            <input type="text" class="form-control" v-model="doctor.email" id="email" required
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

</template>

<script>
import DataService from '@/service/PharmacistDataService.js';
import UtilService from '@/service/UtilService.js';
import AuthService from '@/service/AuthService.js';

// import PharmacistDataService from '../../service/PharmacistDataService';

export default {
    name: "PharmacistProfileInfo",
    //props: ['id'],
    setup() {
      return { UtilService }
    },

	data: function () {
		return {
      doctor: null
		}
	},
    methods: {
        loadDoctorData() {

            DataService.getPharmacist(this.id) // HARDCODED this.id
                .then(response => {
                    console.log(this.id);
                    this.doctor = response.data;
                    console.log(response.data);
                });
        },
        showModal() {
          $('#editDataModal').modal();
        },
        editPersonalData: function()
        {
            // Validation
            // var price = parseFloat(this.product.price);
            // if(isNaN(price))
            // {
            //     this.notifications.push({
            //         type: 'danger',
            //         message: 'Price must be a number'
            //     });
            //     return false;
            // }
            DataService.editPersonalData(this.doctor)
            .then((response) => {
                if (response.data)
                    alert("You have successfuly changed personal data!");
            });
            $('#editDataModal').modal('hide');
        },
    },
    mounted() {
        this.loadDoctorData();
    },
	created() {
		  this.id = AuthService.getCurrentUser().id;
	}
}
</script>