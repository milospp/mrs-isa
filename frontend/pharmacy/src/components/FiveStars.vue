<template>
    <div class="rate">

            <input :disabled="disableEdit" type="radio" :id="inputId+'star5'" name="rate" value="5" @input="changePageTitle" v-model="modelValue" />
            <label :for="inputId+'star5'" title="text" :class="{star: !disableEdit, readonly: disableEdit}">5 stars</label>
            <input :disabled="disableEdit" type="radio" :id="inputId+'star4'" name="rate" value="4" @input="changePageTitle" v-model="modelValue" />
            <label :for="inputId+'star4'" title="text" :class="{star: !disableEdit, readonly: disableEdit}">4 stars</label>
            <input :disabled="disableEdit" type="radio" :id="inputId+'star3'" name="rate" value="3" @input="changePageTitle" v-model="modelValue" />
            <label :for="inputId+'star3'" title="text" :class="{star: !disableEdit, readonly: disableEdit}">3 stars</label>
            <input :disabled="disableEdit" type="radio" :id="inputId+'star2'" name="rate" value="2" @input="changePageTitle" v-model="modelValue" />
            <label :for="inputId+'star2'" title="text" :class="{star: !disableEdit, readonly: disableEdit}">2 stars</label>
            <input :disabled="disableEdit" type="radio" :id="inputId+'star1'" name="rate" value="1" @input="changePageTitle" v-model="modelValue" />
            <label :for="inputId+'star1'" title="text" :class="{star: !disableEdit, readonly: disableEdit}">1 star</label>
    </div>
</template>

<script>
export default {

 props: {
    modelValue: String, // previously was `value: String`
    inputId: {
        type: String,
        default: "",
    },
    
    disableEdit: {
        type: Boolean,
        default: false,
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
    .readonly {
        cursor: default !important;
    }

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
    .rate:not(:checked) > label.star:hover,
    .rate:not(:checked) > label.star:hover ~ label.star {
        color: #deb217;  
    }
    .rate > input:checked + label.star:hover,
    .rate > input:checked + label.star:hover ~ label.star,
    .rate > input:checked ~ label.star:hover,
    .rate > input:checked ~ label.star:hover ~ label.star,
    .rate > label.star:hover ~ input:checked ~ label.star {
        color: #c59b08;
    }
</style>