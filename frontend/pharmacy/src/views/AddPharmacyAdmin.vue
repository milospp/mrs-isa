<template>
  <NavBar/>
  <div class="container">
    <h1 align="left">Register a new pharmacy administrator</h1>
    <p>
    <AddPharmacyAdminForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddPharmacyAdminForm from '@/components/AddPharmacyAdminForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'Register pharmacy administrator',
  components: {
    NavBar, AddPharmacyAdminForm
  }
}
</script>