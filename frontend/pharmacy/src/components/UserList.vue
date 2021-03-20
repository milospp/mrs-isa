<template>
    <div class="container" >
        <table class="table table-hover">
            <thead>
                <th :key="head" v-for="head in header">{{head}}</th>
            </thead>
            <tbody>
                <tr :key="p.username" v-for="p in users">
                    <td :key="attr" v-for="attr in attributes">{{p[attr]}}</td>
                </tr>
            </tbody>
            
        </table>
        
    </div>
</template>

<script>


export default {
    name: 'UserList',
    data() {
        return {
            patients: [],
            message: null,
            INSTRUCTOR: 'in28minutes'
        };
    },
    // methods: {
    //     refreshPatients() {
    //         PatientDataService.retrieveAllPatients(this.INSTRUCTOR) // HARDCODED
    //             .then(response => {
    //                 this.patients = response.data;
    //             });
    //     },
    //     created() {
    //         this.refreshPatients();
    //     }
    // }
    props: {
        header: Array,
        attributes: Array,
        users: Array
    }
}
</script>