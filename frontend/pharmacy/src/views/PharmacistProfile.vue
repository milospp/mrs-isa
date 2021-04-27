<template>
    <NavBar/>
    <div class="row">
        <div class="col-md-12">
            <div id="menu3" class="tab-pane fade">
            </div>
        </div>
    </div>
    <div class="container pt-5">
        <PharmacistProfileInfo/>
    </div>
    <PharmacyWorkingCard doctorId="15"/> <!-- HARDCODED -->
</template>

<style>
  .in {
    opacity: 1 !important;
  }
  .nav-item.active > .nav-link {
    color: #495057;
    background-color: #fff;
    border-color: #dee2e6 #dee2e6 #fff;
  }
</style>

<script>
import NavBar from '@/components/NavBar.vue'
import PharmacistProfileInfo from '@/components/doctorProfile/PharmacistProfileInfo.vue'
import PharmacyWorkingCard from '@/components/doctorProfile/PharmacyWorkingCardPharm.vue'

// @ is an alias to /src
export default {
    name: 'PharmacistProfile',
    components: {
    NavBar,
    PharmacistProfileInfo,
    PharmacyWorkingCard
    //PharmacyInfo
    },
    data() {
        return {
            message: null
        };
    }
}
</script>
