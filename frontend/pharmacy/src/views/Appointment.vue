<template>
<div>
    <NavBar/>
    <div class="container">
        <h1 class="gray" v-if="appointment.type == 'EXAMINATION'">Examination</h1>
        <h1 class="gray" v-else-if="appointment.type == 'COUNSELING'">Counseling</h1>
        <h3 class="gray">Patient: {{appointment.examination.patient.name}} {{appointment.examination.patient.surname}}</h3>
        <!-- <h4 class="gray">Dermatologist: {{appointment.doctor.name}} {{appointment.doctor.surname}}</h4> -->
        <p class="gray">Appointment: {{UtilService.formatDateTime(appointment.startTime)}}</p>
        <p class="gray">Duration: {{appointment.durationInMins}} min</p>
        <p class="gray">Pharmacy: {{appointment.pharmacy.name}}</p>


        <div v-if="status == 'PENDING'">
            <div class="form-group">
                <div class="custom-control custom-checkbox">
                    <input v-model="patientAppeared" type="checkbox" class="custom-control-input" id="customCheck1">
                    <label class="custom-control-label" for="customCheck1">Patient showed up</label>
                </div>
            </div>
            <div class="form-group">
                <label for="info">Anamnesis (conversation with patient)</label>
                <textarea :disabled="!patientAppeared" v-model="appointment.examination.examinationInfo" class="form-control" id="info" rows="5" placeholder="Info..."></textarea>
            </div>
            <div class="form-group">
                <label for="diagnose">Diagnose</label>
                <input :disabled="!patientAppeared" v-model="appointment.examination.diagnose" class="form-control" placeholder="Diagnose" id="diagnose">
            </div>
            <!-- <div class="form-group">
                <button @click="bookNewAppointmentModal()" :disabled="!patientAppeared" class="btn btn-secondary">Book New Custom 
                    <span v-if="appointment.type == 'EXAMINATION'">
                        Examination
                    </span>
                    <span v-else>
                        Counseling
                    </span>
                </button>
            </div> -->
            <div class="row mb-3">
                <div class="col-md-12">
                    <a class="btn btn-outline-secondary" @click="setAttr" data-toggle="collapse" href="#newAppCollapse" role="button" aria-expanded="false" aria-controls="newAppCollapse">
                        Book Next Appointment
                    </a>
                </div>
	        </div>
            <div class="searchForm collapse bg-light row p-4" id="newAppCollapse">
                <div class="col pr-0"><!--d-flex justify-content-center
                :date="selectedDate"-->
                <Calendar 
                    :min-date='new Date()'
                    v-model="date"
                    :attributes="attributes"
                    ref="calendar"
                    @dayclick='dayClicked'
                >
                </Calendar>
                </div>
                <div class="col-6">
                    <!--  d-flex justify-content-center <p>Here will be table with appointments</p> -->
                    <table v-if="appointmentsOnDay.length > 0" class="table table-stirped" style="text-align: left; table-layout: fixed;">
                        <thead>
                            <th>Start time</th>
                            <th>Duration</th>
                            <th>Price</th>
                            <th>Book</th>
                        </thead>
                        <tbody>
                            <tr :key="app" v-for="app in appointmentsOnDay">
                                <td>{{app.customData.data.startTime}}</td>
                                <td>{{app.customData.data.durationInMins}} min</td>
                                <td>${{app.customData.data.price}}</td>
                                <td>
                                    <button class="btn btn-primary" @click="bookExistingAppointment(app)">Book</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div id="customTimeAppointment" class="form-group col-3">
                        <div class="form-group">
                            <label for="datetime">Date and time</label>
                            <input id="datetime" class="form-control" v-model="newAppointment.dateTime" type="datetime-local">
                        </div>
                        <div class="form-group">
                            <label for="duration">Duration (minutes)</label>
                            <input id="duration" class="form-control" v-model="newAppointment.durationInMins" type="number" min="5" step="5">
                        </div>
                        <div class="form-group">
                            <label for="price">Price: <strong>${{ predictedPrice.toFixed(2) }}</strong></label>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" @click="bookAtCustomTime()">Book</button>
                        </div>
                    </div>
            </div>
        <div class="form-group">
            <label for="selectMed">Therapy</label>
                <div class="row mb-2">
                    <select v-model="therapyMed" :disabled="!patientAppeared" class="form-control col mx-3" id="selectMed">
                        <option :key="m.id" v-for="m in medicines" :value="m">{{m.medicine.name}}</option>
                    </select>
                    <button class="btn btn-secondary mr-3" :disabled="!patientAppeared || !therapyMed" @click="showSpecification()">View Specification</button>
                    <input type="number" v-model="therapyDays" class="form-control col-2 mr-3" id="days" min="1" :disabled="!patientAppeared">
                    <input type="date" class="form-control col-2 mr-3" v-model="reservationDate" :disabled="!patientAppeared"/>
                    <button class="btn btn-primary mr-3" @click="addTherapy()" :disabled="!patientAppeared">Add</button>
                </div>

                <div id="therapyDiv">
                    <table class="table table-striped box-shadow" style="overflow-y: auto;">
                        <thead>
                            <th>Medicine</th>
                            <th>Days</th>
                            <th>Reservation date</th>
                            <th></th>
                        </thead>
                        <tbody id="therapyRow">
                            <tr :key="r.id" v-for="r in reservations">
                                <td>{{r.medicineInPharmacy.medicine.name}}</td>
                                <td>{{r.days}}</td>
                                <td>{{r.lastDate[2]}}/{{r.lastDate[1]}}/{{r.lastDate[0]}}</td>
                                <td>
                                    <button class="btn btn-danger" @click="removeTherapy(r.id)">
                                        Remove
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="form-group">
                    <button class="form-control btn btn-success" id="finish" @click="finishAppointment()">Finish</button>
                </div>

            </div>
        </div>

        <div v-else-if="status == 'HELD'">
            <p class="badge badge-success">HELD</p>
            <div class="form-group">
                <label for="info">Anamnesis (conversation with patient)</label>
                <textarea :disabled="true" v-model="appointment.examination.examinationInfo" class="form-control" id="info" rows="5"></textarea>
            </div>
            <div class="form-group">
                <label for="diagnose">Diagnose</label>
                <input :disabled="true" v-model="appointment.examination.diagnose" class="form-control" id="diagnose">
            </div>
            <div id="therapyDiv2">
                    <table class="table table-striped box-shadow">
                        <thead>
                            <th>Medicine</th>
                            <th>Days</th>
                        </thead>
                        <tbody id="therapyRow">
                            <tr :key="t.id" v-for="t in appointment.examination.therapy">
                                <td>{{t.medInPharma.medicine.name}}</td>
                                <td>{{t.days}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            <div class="form-group">
                <button :disabled="true" class="form-control btn btn-primary" id="finish" @click="finishAppointment()">Finish</button>
            </div>
        </div>

        <div v-else-if="status == 'NOT_HELD'">
            <p class="badge badge-warning">PATIENT DID NOT SHOW UP</p>
        </div>
        <div v-else-if="status == 'CANCELED'">
            <p class="badge badge-danger">CANCELED</p>
        </div>

    </div>

    <!-- MODAL for med specification -->
    <div class="modal fade" id="specificationModal" tabindex="-1" role="dialog" aria-labelledby="specificationModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Specification of 
                        <span v-if="therapyMed">{{therapyMed.medicine.name}}</span>
                    </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div>
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <td>Structure: </td>
                                <td><span v-if="therapyMed">{{therapyMed.medicine.specification.structure}}</span></td>
                            </tr>
                            <tr>
                                <td>Daily intake: </td>
                                <td><span v-if="therapyMed">{{therapyMed.medicine.specification.dailyIntake}}</span></td>
                            </tr>
                            <tr>
                                <td>Side effects: </td>
                                <td><span v-if="therapyMed">{{therapyMed.medicine.specification.sideEffects}}</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- MODAL for replacement med reservation -->
    <div class="modal fade" id="replacementModal" tabindex="-1" role="dialog" aria-labelledby="specificationModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="Potvrdica">Replacement of 
                        <span v-if="therapyMed">{{therapyMed.medicine.name}}</span>
                    </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div>
                    <table class="table table-striped">
                        <thead>
                            <th>Name</th>
                            <th> </th>
                        </thead>
                        <tbody>
                            <tr :key="med.medicine.code" v-for="med in currentReplacements">
                                <td>{{med.medicine.name}}</td>
                                <td>
                                    <button class="btn btn-primary mr-3" @click="selectReplacementMed(med)" :disabled="!patientAppeared">Select</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
</template>

<style>
  .in {
    opacity: 1 !important;
  }
  .nav-item.active > .nav-link {
    color: #495057;
    background-color: #fff;
    border-color: #dee2e6 #dee2e6 #fff;
  }
  .gray {
      color: #5e6469;
  }
</style>

<script>
import NavBar from '@/components/NavBar.vue'
import AppointmentDataService from '@/service/AppointmentDataService.js'
import UtilService from '../service/UtilService.js';
import $ from 'jquery';
import MedicineDataService from '../service/MedicineDataService.js';
import PatientDataService from '../service/PatientDataService.js';
import AuthService from "../service/AuthService.js";

export default {
    name: 'Appointment',
    components: {
    NavBar,
    },
    setup() {
      return {UtilService}
    },
    data() {
        return {
            message: null,
            date: new Date(),
            freeAppointments: [],
            appointments: [],
            bookedAppointments: [],
            appointment: {
                id: null,
                examination: {
                    patient: {
                        name: '',
                        surname: '',
                        status: '',
                    },
                    diagnose: '',
                    examinationInfo: '',
                    therapy: []
                },
                startTime: '',
                pharmacy: { name: '' },
                durationInMins: 5,
                doctor: {
                        name: '',
                        surname: '',
                    },
                price: 0,
            },
            patientAppeared: null,
            newAppointment: {
                dateTime : null,
                price : 0,
                durationInMins : 5
            },
            medicines: [],
            therapyMed: null,
            therapyDays: null,
            reservationDate: null,
            reservations: {},
            status: '',

            currentReplacements: null,
            selectedDay: new Date(),
        };
    },
    methods: {
        getAppointmentData(){
            AppointmentDataService.getAppointmentInfo(this.appId)
            .then(response => {
                    this.appointment = response.data;
                    this.status = this.appointment.examination.status;
                    MedicineDataService.getAllMedicineForPharmacy(this.appointment.pharmacy.id)
                    .then(response =>
                            {
                                this.medicines = response.data;
                            });
                });
        },
        finishAppointment(){
            if (this.appointment.examination.status == 'PENDING'){
                if (this.patientAppeared != null){
                    if (this.patientAppeared == true)
                        this.appointment.examination.status = 'HELD';
                    else{
                        this.appointment.examination.status = 'NOT_HELD';
                        this.appointment.examination.examinationInfo = null;
                        this.appointment.examination.diagnose = null;
                    }
                }
            }
            for (let r in this.reservations){
                this.appointment.examination.therapy.push({medInPharma: this.reservations[r].medicineInPharmacy, days: this.reservations[r].days+''});
            }
            AppointmentDataService.postAppointmentInfo(this.appointment)
            .then(response => {
                    if (response.data){
                        alert("Info saved.");
                        this.status = this.appointment.examination.status;
                    }
                });
        },
        showSpecification() {
            $('#specificationModal').modal();
        },
        getReplacementMedicine(unavailableMedInPharma) {
            this.currentReplacements = [];
            for (let m of unavailableMedInPharma.medicine.replacementMedicationIds){
                for (let one of this.medicines){
                    if (one.medicine.code === m){
                        this.currentReplacements.push(one);
                    }
                }
            }
            $('#replacementModal').modal();
        },
        emptyReplacementsAndClosingModal(){
            $('#replacementModal').modal('hide');
            this.currentReplacements = null;
        },
        selectReplacementMed(med){
            this.therapyMed = med;
            this.emptyReplacementsAndClosingModal();
        },
        addTherapy() {
            if (this.therapyMed && this.therapyDays && this.reservationDate){
                let unique = true;
                for (let t in this.reservations){
                    if (this.reservations[t].medicineInPharmacy.id === this.therapyMed.id){
                        unique = false;
                        break;
                    }
                }
                if (!unique){ alert('Medicine is already prescribed'); return; }
                if (UtilService.isPastDate(this.reservationDate)) { alert('Date is invalid'); return; }

                let reserve_form = {
                    medicineId: this.therapyMed.medicine.id,
                    pharmacyId: this.appointment.pharmacy.id,
                    patientId: this.appointment.examination.patient.id,
                    quantity: 1,
                    expirityDate: this.reservationDate
                };
                MedicineDataService.reserveMedicine(reserve_form, AuthService.getCurrentUser().id)
                    .then(response => {
                        if (response.data){
                            alert('Medicine successfuly reserved');
                            console.log(response.data.id);
                            this.reservations[response.data.id] = response.data;
                            this.reservations[response.data.id].days = this.therapyDays;
                            
                            this.therapyMed = null; this.therapyDays = null; this.reservationDate = null;
                        }
                    })
                    .catch(error => {
                        alert('Reservation unsuccessful');
                        this.getReplacementMedicine(this.therapyMed);
                    });
                
                
            }           
        },
        removeTherapy(reservationIndex){
            PatientDataService.cancelReservation(reservationIndex)
                .then(response => {
                    if (response){
                        delete this.reservations[reservationIndex];
                    }
                });
        },
        bookNewAppointmentModal(){
            $('#newAppointmentModal').modal();
        },
        bookAtCustomTime(){
            this.newAppointment.price = this.predictedPrice;
            AppointmentDataService.bookCustomAppointment(this.newAppointment.dateTime, this.appointment.doctor.id, this.appointment.examination.patient.id, this.appointment.pharmacy.id, this.newAppointment.price, this.newAppointment.durationInMins)
            .then(response => {
                if (response.data){
                    alert('Successfuly booked appoinment at ' + this.newAppointment.dateTime);
                    this.bookedAppointments.push(this.newAppointment);
                }
                else {
                    alert('Time is not valid');
                }
            });
        },
        bookExistingAppointment(app){
            AppointmentDataService.bookAppointment(app.key, this.appointment.examination.patient.id)
                .then(response => {
                    if (response.data){
                        alert('Successfuly booked appointment!');
                        this.setAttr();
                        this.bookedAppointments.push(this.appointment);
                    }
                });
        },
        dayClicked(day) {
            this.selectedDay = new Date(day.date);
            console.log('selected day:',this.selectedDay);
        },
        setAttributes(pharmacyId) {
        AppointmentDataService.getDermAppFromPharmacyFree(this.doctor.id, pharmacyId)
        .then(response => {
            this.appointments = response.data;
        });
      },
      setAttributesForPharmacist() {
        AppointmentDataService.getPharmAppForCalendarFree(this.doctor.id)
          .then(response => {
            this.appointments = response.data;
          });
      },
      setAttr(){
        if (this.doctor.role === "PHARMACIST") {
            this.setAttributesForPharmacist();
        } else if (this.doctor.role === "DERMATOLOGIST") {
            if (this.appointment.pharmacy.id)
                this.setAttributes(this.appointment.pharmacy.id);
        }
      }
    },
    computed: {
        attributes() {
            return this.appointments.map(t => ({
                key: t.id,
                dates: Date.parse(t.startDate),
                customData: {
                    data: t,
                },
                highlight: 'gray',
                popover: {
                    label: t.startTime + ' ' + t.durationInMins + ' min',
                    visibility: 'hover',
                },
                }));
        },
        predictedPrice() {
            if (this.appointment.pharmacy)
                return this.appointment.pharmacy.pricePerHour * this.newAppointment.durationInMins/60;
            else
                return 0;
        },
        appointmentsOnDay() {
            let apps = [];
            var a = this.attributes;
            var now = new Date();
            now.setHours(0,0,0,0);
            for (let attr of a){
                let date = new Date(attr.dates);

                if (date.toDateString() === this.selectedDay.toDateString() && date >= now)
                    apps.push(attr);
            }
            console.log(apps);
            return apps;
        },
        doctor() {
            return AuthService.getCurrentUser();
        },
    },
    created() {
        this.appId = this.$route.params.id;
        this.getAppointmentData();
        
    },
    mounted() {
    }
}
</script>