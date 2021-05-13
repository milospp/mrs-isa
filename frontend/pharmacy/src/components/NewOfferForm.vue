<template>

<h3>Available orders</h3>

<div style="height: 300px; overflow-y: scroll;">
<form v-on:submit.prevent="">
    <table class="table table-striped">
        <thead class="card-header">
            <th>Pharmacy</th>
            <th>Orderer</th>
            <th>Start date</th>
            <th>End date</th>
            <th></th>
            <th></th>
        </thead>
        <tbody>
            <tr :key="order.id" v-for="order in this.availableOrders">
                <td>{{order.pharmacy.name}}</td>
                <td>{{order.author.name+" "+order.author.surname}}</td>
                <td>{{order.startDate[1]+"/"+order.startDate[2]+"/"+order.startDate[0]+" - "+ order.startDate[4]+":"+order.startDate[5]}}</td>
                <td>{{order.endDate[1]+"/"+order.endDate[2]+"/"+order.endDate[0]+" - "+ order.endDate[4]+":"+order.endDate[5]}}</td>
                <td><form v-on:click.prevent=""><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#podaci">View medicines</button></form></td>
                <td><form v-on:click.prevent=""><button type="button" class="btn btn-primary">Select</button></form></td>
            </tr>
        </tbody>
    </table>
</form>
</div>





</template>

<script>
import OrderDataService from '../service/OrderDataService.js';

export default {
    name: 'NewOfferForm',
    data(){
        return{
            availableOrders: [],
        };
    },
    methods: {
        getAvailableOrders(){
            OrderDataService.getAvailableOrders().then(response => {
                this.availableOrders = response.data;
            });
        }
    },
    created(){
        this.getAvailableOrders();
    },
}
</script>