<template>

    <div style="height: 450px; overflow-y: scroll;">
    <table class="table table-striped">
        <thead class="card-header">
            <th>Author</th>
            <th>Description</th>
            <th></th>
            <th>About</th>
            <th>Response</th>
        </thead>
        <tbody>
            <tr :key="complaint.id" v-for="complaint in this.myResponses">
                <td>{{complaint.author.name+" "+complaint.author.surname}}</td>
                <td style="width: 350px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; display: inline-block;">
                    {{complaint.description}} 
                </td>
                <td>
                    <button class="btn btn-primary" v-on:click="showDescription(complaint)">Full description</button>
                </td>
                <td>{{setAbout(complaint)}}</td>
                <td>
                    <button class="btn btn-primary" v-on:click="showResponse(complaint)">View response</button>
                </td>
            </tr>
        </tbody>
    </table>
    </div>

</template>

<script>

import ComplaintDataService from '@/service/ComplaintDataService.js';
import PharmacyDataService from '@/service/PharmacyDataService.js';
import UserDataService from '@/service/UserDataService.js';
import AuthService from '@/service/AuthService.js';

export default {
    name: "RespondedComplaints",
    data(){
        return {
            admin: {},
            myResponses: [],
        }
    },
    methods: {
        getResponses(){
            ComplaintDataService.getAdminResponses(this.admin.id).then(response => {
                this.myResponses = response.data;

                UserDataService.getAllUsers().then(response => {
                    for(let user of response.data){
                        for(let complaint of this.myResponses){
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
                        for(let complaint of this.myResponses){
                            if(pharmacy.id == complaint.pharmacy){
                                complaint.pharmacy = pharmacy;
                            }
                        }
                    }
                });

                console.log(this.myResponses);
            });
        },
        setAbout(complaint){
            if(complaint.doctor){
                return complaint.doctor.name+" "+complaint.doctor.surname;
            }
            if(complaint.pharmacy){
                return complaint.pharmacy.name;
            }
        },
        showDescription(complaint){
            this.$toast.show(complaint.description, {position: "top-left", type: "info"});
        },
        showResponse(complaint){
            this.$toast.show(complaint.response, {position: "top-right", type: "info"});
        },
    },
    created(){
        this.admin = AuthService.getCurrentUser();
        this.getResponses();
        
    },
}

</script>