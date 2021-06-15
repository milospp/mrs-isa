<template>
</template>
<script>
import PatientDataService from '@/service/PatientDataService.js'

export default {
    name: 'ActivatePatientView',
    mounted(){
        PatientDataService.activateUser(this.$route.query.token).then(response => {
            if(response.data == 0){
                this.$toast.show(
                    "You have activated your account, you may now log in.",
                    {
                        position: "top", type: "success",
                    }
                );
            }else if(response.data == 1){
                this.$toast.show(
                    "Your activation link has expired.",
                    {
                        position: "top", type: "error",
                    }
                );
            }else if(response.data == 2){
                this.$toast.show(
                    "Your activation link is not valid.",
                    {
                        position: "top", type: "error",
                    }
                );
            }

            this.$router.push("/");
        });
    }
}

</script>