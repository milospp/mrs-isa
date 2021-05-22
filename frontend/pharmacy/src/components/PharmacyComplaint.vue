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

    <h4>Pharmacies you bought from: </h4>

    <table class="table table-striped">
        <thead class="card-header">
            <th>Name</th>
            <th>Description</th>
            <th>Address</th>
            <th>Rating</th>
            <th></th>
        </thead>
        <tbody>
            <tr :key="key" v-for="(value, key) in this.pharmacies">
                <td>{{value[1].name}}</td>
                <td>{{value[1].description}}</td>
                <td>{{value[1].address.street+" "+value[1].address.number+", "+value[1].address.city}}</td>
                <td>{{value[1].rating}}</td>
                <td>
                    <button class="btn btn-primary" type="submit" data-dismiss="modal" v-on:click="" data-toggle="modal" data-target="#complaintModal">Complaint</button>
                </td>
            </tr>
        </tbody>
    </table>

</template>
<script>
import AuthService from '../service/AuthService.js';
import PharmacyDataService from '../service/PharmacyDataService.js';
import DermatologistDataService from '../service/DermatologistDataService.js';
import PharmacistDataService from '../service/PharmacistDataService.js';
import MedicineDataService from '../service/MedicineDataService.js';


export default {
    name: 'PharmacyComplaint',
    data() {
        return {
            author: {},
            complaint: {id: 0, author: this.author, pharmacy: {}, doctor: {}, description: "", response: ""},
            queryParams: {name: "", description: "", address: "", minRating: 0, maxRating: 5},
            pharmacies: new Map(),
        };
    },
    methods: {
        setPharmacies(){
            MedicineDataService.getPatientsPurchases(this.author.id).then(response => {
                for(let reservation of response.data){
                    this.pharmacies.set(reservation.medicineInPharmacy.pharmacy.id.toString(), reservation.medicineInPharmacy.pharmacy);
                }

                console.log(this.pharmacies);
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