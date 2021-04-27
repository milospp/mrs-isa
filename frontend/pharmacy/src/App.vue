<template>

  <router-view/>
</template>

<script>
import axios from "axios";
export default {

    beforeMount() {
        let tokenObj = JSON.parse(localStorage.getItem('userToken'));
        if (tokenObj == null || tokenObj == undefined) return;

        let token = tokenObj.accessToken
        alert(token);
        axios.interceptors.request.use(function(config) {
            if(token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        }, function(error) {
            if (error.response.status === 401) {

                router.push('/logout');
            }
            console.log("IDI NA LOGIN radi");
            alert("greska?");
            return Promise.reject(error);
        });

        axios.interceptors.response.use((response) => {
          return response
        }, (error) => {
          if (error.response.status == 401) {
            console.log("send me to login APP")
          }
          return Promise.reject(error);
        })

    },

    mounted() {
     /*   let self = this;
        let token;

        let tokenObj = JSON.parse(localStorage.getItem('userToken'));
        if (tokenObj == null || tokenObj == undefined) {
          token = ""; 
        } else {
          token = tokenObj.accessToken;
        }


        axios.interceptors.response.use((config) => {
            if(token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;        
          }, (error) => {
          if (error.response.status == 401) {
            alert("You will be redirected to hope page (disabled ATM)");
            //self.$router.push("/login");
            console.log("send me to login APP");
          }
          return Promise.reject(error);
        })*/2
    }

    
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
}
#nav {
  padding: 30px;
}
#nav a {
  font-weight: bold;
  color: #2c3e50;
}
#nav a.router-link-exact-active {
  color: #42b983;
}
</style>