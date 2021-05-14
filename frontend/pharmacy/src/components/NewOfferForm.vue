<template>

<h3>Available orders</h3>

<div style="height: 400px; overflow-y: scroll;">
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
                <td>{{order.startDate[1]+"/"+order.startDate[2]+"/"+order.startDate[0]+" - "+ order.startDate[3]+":"+order.startDate[4]}}</td>
                <td>{{order.endDate[1]+"/"+order.endDate[2]+"/"+order.endDate[0]+" - "+ order.endDate[3]+":"+order.endDate[4]}}</td>
                <td><form v-on:click.prevent="this.setMedsIntoModal(order.allMedicines)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#medicinesModal">View medicines</button></form></td>
                <td><form v-on:click.prevent="this.selectedOrder(order)"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newOfferModal">Select</button></form></td>
            </tr>
        </tbody>
    </table>
</form>
</div>

<div class="modal fade" id="medicinesModal" tabindex="-1" role="dialog" aria-labelledby="medicinesModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="Potvrdica">Ordered medicines: </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
            </div>
            <div>
                <table class="table table-striped">
                    <thead class="card-header">
                        <th>Code</th>
                        <th>Name</th>
                        <th>Quantity</th>
                    </thead>
                    <tbody>
                        <tr v-for="item in this.medicinesInOrder"><td>{{item.medicine.code}}</td><td>{{item.medicine.name}}</td><td>{{item.quantity}}</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="newOfferModal" tabindex="-1" role="dialog" aria-labelledby="newOfferModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="Potvrdica">Fill in delivery details: </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
            </div>
            <div>
                <form v-on:submit="">
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <td>Select delivery time: </td><td><input type="datetime-local" class="form-control" id="startTime" v-model="newOffer.end_date" @change="" v-bind:max="this.orderExpires"></td>
                            </tr>
                            <tr>
                                <td>Description: </td><td><textarea v-model="newOffer.offer_description" rows="3" style="width : 100%;"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit">Confirm offer</button>
                </div>
            </div>
        </div>
    </div>
</div>



</template>

<script>
import OrderDataService from '../service/OrderDataService.js';

export default {
    name: 'NewOfferForm',
    data(){
        return{
            availableOrders: [],
            medicinesInOrder: [],
            newOffer: {end_date: [], offer_description: "", price: 0, start_date: [], status: 0, order_id: 0, supplier_id: 0},
            orderExpires: [],
        };
    },
    methods: {
        getAvailableOrders(){
            OrderDataService.getAvailableOrders().then(response => {
                this.availableOrders = response.data;
                console.log(this.availableOrders);
            });
        },

        setMedsIntoModal(medicines){
            this.medicinesInOrder = medicines;
        },
        selectedOrder(order){
            this.orderExpires = this.formatDateForPicker(order.endDate);
            console.log(this.orderExpires);
        },
        formatDateForPicker(date){
            return date[0]+"-"+('0' + date[1]).slice(-2)+"-"+('0' + date[2]).slice(-2)+"T"+('0' + date[3]).slice(-2)+":"+('0' + date[4]).slice(-2);
        }
    },
    created(){
        this.getAvailableOrders();
    },
}
</script>