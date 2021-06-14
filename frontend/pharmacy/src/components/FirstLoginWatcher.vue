<template>
</template>
<script>
import AuthService from '../service/AuthService.js';
export default { 
    name: "FirstLoginWatcher",
    data() {
        return {
            message: null,
            passwordResetDate: "",
            currentUser: {}
        };
    },
    methods:{
        getLoggedIn(){
            this.currentUser = AuthService.getCurrentUser();            
            if(this.currentUser){
                AuthService.getPasswordResetDate(this.currentUser.id).then(response => {
                    this.passwordResetDate = response;
                    if(!this.passwordResetDate){
                        this.$router.push("/firstLogin");
                    }
                });
            }
        }
    },
    created(){
        this.getLoggedIn();
    }
}

</script>