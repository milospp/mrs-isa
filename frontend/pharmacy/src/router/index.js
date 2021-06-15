import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import OldHome from '../views/OldHome.vue'
import Pharmacies from '../views/Pharmacies.vue'
import Medicines from '../views/Medicines.vue'
import Register from '@/views/Register.vue'
import Patients from '@/views/Patients.vue'
import AddPharmacist from '@/views/AddPharmacist.vue'
import HomePagePharmacyAdmin from '@/views/HomePagePharmacyAdmin.vue'
import CartPharmacyAdmin from '@/views/CartPharmacyAdmin.vue'
import PAMoreInfo from '@/views/PAMoreInfo.vue'
import PAProfile from '@/views/PAProfile.vue'
import ChangingOrder from '@/views/ChangingOrder.vue'
import HireDermatologist from '@/views/HireDermatologist.vue'
import AddNewPricelist from '@/views/AddNewPricelist.vue'
import Vacations from '@/views/Vacations.vue'
import PharmacyQRcode from '@/views/PharmacyQRcode.vue'
// import AddPharmacy from '@/views/AddPharmacy.vue'
// import AddPharmacyAdmin from '@/views/AddPharmacyAdmin.vue'
import PharmacistHomePage from '@/views/PharmacistHomePage.vue'
import DispenseMedication from '@/views/DispenseMedication.vue'
import VacationRequest from '@/views/VacationRequest.vue'
import PharmacyPage from '@/views/PharmacyPage.vue'
import AllDoctors from '@/views/AllDoctors.vue'
import PatientProfile from '@/views/PatientProfile.vue'
import PharmacistProfile from '@/views/PharmacistProfile.vue'
import DermatologistProfile from '@/views/DermatologistProfile.vue'
import DermatologistHomePage from '@/views/DermatologistHomePage.vue'
import DermAppointments from '../views/DermAppointments.vue'
// import AddSupplier from '@/views/AddSupplier.vue'
// import AddDermatologist from '@/views/AddDermatologist.vue'
// import AddMedicine from '@/views/AddMedicine.vue'
import Login from '@/views/Login.vue'
import LogoutView from '@/views/LogoutView.vue'
import Appointment from '@/views/Appointment.vue'
// import AddSysAdmin from '@/views/AddSysAdmin.vue'
import SupplierProfile from '@/views/SupplierProfile.vue'
import CounselingPage from '@/views/CounselingPage.vue'
import MedicineSearchPage from '@/views/MedicineSearchPage.vue'
import FirstLogin from '@/views/FirstLogin.vue'
import ChangePassword from '@/views/ChangePassword.vue'
import SuppliersOffersPage from '@/views/SuppliersOffersPage.vue'
import AddNewOffer from '@/views/AddNewOffer.vue'
import ComplaintPage from '@/views/ComplaintPage.vue'
import ComplaintResponsePage from '@/views/ComplaintResponsePage.vue'
import PatientsComplaints from '@/views/PatientsComplaints.vue'
import AdminResponses from '@/views/AdminResponses.vue'
import SysAdminAdd from '@/views/SysAdminAdd.vue'
import LoyaltyProgramView from '@/views/LoyaltyProgramView.vue'
import ePrescriptionPage from '@/views/ePrescriptionPage.vue'
import ActivatePatientView from '@/views/ActivatePatientView.vue'


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
    //props: route => ({ role: route.query.q })
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/patients',
    name: 'Patients',
    component: Patients,
    //props: route => ({ role: route.query.q })
  },
  {
    path: '/addPharmacist/:id',
    name: 'AddPharmacist',
    component: AddPharmacist    // view je komponenta
  },
  {
    path: '/hireDermatologist/:id',
    name: 'HireDermatologist',
    component: HireDermatologist    // view je komponenta
  },
  {
    path: '/homePagePharmacyAdmin',
    name: 'HomePagePharmacyAdmin',
    component: HomePagePharmacyAdmin    // view je komponenta
  },
  {
    path: '/cartPharmacyAdmin',
    name: 'CartPharmacyAdmin',
    component: CartPharmacyAdmin    // view je komponenta
  },
  {
    path: '/changeOrder',
    name: 'ChangeOrder',
    component: ChangingOrder    // view je komponenta
  },
  {
    path: '/pharmacyMoreInfo',
    name: 'PAMoreInfo',
    component: PAMoreInfo    // view je komponenta
  },
  {
    path: '/pharmacyAdminProfile',
    name: 'PAProfile',
    component: PAProfile    // view je komponenta
  },
  {
    path: '/addPricelist',
    name: 'AddNewPricelist',
    component: AddNewPricelist    // view je komponenta
  },
  {
    path: '/vacationRequests',
    name: 'Vacations',
    component: Vacations    // view je komponenta
  },
  {
    path: '/PharmacyQRcode/:id',
    name: 'PharmacyQRcode',
    component: PharmacyQRcode    // view je komponenta
  },
  {
    path: '/allDoctors',
    name: 'AllDoctors',
    component: AllDoctors    // view je komponenta
  },
  // {
  //   path: '/addPharmacy',
  //   name: 'AddPharmacy',
  //   component: AddPharmacy
  // },
  // {
  //   path: '/addPharmacyAdmin',
  //   name: 'AddPharmacyAdmin',
  //   component: AddPharmacyAdmin
  // },
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
    component: VacationRequest,
    //props: route => ({ role: route.query.q })
  },
  {
    path: '/pharmacyPage/:id',
    name: 'PharmacyPage',
    component: PharmacyPage    // view je komponenta
  },
  // {
  //   path: '/profile/:id',
  //   name: 'profile',
  //   component: PatientProfile    // view je komponenta
  // },
  {
    path: '/profile',
    name: 'profile',
    component: PatientProfile    // view je komponenta
  },
  {
    path: '/pharmacist/profile',
    name: 'PharmacistProfile',
    component: PharmacistProfile    // view je komponenta
  },
  {
    path: '/dermatologist/profile',
    name: 'DermatologistProfile',
    component: DermatologistProfile    // view je komponenta
  },
  {
    path: '/dermatologist',
    name: 'DermatologistHomePage',
    component: DermatologistHomePage
  },
  {
    path: '/derm-appointments',
    name: 'DermAppointments',
    component: DermAppointments
  },
  // {
  //   path: '/addSupplier',
  //   name: 'AddSupplier',
  //   component: AddSupplier
  // },
  // {
  //   path: '/addDermatologist',
  //   name: 'AddDermatologist',
  //   component: AddDermatologist
  // },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/logout',
    name: 'Logout',
    component: LogoutView
  },
  // {
  //   path: '/addMedicine',
  //   name: 'AddMedicine',
  //   component: AddMedicine
  // },
  // {
  //   path: '/addSysAdmin',
  //   name: 'AddSysAdmin',
  //   component: AddSysAdmin
  // },
  {
    path: '/supplier/profile',
    name: 'SupplierProfile',
    component: SupplierProfile    
  },
  {
    path: '/medicineSearch',
    name: 'MedicineSearchPage',
    component: MedicineSearchPage
  },
  {
    path: '/appointment/:id',
    name: 'Appointment',
    component: Appointment    // view je komponenta
  },
  {
    path: '/firstLogin',
    name: 'FirstLogin',
    component: FirstLogin
  },
  {
    path: '/change-password',
    name: 'ChangePassword',
    component: ChangePassword
  },
  {
    path: '/addNewOffer',
    name: 'AddNewOffer',
    component: AddNewOffer
  },
  {
    path: '/SuppliersOffers',
    name: 'SuppliersOffersPage',
    component: SuppliersOffersPage
  },
  {
    path: '/counseling',
    name: 'Counseling',
    component: CounselingPage
  },
  {
    path: '/newComplaint',
    name: 'ComplaintPage',
    component: ComplaintPage
  },
  {
    path: '/complaints',
    name: 'ComplaintResponsePage',
    component: ComplaintResponsePage
  },
  {
    path: '/patientsComplaints',
    name: 'PatientsComplaints',
    component: PatientsComplaints
  },
  {
    path: '/adminResponses',
    name: "AdminResponses",
    component: AdminResponses
  },
  {
    path: '/add',
    name: 'SysAdminAdd',
    component: SysAdminAdd
  },
  {
    path: '/loyaltyProgram',
    name: 'LoyaltyProgramView',
    component: LoyaltyProgramView
  },
  {
    path: '/ePrescription',
    name: 'ePrescriptionPage',
    component: ePrescriptionPage
  },
  {
    path: '/activatePatient',
    name: 'ActivatePatientView',
    component: ActivatePatientView
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
