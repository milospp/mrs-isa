<template>
<div>
    <NavBar/>
    <div class="container">
        <h1 class="gray" v-if="appointment.type == 'EXAMINATION'">Examination</h1>
        <h1 class="gray" v-else-if="appointment.type == 'COUNSELING'">Counseling</h1>
        <h3 class="gray">Patient: {{appointment.examination.patient.name}} {{appointment.examination.patient.surname}}</h3>
        <!-- <h3 class="gray">Dermatologist: {{appointment.doctor.name}} {{appointment.doctor.surname}}</h3> -->
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
            <div class="form-group">
                <button @click="bookNewAppointmentModal()" :disabled="!patientAppeared" class="btn btn-secondary">Book New Custom 
                    <span v-if="appointment.type == 'EXAMINATION'">
                        Examination
                    </span>
                    <span v-else>
                        Counseling
                    </span>
                </button>
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
                    <table class="table table-striped box-shadow">
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
                                <td>{{r.lastDate}}</td>
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

    <!-- MODAL for booking new appointment -->
    <div class="modal" tabindex="-1" role="dialog" id="newAppointmentModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Book Custom Appointment</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="customTimeAppointment" class="form-group">
                        <div class="form-group">
                            <label for="datetime">Date and time</label>
                            <input id="datetime" class="form-control" v-model="newAppointment.dateTime" type="datetime-local">
                        </div>
                        <div class="form-group">
                            <label for="duration">Duration (minutes)</label>
                            <input id="duration" class="form-control" v-model="newAppointment.durationInMins" type="number" min="5" step="5">
                        </div>
                        <div class="form-group">
                            <label for="price">Price</label>
                            <input id="price" class="form-control" v-model="newAppointment.price" type="number" min="0">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" @click="bookAtCustomTime()">Book</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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
                durationInMins: 0,
                doctor: {
                        name: '',
                        surname: '',
                    },
                price: 0,
            },
            patientAppeared: null,
            newAppointment: {
                "dateTime": null,
                "price": 0,
                "durationInMins": 0
            },
            medicines: [],
            therapyMed: null,
            therapyDays: null,
            reservationDate: null,
            reservations: {},
            status: '',

            currentReplacements: null,
        };
    },
    methods: {
        getAppointmentData(){
            AppointmentDataService.getAppointmentInfo(this.appId)
            .then(response => {
                    this.appointment = response.data;
                    this.status = this.appointment.examination.status;
                    console.log(response.data);
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
            console.table(this.appointment.examination.therapy);


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
            alert('looking for replacements...');
            this.currentReplacements = [];
            for (let m of unavailableMedInPharma.medicine.replacementMedicationIds){
                //alert('replacement med code' + m);
                for (let one of this.medicines){
                    //alert(m + ' vs ' + one.medicine.code);
                    if (one.medicine.code === m){
                        //alert('replacement med name' + one.medicine.name);
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
                console.log('polja nisu prazna');
                console.log(this.reservations);
                for (let t in this.reservations){
                    console.log('gledam neki lek u terapiji' + t);
                    console.log(JSON.stringify(this.reservations[t].medicineInPharmacy.id));
                    if (this.reservations[t].medicineInPharmacy.id === this.therapyMed.id){
                        unique = false;
                        console.log('vec je rezervisan taj lek');
                        console.log(unique);
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
                            console.log("rezervacije!!!\n"+JSON.stringify(this.reservations));

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
            console.log("rezervacija koja je selektovana!!!\n"+JSON.stringify(this.reservations[reservationIndex]));
            console.log(reservationIndex + " = " + this.reservations[reservationIndex].id);
            PatientDataService.cancelReservation(reservationIndex)
                .then(response => {
                    if (response){
                        
                        console.log(JSON.stringify(this.appointment.examination.therapy));

                        delete this.reservations[reservationIndex];
                        console.log('nakon brisanja');
                        console.log(JSON.stringify(this.reservations));
                        
                    }
                });
        },
        bookNewAppointmentModal(){
            $('#newAppointmentModal').modal();
        },
        bookAtCustomTime(){
            console.log(this.newAppointment.dateTime, this.appointment.doctor.id, this.appointment.examination.patient.id, this.appointment.pharmacy.id, this.newAppointment.price, this.newAppointment.durationInMins);
            AppointmentDataService.bookCustomAppointment(this.newAppointment.dateTime, this.appointment.doctor.id, this.appointment.examination.patient.id, this.appointment.pharmacy.id, this.newAppointment.price, this.newAppointment.durationInMins)
            .then(response => {
                if (response.data){
                    alert('Successfuly booked appoinment at ' + this.newAppointment.dateTime);
                }
                else {
                    alert('Time is not valid');
                }
            });
        }
    },
    created() {
        this.appId = this.$route.params.id;
        this.getAppointmentData();
    },
}
</script>