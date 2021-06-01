<template>
    <NavBar/>
    <div class="container">
        <PAProfileInfo/>
    </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import PAProfileInfo from '@/components/pharmacyAdminProfile/PAProfileInfo.vue'

export default {
    name: 'PAProfile',
    components: {
    NavBar,
    PAProfileInfo
    },
}
</script>
