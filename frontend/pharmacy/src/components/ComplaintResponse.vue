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
                v-on:click="printShit()" data-toggle="modal" data-target="#responseModal">Respond</button> </td>
            </tr>
        </tbody>
    </table>
</form>
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

        printShit(){
            console.log(this.allComplaints);
        },
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
                            break;
                        }
                    }
                }
            });
        });

        

        
    }
}
</script>