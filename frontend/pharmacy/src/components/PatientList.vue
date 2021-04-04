<template>
    <div class="container">
        <div class="row">
            <div class="container" style="padding: 0px;">
                <form v-on:submit.prevent="searchPatients(this)" style="float: right;">
                    <div class="form-row form-inline mb-2" >
                        <div class="form-group col-auto">
                            <input v-model="searchName" type="text" class="form-control" id="name" placeholder="First Name">
                        </div>
                        <div class="form-group col-auto">
                            <input v-model="searchSurname" type="text" class="form-control" id="surname" placeholder="Last Name">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="container" style="padding: 0px;">
                <table class="table table-hover table-striped box-shadow">
                    <thead class="card-header">
                        <th style="">First name</th>
                        <th>Last name</th>
                        <th>Address</th>
                        <th>Phone number</th>
                    </thead>
                    <tbody>
                        <tr :key="p.username" v-for="p in patients" v-on:dblclick="patientInfo(Object.values(p))" class="clickable">
                            <td>{{p.name}}</td>
                            <td>{{p.surname}}</td>
                            <td>{{UtilService.AddressToString(p.address)}}</td>
                            <td>{{p.phoneNumber}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- <form>
    <div class="form-group row">
    <label for="name" class="col col-form-label">First Name</label>
    <div class="col">
      <input type="text" class="form-control" id="name" placeholder="First Name">
    </div>
  </div>
  <div class="form-group row">
    <label for="surname" class="col col-form-label">Last Name</label>
    <div class="col">
      <input type="text" class="form-control" id="surname" placeholder="Last Name">
    </div>
  </div>
</form> -->
</template>

<script>
import PatientDataService from '../service/PatientDataService.js';
import UtilService from '../service/UtilService.js';

export default {
    name: 'PatientList',
    setup() {
      return {UtilService}
    },
    data() {
        return {
            patients: [],
            message: null,
            searchName: "",
            searchSurname: ""
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
        },
        searchPatients(e){
            if (this.searchName || this.searchSurname){
                PatientDataService.searchPatients(this.searchName, this.searchSurname)
                .then(response => {
                        this.patients = response.data;
                        console.log(response.data);
                    })
                .catch(function (error) {
                    if (error.response) {
                    console.log(error.response.data);
                    } else if (error.request) {
                    console.log(error.request);
                    }
                    console.log("error.config");
                    console.log(error.config);
                });
            }
            else {
                this.refreshPatients();
            }
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
        overflow: auto;
        position: relative;
    }
    .clickable {
        cursor: pointer;
    }
    .active {
        color: #42b983;
    }
    /*th {
        position: sticky;
        top: 0;
        color:  #007bff;
        text-decoration: none;
    }*/
</style>