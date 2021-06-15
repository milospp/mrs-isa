<template>
  <!-- Modal -->
    <div class="modal fade" :id="modalId" tabindex="-1" role="dialog" aria-labelledby="reserveMedModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="reserveMedModalLabel">{{modelValue?.name}}</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">

                <div class="" align="left">Name: {{modelValue?.name}}</div>
                <div class="" align="left">Structure: {{modelValue?.structure}}</div>
                <div class="" align="left">Manufacturer: {{modelValue?.manufacturer}}</div>
                <div class="" align="left">Note: {{modelValue?.note}}</div>
                <div class="" align="left">Points: {{modelValue?.points}}</div>
                <div class="" align="left">Type: {{modelValue?.type}}</div>

                <div class="modal-footer">
                
            </div>
            <table v-if="medicinesInPharmacies" class="table table-striped">
                <thead class="card-header">
                  <th>Price</th>
                  <th>Quantity</th>
                  <th>Name</th>
                  <!-- <th>Address</th> -->
                  <th></th>
                </thead>
                <tbody>
                    <tr :key="mip.id" v-for="mip in this.medicinesInPharmacies" for="radio-1" v-on:click="selectPharmacy(mip)">
                      <td>{{mip.currentPrice}}</td>
                      <td>{{mip.inStock}}</td>
                      <td>{{mip.pharmacy.name}}</td>
                      <!-- <td>{{addressToString(mip.pharmacy.address)}}</td> -->
                      <td><input class="form-check-input" type="radio" name="" :id="'radio-' + mip.pharmacy.id" :value="mip.pharmacy.id" v-model="selectedPharmacyId"></td>
                  </tr>
                </tbody>
            </table>

            <div v-if="selectedMIP">
                <div v-if="error_message" class="alert alert-danger" role="alert">{{error_message}}</div>
                <div class="modal-body" align="left">Quantity: <input type="number" v-model="quantity" min="1" :max="selectedMIP?.inStock" /> (max = {{selectedMIP?.inStock}})</div>
                <div class="modal-body" align="left">Expiry date: <input type="date" v-model="res_date"/></div>
            </div>


        </div>
        <div class="modal-footer">
            <button :disabled="!selectedMIP" type="button" class="btn btn-primary" v-on:click.prevent="reserveMedicine">Reserve</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        </div>
    </div>
    </div>
</template>

<script>
import MedicineDataService from "@/service/MedicineDataService.js";
import UtilService from "@/service/UtilService.js";

export default{
    props: {
        modalId: {
            default: "ratingModal"
        },
        patientId: Number,
        modelValue: Object,
    },


	data: function () {
		return {
            medicinesInPharmacies: null,
            selectedPharmacyId: null,
            selectedMIP: null,
            quantity: 1,
            res_date: Date(),
            error_message: null,

		}
	},


    watch: {
        'modelValue': function() {
            this.selectedMIP = null;
            this.selectedPharmacyId = null;
            this.getMedicineInPharmacies();
        }
    },

    computed: {

    },

    methods:{
        addressToString: UtilService.AddressToString,

        getMedicineInPharmacies() {
            MedicineDataService.getMedicineInPharmacies(this.modelValue.id) // HARDCODED
                .then(response => {
                    this.medicinesInPharmacies = response.data;
                });
        },

        selectPharmacy(mip){
            this.selectedMIP = mip;
            this.selectedPharmacyId = mip.pharmacy.id;
        },

        validateReservation() {
            this.error_message = null;
            if (this.quantity < 1) {this.error_message = "Min quantity is 0"; return false};
            if (this.quantity > this.selectedMIP.inStock) {this.error_message = "Max quantity is " + this.selectedMIP.inStock; return false};
            if (UtilService.isPastDate(this.res_date)) {this.error_message = "Date must be in the future"; return false;}
            return true;
        },

        reserveMedicine() {
            if(!this.validateReservation()) return;

            let reserve_form = {
            medicineId: this.selectedMIP.medicine.id,
            pharmacyId: this.selectedMIP.pharmacy.id,
            patientId: this.patientId, // TODO: PATIENT FORM SESSION
            quantity: this.quantity,
            expirityDate: this.res_date,
            };

            MedicineDataService.reserveMedicineAsPatient(reserve_form)
            .then(response => {
                this.selectedMIP.inStock -= this.quantity;
                $('#' + this.modalId).modal('hide'); 
                this.$toast.show(
                    "You have successfully reserved medicine!",
                    {
                        position: "top", type: "success"
                    }
                );

            }).catch(error => {
            // TODO: DODATI OSTALE PROVERE!!!!
            this.$toast.show(
                    "You're alergic to this medicine.",
                    {
                        position: "top", type: "error"
                    }
                );
            this.poruka = "Pacijent je alergičan";
                });



        },


    },

    created(){
    },

}
</script>

<style>

</style>