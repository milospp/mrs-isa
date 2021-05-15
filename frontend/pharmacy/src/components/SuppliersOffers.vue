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
              <input type="text" class="form-control" id="inputName" v-model="searchParams.name">
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

                  <input type="number" class="form-control" id="inputPrice" min="0" v-model="searchParams.minPrice">

                  <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroupPrepend2">-</span>
                  </div>
                  <input type="number" class="form-control" id="inputPrice" :min="Math.max(0,searchParams.minPoints)" v-model="searchParams.maxPrice">
              </div>
            </div>

            <div class="form-group col-md-2">
                <label for="inputMinRating">Start date</label>
                <input type="datetime-local" class="form-control" id="startTime" v-model="searchParams.startDate" @change="">
            </div>

            <div class="form-group col-md-2">
                <label for="inputMinRating">Delivery date</label>
                <input type="datetime-local" class="form-control" id="startTime" v-model="searchParams.endDate" @change="">
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
                <option value="SDATE_DES">By start des.</option>
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
        </thead>
        <tbody>
            <tr :key="offer.id" v-for="offer in this.suppliersOffers">
                <td>{{offer.offerDescription}}</td>
                <td>{{offer.price}}</td>
                <td>{{offer.startDate[1]+"/"+offer.startDate[2]+"/"+offer.startDate[0]+" - "+ offer.startDate[3]+":"+offer.startDate[4]}}</td>
                <td>{{offer.endDate[1]+"/"+offer.endDate[2]+"/"+offer.endDate[0]+" - "+ offer.endDate[3]+":"+offer.endDate[4]}}</td>
                <td>{{statusNrToTxt(offer.status)}}</td>
            </tr>
        </tbody>
    </table>
</form>
</div>



</template>

<script>

import OfferDataService from '@/service/OfferDataService.js';
import AuthService from '@/service/AuthService.js';

export default {
    name: 'SuppliersOffers',
    data(){
        return {
            id:"",
            suppliersOffers: [],
            searchParams: {description:"", status:"", minPrice: 0, maxPrice: 0, startDate: [], endDate: []},
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

        }
    },
    created(){
        this.id = AuthService.getCurrentUser().id;
        this.getSuppliersOffers();
    }
}
</script>