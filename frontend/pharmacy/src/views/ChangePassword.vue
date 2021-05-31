<template>
    <NavBar/>
    <div class="container">

        <h1 class="title" align="center">Change password</h1>
        <form v-on:submit.prevent="checkForm(this)">
            <div class="form-group">
                <label for="oldPassword">Old password</label>
                    <input 
                        type="password" id="oldPassword" v-model="oldPassword" class="form-control" required
                        pattern="[a-zA-Z0-9]{6,}"  title="Insert old password"
                    >
                    <p style="color: red" v-if="invalidOldPass">Invalid old password</p>
            </div>

            <div class="form-group">
                <label for="password">New password</label>
                    <input 
                        type="password" id="password" v-model="newPassword" class="form-control" required
                        pattern="[a-zA-Z0-9]{6,}"  title="New password must have at least 6 symbols"
                    >
                    <p style="color: red" v-if="samePassword">New password cannot be same as old</p>
            </div>

            <div class="form-group">
                <label for="passwordRepeat">Repeat new password</label>
                    <input 
                        type="password" id="passwordRepeat" v-model="passwordRepeat" class="form-control" required
                        pattern="[a-zA-Z0-9]{6,}"  title="New password must have at least 6 symbols"
                    >
                    <p style="color: red" v-if="mismatching">Both field values must match</p>
            </div>

            <div class="form-group">
                <input type="submit" id="changePassword" value="Update password" class="btn btn-success" >
            </div>
        </form>
    </div>
</template>

<script>
// @ is an alias to /src
import NavBar from '@/components/NavBar.vue'
import AuthService from '../service/AuthService.js';

export default {
    name: 'ChangePassword',
    components: {
        NavBar
    },
    data() {
        return {
			bearer : "",
            message: null,
            oldPassword: "",
            oldPasswordCheck: "",
            newPassword: "",
            passwordRepeat: "",
            mismatching: false,
            samePassword: false,
            invalidOldPass: false,
            currentUser: {}
        };
    },
    methods: {     
        checkForm(e) {
            if(this.newPassword == this.passwordRepeat){
                this.mismatching = false;
            }else{
                this.mismatching = true;
            }

            if(this.newPassword == this.oldPasswordCheck || this.passwordRepeat == this.oldPasswordCheck){
                this.samePassword = true;
            }else{
                this.samePassword = false;
            }

            if (this.oldPassword != this.oldPasswordCheck){
                this.invalidOldPass = true;
            }else{
                this.invalidOldPass = false;
            }

            if(!this.mismatching && !this.samePassword && !this.invalidOldPass){
                this.saveChangedPassword();
            }
        },
        getLoggedIn(){
            this.currentUser = AuthService.getCurrentUser();
            this.oldPasswordCheck = AuthService.getOldPassword();
        },
        saveChangedPassword(){
            AuthService.SendNewPassword(this.currentUser.id,this.newPassword).catch(function (error) {
					if (error.response) {
						console.log(error.response.data);
					} else if (error.request) {
						console.log(error.request);
					}
					console.log("error.config");
					console.log(error.config);
			}).then(response => {
                if (response.data) {
                    AuthService.logout();
                    this.$router.push("/");
                }
            });
        }
    },
    created(){
        this.getLoggedIn();
    }
}
</script>

<style scoped>
.title {
    margin: 20px;
}
</style>