<template>
<div>
    <NavBar role="dermatologist"/>
    <div class="container">
        <h1 class="gray" v-if="appointment.type == 'EXAMINATION'">Examination</h1>
        <h1 class="gray" v-else>Examination</h1>
        <h3 class="gray">Patient: {{appointment.examination.patient.name}} {{appointment.examination.patient.surname}}</h3>
        <!-- <h3 class="gray">Dermatologist: {{appointment.doctor.name}} {{appointment.doctor.surname}}</h3> -->
        <p class="gray">Appointment: {{UtilService.formatDateTime(appointment.startTime)}}</p>
        <p class="gray">Duration: {{appointment.durationInMins}} min</p>
        <p class="gray">Pharmacy: {{appointment.pharmacy.name}}</p>


        <div v-if="appointment.examination.status == 'PENDING'">
            <div class="form-group">
                <label for="info">Anamnesis (conversation with patient)</label>
                <textarea v-model="appointment.examination.examinationInfo" class="form-control" id="info" rows="5" placeholder="Info..."></textarea>
            </div>
            <div class="form-group">
                <label for="diagnose">Diagnose</label>
                <input v-model="appointment.examination.diagnose" class="form-control" placeholder="Diagnose" id="diagnose">
            </div>
            <div class="form-group">
                <button class="form-control btn btn-primary" id="finish" @click="finishAppointment()">Finish</button>
            </div>
        </div>

        <div v-else-if="appointment.examination.status == 'HELD'">
            <div class="form-group">
                <label for="info">Anamnesis (conversation with patient)</label>
                <textarea :disabled="true" v-model="appointment.examination.examinationInfo" class="form-control" id="info" rows="5" placeholder="Info..."></textarea>
            </div>
            <div class="form-group">
                <label for="diagnose">Diagnose</label>
                <input :disabled="true" v-model="appointment.examination.diagnose" class="form-control" placeholder="Diagnose" id="diagnose">
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
            }
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
            this.appointment.examination.status = 'HELD';
            // alert(this.appointment.examination.examinationInfo);
            AppointmentDataService.postAppointmentInfo(this.appointment)
            .then(response => {
                    if (response.data){
                        alert("Info saved.");
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