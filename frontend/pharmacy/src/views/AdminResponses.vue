<template>
    <NavBar/>
    <div class="container">
        <h1>Responded complaints</h1>
        <RespondedComplaints/>
        
    </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import RespondedComplaints from '@/components/RespondedComplaints.vue';

export default {
    name: "AdminResponses",
    components: {
        NavBar, RespondedComplaints
    }
}


</script>