<template>
  <div class="container">
    <h2 class="h2 mb-3 text-center">Working Calendar</h2>
    <p class="text-lg font-medium text-gray-600 mb-4 text-center">
      <span v-if="calendarDetail === 'month'">Month</span>
      <span v-else-if="calendarDetail === 'year'">Year</span>
      <span v-else>Week</span>ly preview of appointments, examinations, absences and vacations
    </p>
    <div class="row">
    <div v-if="jobs" class="form-group col">
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
  <div class="text-center section" v-if="appointments && calendarDetail === 'month'">
    <Calendar
      class="custom-calendar"
      ref="calendar"
      :masks="masks"
      :attributes="attributes"
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
              v-on:click="a(attr.key)"
            >
              <b>{{ attr.customData.startTime }}</b> <b>{{ attr.customData.durationInMins}}</b>m <span v-if="!attr.customData.typeForClass.includes('free')"> {{ attr.customData.patientName }} {{ attr.customData.patientSurname }} </span>
            </p> <!--appointment-->
          </div>
          <!-- </div> -->
        </div>
      </template>
    </Calendar>
  </div>
  </div>
</template>

<script>
import AppointmentDataService from '@/service/AppointmentDataService.js';
import PharmacyDataService from '@/service/PharmacyDataService.js';
import AuthService from '../service/AuthService.js';

export default {
  data() {
    const month = new Date().getMonth();
    const year = new Date().getFullYear();
    return {
      // my
      currentMonth: month,
      calendarRef: null,

      chosenPharmacyId: 1,
      doctor: null,
      jobs: null,
      appointments: null,

      calendarDetail: 'month',

      //
      masks: {
        weekdays: 'WWWW',
      },
      /*attributes: [
        {
          key: 1,
          customData: {
            title: 'L',
            class: 'counseling',
          },
          dates: new Date(year, month, 1),
        },
        {
          key: 2,
          customData: {
            title: 'T',
            class: 'free',
          },
          dates: new Date(year, month, 2),
        },
        {
          key: 3,
          customData: {
            title: "N",
            class: 'free',
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 4,
          customData: {
            title: 'T',
            class: 'counseling',
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 10,
          customData: {
            title: 'N',
            class: 'examination',
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 5,
          customData: {
            title: 'E',
            class: 'examination',
          },
          dates: new Date(year, month, 7),
        },
        {
          key: 6,
          customData: {
            title: "M",
            class: 'examination',
          },
          dates: new Date(year, month, 11),
        },
        {
          key: 7,
          customData: {
            title: 'C',
            class: 'examination',
          },
          dates: { months: 5, ordinalWeekdays: { 2: 1 } },
        },
        {
          key: 8,
          customData: {
            title: "M",
            class: 'free',
          },
          dates: new Date(year, month, 22),
        },
        {
          key: 9,
          customData: {
            title: 'V',
            class: 'free',
          },
          dates: new Date(year, month, 25),
        },
      ],*/
    };
  },
  mounted(){
      // this.calendarRef = this.$refs.calendar;
      // this.currentMonth = this.calendarRef.month;
      this.doctor = AuthService.getCurrentUser();
      this.getPharmacies();
      this.setAttributes(0);
  },
  computed: {
  	attributes() {
      return this.appointments.map(t => ({
        key: t.id,
        dates: Date.parse(t.startDate),
        customData: t,
      }));
    }
  },
  methods: {
      a(id){
          alert(id);
      },
      setAttributes(pharmacyIdEvent) {
        if (pharmacyIdEvent === 0){ this.appointments = []; return; }
        let pharmacyId = pharmacyIdEvent.target.value;
        if (pharmacyId === "no pharmacy selected") return;
        AppointmentDataService.getDermAppFromPharmacy(this.doctor.id, pharmacyId)
          .then(response => {
            this.appointments = response.data;
            // for (let i = 0; i < response.data.length; i++) {
            //   this.attributes[i] = {
            //     key: response.data[i].id,
            //     customData: response.data[i],
            //     dates: Date.parse(response.data[i].startDate)
            //   }
            //   //alert(JSON.stringify(this.attributes));
            // }
            //this.attributes = response.data;
            //alert(JSON.stringify(this.appointments[1]));
          });
      },
      getPharmacies() {
        PharmacyDataService.getDoctorsJobs(this.doctor.id)
          .then(response => {
            this.jobs = response.data;
            //alert(JSON.stringify(this.jobs[0]));
          });
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
  --coun-bg: #e7aa26;
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