<template>
  <NavBar/>
  <div class="container">
    <h1>Add pharmacist</h1>
    <AddPharmacistForm/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import NavBar from '@/components/NavBar.vue'
import AddPharmacistForm from '@/components/AddPharmacistForm.vue'
export default {
  name: 'AddPharmacist',
  components: {
    NavBar,
    AddPharmacistForm,
  }
}
</script>