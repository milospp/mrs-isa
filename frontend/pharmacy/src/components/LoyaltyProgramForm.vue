<template>

    <div class="container">
        <h3>Define a new user category</h3>
        <div class="container">
            <form v-on:submit.prevent="createNewCategory()">
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
                            required="required" title="Discount input" step="0.1">
                    <label class="col-form-label col-sm-1">%</label>
                </div>
                <button type="submit" id="dugme" class="btn btn-primary" align="end">Add</button>
            </form>
        </div>
        <br>
        <h3>Current categories</h3>
        <div style="height: 200px; overflow-y: scroll;">
            <table class="table table-striped">
                <thead class="card-header">
                    <th>Category name</th>
                    <th>Point limit</th>
                    <th>Discount in %</th>
                    <th></th>
                </thead>
                <tbody>
                    <tr :key="c.id" v-for="c in this.allCategories">
                        <td>{{c.name}}</td>
                        <td>{{c.requiredPoints}}</td>
                        <td>{{c.discount}}</td>
                        <td><button type="submit" class="btn btn-secondary" v-on:click="setSelectedCategory(c)" 
                        data-toggle="modal" data-target="#editCategoryModal">Edit</button></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <br>
        <h3>Amount of points for completing examinations</h3>
        <div class="container">
            <form v-on:submit.prevent="setExaminationPoints()">
                <div class="form-group row">
                    <input 
                            type="number" class="form-control col-sm-1" v-bind:name="ePoints" v-bind:min="0" v-model="examinationPoints"
                            required="required" title="Users will receieve this many points after appearing on examinations">&nbsp;
                    <button type="submit" id="dugme" class="btn btn-primary" align="end">Set</button>
                </div>
               
            </form>
        </div>





        <div class="modal fade" id="editCategoryModal" tabindex="-1" role="dialog" aria-labelledby="editCategoryModal" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmation">"{{this.selectedCategory.name}}" category</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div>
                        <form>
                            <table class="table table-striped">
                                <tbody>
                                    <tr><td>
                                    <div class="container">
                                        
                                            <div class="form-group row">
                                                <label for="inputName" class="col-form-label col-sm-2">Category</label>
                                                <input 
                                                        type="text" class="form-control col-sm-3" id="inputName" v-model="selectedCategory.name" required
                                                        size="31">
                                                <label for="inputPoints" class="col-form-label col-sm-2">Point limit</label>
                                                <input 
                                                        type="number" class="form-control col-sm-1" id="pointsLimit" v-model="selectedCategory.requiredPoints" v-bind:min="0"
                                                        required="required">
                                                <label for="inputDiscount" class="col-form-label col-sm-2">Discount</label>
                                                <input 
                                                        type="number" class="form-control col-sm-1" id="discount" v-model="selectedCategory.discount" v-bind:min="0"
                                                        required="required" step="0.1">
                                                <label class="col-form-label col-sm-1">%</label>
                                            </div>
                                        
                                    </div>
                                    </td></tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit" v-on:click="editCategory(this.selectedCategory)" data-dismiss="modal">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    
    </div>

</template>
<script>
import LoyaltyDataService from '../service/LoyaltyDataService.js';

export default {
    name: 'LoyaltyProgramForm',
    data(){
        return {
            newCategory: {name: "", requiredPoints: 0, discount: 0},
            allCategories: [],
            selectedCategory: {name: "", requiredPoints: 0, discount: 0},
            examinationPoints: 0,
        }
    },
    methods: {
        createNewCategory(){
            LoyaltyDataService.sendLoyaltyProgram(this.newCategory).then(response => {
                switch (response.data) {
                    case 0:
                        this.$toast.show(
                            "New user category succesfully added!",
                            {
                                position: "top", type: "success",
                            }
                        );
                        break;
                    case 1:
                        this.$toast.show(
                            "Category with this name already exists!",
                            {
                                position: "top", type: "error",
                            }
                        );
                        break;
                    case 2:
                        this.$toast.show(
                            "Category with this point limit already exists!",
                            {
                                position: "top", type: "error",
                            }
                        );
                        break;
                
                    default:
                        console.log("UNKNOWN RESULT OF NEW CATEGORY");
                        break;
                }

                this.fetchCategories();
            });
        },

        fetchCategories(){
            LoyaltyDataService.getAllCategories().then(response => {
                this.allCategories = response.data;
            });
        },

        setSelectedCategory(category){
            this.selectedCategory = category;
        },

        editCategory(category){
            LoyaltyDataService.editLoyaltyProgram(category).then(response => {
                switch (response.data) {
                    case 0:
                        this.$toast.show(
                            "Category succesfully updated!",
                            {
                                position: "top", type: "success",
                            }
                        );
                        break;
                    case 1:
                        this.$toast.show(
                            "Category with this name already exists!",
                            {
                                position: "top", type: "error",
                            }
                        );
                        break;
                    case 2:
                        this.$toast.show(
                            "Category with this point limit already exists!",
                            {
                                position: "top", type: "error",
                            }
                        );
                        break;
                
                    default:
                        console.log("UNKNOWN RESULT OF NEW CATEGORY");
                        break;
                }


                this.fetchCategories();
            });
        },

        setExaminationPoints(){

            LoyaltyDataService.setExaminationPoints(this.examinationPoints).then(response => {
                this.$toast.show(
                    "Examination points successfully set!",
                    {
                        position: "bottom", type: "success",
                    }
                );
            });
        },
    },
    created(){
        this.fetchCategories();
        LoyaltyDataService.getExaminationPoints().then(response => {
            this.examinationPoints = response.data;
        });
    },
}
</script>