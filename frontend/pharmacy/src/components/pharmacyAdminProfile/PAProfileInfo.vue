<template>

    <div class="col-md-12">
        <h4>&emsp;</h4>
        <h2>{{admin?.name}} {{admin?.surname}}</h2>
        <h4>Address: {{admin?.address.state}} {{admin?.address.city}} {{admin?.address.street}} {{admin?.address.number}}</h4>
        <h4>Phone: {{admin?.phoneNumber}}</h4>
        <h4>&emsp;</h4>
    </div>

    <div class="col-md-12">
        <div class="d-inline-flex mr-2">
            <button class="btn btn-block btn-warning" data-toggle="modal" data-target="#promenaPodataka">Edit</button>
        </div>
        <div class="d-inline-flex">
            <button class="btn btn-block btn-secondary" v-on:click="promeniSifru()">Change password</button>
        </div>
      </div>

 <div class="modal fade" id="promenaPodataka" tabindex="-1" role="dialog" aria-labelledby="radVr" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="vreme">Edit personal data</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        
        <div class="form-group">
            <label name="product_name">First name</label>
            <input type="text" class="form-control" v-model="name" required>
        </div>

        <div class="form-group">
            <label name="product_price">Last name</label>
            <input type="text" class="form-control" v-model="surname" required>
        </div>

        <div class="d-inline-flex row" id="street_and_number">
            <div class="form-group col">
                <label name="product_price">Street</label>
                <input type="text" class="form-control" v-model="street"  required>
            </div>
            <div class="form-group col-5 ml-1">
                <label name="product_price">Number</label>
                <input type="text" class="form-control" v-model="number">
            </div>
        </div>
        
        <div class="form-group">
            <label name="product_price">City</label>
            <input type="text" class="form-control" v-model="city" required>
        </div>

        <div class="form-group">
            <label name="product_price">State</label>
            <input type="text" class="form-control" v-model="state" required>
        </div>
        
        <div class="form-group">
            <label name="product_price">Phone Number</label>
            <input type="text" class="form-control" v-model="phoneNumber" required
            pattern="[0-9]*" title="Phone number must number" size="31">
        </div>

        <div class="form-group">
            <label name="product_price">Email</label>
            <input type="text" class="form-control" v-model="email" required
            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$">
        </div>

         <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje" v-on:click.prevent="sacuvaj()" data-dismiss="modal">Save</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

 <!-- obavestenje -->
  <div class="modal fade" id="obavestenje" tabindex="-1" role="dialog" aria-labelledby="poruka" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="poruka">Message</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" align="left"><label>{{this.poruka}}</label></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="izloguj()">OK</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import AuthService from '@/service/AuthService.js';
import PharmacyAdminDataService from '@/service/PharmacyAdminDataService.js';

export default {
	data: function () {
		return {
            admin: null, 
            name: null, surname : null, state : null, city : null, street : null, 
            number : null, phoneNumber : null, email : null,
            poruka: "Wait... Your require is in processing", 
            izlaz: false,
		}
	},
    created() {
		this.id = AuthService.getCurrentUser().id;
        this.ucitajAdmina();
	},
    methods: {
        promeniSifru() { this.$router.push("/change-password"); },
        ucitajAdmina() {
            PharmacyAdminDataService.getPA(this.id)
            .then(response => { 
                this.admin = response.data;
                this.name = this.admin.name;
                this.surname = this.admin.surname;
                this.state = this.admin.address.state;
                this.street = this.admin.address.street;
                this.city = this.admin.address.city;
                this.number = this.admin.address.number;
                this.phoneNumber = this.admin.phoneNumber;
                this.email = this.admin.email;
             });
        },
        izloguj() { 
            if (this.izlaz) {AuthService.logout(); this.$router.push("/");}
            else this.poruka = 'Wait... Your require is in processing';
        },
        sacuvaj() {
            this.izlaz = !(this.email == this.admin.email);
            PharmacyAdminDataService.editPA(this.admin, this.name, this.surname, this.email, 
                this.phoneNumber, this.state, this.city, this.street, this.number)
            .then(response => {
                if (response.data == 1) { this.poruka = "Email is not unique;"; return; }
                if (this.izlaz) {
                    this.izlaz = true; 
                    this.poruka = "You successfully edited your data.You must login again.";
                    return;
                }
                this.ucitajAdmina();
                this.poruka = "You successfully edited your data.";
            });
        },
    },
}
</script>