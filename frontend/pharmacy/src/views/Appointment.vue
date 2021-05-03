<template>
<div>
    <NavBar/>
    <div class="container">
        <h1 class="gray" v-if="appointment.type == 'EXAMINATION'">Examination</h1>
        <h1 class="gray" v-else>Counseling</h1>
        <h3 class="gray">Patient: {{appointment.examination.patient.name}} {{appointment.examination.patient.surname}}</h3>
        <!-- <h3 class="gray">Dermatologist: {{appointment.doctor.name}} {{appointment.doctor.surname}}</h3> -->
        <p class="gray">Appointment: {{UtilService.formatDateTime(appointment.startTime)}}</p>
        <p class="gray">Duration: {{appointment.durationInMins}} min</p>
        <p class="gray">Pharmacy: {{appointment.pharmacy.name}}</p>


        <div v-if="appointment.examination.status == 'PENDING'">
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
                <button class="form-control btn btn-primary" id="finish" @click="finishAppointment()">Finish</button>
            </div>
        </div>

        <div v-else-if="appointment.examination.status == 'HELD'">
            <p class="badge badge-success">HELD</p>
            <div class="form-group">
                <label for="info">Anamnesis (conversation with patient)</label>
                <textarea :disabled="true" v-model="appointment.examination.examinationInfo" class="form-control" id="info" rows="5"></textarea>
            </div>
            <div class="form-group">
                <label for="diagnose">Diagnose</label>
                <input :disabled="true" v-model="appointment.examination.diagnose" class="form-control" id="diagnose">
            </div>
            <div class="form-group">
                <button :disabled="true" class="form-control btn btn-primary" id="finish" @click="finishAppointment()">Finish</button>
            </div>
        </div>

        <div v-else-if="appointment.examination.status == 'NOT_HELD'">
            <p class="badge badge-warning">PATIENT DID NOT SHOW UP</p>
        </div>
        <div v-else-if="appointment.examination.status == 'CANCELED'">
            <p class="badge badge-danger">CANCELED</p>
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

// @ is an alias to /src
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
                id: 1,
                examination: {
                    patient: {
                        name: '',
                        surname: '',
                        status: '',
                    },
                    diagnose: '',
                    examinationInfo: '',
                },
                startTime: '',
                pharmacy: '',
                durationInMins: 0,
                doctor: {
                        name: '',
                        surname: '',
                    },
                price: 0,
            },
            patientAppeared: null,
            //newAppointmentDateTime: null,
            newAppointment: null,
            newAppointment: {
                "dateTime": null,
                "price": 0,
                "durationInMins": 0
            },
        };
    },
    methods: {
        getAppointmentData(){
            AppointmentDataService.getAppointmentInfo(this.appId)
            .then(response => {
                    this.appointment = response.data;
                    console.log(response.data);
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
            // alert(this.appointment.examination.examinationInfo);
            AppointmentDataService.postAppointmentInfo(this.appointment)
            .then(response => {
                    if (response.data){
                        alert("Info saved.");
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
        // confirmLeave() {
        //     return window.confirm('Do you really want to leave? you have unsaved changes!')
        // },

        // confirmStayInDirtyForm() {
        //     return this.form_dirty && !this.confirmLeave()
        // },

        // beforeWindowUnload(e) {
        //     if (this.confirmStayInDirtyForm()) {
        //     // Cancel the event
        //     e.preventDefault();
        //     // Chrome requires returnValue to be set
        //     e.returnValue = '';
        //     }   
        // },
    },
    // beforeRouteLeave (to, from, next) {
    //     // If the form is dirty and the user did not confirm leave,
    //     // prevent losing unsaved changes by canceling navigation
    //     if (this.confirmStayInDirtyForm()){
    //         next(false)
    //     } else {
    //         // Navigate to next view
    //         next()
    //     }
    // },
    created() {
    // window.addEventListener('beforeunload', this.beforeWindowUnload);
        this.appId = this.$route.params.id;
        // this.id = 3;
        this.getAppointmentData();
    },

    // beforeUnmount() {
    // window.removeEventListener('beforeunload', this.beforeWindowUnload)
    // },

}
</script>