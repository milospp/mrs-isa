<template>
    <NavBar/>
    <div class="container">
        <VacationRequests/>
    </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import VacationRequests from '@/components/VacationRequests.vue'

export default {
    name: 'SAVacationRequests',
    components: {
    NavBar,
    VacationRequests
    },
}
</script>
