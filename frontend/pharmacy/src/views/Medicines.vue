<template>
  <NavBar/>
  <div class="container">
    <h1>Medicines</h1> 
    <MedicinesList/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import MedicinesList from '@/components/MedicinesList.vue'

export default {
  name: 'Medicines',
  //props: ['role'],
  components: {
    NavBar,
    MedicinesList
  },
  created() {
    console.log(this.role);
  }
}
</script>