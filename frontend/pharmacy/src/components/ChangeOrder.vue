<template>
    <div v-if="!this.narudzbenica">
        <label> Something goes wrong </label>
    </div>
    <div v-else-if="this.narudzbenica.allOffer.length == 0">
        <label> Start date: {{this.narudzbenica.startDate[2]}}.{{this.narudzbenica.startDate[1]}}.{{this.narudzbenica.startDate[0]}}. 
        {{this.narudzbenica.startDate[3]}}:{{this.narudzbenica.startDate[4]}} <br />
             End date: <input type="datetime-local" v-model="this.narudzbenica.endDate" placeholder="this.narudzbenica.endDate"/>  <br />
             Chosen offer: /</label>

        <div class="row">
        <div class="col-md-12">
            <div>
            <ul class="nav nav-tabs">
                <li class="nav-item active"><a class="nav-link" data-toggle="tab" href="#tab-medicines">Order info</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#menu1">All medicines</a></li>
            </ul>
            
            <div class="tab-content">
                <div id="tab-medicines" class="tab-pane in fade active in">
                <h3>Medicines</h3>
                
            </div>

            <div id="menu1" class="tab-pane fade">
                <h3>All medicines</h3>
                    <!-- <table class="table table-striped">
                    <thead class="card-header">
                    <th>Code</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Structure</th>
                    <th></th>
                    </thead>
                    <tbody>
                        <tr :key="l" v-for="l in this.sviLekovi">
                        <td>{{l.code}}</td>
                        <td>{{l.name}}</td>
                        <td>{{l.type}}</td>
                        <td>{{l.structure}}</td>
                        <td><form v-on:click.prevent="dodajUKorpu(l)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#obavestenje">Add to cart</button></form></td>
                    </tr>
                    </tbody>
                </table> -->
                </div>

            </div>
            </div>
        </div>
        </div>
    </div>         <!-- uslovni div -->
    <div v-else>
        <label> Start date: {{this.narudzbenica.startDate[2]}}.{{this.narudzbenica.startDate[1]}}.{{this.narudzbenica.startDate[0]}}. 
        {{this.narudzbenica.startDate[3]}}:{{this.narudzbenica.startDate[4]}} </label>
        <label> End date: {{this.narudzbenica.endDate[2]}}.{{this.narudzbenica.endDate[1]}}.{{this.narudzbenica.endDate[0]}}. 
            {{this.narudzbenica.endDate[3]}}:{{this.narudzbenica.endDate[4]}} </label>
        <label> Chosen offer: </label>
    </div>


</template>

<script>
import AuthService from "../service/AuthService.js";
import OrderDataService from '../service/OrderDataService.js';

export default {
    name: 'ChangeOrder',
    data() {
        return {
            narudzbenica: null,
        };
    },
    created() {
        var podaci = JSON.parse(localStorage.getItem("narudzbenica"));
        OrderDataService.findOneOrder(podaci)
            .then(response => {this.narudzbenica = response.data;});
        
            
    },
    mounted() {  

    },
    methods : {

    }
}
</script>