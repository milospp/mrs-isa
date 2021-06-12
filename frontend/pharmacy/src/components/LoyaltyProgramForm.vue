<template>

    <div class="container">
        <h3>Define a new user category</h3>
        <div class="container">
            <form v-on:submit.prevent="checkNewCategory(this)">
                <div class="form-group row">
                    <label for="inputName" class="col-form-label col-sm-2">Category</label>
                    <input 
                            type="text" class="form-control col-sm-4" id="inputName" v-model="newCategory.name" required
                            size="31" placeholder="category name" >
                    <label for="inputPoints" class="col-form-label col-sm-2">Point limit</label>
                    <input 
                            type="number" class="form-control col-sm-1" id="pointsLimit" v-model="newCategory.requiredPoints" v-bind:min="0"
                            required="required" title="Users with points above this limit will be granted a discount of your choosing">
                    <label for="inputDiscount" class="col-form-label col-sm-1">Discount</label>
                    <input 
                            type="number" class="form-control col-sm-1" id="discount" v-model="newCategory.discount" v-bind:min="0"
                            required="required" title="Discount input">
                    <label class="col-form-label col-sm-1">%</label>
                </div>
                <button type="submit" id="dugme" class="btn btn-primary" align="end">Add</button>
            </form>
        </div>

        <h3>Current categories</h3>
    </div>

</template>
<script>
import LoyaltyDataService from '../service/LoyaltyDataService.js';

export default {
    name: 'LoyaltyProgramForm',
    data(){
        return {
            newCategory: {name: "", requiredPoints: 0, discount: 0},
        }
    },
    methods: {
        checkNewCategory(e){
            console.log(this.newCategory);
        },
    },
    created(){

    },
}
</script>