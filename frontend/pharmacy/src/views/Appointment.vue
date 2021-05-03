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
            <label for="selectMed">Therapy</label>
                <div class="row mb-2">
                    <!-- <input :disabled="!patientAppeared" type="search" class="form-control col" id="searchMedicine" placeholder="Search Medicine"> -->
                    
                    <select v-model="therapyMed" :disabled="!patientAppeared" class="form-control col mx-3" id="selectMed">
                        <option :key="m.id" v-for="m in medicines" :value="m">{{m.medicine.name}}</option>
                    </select>
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
                <textarea :disabled="true" v-model="appointment.examination.examinationInfo" class="form-control" id="info" rows="5" placeholder="Info..."></textarea>
            </div>
            <div class="form-group">
                <label for="diagnose">Diagnose</label>
                <input :disabled="true" v-model="appointment.examination.diagnose" class="form-control" placeholder="Diagnose" id="diagnose">
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
import MedicineDataService from '../service/MedicineDataService.js';

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
                pharmacy: '',
                durationInMins: 0,
                doctor: {
                        name: '',
                        surname: '',
                    },
            },
            patientAppeared: null,
            medicines: [{name: 'lek1', code: 1}, {name: 'lek2', code: 2}, {name: 'lek3', code: 3}],
            therapyMed: null,
            therapyDays: null,
            reservationDate: null,
            reservations: {},
            status: '',
        };
    },
    methods: {
        getAppointmentData(){
            AppointmentDataService.getAppointmentInfo(this.appId)
            .then(response => {
                    this.appointment = response.data;
                    this.status = this.appointment.examination.status;
                    console.log(response.data);
                    MedicineDataService.getMedicineForPharmacy(this.appointment.pharmacy.id)
                    .then(response =>
                            {
                                this.medicines = response.data;
                                for (var m in this.medicines.values){
                                    //console.log(JSON.stringify(m));
                                }
                            });
                });
        },
        finishAppointment(){
            //console.log(JSON.stringify(this.appointment));
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
                        this.status = this.appointment.examination.status;
                    }
                });
        },
        addTherapy() {
            if (this.therapyMed && this.therapyDays && this.reservationDate){
                let unique = true;
                console.log('polja nisu prazna');
                for (let t in this.appointment.examination.therapy){
                    console.log('gledam neki lek u terapiji');
                    if (this.appointment.examination.therapy[t].medInPharma === this.therapyMed){
                        unique = false;
                        console.log('vec je rezervisan taj lek');
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

                MedicineDataService.reserveMedicine(reserve_form)
                    .then( response => {
                        if (response.data){
                            alert('Medicine successfuly reserved');
                            this.reservations[response.data.id] = response.data;
                            this.reservations[response.data.id].days = this.therapyDays;
                            console.log("rezervacije!!!\n"+JSON.stringify(this.reservations));

                            this.appointment.examination.therapy.push({medInPharma: this.therapyMed, days: this.therapyDays+''});
                            console.log("terapija!!!\n"+JSON.stringify(this.appointment.examination.therapy));
                            //$('#therapyRow').append('<tr><td>'+ this.therapyMed.medicine.name + '</td><td>' + this.therapyDays +'</td><td>'+ this.reservationDate +'</td><td><button class="btn btn-danger" onclick="removeTherapy('+ response.data.id +')">Remove</button></td></tr>');
                            this.therapyMed = null; this.therapyDays = null; this.reservationDate = null;
                        }
                    });
                
                
            }
            
        },
        removeTherapy(reservationIndex){
            console.log("rezervacija koja je selektovana!!!\n"+JSON.stringify(this.reservations[reservationIndex]));
            
        }
        // getMedicineForTherapy(){
            
        // }
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
        // this.getMedicineForTherapy();
    },

    // beforeUnmount() {
    // window.removeEventListener('beforeunload', this.beforeWindowUnload)
    // },

}
</script>