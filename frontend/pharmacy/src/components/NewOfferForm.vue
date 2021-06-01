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
                <form>
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <td>Select delivery time: </td><td><input type="datetime-local" class="form-control" id="startTime" v-model="newOffer.endDate" 
                                @change="setDeliveryDate()" v-bind:max="this.orderExpires" v-bind:min="(new Date()).toISOString().substring(0, 16)"></td>
                            </tr>
                            <tr>
                                <td>Description: </td><td><textarea v-model="newOffer.offerDescription" rows="3" style="width : 100%;"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit" v-on:click="placeNewOffer()" data-dismiss="modal">Confirm offer</button>
                </div>
            </div>
        </div>
    </div>
</div>



</template>

<script>
import OrderDataService from '../service/OrderDataService.js';
import AuthService from '@/service/AuthService.js';
import MedicineDataService from '@/service/MedicineDataService.js';
import OfferDataService from '@/service/OfferDataService.js';

export default {
    name: 'NewOfferForm',
    data(){
        return{
            id: "",
            availableOrders: [],
            medicinesInOrder: [],
            suppliersMedicines: [],
            currentOrder: [],
            newOffer: {id: 0, endDate: [], offerDescription: "", price: 0, startDate: [], status: 0, order: 0, supplier: 0},
            orderExpires: [],
        };
    },
    methods: {
        getAvailableOrders(id){
            OrderDataService.getAvailableOrders(id).then(response => {
                this.availableOrders = response.data;
                console.log(this.availableOrders);
            });
        },

        setMedsIntoModal(medicines){
            this.medicinesInOrder = medicines;
        },
        selectedOrder(order){
            this.orderExpires = this.formatDateForPicker(order.endDate);
            this.currentOrder = order;
            console.log(this.currentOrder);
        },
        formatDateForPicker(date){
            return date[0]+"-"+('0' + date[1]).slice(-2)+"-"+('0' + date[2]).slice(-2)+"T"+('0' + date[3]).slice(-2)+":"+('0' + date[4]).slice(-2);
        },
        placeNewOffer(){
            this.newOffer.supplier = AuthService.getCurrentUser();
            this.newOffer.order = this.currentOrder.id;

            if(this.newOffer.endDate.length == 0){
                alert("You have not picked a delivery date!");
                return;
            }

            if(!this.hasEnoughMeds(this.currentOrder)){
                alert("You do not have enough medicines at your disposal for this transaction!");
                return;
            }

            this.newOffer.price = this.calculateOfferPrice(this.currentOrder);

            console.log(this.newOffer);
            OfferDataService.sendOffer(this.newOffer).then(response => {
                this.$router.go("/addNewOffer");
            })
            
        },
        setDeliveryDate(){
            console.log(this.newOffer.endDate);
        },
        hasEnoughMeds(order){
            for(var item of order.allMedicines){
                if(item.quantity > this.suppliersMedicines[item.medicine.code].inStock){
                    return false;
                }
            }

            return true;
        },
        calculateOfferPrice(order){
            let total = 0;
            for(var item of order.allMedicines){
                total += this.suppliersMedicines[item.medicine.code].currentPrice*item.quantity;
            }

            return total;
        }
    },
    created(){
        this.id = AuthService.getCurrentUser().id;
        this.getAvailableOrders(this.id);
        MedicineDataService.getSuppliersMedicines(this.id).then(response => {
            this.suppliersMedicines = response.data;
            console.log(this.suppliersMedicines);
        });
    },
}
</script>