<template>
  <NavBar/>
  <div class="container">
    <h1>Add pricelist</h1>
    <AddPricelist/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import AddPricelist from '@/components/AddPricelist.vue'
export default {
  name: 'AddNewPricelist',
  components: {
    NavBar,
    AddPricelist,
  }
}
</script>