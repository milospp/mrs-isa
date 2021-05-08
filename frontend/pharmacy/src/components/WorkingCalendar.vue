<template>
  <div class="text-center section">
    <h2 class="h2 mb-3">Monthly Calendar</h2>
    <!-- <p class="text-lg font-medium text-gray-600 mb-6">
      Monthly appointments and examinations
    </p> -->
    <Calendar
      class="custom-calendar"
      ref="calendar"
      :masks="masks"
      :attributes="attributes"
      disable-page-swipe
      is-expanded
    >
      <template v-slot:day-content="{ day, attributes }">
        <div class="flex flex-col z-10 overflow-hidden whole-day"> <!--whole day-->
          <span class="day-label text-sm text-gray-900">{{ day.day }}</span> <!--number of a day-->
          <div class="flex-grow overflow-y-auto overflow-x-auto day"> <!--all content of a day-->
            <p
              v-for="attr in attributes"
              :key="attr.key"
              class="rounded p-1 mt-0 mb-0 mx-1 appointment"
              :class="attr.customData.class"
              v-on:click="a(attr.key)"
            >
              {{ attr.customData.title }}
            </p> <!--appointment-->
          </div>
        </div>
      </template>
    </Calendar>
  </div>
</template>

<script>
export default {
  data() {
    const month = new Date().getMonth();
    const year = new Date().getFullYear();
    return {
      // my
      currentMonth: month,
      calendarRef: null,

      //
      masks: {
        weekdays: 'WWWW',
      },
      attributes: [
        {
          key: 1,
          customData: {
            title: 'Lunch with mom.',
            class: 'counseling',
          },
          dates: new Date(year, month, 1),
        },
        {
          key: 2,
          customData: {
            title: 'Take Noah to basketball practice',
            class: 'free',
          },
          dates: new Date(year, month, 2),
        },
        {
          key: 3,
          customData: {
            title: "Noah's basketball game.",
            class: 'free',
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 4,
          customData: {
            title: 'Take car to the shop',
            class: 'counseling',
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 10,
          customData: {
            title: 'New Appointment',
            class: 'examination',
          },
          dates: new Date(year, month, 5),
        },
        {
          key: 5,
          customData: {
            title: 'E Petar Petrovic 11:00-12:00',
            class: 'examination',
          },
          dates: new Date(year, month, 7),
        },
        {
          key: 6,
          customData: {
            title: "Mia's gymnastics practice.",
            class: 'examination',
          },
          dates: new Date(year, month, 11),
        },
        {
          key: 7,
          customData: {
            title: 'Cookout with friends.',
            class: 'examination',
          },
          dates: { months: 5, ordinalWeekdays: { 2: 1 } },
        },
        {
          key: 8,
          customData: {
            title: "Mia's gymnastics recital.",
            class: 'free',
          },
          dates: new Date(year, month, 22),
        },
        {
          key: 9,
          customData: {
            title: 'Visit great grandma.',
            class: 'free',
          },
          dates: new Date(year, month, 25),
        },
      ],
    };
  },
  mounted(){
      this.calendarRef = this.$refs.calendar;
      this.currentMonth = this.calendarRef.month;
  },
  methods: {
      a(id){
          alert(id);
      }
  }
};
</script>

<style lang="postcss" scoped>
.appointment{
    font-size: 12px;
    font-weight: bold;
    padding: 0;
    margin: 0;
}

.free{
    background-color: var(--free-bg);
    color: var(--free-txt);
}

.examination{
    background-color: var(--exam-bg);
    color: var(--exam-txt);
}

.counseling{
    background-color: var(--coun-bg);
    color: var(--coun-txt);
}

.day {
    /* border: #000000 3px solid; */
    height: 100%;
    width: 100%;
    border-bottom: var(--blue-border);
}

.whole-day {
    height: 120px;
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
  --free-txt: #000000;
  --exam-txt: #b1a3ff;
  --coun-txt: #fff0d0;
  --free-bg: #7f88b1;
  --exam-bg: #4625ff;
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