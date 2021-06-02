<template>
    <NavBar/>
    <div class="container">
        <h1>My past complaints</h1>
        <ComplaintHistory/>
    </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import ComplaintHistory from '@/components/ComplaintHistory.vue'


export default {
    name: 'PatientsComplaints',
    components: {
    NavBar, ComplaintHistory
  },
}
</script>