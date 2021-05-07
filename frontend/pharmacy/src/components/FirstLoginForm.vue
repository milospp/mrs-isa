<template>
    <form v-on:submit.prevent="checkForm(this)">
        <table align="center">
            <tr><td colspan="3">We have noticed that you are logging into this account for the first time, welcome!<br>We require you to change your password<br><br></td></tr>
            <tr>
                <td align="right">New password:</td>
                <td>
                    <input 
                        type="password" id="password" v-model="newPassword" style="width : 100%" required
                        pattern="[a-zA-Z0-9]{6,}"  title="New password must have at least 6 symbols"
                    ></td>
                <td>
                    <p style="color: red" v-if="this.mismatching">Both field values must match!</p>
                    <p style="color: red" v-if="this.samePassword">New password cannot be same as old!</p>
                </td>
            </tr>

            <tr>
                <td align="right">Repeat new password:</td>
                <td>
                    <input 
                        type="password" id="passwordRepeat" v-model="passwordRepeat" style="width : 100%" required
                        pattern="[a-zA-Z0-9]{6,}"  title="New password must have at least 6 symbols"
                    ></td>
            </tr>

            <tr>
                <td></td>
                <td ><input type="submit" id="changePassword" value="Confirm password change" style="width : 100%"></td>
            </tr>
        </table>
    </form>
</template>

<script>
import AuthService from '../service/AuthService.js';
export default {
    name: 'FirstLoginForm',
    data() {
        return {
			bearer : "",
            message: null,
            oldPassword: "",
            newPassword: "",
            passwordRepeat: "",
            mismatching: false,
            samePassword: false,
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

            if(this.newPassword == this.oldPassword || this.passwordRepeat == this.oldPassword){
                this.samePassword = true;
            }else{
                this.samePassword = false;
            }

            if(!this.mismatching && !this.samePassword){
                this.saveChangedPassword();
            }
        },
        getLoggedIn(){
            this.currentUser = AuthService.getCurrentUser();
            this.oldPassword = AuthService.getOldPassword();
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
			}).then(response => { this.$router.push("/"); });
        }
    },
    created(){
        this.getLoggedIn();
    }
}
</script>