<template>
  <div class="container">
    <h2 class="h2 mb-3 text-center">Working Calendar</h2>
    <p class="text-lg font-medium text-gray-600 mb-4 text-center">
      <span v-if="calendarDetail === 'month'">Month</span>
      <span v-else-if="calendarDetail === 'year'">Year</span>
      <span v-else>Week</span>ly preview of appointments, examinations, absences and vacations
    </p>
    <div class="row">
      <div v-if="doctor.role === 'DERMATOLOGIST' && jobs" class="form-group col">
          <label for="pharmacySelect">Pharmacy</label>
          <select name="pharmacySelect" id="pharmacySelect" class="form-control" v-on:change="setAttributes($event)">
            <option value="no pharmacy selected">--- Choose a pharmacy ---</option>
            <option :key="w.pharmacyDTO.id" v-for="w in jobs" :value="w.pharmacyDTO.id">{{w.pharmacyDTO.name}}</option>
            <!-- <button class="btn btn-primary d-inline"></button> -->
          </select>
      </div>
      <div class="form-group col">
        <label for="calendarSelect">Calendar</label>
        <select name="calendarSelect" id="calendarSelect" class="form-control" v-model="calendarDetail">
          <option value="week">Week</option>
          <option value="month">Month</option>
          <option value="year">Year</option>
        </select>
      </div>
    </div>
    <!-- M O N T H     
        is-double-paned   @dayClick="a('aaa')" -->
    <div class="text-center section" v-if="appointments && calendarDetail === 'month'">
      <Calendar
        class="custom-calendar"
        ref="calendar"
        :masks="masks"
        :attributes="attributes"
        :min-date="new Date()"
        is-expanded
      >
        <template v-slot:day-content="{ day, attributes }">
          <div class="flex flex-col z-10 overflow-hidden whole-day"> <!--whole day-->
            <span class="day-label text-sm text-gray-900">{{ day.day }}</span> <!--number of a day-->
            <!-- <div class="day-wrapper"> -->
            <div class="flex-grow overflow-y-auto overflow-x-auto day"> <!--all content of a day-->
              <p
                v-for="attr in attributes"
                :key="attr.key"
                class="rounded p-0 mt-0 mb-0 mx-1 appointment lbl"
                :class="attr.customData.typeForClass"
                v-on:dblclick="startAppointment(attr)"
              >
                <b>{{ attr.customData.startTime }}</b> <b>{{ attr.customData.durationInMins}}</b>m <span v-if="!attr.customData.typeForClass.includes('free')"> {{ attr.customData.patientName }} {{ attr.customData.patientSurname }} </span>
              </p> <!--appointment-->
            </div>
            <!-- </div> -->
          </div>
        </template>
      </Calendar>
    </div>
    <!-- Y E A R   @dayclick='dayClicked'   is-double-paned -->
    <div class="text-center section" v-else-if="appointments && calendarDetail === 'year'">
      <Calendar
      :attributes="attributes"
      :month="1"
      :columns="4" :rows="3"
      :min-date="new Date()"
      ref="calendar"
      is-expanded
      >
      <!-- <template v-slot:day-content="{ day, attributes }">
      </template> -->
    </Calendar>
    </div>
    <!--MODAL FOR CHOOSING APPOINTMENT TO START IN YEARLY CALENDAR-->
    <div>
      <div>
          <div class="modal fade" id="appointmentsModal" tabindex="-1" role="dialog" aria-labelledby="appointmentsModal" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div v-if="selectedAppointment" class="modal-content">
                <div class="modal-header">
                  <span v-bind:class="{ 'badge-info': selectedAppointment.type == 'COUNSELING', 'badge-primary': selectedAppointment.type == 'EXAMINATION' }" class="badge">{{selectedAppointment.type}}
                  <span v-if="false" class="badge badge-danger">CALCELED</span>
                  </span>
                  <h5 class="modal-title" id="appointmentsModalLabel">Appointments on this day</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <table class="table">
                    <thead>
                      <th>Time</th>
                      <th>Duration</th>
                      <th>Patient</th>
                      <th></th>
                    </thead>
                    <tbody>
                      <tr :key="app" v-for="app in appointmentsOnDay">
                        <td>{{app.startTime}}</td>
                        <td>{{app.durationInMins}}</td>
                        <td>{{app.patientName}} {{app.patientSurname}}</td>
                        <td v-if="!(app.typeForClass.includes('over') || app.typeForClass.includes('free'))">
                          <button class="btn btn-primary" @click="startAppointment(app.id)">Start</button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
              </div>
            </div>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
import AppointmentDataService from '@/service/AppointmentDataService.js';
import PharmacyDataService from '@/service/PharmacyDataService.js';
import AuthService from '../service/AuthService.js';

console.warn = () => {};

export default {
  data() {
    const month = new Date().getMonth();
    const january = 1;
    const year = new Date().getFullYear();
    return {
      // my
      currentMonth: january,

      chosenPharmacyId: 1,
      jobs: null,
      appointments: null,
      appointmentsOnDay: [],

      calendarDetail: 'month',
      masks: {
        weekdays: 'WWWW',
      },
    };
  },
  mounted(){
      this.getPharmacies();
      if (this.doctor.role === "PHARMACIST") {
        this.setAttributesForPharmacist();
      } else if (this.doctor.role === "DERMATOLOGIST") {
        this.setAttributes(0);
      }
  },
  computed: {
  	attributes() {
      if (this.calendarDetail === 'month') {
        return this.appointments.map(t => ({
          key: t.id,
          dates: Date.parse(t.startDate),
          //{ start: new Date(2018, 0, 1), end: new Date(2018, 0, 5) } for vacations
          customData: t,
        }));
      } else if (this.calendarDetail === 'year'){
        return this.appointments.map(t => ({
          dates: Date.parse(t.startDate),
          dot: {
            backgroundColor: '#ff8080',
            opacity: t.typeForClass.includes('free') ? 0.3 : 1,//t.typeForClass.includes('held') ? 0.5 : 1,//todo.isComplete ? 0.3 : 1,
          },
          popover: {
            label: t.startTime + ' ' + t.durationInMins + 'm ' + t.patientName + ' ' + t.patientSurname,
            visibility: 'focus',
            //slot: 'todo-row', // Matches slot from above
          },
        }));
      }
    },
    doctor() {
      return AuthService.getCurrentUser();
    },
    calendar() { console.log(this.$refs.calendar); return this.$refs.calendar; }
  },
  methods: {
      a(id){
          alert(id);
          //this.calendar.move({ month: 1, year: 2021 });
      },
      /*dayClicked(day) {
        var a = this.attributes;
        alert(a);
        for (let attr of a){
          let date = new Date(attr.dates);
          let thisDate = new Date(day.date);

          let sameYear = date.getFullYear() === thisDate.getFullYear();
          let sameMonth = date.getMonth() === thisDate.getMonth();
          let sameDay = date.getDay() === thisDate.getDay()
          //alert(date.getFullYear() + ' vs ' + thisDate.getFullYear());
          if (sameYear && sameMonth && sameDay)
            //alert('found app on this day');
            this.appointmentsOnDay.push(this.appointments[1]);
          console.log(this.appointmentsOnDay);
          if (this.appointmentsOnDay.length > 0)
            $('#appointmentsModal').show();
        }
        //'<a href="/appointment/'+t.id+'">start</a>'
      },*/
      startAppointment(attributeApp) {
        if (attributeApp.customData.typeForClass.includes('over'))
          alert("Appointemnt already held.");
        else if (attributeApp.customData.typeForClass.includes('free'))
          alert("Appointment not booked yet.");
        else
          window.location.href = "/appointment/" + attributeApp.key;
      },
      setAttributes(pharmacyIdEvent) {
        if (pharmacyIdEvent === 0){ this.appointments = []; return; }
        let pharmacyId = pharmacyIdEvent.target.value;
        if (pharmacyId === "no pharmacy selected") return;
          AppointmentDataService.getDermAppFromPharmacy(this.doctor.id, pharmacyId)
          .then(response => {
            this.appointments = response.data;
          });
      },
      setAttributesForPharmacist() {
        AppointmentDataService.getPharmAppForCalendar(this.doctor.id)
          .then(response => {
            this.appointments = response.data;
          });
      },
      getPharmacies() {
        if (this.doctor){
          PharmacyDataService.getDoctorsJobs(this.doctor.id)
            .then(response => {
              this.jobs = response.data;
            });
          }
      }
  }
};
</script>

<style lang="postcss" scoped>
.appointment{
    font-size: 12px;
    /* font-weight: bold; */
    padding: 0;
    margin: 0;
    overflow: hidden;
}

.free{
    background-color: var(--free-bg);
    color: var(--free-txt);
}

.examination{
    background-color: var(--exam-bg);
    color: var(--exam-txt);
}

.over {
  background-color: var(--held-bg);
  color: var(--held-txt);
}

.counseling{
    background-color: var(--coun-bg);
    color: var(--coun-txt);
}

.day-wrapper {
    /* height: 100%; */
    /* width: 100%; */
    /* border: 1px solid green; */
    /* overflow: auto; */
}

.day {
    /* border: #000000 3px solid; */
    height: 100%;
    width: 100%;
    /* border-bottom: var(--blue-border); */
    
    /* overflow: hidden; */
    /* position: relative; */
    overflow-y: auto;
    /* padding-right: 17px; */
    /* overflow: auto; */
}

.today {
  color: red;
}

.not-today {
  color: black;
}

.whole-day {
    height: 150px;
    width: 100%;
    border: var(--blue-border);
}

/* .left-top-border {
    border-left: var(--blue-border);
    border-top: var(--blue-border);
} */

.custom-calendar {
    border-width: 2px;
}

/* ::-webkit-scrollbar {
  width: 0px;
}
::-webkit-scrollbar-track {
  display: none;
} */
.custom-calendar.vc-container {
  --blue-border: 1px solid #9bc6f5;
  --pink-border-highlight: 1px solid #ff00ea;
  --day-width: 10%;
  --day-height: 10%;
  --held-txt: #61a0ff;
  --free-txt: #6d6d6d;
  --exam-txt: #ffffff;
  --coun-txt: #fff0d0;
  --held-bg: #eeeeee;
  --free-bg: #cecece;
  --exam-bg: #0d6efd;
  --coun-bg: #ffa600;
    border-radius: 1;
    border-width: 2px;
    height: 100%;
    /* width: 500px; */
  }
  /* .custom-calendar.vc-container .vc-header {
    background-color: #e7aa26;
    padding: 10px 0;
  }
  .custom-calendar.vc-container .vc-weeks {
    padding: 0;
  }
  .custom-calendar.vc-container .vc-weekday {
    background-color: var(--weekday-bg);
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    padding: 5px 0;
  }
  .custom-calendar.vc-container .vc-day {
    padding: 0 5px 3px 5px;
    text-align: left;
    height: var(--day-height);
    min-width: var(--day-width);
    background-color: rgb(197, 216, 206);
  }
  .custom-calendar.vc-container .weekday-1 .weekday-7 {
    background-color: #13466d;
  }
  .custom-calendar.vc-container :not(.on-bottom) {
    border-bottom: var(--day-border);
  }
  .custom-calendar.vc-container .weekday-1 {
    border-bottom: var(--day-border-highlight);
  }
  .custom-calendar.vc-container :not(.on-right) {
    border-right: var(--day-border);
  }
  .custom-calendar.vc-container .vc-day-dots {
    margin-bottom: 5px;
  } */
</style>