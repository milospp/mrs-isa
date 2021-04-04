<template>
  <NavBar/>
  <div class="container">
    <h1 align="left">Register a supplier</h1>
    <p>
    <AddSupplierForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddSupplierForm from '@/components/AddSupplierForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'Register supplier',
  components: {
    NavBar, AddSupplierForm
  }
}
</script>