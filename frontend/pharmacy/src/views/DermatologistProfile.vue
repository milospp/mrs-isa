<template>
    <NavBar/>
    <div class="row">
        <div class="col-md-12">
            <div id="menu3" class="tab-pane fade">
            </div>
        </div>
    </div>
    <div class="container pt-5">
        <DermatologistProfileInfo/>
    </div>
    <PharmacyWorkingCard/> <!-- HARDCODED -->
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
import DermatologistProfileInfo from '@/components/doctorProfile/DermatologistProfileInfo.vue'
import PharmacyWorkingCard from '@/components/doctorProfile/PharmacyWorkingCardDerm.vue'

// @ is an alias to /src
export default {
    name: 'DermatologistProfile',
    components: {
    NavBar,
    DermatologistProfileInfo,
    PharmacyWorkingCard
    },
    data() {
        return {
            message: null
        };
    }
}
</script>
