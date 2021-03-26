<template>
    <img src="../assets/kalendar.jpg" alt="radni kalendar">
</template>

<script>
// @ is an alias to /src       pharmacy/src/public/

export default {
  name: 'WorkingCalendar',
  props: {
      email: String
  }
}
</script>

<style scoped>
img {
    width: 50%;
    height: auto;
    margin: 20px;
}
</style>