<template>
    <div class="container">
        <div class="row">
        <table class="table table-hover table-bordered box-shadow">
            <thead class="card-header">
                <th>First name</th>
                <th>Last name</th>
                <th>Address</th>
                <th>Phone number</th>
            </thead>
            <tbody>
                <tr :key="p.username" v-for="p in patients" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
                    <td>{{p.name}}</td>
                    <td>{{p.surname}}</td>
                    <td>{{p.address.street}} {{p.address.number}}, {{p.address.city}}, {{p.address.state}}</td>
                    <td>{{p.phoneNumber}}</td>
                </tr>
            </tbody>
            
        </table>
        </div>
    </div>
</template>

<script>
import PatientDataService from '../service/PatientDataService.js';

export default {
    name: 'PatientList',
    data() {
        return {
            patients: [],
            message: null,
        };
    },
    methods: {
        refreshPatients() {
            PatientDataService.retrieveAllPatients() // HARDCODED
                .then(response => {
                    this.patients = response.data;
                    console.log(response.data);
                });
        },
        patientInfo(patient){
            alert(patient);
        }
    },
    mounted() {
        this.refreshPatients();
    },
    created() {
        this.refreshPatients();
    }
}
</script>

<style scoped>
    .table {
        color: inherit;
    }
    .clickable {
        cursor: pointer;
    }
</style>