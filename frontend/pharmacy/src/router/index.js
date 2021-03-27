import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import OldHome from '../views/OldHome.vue'
import Pharmacies from '../views/Pharmacies.vue'
import Medicines from '../views/Medicines.vue'
import Register from '@/views/Register.vue'
import Patients from '@/views/Patients.vue'
import AddPharmacist from '@/views/AddPharmacist.vue'
import AddPharmacy from '@/views/AddPharmacy.vue'
import PharmacistHomePage from '@/views/PharmacistHomePage.vue'
import DispenseMedication from '@/views/DispenseMedication.vue'
import VacationRequest from '@/views/VacationRequest.vue'
import PharmacyPage from '@/views/PharmacyPage.vue'
import PatientProfile from '@/views/PatientProfile.vue'

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
    component: Medicines,
    props: route => ({ role: route.query.q })
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
  },
  {
    path: '/addPharmacy',
    name: 'AddPharmacy',
    component: AddPharmacy
  },
  {
    path: '/pharmacist',
    name: 'PharmacistHomePage',
    component: PharmacistHomePage
  },
  {
    path: '/pharmacist/dispense-med',
    name: 'DispenseMedication',
    component: DispenseMedication
  },
  {
    path: '/vacation-request',
    name: 'VacationRequest',
    component: VacationRequest
  },
  {
    path: '/pharmacyPage/:id',
    name: 'PharmacyPage',
    component: PharmacyPage    // view je komponenta
  },
  {
    path: '/profile/:id',
    name: 'profile',
    component: PatientProfile    // view je komponenta
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
