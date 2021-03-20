<template>
  <DermaPharmaNavbar :role="role"/>
  <PatientList/>
</template>

<script>
import PatientList from './components/PatientList.vue';
import DermaPharmaNavbar from './components/DermaPharmaNavbar.vue';

export default {
  name: 'App',
  components: {
    DermaPharmaNavbar,
    PatientList
  },
  data() {
    return []
  },
  created() {
    // this.role = 'pharmacist',
    // this.header = ["Korisničko ime", "Ime", "Prezime"],
    // this.attributes = ["username", "name", "surname"],
    this.patients = []
    // this.patients = [
    //   {
    //     username: "perica",
    //     name: "Pera",
    //     surname: "Peric",
    //   },
    //   {
    //     username: "marica",
    //     name: "Mara",
    //     surname: "Maric",
    //   },
    //   {
    //     username: "milica",
    //     name: "Mila",
    //     surname: "Milic",
    //   },
    // ]
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
  overflow-y: scroll;
}
</style>


