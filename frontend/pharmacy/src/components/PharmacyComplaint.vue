<template>
    <h4>Search</h4>
    <table>
        <tr>
            <td>Name: </td><td><input type="text" id="name" v-model="queryParams.name" v-on:change="filterResults()"></td>
            <td>Description: </td><td><input type="text" id="description" v-model="queryParams.description" v-on:change="filterResults()"></td>
            <td>Address: </td><td><input type="text" id="address" v-model="queryParams.address" v-on:change="filterResults()"></td>
            <td>Rating: </td><td><input type ="number" id="minRating" v-model="queryParams.minRating" v-on:change="filterResults()" v-bind:min="0" v-bind:max="5"></td>
            <td>-</td><td><input type ="number" id="maxRating" v-model="queryParams.maxRating" v-on:change="filterResults()" v-bind:min="0" v-bind:max="5"></td>
        </tr>
    </table>

    <h4>Pharmacies you bought from or visited: </h4>

    <div style="height: 350px; overflow-y: scroll;">
    <table class="table table-striped">
        <thead class="card-header">
            <th>Name</th>
            <th>Description</th>
            <th>Address</th>
            <th>Rating</th>
            <th></th>
        </thead>
        <tbody>
            <tr :key="key" v-for="(value, key) in this.results">
                <td>{{value[1].name}}</td>
                <td>{{value[1].description}}</td>
                <td>{{value[1].address.street+" "+value[1].address.number+", "+value[1].address.city}}</td>
                <td>{{value[1].rating}}</td>
                <td>
                    <button class="btn btn-primary" type="submit" data-dismiss="modal" v-on:click="this.setPharmacy(value[1])" 
                    data-toggle="modal" data-target="#complaintModal">Complaint</button>
                </td>
            </tr>
        </tbody>
    </table>
    </div>


    <div class="modal fade" id="complaintModal" tabindex="-1" role="dialog" aria-labelledby="complaintModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Describe your dissatisfaction with {{complaint.pharmacy.name}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div>
                    <table class="table table-striped">
                        <thead class="card-header">
                            <th>Description</th>
                        </thead>
                        <tbody>
                            <tr>
                                <td><textarea v-model="complaint.description" rows="5" style="width : 100%;"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit" data-dismiss="modal" v-on:click="sendComplaint()">Confirm</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>
<script>
import AuthService from '../service/AuthService.js';
import ComplaintDataService from '../service/ComplaintDataService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';


export default {
    name: 'PharmacyComplaint',
    data() {
        return {
            author: {},
            complaint: {id: 0, author: 0, pharmacy: 0, doctor: 0, description: "", response: ""},
            queryParams: {name: "", description: "", address: "", minRating: 0, maxRating: 5},
            pharmacies: new Map(),
            results: new Map(),
        };
    },
    methods: {
        setPharmacies(){
            MedicineDataService.getPatientsPurchases(this.author.id).then(response => {
                for(let reservation of response.data){
                    this.pharmacies.set(reservation.medicineInPharmacy.pharmacy.id.toString(), reservation.medicineInPharmacy.pharmacy);
                }

                PharmacyDataService.getVisitedPharmacies(this.author.id).then(response => {
                    for(let pharmacy of response.data){
                        this.pharmacies.set(pharmacy.id.toString(), pharmacy);
                    }
                });

                this.results = this.pharmacies;
            });
            
        },
        filterResults(){
            this.results = new Map();

            for(let [key, value] of this.pharmacies){
                if(value.name.includes(this.queryParams.name) && value.description.includes(this.queryParams.description) &&
                   (value.address.street+" "+value.address.number+", "+value.address.city).includes(this.queryParams.address) && (value.rating <= this.queryParams.maxRating && value.rating >= this.queryParams.minRating)){
                    this.results.set(value.id, value);
                }
            }
        },
        setPharmacy(pharmacy){
            this.complaint.pharmacy = pharmacy;
        },
        sendComplaint(){
            this.complaint.author = this.author.id;
            this.complaint.pharmacy = this.complaint.pharmacy.id;
            console.log("new complaint");
            console.log(this.complaint);
            ComplaintDataService.sendComplaint(this.complaint).then(response => {
                this.complaint.description = "";
            });
        }
    },
    mounted(){
        this.setPharmacies();
    },
    created(){
        this.author = AuthService.getCurrentUser();
        
    },
}
</script>