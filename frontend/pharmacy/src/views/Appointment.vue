<template>
<div>
    <NavBar role="dermatologist"/>
    <div class="container">
        <h1 class="gray" v-if="appointment.id == 1">Examination</h1>
        <h3 class="gray">Patient: {{appointment.examination.patient.name}} {{appointment.examination.patient.surname}}</h3>
        <h3 class="gray">Dermatologist: {{appointment.examination.doctor.name}} {{appointment.examination.doctor.surname}}</h3>
        <p class="gray">Appointment: {{appointment.startTime}}</p>
        <p class="gray">Duration: {{appointment.durationInMins}} min</p>
        <p class="gray">Pharmacy: {{appointment.pharmacy}}</p>
        <div class="form-group">
            <label for="info">Information about examination</label>
            <textarea class="form-control rounded-0" id="info" rows="10" placeholder="Info..."></textarea>
        </div>
        <div class="form-group">
            <label for="diagnose">Diagnose</label>
            <input class="form-control rounded-0" placeholder="Diagnose" id="diagnose">
        </div>
        <div class="form-group">
            <button class="form-control btn btn-primary" id="finish">Finish</button>
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

// @ is an alias to /src
export default {
    name: 'Appointment',
    components: {
    NavBar,
    },
    data() {
        return {
            message: null,
            appointment: {
                id: 1,
                examination: {
                    patient: {
                        name: 'Pera',
                        surname: 'Djura',
                    },
                    doctor: {
                        name: 'Doktor',
                        surname: 'Doktorkovic',
                    }
                },
                startTime: '123546578',
                pharmacy: 'Apoteeeeeka',
                durationInMins: 30,
            },
        };
    },
    methods: {
        confirmLeave() {
            return window.confirm('Do you really want to leave? you have unsaved changes!')
        },

        confirmStayInDirtyForm() {
            return this.form_dirty && !this.confirmLeave()
        },

        beforeWindowUnload(e) {
            if (this.confirmStayInDirtyForm()) {
            // Cancel the event
            e.preventDefault();
            // Chrome requires returnValue to be set
            e.returnValue = '';
            }   
        },
    },
    beforeRouteLeave (to, from, next) {
        // If the form is dirty and the user did not confirm leave,
        // prevent losing unsaved changes by canceling navigation
        if (this.confirmStayInDirtyForm()){
            next(false)
        } else {
            // Navigate to next view
            next()
        }
    },
    created() {
    window.addEventListener('beforeunload', this.beforeWindowUnload)
    },

    beforeDestroy() {
    window.removeEventListener('beforeunload', this.beforeWindowUnload)
    },

}
</script>