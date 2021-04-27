<template>

  <router-view :key="componentKey"/>
</template>

<script>
import axios from "axios";
import AuthService from '@/service/AuthService.js';


export default {
    data() {
      return {
        componentKey: 0,
      };
    },
    methods: {
      registerAxios() {
        let tokenObj = JSON.parse(localStorage.getItem('userToken'));
        if (tokenObj == null || tokenObj == undefined) return;

        let token = tokenObj.accessToken
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
      forceRerender() {
        this.componentKey += 1;
      }
    },

    beforeMount() {
      this.registerAxios();
      AuthService.setCurrentUser()
      .then(response => {
        this.forceRerender();
      })
      .catch(error => {
        if (error.response.status == 404){
          AuthService.logout();
          this.forceRerender();
          //window.location.href = "/";
        }
      });

    },

    mounted() {
        let self = this;

        axios.interceptors.response.use((config) => {
            return config;        
          }, (error) => {
          if (error.response.status == 401) {
            alert("You will be redirected to hope page (disabled ATM)");
            //self.$router.push("/login");
            console.log("send me to login APP");
          }
          return Promise.reject(error);
        })
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