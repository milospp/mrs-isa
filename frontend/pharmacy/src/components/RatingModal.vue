<template>
  <!-- Modal -->
    <div class="modal fade" :id="modalId" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">{{modelValue.title}}</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <p>{{modelValue.body}}</p>
            <five-stars v-model="modelValue.ratingValue" v-bind:inputId="modalId" />
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" 
            v-bind:disabled="!isRatingValid || (modelValue.oldValue==modelValue.ratingValue && modelValue.oldValue > 0)" 
            v-on:click="this.$emit('rated')">{{voteButtonText}} {{ratingBracketValue}}</button>
        </div>
        </div>
    </div>
    </div>
</template>

<script>
import FiveStars from "@/components/FiveStars.vue";

export default{
    components: { FiveStars },

    props: {
        modalId: {
            default: "ratingModal"
        },
        modalTitle: {
            default: "Modal Title"
        },
        modelValue: Object,
    },


	data: function () {
		return {
            ratingValue: 0,
		}
	},

    computed: {
        isRatingValid(){
        if (this.modelValue.ratingValue < 1 || this.modelValue.ratingValue > 5)
            return false;
        else return true;
        },
        voteButtonText() {
        if (this.modelValue.oldValue <= 0)
            return "Submit Vote";

        if (this.modelValue.ratingValue == this.modelValue.oldValue){
            return "Already voted";
        }

        return "Resubmit Vote";

        },

        ratingBracketValue(){
            if (this.modelValue.ratingValue>0) return " ( "+ this.modelValue.ratingValue +" )";
            return "";
        }
    }

}
</script>

<style>

</style>