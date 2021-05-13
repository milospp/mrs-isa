<template>
  <NavBar/>
  <div class="container">
    <h1>Define a new offer</h1>
    <NewOfferForm/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import NewOfferForm from '@/components/NewOfferForm.vue'
export default {
  name: 'AddNewOffer',
  components: {
    NavBar,
    NewOfferForm,
  }
}
</script>