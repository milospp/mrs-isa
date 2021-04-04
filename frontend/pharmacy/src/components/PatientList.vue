<template>
    <div class="container">
        <div class="row">

                <form>
                    <div class="form-row form-inline mb-2" >
                        <div class="form-group col-auto">
                            <input type="text" class="form-control" id="name" placeholder="First Name">
                        </div>
                        <div class="form-group col-auto">
                            <input type="text" class="form-control" id="surname" placeholder="Last Name">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>  
                </form>

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
        },
        searchBar() {
            const searchFocus = document.getElementById('search-focus');
            const keys = [
            { keyCode: 'AltLeft', isTriggered: false },
            { keyCode: 'ControlLeft', isTriggered: false },
            ];

            window.addEventListener('keydown', (e) => {
            keys.forEach((obj) => {
                if (obj.keyCode === e.code) {
                obj.isTriggered = true;
                }
            });

            const shortcutTriggered = keys.filter((obj) => obj.isTriggered).length === keys.length;

            if (shortcutTriggered) {
                searchFocus.focus();
            }
            });

            window.addEventListener('keyup', (e) => {
            keys.forEach((obj) => {
                if (obj.keyCode === e.code) {
                obj.isTriggered = false;
                }
            });
            });

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