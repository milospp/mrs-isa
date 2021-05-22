<template>
    <h4>Search</h4>
    <table>
        <tr>
            <td>Name: </td><td><input type="text" id="phName" v-model="queryParams.name" v-on:change="filterResults()"></td>
            <td>Surname: </td><td><input type="text" id="phSurname" v-model="queryParams.surname" v-on:change="filterResults()"></td>
            <td>Address: </td><td><input type="text" id="phAddress" v-model="queryParams.address" v-on:change="filterResults()"></td>
            <td>Rating: </td><td><input type ="number" id="phMinRating" v-model="queryParams.minRating" v-on:change="filterResults()" v-bind:min="0" v-bind:max="5"></td>
            <td>-</td><td><input type ="number" id="phMaxRating" v-model="queryParams.maxRating" v-on:change="filterResults()" v-bind:min="0" v-bind:max="5"></td>
        </tr>
    </table>

    <h4>Pharmacies you bought from: </h4>

    <div style="height: 350px; overflow-y: scroll;">
    <table class="table table-striped">
        <thead class="card-header">
            <th>Name</th>
            <th>Surname</th>
            <th>Address</th>
            <th>Rating</th>
            <th></th>
        </thead>
        <tbody>
            <tr :key="key" v-for="(value, key) in this.results">
                <td>{{value[1].name}}</td>
                <td>{{value[1].surname}}</td>
                <td>{{value[1].address.street+" "+value[1].address.number+", "+value[1].address.city}}</td>
                <td>{{value[1].rating}}</td>
                <td>
                    <button class="btn btn-primary" type="submit" data-dismiss="modal" v-on:click="this.setPharmacist(value[1])" 
                    data-toggle="modal" data-target="#pharmacistComplaintModal">Complaint</button>
                </td>
            </tr>
        </tbody>
    </table>
    </div>


    <div class="modal fade" id="pharmacistComplaintModal" tabindex="-1" role="dialog" aria-labelledby="pharmacistComplaintModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Describe your dissatisfaction with {{complaint.doctor.name+" "+complaint.doctor.surname}}</h5>
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
import PharmacistDataService from '../service/PharmacistDataService.js';

export default {
    name: 'PharmacistComplaint',
    data() {
        return {
            author: {},
            complaint: {id: 0, author: 0, pharmacy: 0, doctor: 0, description: "", response: ""},
            queryParams: {name: "", description: "", address: "", minRating: 0, maxRating: 5},
            pharmacists: new Map(),
            results: new Map(),
        };
    },
    methods: {
        setPharmacists(){
            PharmacistDataService.getVisitedPharmacists(this.author.id).then(response => {
                for(let doctor of response.data){
                    if(doctor.role == "PHARMACIST"){
                        this.pharmacists.set(doctor.id.toString(), doctor);
                    }
                }

                this.results = this.pharmacists;
            });
        },
        filterResults(){
            this.results = new Map();

            for(let [key, value] of this.pharmacists){
                if(value.name.includes(this.queryParams.name) && value.surname.includes(this.queryParams.surname) &&
                   (value.address.street+" "+value.address.number+", "+value.address.city).includes(this.queryParams.address) && (value.rating <= this.queryParams.maxRating && value.rating >= this.queryParams.minRating)){
                    this.results.set(value.id, value);
                }
            }
        },
        setPharmacist(pharmacist){
            this.complaint.doctor = pharmacist;
        },
        sendComplaint(){
            this.complaint.author = this.author.id;
            this.complaint.doctor = this.complaint.doctor.id;
            ComplaintDataService.sendComplaint(this.complaint).then(response => {
                this.complaint.description = "";
            });
        }
    },
    mounted(){
        this.setPharmacists();
    },
    created(){
        this.author = AuthService.getCurrentUser();
    },
}
</script>