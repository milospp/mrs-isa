import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'jquery';
import $ from 'jquery';
import 'bootstrap'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import 'vue-choice';
import axios from 'axios';
import { SetupCalendar, Calendar, DatePicker } from 'v-calendar';
import Toasted from 'vue-toasted';
import Toaster from '@meforma/vue-toaster';



window.$ = window.jQuery = require('jquery');

const app = createApp(App);
app.use(store)
    .use(router)
    .use(SetupCalendar, {})
    .use(Toaster)
    .component('Calendar', Calendar)
    .component('DatePicker', DatePicker)
    .mount('#app');
    
app.config.globalProperties.$axios = axios;
