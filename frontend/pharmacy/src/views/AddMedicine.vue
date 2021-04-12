<template>
  <NavBar/>
  <div class="container">
    <h1>Register a new medicine</h1>
    <p>
    <AddMedicineForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddMedicineForm from '@/components/AddMedicineForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'AddMedicine',
  components: {
    NavBar, AddMedicineForm
  }
}
</script>