<template>
<div style="height: 500px; overflow-y: scroll;">
<form v-on:submit.prevent="">
    <table class="table table-striped">
        <thead class="card-header">
            <th>Patient</th>
            <th>Text</th>
            <th>About</th>
            <th></th>
        </thead>
        <tbody>
            <tr :key="complaint.id" v-for="complaint in this.allComplaints">
                <td>{{complaint.author.name+" "+complaint.author.surname}}</td>
                <td style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap; width: 350px; display: inline-block;">{{complaint.description}}</td>
                <td>{{setAbout(complaint)}}</td>
                <td><button type="submit" class="btn btn-primary"
                v-on:click="setComplaint(complaint)" data-toggle="modal" data-target="#responseModal">Respond</button> </td>
            </tr>
        </tbody>
    </table>
</form>
</div>


    <div class="modal fade" id="responseModal" tabindex="-1" role="dialog" aria-labelledby="responseModal" aria-hidden="true" size="lg">
        <div class="modal-dialog modal-dialog-centered" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Respond to {{this.chosenComplaint.author.name+" "+this.chosenComplaint.author.surname}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div>
                    <table class="table table-striped" >
                        <thead class="card-header">
                            <th>Complaint description</th>
                            <th>Your response</th>
                        </thead>
                        <tbody>
                            <tr>
                                <td><textarea v-model="this.chosenComplaint.description" rows="5" style="width : 100%;" readonly></textarea></td>
                                <td><textarea v-model="this.chosenComplaint.response" rows="5" style="width : 100%;"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit" data-dismiss="modal" v-on:click="sendResponse(this.chosenComplaint)">Confirm</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>

import ComplaintDataService from '@/service/ComplaintDataService.js';
import PharmacyDataService from '@/service/PharmacyDataService.js';
import UserDataService from '@/service/UserDataService.js';
import AuthService from '@/service/AuthService.js';


export default {
    name: "ComplaintResponse",
    data(){
        return {
            allComplaints: [],
            chosenComplaint: {author: {name: "", surname: ""}, description: ""},
        }
    },
    methods: {
        setAbout(complaint){
            if(complaint.doctor){
                return complaint.doctor.name+" "+complaint.doctor.surname;
            }
            if(complaint.pharmacy){
                return complaint.pharmacy.name;
            }
        },
        setComplaint(complaint){
            this.chosenComplaint = complaint;
            console.log(this.chosenComplaint);
        },

        sendResponse(complaint){
            let fullAuthor = JSON.parse(JSON.stringify(this.chosenComplaint.author));
            let fullPharmacy = {};
            let fullDoctor = {};
            this.chosenComplaint.author =  this.chosenComplaint.author.id;
            if(this.chosenComplaint.pharmacy){
                fullPharmacy = JSON.parse(JSON.stringify(this.chosenComplaint.pharmacy));
                this.chosenComplaint.pharmacy = this.chosenComplaint.pharmacy.id;
            }
            if(this.chosenComplaint.doctor){
                fullDoctor = JSON.parse(JSON.stringify(this.chosenComplaint.doctor));
                this.chosenComplaint.doctor = this.chosenComplaint.doctor.id;
            }

            ComplaintDataService.respondToComplaint(complaint).then(response => {
                this.chosenComplaint.author =  fullAuthor;
                if(fullPharmacy){
                    this.chosenComplaint.pharmacy = fullPharmacy;
                }
                if(fullDoctor){
                    this.chosenComplaint.doctor = fullDoctor;
                }
            });
        }
    },
    created(){
        ComplaintDataService.getAllComplaints().then(response => {
            for(let complaint of response.data){
                if(!complaint.response){
                    this.allComplaints.push(complaint);
                }
            }
            
            UserDataService.getAllUsers().then(response => {
                for(let user of response.data){
                    for(let complaint of this.allComplaints){
                        if(user.role == "PATIENT"){
                            if(complaint.author == user.id){
                                complaint.author = user;
                            }
                        }else if(user.role == "DERMATOLOGIST" || user.role == "PHARMACIST"){
                            if(complaint.doctor == user.id){
                                complaint.doctor = user;
                            }
                        }
                    }
                }
            });
            PharmacyDataService.getAllPharmacies().then(response => {
                for(let pharmacy of response.data){
                    for(let complaint of this.allComplaints){
                        if(pharmacy.id == complaint.pharmacy){
                            complaint.pharmacy = pharmacy;
                        }
                    }
                }
            });
        });

        console.log(this.allComplaints);

        
    }
}
</script>