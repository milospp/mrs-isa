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

    <table class="table table-striped">
    <thead class="card-header">
        <th>Medicine code</th>
        <th>Medicine name</th>
        <th>Price</th>
        <th>Action/promotion</th>
        <th>Needed quantity</th>
        <th>Have</th>
    </thead>
    <tbody>
        <tr :key="l" v-for="l in this.odgovarajuciLekovi">
            <td>{{l.kod}}</td>
            <td>{{l.ime}}</td>
            <td>{{l.cena}}</td>
            <td>{{l.akcija}}</td>
            <td>{{l.kolicina}}</td>
            <td>{{l.dovoljno}}</td>
        </tr>
    </tbody>
    </table>

    <br>

    <div>
        <h5>Total: {{ukupno}}</h5>
    </div>


</template>
<script>
import AuthService from '../service/AuthService.js';
import UtilService from '../service/UtilService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';

export default {
    name: 'PharmacyQRCPage',
    setup() {
      return { UtilService}
    },
    data(){
        return {
            user: null,
            dataFromQR: "",
            ePrescription: { id: 0, code: "", patient: null, issueDate: null, medicines: [], pharmacy: null},
            pharmacy: null, allMedicines: [], odgovarajuciLekovi: [], ukupno: null,
        }
    },
    methods: {
        onDecode (decodedString) {
            this.ePrescription = { id: 0, code: "", patient: null, issueDate: null, medicines: [], pharmacy: null};
           
            this.dataFromQR = decodedString;
            let parsedQR = JSON.parse(this.dataFromQR);

            
            this.ePrescription.id = parsedQR.id;
            this.ePrescription.code = parsedQR.code;

            this.ePrescription.patient = this.user;

            var ind = 0;
            for(let medicine of parsedQR.medicines){
                this.ePrescription.medicines[ind] = medicine;
                ind += 1;
            }
            this.pronadjiLekove();
        },
        pronadjiLekove() {
            var ind = 0;
            this.ukupno = 0;
            var imaDovoljno = "da";
            this.odgovarajuciLekovi = [];
            for (var lek of this.ePrescription.medicines) {
                let pronadjeniLek = null;
                for (var lekApoteke of this.allMedicines) {
                    if (lek[0] == lekApoteke.medicine.code) {
                        pronadjeniLek = lekApoteke;
                        break;
                    }
                }
                if (pronadjeniLek == null) {
                    imaDovoljno = "ne postoji";
                    this.odgovarajuciLekovi[ind] = {"kod": lek[0], "ime": "/", "cena": "/", "akcija": "/",
                        "kolicina": lek[1], "dovoljno": "Not have"};
                    this.ukupno = "This pharmacy do not have all medicines.";
                }
                else {
                    if (pronadjeniLek.inStock < lek[1]) imaDovoljno = "ne";
                    this.odgovarajuciLekovi[ind] = {
                        "kod": lek[0], 
                        "ime": pronadjeniLek.medicine.name, 
                        "cena": pronadjeniLek.priceType == 'NORMAL' ? 
                            pronadjeniLek.currentPrice : pronadjeniLek.oldPrice,
                        "akcija": pronadjeniLek.priceType == 'NORMAL' ? 
                            "" : (pronadjeniLek.priceType == 'ACTION' ? 
                            pronadjeniLek.currentPrice + "(" + (100-pronadjeniLek.currentPrice*100/pronadjeniLek.currentPrice.oldPrice) + "%)" :
                            pronadjeniLek.currentPrice),
                        "kolicina": lek[1], 
                        "dovoljno": pronadjeniLek.inStock >= lek[1] ? "Enough" : "Not enough"};
                    if (imaDovoljno == "da") 
                        this.ukupno += pronadjeniLek.currentPrice * lek[1];
                    else if (imaDovoljno == "ne") this.ukupno = "This pharmacy do not have enough medicines."; 
                }

                ind += 1;
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
        MedicineDataService.getMedicineForPharmacy(this.id)
            .then(response => { 
                this.allMedicines = response.data;
            });
    },
}
</script>