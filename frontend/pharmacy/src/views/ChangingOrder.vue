<template>
  <NavBar/>
  <div class="container">
    <h1>View order</h1>
    <Order/>
  </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import Order from '@/components/ChangeOrder.vue'
export default {
  name: 'AddPharmacist',
  components: {
    NavBar,
    Order,
  }
}
</script>