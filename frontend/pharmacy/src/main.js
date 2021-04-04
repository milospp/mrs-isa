import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'jquery';
import $ from 'jquery';
import 'bootstrap'; 
import 'bootstrap/dist/css/bootstrap.min.css';

window.$ = window.jQuery = require('jquery');


createApp(App).use(store).use(router).mount('#app')
