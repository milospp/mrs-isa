<template>

    <div v-if="doctor" class="row">
      <div class="col-md-12">
        <h2>{{doctor.name}} {{doctor.surname}}</h2>
        <p class="rating">Rating: <span>TODO: 9.7/10</span></p>

        <p>Address: {{UtilService.AddressToString(doctor.address)}}</p>
        <p>Phone: {{doctor.phoneNumber}}</p>
      </div>
      
      <div class="col-md-12">
        <div class="d-inline-flex">
            <button class="btn btn-block btn-warning" v-on:click="showModal()">Edit</button>
        </div>
      </div>

<div class="modal fade" id="editDataModal" tabindex="-1" role="dialog" aria-labelledby="editDataModal" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editDataModalTitle">Edit Personal Data</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

          <div id="update-data">
        <h1>Edit Personal Data</h1>

        <form v-on:submit.prevent="editProduct">
            <!-- <div class="form-group">
                <label name="product_id">ID</label>
                <input type="text" class="form-control" disabled v-model="doctor.id" id="id">
            </div> -->

            <div class="form-group">
                <label name="product_name">First name</label>
                <input type="text" class="form-control" v-model="doctor.name" id="product_name" required>
            </div>

            <div class="form-group">
                <label name="product_price">Last name</label>
                <input type="text" class="form-control" v-model="doctor.surname" id="product_price" required>
            </div>

            <div class="form-group">
                <label name="product_price">State</label>
                <input type="text" class="form-control" v-model="doctor.address.state" id="product_price" required>
            </div>
            <div class="form-group">
                <label name="product_price">City</label>
                <input type="text" class="form-control" v-model="doctor.address.city" id="product_price" required>
            </div>
            <div class="form-group">
                <label name="product_price">Street</label>
                <input type="text" class="form-control" v-model="doctor.address.street" id="product_price" required>
            </div>
            <div class="form-group">
                <label name="product_price">Number</label>
                <input type="text" class="form-control" v-model="doctor.address.number" id="product_price" required>
            </div>

            
        </form>
    </div>

  </div>
      <div class="modal-footer">
          <div class="form-group">
                <button class="btn btn-primary">Update</button>
            </div>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
</div>

      
    </div>
  </div>
</div>

      

</template>

<script>
    import DataService from '@/service/DermatologistDataService.js';
    import UtilService from '@/service/UtilService.js';
import PharmacistDataService from '../../service/PharmacistDataService';

export default {
    name: "DermatologistProfileInfo",
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

            DataService.getDermatologist(this.id) // HARDCODED this.id
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

            PharmacistDataService.editPersonalData(doctor)
            .then((response) => {
                if (response.data)
                    this.doctor = response.data;
            });
        },
    },
    mounted() {
        this.loadDoctorData();
    },
	created() {
		  this.id = this.$route.params.id;
	}
}
</script>