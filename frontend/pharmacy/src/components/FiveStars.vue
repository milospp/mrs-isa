<template>
    <div class="rate">
        <input type="radio" :id="inputId+'star5'" name="rate" value="5" @input="changePageTitle" v-model="modelValue" />
        <label :for="inputId+'star5'" title="text">5 stars</label>
        <input type="radio" :id="inputId+'star4'" name="rate" value="4" @input="changePageTitle" v-model="modelValue" />
        <label :for="inputId+'star4'" title="text">4 stars</label>
        <input type="radio" :id="inputId+'star3'" name="rate" value="3" @input="changePageTitle" v-model="modelValue" />
        <label :for="inputId+'star3'" title="text">3 stars</label>
        <input type="radio" :id="inputId+'star2'" name="rate" value="2" @input="changePageTitle" v-model="modelValue" />
        <label :for="inputId+'star2'" title="text">2 stars</label>
        <input type="radio" :id="inputId+'star1'" name="rate" value="1" @input="changePageTitle" v-model="modelValue" />
        <label :for="inputId+'star1'" title="text">1 star</label>
    </div>
</template>

<script>
export default {

 props: {
    modelValue: String, // previously was `value: String`
    inputId: {
        type: String,
        default: "",
    }
  },
  emits: ['update:modelValue'],
  methods: {
    changePageTitle(title) {
      this.$emit('update:modelValue', title.target.value) // previously was `this.$emit('input', title)`
    }
  },
  data () {
    return {
      id: null
    }
  }, 

}
</script>

<style scoped>
    .rate {
        float: left;
        height: 46px;
        padding: 0 10px;
    }
    .rate:not(:checked) > input {
        position:absolute;
        top:-9999px;
    }
    .rate:not(:checked) > label {
        float:right;
        width:1em;
        overflow:hidden;
        white-space:nowrap;
        cursor:pointer;
        font-size:30px;
        color:#ccc;
    }
    .rate:not(:checked) > label:before {
        content: '★ ';
    }
    .rate > input:checked ~ label {
        color: #ffc700;    
    }
    .rate:not(:checked) > label:hover,
    .rate:not(:checked) > label:hover ~ label {
        color: #deb217;  
    }
    .rate > input:checked + label:hover,
    .rate > input:checked + label:hover ~ label,
    .rate > input:checked ~ label:hover,
    .rate > input:checked ~ label:hover ~ label,
    .rate > label:hover ~ input:checked ~ label {
        color: #c59b08;
    }
</style>