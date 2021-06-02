<template>

    <div class="d-flex justify-content-center">
        <form v-on:submit.prevent="checkForm(this)">
            <div class="form-group">
                <label for="email">Email address</label>
                <input
                    type="text" id="email" v-model="loginData.username" requiredd
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com" class="form-control" aria-describedby="emailHelp">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" v-model="loginData.password">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>


        <!-- <form v-on:submit.prevent="checkForm(this)">
        <table align="center">
            <tr>
                <td align="right">E-mail:</td>
                <td>
                    <input 
                        type="text" id="email" v-model="loginData.username" requiredd
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,}$" title="Email must be in form example@yahoo.com"
                    ></td>
            </tr>

            <tr>
                <td align="right">Password:</td>
                <td>
                    <input 
                        type="password" id="password" v-model="loginData.password"
                    ></td>
            </tr>

            <tr>
                <td></td>
                <td colspan="2"><input type="submit" id="dugme" value="Login"></td>
            </tr>
        </table>
    </form> -->
</template>

<script>
import AuthService from '../service/AuthService.js';
export default {
    name: 'LoginForm',
    data() {
        return {
			bearer : "",
            message: null,
            loginData: {
                username : "",
                password : ""
            }
        };
    },
    methods: {     
        checkForm(e) {
            AuthService.login(this.loginData);
			// AuthService.setCurrentUser();
        }
    }
}
</script>