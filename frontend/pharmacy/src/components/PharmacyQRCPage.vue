<template>
    <div v-if="pharmacy" class="row">
        <div class="col-md-4" align="left">
            <h2>{{pharmacy.name}}</h2>
            <h5>Address: {{UtilService.AddressToString(pharmacy.address)}}</h5>
            <p class="rating">Rating: {{pharmacy.rating}} / 5<br />
            Description: {{pharmacy.description}}</p>
        </div>
    </div>

    <br>

    <div>
        <div class="mb">Upload your QR code below</div>
        <qr-capture @decode="onDecode" class="mb"></qr-capture>
    </div>

    <br>

</template>
<script>
import AuthService from '../service/AuthService.js';
import UtilService from '../service/UtilService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';
import PatientDataService from '../service/PatientDataService.js';

export default {
    name: 'PharmacyQRCPage',
    setup() {
      return { UtilService}
    },
    data(){
        return {
            user: null,
            dataFromQR: "",
            ePrescription: { id: 0, code: "", patient: null, issueDate: null, medicines: {}, pharmacy: null},
            pharmacy: null,
        }
    },
    methods: {
        onDecode (decodedString) {
            this.ePrescription = { id: 0, code: "", patient: null, issueDate: null, medicines: {}, pharmacy: null};
           
            this.dataFromQR = decodedString;
            let parsedQR = JSON.parse(this.dataFromQR);

            
            this.ePrescription.id = parsedQR.id;
            this.ePrescription.code = parsedQR.code;

            this.ePrescription.patient = this.user;

            for(let medicine of parsedQR.medicines){
                this.ePrescription.medicines[medicine[0]] = medicine[1];
            }
        },

    },
    created(){
        this.id = this.$route.params.id;
        this.user = AuthService.getCurrentUser();
        PharmacyDataService.getPharmacy(this.id)
            .then(response => {
                this.pharmacy = response.data;
            });
    },
}
</script>