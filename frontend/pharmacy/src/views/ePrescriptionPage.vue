<template>
    <NavBar/>
    <div class="container">
        <h1>ePrescription scanner</h1>
        <ePrescriptionForm/>
    </div>
</template>
<script>
import NavBar from '@/components/NavBar.vue';
import ePrescriptionForm from '@/components/ePrescriptionForm.vue';

export default {
    name: 'ePrescriptionPage',
    components: {
        NavBar, ePrescriptionForm
    }
}

</script>