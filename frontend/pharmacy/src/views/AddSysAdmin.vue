<template>
  <NavBar/>
  <div class="container">
    <h1 align="left">Register a new system administrator</h1>
    <p>
    <AddSysAdminForm/>
    </p>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AddSysAdminForm from '@/components/AddSysAdminForm.vue'
import NavBar from '@/components/NavBar.vue'
export default {
  name: 'Register sys. admin.',
  components: {
    NavBar, AddSysAdminForm
  }
}
</script>