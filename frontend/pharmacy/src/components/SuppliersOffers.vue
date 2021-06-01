<template>

<div class="row mb-3">
			<div class="col-md-12">
				<a class="btn btn-primary float-right" data-toggle="collapse" href="#searchOfferCollapse" role="button" aria-expanded="false" aria-controls="searchOfferCollapse">
					Filter
				</a>
			</div>
	</div>
  <div class="searchForm collapse mb-2 bg-light" id="searchOfferCollapse">
    <form v-on:submit.prevent="this.searchOffers()" class="p-3">
          <h4>Filter</h4>

        <div class="form-row">
            <div class="form-group col-md-4">
              <label for="inputDesc">Description</label>
              <input type="text" class="form-control" id="inputName" v-model="searchParams.description">
            </div>

            <div class="form-group col-md-2">
              <label for="inputStatus">Status</label>
              <select class="form-control" id="inputStatus" v-model="searchParams.status">
                <option value="ACCEPTED">Accepted</option>
                <option value="REJECTED">Rejected</option>
                <option value="INDEFINITELY">Awaiting acceptance</option>
              </select>          
            </div>

            <div class="form-group col-md-2">
              <label for="inputPrice">Price range</label>

              <div class="input-group">

                  <input type="number" class="form-control" id="inputMinPrice" min="0" v-model="searchParams.minPrice" size="7">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroupPrepend2">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputMaxPrice" :min="Math.max(0,searchParams.minPoints)" v-model="searchParams.maxPrice" size="7">
              </div>
            </div>

            <div class="form-group col-md-2">
                <label for="inputMinRating">Start date from</label>
                <input type="datetime-local" class="form-control" id="startTimeFrom" v-model="searchParams.startDate" @change="">
            </div>

            <div class="form-group col-md-2">
                <label for="inputMinRating">Start date to</label>
                <input type="datetime-local" class="form-control" id="startTimeTo" v-model="searchParams.endDate" @change="">
            </div>


            <div class="form-group col-md-2">
              <label for="sortSelect">Sort</label>
              <select class="form-control" id="sortSelect" v-model="searchParams.sort">
                <option value="DESC_ASC">By desc. asc.</option>
                <option value="DESC_DES">By desc. des.</option>
                <option value="STATUS_ASC">By status asc.</option>
                <option value="STATUS_DES">By status des.</option>
                <option value="PRICE_ASC">By price asc.</option>
                <option value="PRICE_DES">By price des.</option>
                <option value="SDATE_ASC">By start date asc.</option>
                <option value="SDATE_DES">By start date des.</option>
                <option value="EDATE_ASC">By end date asc.</option>
                <option value="EDATE_DES">By end date des.</option>
              </select>          
            </div>

        </div>
        <button type="submit" class="btn btn-primary">Search</button> 
    </form>
</div>


<div style="height: 400px; overflow-y: scroll;">
<form v-on:submit.prevent="">
    <table class="table table-striped">
        <thead class="card-header">
            <th>Description</th>
            <th>Total price</th>
            <th>Start Date</th>
            <th>Delivery date</th>
            <th>Status</th>
            <th></th>
        </thead>
        <tbody>
            <tr :key="offer.id" v-for="offer in this.suppliersOffers">
                <td>{{offer.offerDescription}}</td>
                <td>{{offer.price}}</td>
                <td>{{offer.startDate[1]+"/"+offer.startDate[2]+"/"+offer.startDate[0]+" - "+ offer.startDate[3]+":"+offer.startDate[4]}}</td>
                <td>{{offer.endDate[1]+"/"+offer.endDate[2]+"/"+offer.endDate[0]+" - "+ offer.endDate[3]+":"+offer.endDate[4]}}</td>
                <td>{{statusNrToTxt(offer.status)}}</td>
                <td v-if="offer.status == 'INDEFINITELY'"><button type="submit" class="btn btn-primary"
                v-on:click="this.setEdittedOffer(offer)" data-toggle="modal" data-target="#editOfferModal">Edit</button> </td>
            </tr>
        </tbody>
    </table>
</form>
</div>

<div class="modal fade" id="editOfferModal" tabindex="-1" role="dialog" aria-labelledby="editOfferModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="Potvrdica">Editing offer: </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
            </div>
            <div>
                <table class="table table-striped">
                    <thead class="card-header">
                        <th>Description</th>
                        <th>Delivery date</th>
                    </thead>
                    <tbody>
                        <tr>
                            <td><textarea v-model="edittedOffer.offerDescription" rows="3" style="width : 100%;"></textarea></td>
                            <td><input type="datetime-local" class="form-control" id="endTime" v-model="edittedOffer.endDate" 
                            v-bind:max="this.orderExpires" v-bind:min="this.formatDateForPicker(this.edittedOffer.startDate)"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit" data-dismiss="modal" v-on:click="this.updateOffer(this.edittedOffer)">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</div>


</template>

<script>

import OfferDataService from '@/service/OfferDataService.js';
import OrderDataService from '@/service/OrderDataService.js';
import AuthService from '@/service/AuthService.js';

export default {
    name: 'SuppliersOffers',
    data(){
        return {
            id:"",
            suppliersOffers: [],
            searchParams: {description:"", status:"", minPrice: 0, maxPrice: Number.MAX_SAFE_INTEGER, startDate: new Date().toISOString().substring(0, 16), endDate: new Date().toISOString().substring(0, 16)},
            edittedOffer: { endDate: [], startDate: []},
            orderExpires: [],
        };   
    },
    methods: {
        getSuppliersOffers(){
            OfferDataService.getOffersOfSupplier(this.id).then(response => {
                this.suppliersOffers = response.data;
            });
        },
        statusNrToTxt(status){
            if(status == 0){
                return "Accepted";
            }else if(status == 1){
                return "Rejected";
            }else{
                return "Awaiting acceptance";
            }
        },
        searchOffers(){
            OfferDataService.searchOffers(this.searchParams)
                .then(response => {
                    this.suppliersOffers = response.data;
                    console.log(response.data);
            });
        },
        setEdittedOffer(offer){
            this.edittedOffer = offer;
            OrderDataService.findOrderById(this.edittedOffer.order).then(response => {
                this.orderExpires = this.formatDateForPicker(response.data.endDate);
            });
        },
        formatDateForPicker(date){
            return date[0]+"-"+('0' + date[1]).slice(-2)+"-"+('0' + date[2]).slice(-2)+"T"+('0' + date[3]).slice(-2)+":"+('0' + date[4]).slice(-2);
        },
        updateOffer(offer){
            console.log(offer);
            offer.supplier.id = this.id;
            offer.supplier.email = AuthService.getCurrentUser().email;
            let dateArray = offer.endDate.split("T")[0].split("-");
            dateArray.push(this.orderExpires.split("T")[1].split(":")[0]);
            dateArray.push(this.orderExpires.split("T")[1].split(":")[1]);
            dateArray[0] = parseInt(dateArray[0]);
            dateArray[1] = parseInt(dateArray[1]);
            dateArray[2] = parseInt(dateArray[2]);
            dateArray[3] = parseInt(dateArray[3]);
            dateArray[4] = parseInt(dateArray[4]);
            offer.endDate = dateArray;
            OfferDataService.updateOffer(offer);
        },
    },
    created(){
        this.id = AuthService.getCurrentUser().id;
        this.getSuppliersOffers();
    }
}
</script>