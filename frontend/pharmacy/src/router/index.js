import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import OldHome from '../views/OldHome.vue'
import Pharmacies from '../views/Pharmacies.vue'
import Medicines from '../views/Medicines.vue'
import Register from '@/views/Register.vue'
import Patients from '@/views/Patients.vue'
import AddPharmacist from '@/views/AddPharmacist.vue'

const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: HomePage
  },
  {
    path: '/oh',
    name: 'OldHome',
    component: OldHome
  },
  {
    path: '/pharmacies',
    name: 'Pharmacies',
    component: Pharmacies
  },
  {
    path: '/medicines',
    name: 'Medicines',
    component: Medicines
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/patients',
    name: 'Patients',
    component: Patients
  },
  {
    path: '/addPharmacist',
    name: 'AddPharmacist',
    component: AddPharmacist    // view je komponenta
  }
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
