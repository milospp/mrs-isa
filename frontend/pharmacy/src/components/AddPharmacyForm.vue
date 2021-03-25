<template>
<div class="columns">
  <div class="column" >
    <form v-on:submit.prevent="proveraForme(this)" >
        <table border="1">

            
        </table>
    </form>
  </div>
  <div class="column">

  </div>
</div>
    
</template>

<script>
    import PharmacyDataService from '../service/PharmacyDataService.js';
    export default {
        name: 'AddPharmacyForm',
        data(){
           return {}
        },
        methods: {
             proveraForme(e){}
        }
    }
</script>

<style>

.column {
  float: left;
  width: 50%;
}


</style>