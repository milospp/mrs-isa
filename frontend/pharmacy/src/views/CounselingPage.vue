<template>
    <NavBar/>
    <div class="container">
        <h1>Counseling</h1>
        <CounselingForm />
    </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import CounselingForm from '@/components/CounselingForm.vue'

export default {
    name: 'Medicines',

    components: {
        NavBar,
        CounselingForm,
    },
    created() {
        console.log(this.role);
    }
}
</script>