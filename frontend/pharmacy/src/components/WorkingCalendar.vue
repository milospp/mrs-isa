<template>
  <div class="container">
    <h2 class="h2 mb-3 text-center">Working Calendar</h2>
    <p class="text-lg font-medium text-gray-600 text-center">
      <span v-if="calendarDetail === 'month'">Month</span>
      <span v-else-if="calendarDetail === 'year'">Year</span>
      <span v-else>Week</span>ly preview of appointments, examinations, absences and vacations
    </p>
    <!-- <p v-if="calendarDetail === 'week'" class="text-lg font-medium text-gray-600 text-center">{{thisWeekStart.toDateString()}} - {{thisWeekEnd.toDateString()}}</p> -->
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
    <!-- :date-classes="myDateClasses" -->
    <div class="text-center section calendar-parent" v-if="appointments && calendarDetail === 'week'">
      <calendar-view
            :items="attributes"
            :show-date="showDate"
            :time-format-options="{ hour: 'numeric', minute: '2-digit' }"
            :enable-drag-drop="false"
            :disable-past="disablePast"
            :disable-future="disableFuture"
            :show-times="showTimes"
            itemContentHeight="2.4rem"
            :display-period-uom="displayPeriodUom"
            :display-period-count="displayPeriodCount"
            :starting-day-of-week="startingDayOfWeek"
            :class="themeClasses"
            :period-changed-callback="periodChanged"
            :current-period-label="useTodayIcons ? 'icons' : ''"
            :displayWeekNumbers="displayWeekNumbers"
            :enable-date-selection="true"
            :selection-start="selectionStart"
            :selection-end="selectionEnd"
            @date-selection-start="setSelection"
            @date-selection="setSelection"
            @date-selection-finish="finishSelection"
            @drop-on-date="onDrop"
            @click-date="onClickDay"
            @click-item="onClickItem"
            >
            <template #header="{ headerProps }">
					    <calendar-view-header id="calendar-header-nav" :header-props="headerProps" @input="setShowDate" />
				    </template>

      </calendar-view>
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
    <!-- Y E A R      is-double-paned -->
    <div class="text-center section row" v-else-if="appointments && calendarDetail === 'year'">
      <div class="col">
      <Calendar
      :attributes="attributes"
      :month="1"
      :columns="4" :rows="3"
      :min-date="new Date()"
      ref="calendar"
      @dayclick='dayClicked'
      is-expanded
      >
      </Calendar>
      </div>
      <div class="col">
        <div class="text-center bg-light my-3">
          <h3>On selected day...{{UtilService.formatDate(selectedDay)}}</h3>
          <!-- <div class="container row" :key="app.id" v-for="app in appointmentsOnDay">
          </div> -->
          <div v-if="appointmentsOnDay.length > 0">
            <table style="text-align: left; table-layout: fixed;" class="table table-striped">
              <thead>
                <th>Time</th>
                <th>Duration</th>
                <th>Patient</th>
                <th>Start</th>
              </thead>
              <tbody>
                <tr :key="app" v-for="app in appointmentsOnDay">
                  <td>{{app.customData.startTime}}</td>
                  <td>{{app.customData.durationInMins}} min</td>
                  <td>{{app.customData.patientName}} {{app.customData.patientSurname}}</td>
                  <td>
                    <button v-if="!(app.customData.typeForClass.includes('free') || app.customData.typeForClass.includes('over'))" class="btn btn-primary" @click="startAppointment(app)">Start</button>
                    <h4 v-else-if="app.customData.typeForClass.includes('free')"><span class="badge badge-secondary">FREE</span></h4>
                    <h4 v-else-if="app.customData.typeForClass.includes('over')"><span class="badge">OVER</span></h4>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div v-else>
            <p>There are no appointments on this day.</p>
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
import UtilService from '../service/UtilService.js';

import { CalendarView, CalendarViewHeader, CalendarMath } from "vue-simple-calendar"
	
import "vue-simple-calendar/dist/style.css"
// The next two lines are optional themes
import "vue-simple-calendar/static/css/default.css"
import "vue-simple-calendar/static/css/holidays-us.css"

console.warn = () => {};

export default {
  components: {
		CalendarView,
		CalendarViewHeader,
	},
  setup() {
    return {UtilService}
  },
  data() {
    const month = new Date().getMonth();
    const january = 1;
    const year = new Date().getFullYear();
    const todayFull = new Date();
    const today = new Date(todayFull.getFullYear(), todayFull.getMonth(), todayFull.getDay());
    return {
      // my
      currentMonth: january,

      chosenPharmacyId: 1,
      jobs: null,
      appointments: null,

      calendarDetail: 'month',
      masks: {
        weekdays: 'WWWW',
      },
      selectedDay: new Date(),

      showDate: new Date(),
      startingDayOfWeek: 1, // monday is the first day
      disablePast: false,
      disableFuture: false,
      displayPeriodUom: "week",
      displayPeriodCount: 1,
      displayWeekNumbers: false,
      showTimes: false,
      selectionStart: null,
      selectionEnd: null,
      newItemTitle: "",
      newItemStartDate: "",
      newItemEndDate: "",
      useDefaultTheme: false,
      useHolidayTheme: false,
      useTodayIcons: false,
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
      if (this.calendarDetail === 'week') {
        return this.appointments.map(t => ({
          id: t.id,
          startDate: Date.parse(t.startDate),
          //{ start: new Date(2018, 0, 1), end: new Date(2018, 0, 5) } for vacations
          customData: t,
          //startTime: t.startTime,
          title: t.startTime + ' ' + t.durationInMins + 'm<br/>' + t.patientName + ' ' + t.patientSurname,
          classes: ['appointmentWeek'],
          style: t.typeForClass.includes('free') ? 'background-color: #cecece; color: #6d6d6d;' : 
                  t.typeForClass.includes('over') ? 'background-color: #eeeeee; color: #61a0ff;' :
                   t.typeForClass.includes('examination') ? 'background-color: #0d6efd; color: #ffffff;' :
                    t.typeForClass.includes('counseling') ? 'background-color: #ffa600; color: #fff0d0;' : '',
          url: "https://localhost:3000/appoinment/"+t.id,
        }));
      }
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
          customData: t,
          dot: {
            style: {
              backgroundColor: (Date.parse(t.startDate) < this.today) ? 'gray' : (this.doctor.role === 'DERMATOLOGIST') ? '#007bff' : (this.doctor.role === 'PHARMACIST') ? '#ffc107' : 'gray',//'#ff8080',
              opacity: t.typeForClass.includes('free') ? 0.5 : t.typeForClass.includes('over') ? 0.3 : 1,//t.typeForClass.includes('held') ? 0.5 : 1,//todo.isComplete ? 0.3 : 1,
            }
          },
          popover: {
            label: t.startTime + ' ' + t.durationInMins + 'm ' + t.patientName + ' ' + t.patientSurname,
            visibility: 'hover',
          },
        }));
        
      }
    },
    doctor() {
      return AuthService.getCurrentUser();
    },
    calendar() { console.log(this.$refs.calendar); return this.$refs.calendar; },
    appointmentsOnDay() {
      let apps = [];
      var a = this.attributes;
      for (let attr of a){
        let date = new Date(attr.dates);
        if (date.toDateString() == this.selectedDay.toDateString())
          apps.push(attr);
      }
      return apps;
      //'<a href="/appointment/'+t.id+'">start</a>'
    },
    thisWeekNum() {
      return this.getNumberOfWeek(Date.now()) - 2;
    },
    thisWeekStart() {
      return this.getDateOfISOWeek(this.thisWeekNum, new Date().getFullYear());
    },
    thisWeekEnd() {
      let date = this.getDateOfISOWeek(this.thisWeekNum, new Date().getFullYear());
      date.setDate(date.getDate() + 7);
      return date;
    },
    appointments1() {
      return this.appointmentsByDay(1);
    },
    appointments2() {
      return this.appointmentsByDay(2);
    },
    appointments3() {
      return this.appointmentsByDay(3);
    },
    appointments4() {
      return this.appointmentsByDay(4);
    },
    appointments5() {
      return this.appointmentsByDay(5);
    },
    appointments6() {
      return this.appointmentsByDay(6);
    },
    appointments0() {
      return this.appointmentsByDay(0);
    },
    /* weekly calendar stuff */
    userLocale() {
			return CalendarMath.getDefaultBrowserLocale
		},
		dayNames() {
			return CalendarMath.getFormattedWeekdayNames(this.userLocale, "long", 0)
		},
		themeClasses() {
			return {
				"theme-default": this.useDefaultTheme,
				"holiday-us-traditional": this.useHolidayTheme,
				"holiday-us-official": this.useHolidayTheme,
			}
		},
		myDateClasses() {
			// This was added to demonstrate the dateClasses prop. Note in particular that the
			// keys of the object are `yyyy-mm-dd` ISO date strings (not dates), and the values
			// for those keys are strings or string arrays. Keep in mind that your CSS to style these
			// may need to be fairly specific to make it override your theme's styles. See the
			// CSS at the bottom of this component to see how these are styled.
			const o = {}
			const theFirst = this.thisMonth(1)
			const ides = [2, 4, 6, 9].includes(theFirst.getMonth()) ? 15 : 13
			const idesDate = this.thisMonth(ides)
			o[CalendarMath.isoYearMonthDay(idesDate)] = "appointment"
			o[CalendarMath.isoYearMonthDay(this.thisMonth(21))] = ["exanimation", "over"]
			return o
		},
    /* weekly calendar stuff */
  },
  methods: {
      a(id){
          alert(id);
          //this.calendar.move({ month: 1, year: 2021 });
      },
      dayClicked(day) {
        this.selectedDay = new Date(day.date);
        console.log('selected day:',this.selectedDay);
      },
      startAppointment(attributeApp) {
        if (attributeApp.customData.typeForClass.includes('over'))
          alert("Appointemnt already held.");
        else if (attributeApp.customData.typeForClass.includes('free'))
          alert("Appointment not booked yet.");
        else
          window.location.href = "/appointment/" + attributeApp.customData.id;
      },
      startAppointmentFromWeekly(app){
        if (app.typeForClass.includes('over'))
          alert("Appointemnt already held.");
        else if (app.typeForClass.includes('free'))
          alert("Appointment not booked yet.");
        else
          window.location.href = "/appointment/" + app.id;
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
      },
      getNumberOfWeek(date) {
        const todayy = new Date(date);
        const firstDayOfYear = new Date(todayy.getFullYear(), 0, 1);
        const pastDaysOfYear = (todayy - firstDayOfYear) / 86400000;
        return Math.ceil((pastDaysOfYear + firstDayOfYear.getDay() + 1) / 7);
      },
      getDateOfISOWeek(w, y) {
        var simple = new Date(y, 0, 1 + (w - 1) * 7);
        var dow = simple.getDay();
        var ISOweekStart = simple;
        if (dow <= 4)
            ISOweekStart.setDate(simple.getDate() - simple.getDay() + 1);
        else
            ISOweekStart.setDate(simple.getDate() + 8 - simple.getDay());
        return ISOweekStart;
      },
      appointmentsByDay(dayInWeek){
        let mon = [];
        for (let a of this.appointments) {
          let date = new Date(Date.parse(a.startDate));
          let mm = this.getNumberOfWeek(Date.parse(a.startDate));
          let thisss = this.getNumberOfWeek(Date.now());
          if (date.getDay() == dayInWeek && date >= this.thisWeekStart && date < this.thisWeekEnd)
            mon.push(a);
        }
        return mon;
      },
      /* weekly calendar stuff */
      thisMonth(d, h, m) {
        const t = new Date()
        return new Date(t.getFullYear(), t.getMonth(), d, h || 0, m || 0)
      },
      onClickDay(d) {
        this.selectionStart = null
        this.selectionEnd = null
        console.log(`You clicked: ${d.toLocaleDateString()}`);
      },
      onClickItem(e) {
        console.log(`You clicked: ${e.id}`);
        window.location.href = "/appointment/" + e.id;
      },
      setShowDate(d) {
        this.message = `Changing calendar view to ${d.toLocaleDateString()}`
        this.showDate = d
      },
      // setSelection(dateRange) {
      //   this.selectionEnd = dateRange[1]
      //   this.selectionStart = dateRange[0]
      // },
      // finishSelection(dateRange) {
      //   this.setSelection(dateRange)
      //   this.message = `You selected: ${this.selectionStart.toLocaleDateString()} -${this.selectionEnd.toLocaleDateString()}`
      // },
      // onDrop(item, date) {
      //   this.message = `You dropped ${item.id} on ${date.toLocaleDateString()}`
      //   // Determine the delta between the old start date and the date chosen,
      //   // and apply that delta to both the start and end date to move the item.
      //   const eLength = CalendarMath.dayDiff(item.startDate, date)
      //   item.originalItem.startDate = CalendarMath.addDays(item.startDate, eLength)
      //   item.originalItem.endDate = CalendarMath.addDays(item.endDate, eLength)
      // },
      clickTestAddItem() {
        this.items.push({
          startDate: this.newItemStartDate,
          endDate: this.newItemEndDate,
          title: this.newItemTitle,
          id: "e" + Math.random().toString(36).substr(2, 10),
        })
        this.message = "You added a calendar item!"
      },
      /* weekly calendar stuff */
  }
};
</script>

<style lang="postcss" scoped>
.appointment {
    font-size: 12px;
    /* font-weight: bold; */
    padding: 0;
    margin: 0;
    overflow: hidden;
}

.appointmentWeek {
    font-size: 14px;
    /* font-weight: bold; */
    padding: 0;
    margin: 0;
    overflow: hidden;
}

.free {
    /* background-color: var(--free-bg);
    color: var(--free-txt); */
    background-color: #cecece;
    color: #6d6d6d;
}

.examination {
    /* background-color: var(--exam-bg);
    color: var(--exam-txt); */
    background-color: #0d6efd;
    color: #ffffff;
}

.cv-item.examination {
    /* background-color: var(--exam-bg);
    color: var(--exam-txt); */
    background-color: #0d6efd;
    color: #ffffff;
}

.counseling {
    /* background-color: var(--coun-bg);
    color: var(--coun-txt); */
    background-color: #ffa600;
    color: #fff0d0;
}

.over {
  /* background-color: var(--held-bg);
  color: var(--held-txt); */
  background-color: #eeeeee;
  color: #61a0ff;

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

.custom-calendar {
    border-width: 2px;
}

.custom-calendar.vc-container, .calendar-parent {
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
    border-radius: 0.5rem;
    border-width: 2px;
    height: 100%;
    /* width: 500px; */
  }

.calendar-parent {
	display: flex;
	flex-direction: column;
	flex-grow: 1;
	overflow-x: hidden;
	overflow-y: hidden;
	max-height: 450px;
	background-color: white;
  border: #CBD5E0 solid 2px;
  border-radius: 0.5rem;
}


/* For long calendars, ensure each week gets sufficient height. The body of the calendar will scroll if needed */
.cv-week {
	height: 100%;
  min-height: 30rem;
  overflow-x: hidden;
	overflow-y: hidden;
}

.cv-header .periodLabel, .cv-header-nav {
  padding: .4em .6em;
    font-size: var(--text-lg);
    color: var(--gray-800);
    font-weight: 600;
    line-height: 28px;
    cursor: pointer;
    -webkit-user-select: none;
    -ms-user-select: none;
    user-select: none;
    white-space: nowrap;
    text-align: center;
}

.cv-wrapper {
  min-height: 30rem;
}

div.cv-weekdays {
  overflow: hidden !important;
}

.cv-header-nav button {
  background-color: white;
}
/* These styles are optional, to illustrate the flexbility of styling the calendar purely with CSS. */
/* Add some styling for items tagged with the "birthday" class */
/* .cv-item.b {
	background-color: rgb(0, 162, 255);
	border-color: #73ff73;
}
.cv-item.birthday::before {
	content: "\1F382"; /* Birthday cake */
	/* margin-right: 10px;
} */

.cv-item {
  color: red;
  background-color: blue;
}

/* The following classes style the classes computed in myDateClasses and passed to the component's dateClasses prop. */
/* .cv-day.ides {
	background-color: blue;
  color: red;
} */
/* .ides .cv-day-number::before {
	content: "\271D";
}
.cv-day.do-you-remember.the-21st .cv-day-number::after {
	content: "\1F30D\1F32C\1F525";
} */
</style>