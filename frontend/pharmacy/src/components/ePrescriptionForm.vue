<template>
    <div>
        <div class="mb">Upload your QR code below</div>
        
        <qr-capture @decode="onDecode" class="mb"></qr-capture>
    </div>
    <br>

    <div class="container" v-if="eligiblePharmacies.length > 0" style="height: 300px; overflow-y: scroll;">
        <table class="table table-striped">
                <thead class="card-header">
                    <th>Pharmacy name</th>
                    <th>Address</th>
                    <th>Rating</th>
                    <th>Total price</th>
                    <th></th>
                </thead>
                <tbody>
                    <tr :key="pharmacy.id" v-for="pharmacy in this.eligiblePharmacies">
                        <td>{{pharmacy.name}}</td>
                        <td>{{pharmacy.address.street+" "+pharmacy.address.number+", "+pharmacy.address.city}}</td>
                        <td>{{pharmacy.rating}}</td>
                        <td>{{setTotal(pharmacy)}}</td>
                        <td><button type="submit" class="btn btn-primary" v-on:click="selectPharmacy(pharmacy)" 
                        data-toggle="modal" data-target="#selectPharmacyModal">Select</button></td>
                    </tr>
                </tbody>
        </table>
    </div>


    <div class="modal fade" id="selectPharmacyModal" tabindex="-1" role="dialog" aria-labelledby="selectPharmacyModal" aria-hidden="true">
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
                                    <tr><td>
                                    <div class="container">
                                        <p>Are you sure you wish to buy medicines from {{selectedPharmacy.name}}?</p>
                                    </div>
                                    </td></tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit" v-on:click="reserve(selectedPharmacy)" data-dismiss="modal">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

</template>
<script>
import AuthService from '../service/AuthService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
import PatientDataService from '../service/PatientDataService.js';

export default {
    name: 'ePrescriptionForm',
    data(){
        return {
            user: null,
            dataFromQR: "",
            ePrescription: { id: 0, code: "", patient: null, issueDate: null, medicines: {}, pharmacy: null},
            eligiblePharmacies: [],
            totals: new Map(),
            selectedPharmacy: { name: "" },
        }
    },
    methods: {
        onDecode (decodedString) {
            this.ePrescription = { id: 0, code: "", patient: null, issueDate: null, medicines: {}, pharmacy: null};
            this.eligiblePharmacies = [];


            this.dataFromQR = decodedString;
            let parsedQR = JSON.parse(this.dataFromQR);

            
            this.ePrescription.id = parsedQR.id;
            this.ePrescription.code = parsedQR.code;

            this.ePrescription.patient = this.user;

            for(let medicine of parsedQR.medicines){
                this.ePrescription.medicines[medicine[0]] = medicine[1];
            }

            

            PharmacyDataService.getEligible(this.ePrescription).then(response => {
                this.eligiblePharmacies = response.data;

                PharmacyDataService.calculateTotals(this.ePrescription).then(response => {

                    this.totals = response.data;
                });
            });
        },

        selectPharmacy(pharmacy){
            this.selectedPharmacy = pharmacy;
        },
        reserve(pharmacy){
            this.ePrescription.pharmacy = pharmacy;
            console.log(this.ePrescription);

            MedicineDataService.eReserve(this.ePrescription).then(response => {
                if(response.data == 0){
                    this.$toast.show(
                        "All of the medicines are succesfully reserved!",
                        {
                            position: "top", type: "success",
                        }
                    );
                }else if(response.data == 1){
                    this.$toast.show(
                        "It seems that you are alergic to some of the medicines!",
                        {
                            position: "top", type: "error",
                        }
                    );
                }
            });
        },
        setTotal(pharmacy){
            return this.totals[pharmacy.id];
        },
    },
    created(){
        this.user = AuthService.getCurrentUser();

        PatientDataService.getPatientPenalities(this.user.id).then(response => {
            if(response.data.length > 2){
                this.$toast.show(
                            "You have more than 3 penalties, you are not allowed here!",
                            {
                                position: "top", type: "error",
                            }
                        );
                this.$router.push("/profile");
            }
        });
        
    },
}
</script>