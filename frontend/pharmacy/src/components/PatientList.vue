<template>
    <div class="container" >
        <table class="table table-hover">
            <thead>
                <th>Korisničko ime</th>
                <th>Ime</th>
                <th>Prezime</th>
            </thead>
            <tbody>
                <tr :key="p.username" v-for="p in patients">
                    <td>{{p.name}}</td>
                    <td>{{p.surname}}</td>
                    <td>{{p.username}}</td>
                </tr>
            </tbody>
            
        </table>
        
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
        created() {
            this.refreshPatients();
            console.log(this.patients);
        }
    }
}
</script>