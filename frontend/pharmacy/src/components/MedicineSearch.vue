<template>

    <!-- <form v-on:submit.prevent="showResults()"> -->
        <table>
            <tr>
                <td>Name: </td><td><input type="text" id="name" v-model="queryParams.name" v-on:change="filterResults()"></td>
                <td>Type: </td><td><input type="text" id="type" v-model="queryParams.type" v-on:change="filterResults()"></td>
                <td>Form: </td><td>
                    <select id="shape" v-model="queryParams.shape" v-on:change="filterResults()">
                        <option value="" selected>Choose here</option>
                        <option value="tablets">Tablets</option>
                        <option value="pills">Pills</option>
                        <option value="powder">Powder</option>
                        <option value="syrup">Syrup</option>
                        <option value="saline">Saline</option>
                    </select>
                </td>
                <td>Manufacturer: </td><td><input type ="text" id="manufacturer" v-model="queryParams.manufacturer" v-on:change="filterResults()"></td>
                <!-- <td><button type="button" class="btn btn-block btn-primary">Search</button></td> -->
            </tr>
        </table>
    <!-- </form> -->

    <h3>Search results</h3>

    <div>
        <table class="table table-striped">
            <thead class="card-header">
                <th>Name</th>
                <th>Type</th>
                <th>Form</th>
                <th>Manufacturer</th>
                <th>&emsp;</th>
            </thead>
            <tbody>
                <tr v-for="m in this.results">
                <td>{{m.name}}</td>
                <td>{{m.type}}</td>
                <td>{{m.shape}}</td>
                <td>{{m.manufacturer}}</td>
                <td><button type="button" class="btn btn-block btn-primary" data-toggle="modal" data-target="#prices" v-on:click="fetchPrices(m)">View prices</button></td>
                </tr>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="prices" tabindex="-1" role="dialog" aria-labelledby="Prices" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Prices in pharmacies for {{this.medName}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div>
                    <table class="table table-striped">
                        <thead class="card-header">
                            <th>Pharmacy</th>
                            <th>Price in RSD</th>
                        </thead>
                        <tbody>
                            <tr v-for="p in this.prices"><td>{{p.pharmacyName}}</td><td>{{p.price}}</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</template>

<script>

import MedicineDataService from '../service/MedicineDataService.js';
export default {
    name: 'MedicineSearch',
    data() {
        return {
            message: null,
            medicines: [],
            results: [],
            prices: [],
            medName: "",
            queryParams: { name : "", type : "", shape : "", manufacturer : "" }
        };
    },
    methods: {
        getMedicines() {
            MedicineDataService.getAllMedicines()
                .then(response => {
                    this.medicines = response.data;
                    this.results = this.medicines;
                    console.log(this.medicines);
                });
                
        },
        showResults(){
            console.log(this.queryParams);
        },
        filterResults(){
            this.results = [];

            for(var m of this.medicines){
                if(m.name.startsWith(this.queryParams.name) && m.type.startsWith(this.queryParams.type) &&
                   m.shape.startsWith(this.queryParams.shape) && m.manufacturer.startsWith(this.queryParams.manufacturer)){
                    this.results.push(m);
                }
            }
        },
        fetchPrices(medicine){
            this.medName = medicine.name;
            this.prices = [];
            MedicineDataService.getPricesOfMedicine(medicine).then(response => {
                this.prices = response.data;
                console.log(this.prices);
            });
        }
    },
    created() {
        this.getMedicines();
        
    }
}

</script>