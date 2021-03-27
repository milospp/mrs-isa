<template>
    <table>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" value="Make appointment"></form> </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" value="Get report"></form> </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" value="All medicine"></form> </td>
            <td> </td>
            <td> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="PrikazZaposlenihFarmaceuta()">
                    <input type="submit" value="All pharmacist"></form> </td>
            <td> </td>
            <td> <form v-on:submit.prevent="DodajFarmaceuta()">
                    <input type="submit" value="Add pharmacist"></form> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="PrikazZaposlenihDermatologa()">
                    <input type="submit" value="All pharmacist"></form> </td>
            <td> </td>
            <td> <form v-on:submit.prevent="DodajDermatologa()">
                    <input type="submit" value="Add pharmacist"></form> </td>
        </tr>
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" value="All invalid reciept"></form> </td>
            <td> </td>
            <td></td>
        </tr> 
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" value="Make action and promotion"></form> </td>
            <td> </td>
            <td></td>
        </tr> 
        <tr>
            <td align="left"> <form v-on:submit.prevent="">
                    <input type="submit" value="See profile"></form> </td>
            <td> </td>
            <td></td>
        </tr> 

    </table>

</template>

<script>
import PharmacistDataService from '../service/PharmacistDataService.js';
export default {
    name: 'HpPharmacyAdmin',
    data() {
        return {
            
        };
    },
    created() {
	    this.id = this.$route.params.id; 
    },
    methods: {      // sve metode se pozivaju istovremeno
       
    }
}
</script>