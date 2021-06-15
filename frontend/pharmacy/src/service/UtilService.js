import axios from "axios";
import moment from "moment";


class UtilService {
  AddressToString(address) {
    return address.street + " " + address.number + ", " + address.city + ", " + address.state;
  }

  FormatTime(time) {
    time = time + '';
    return time.split(",")[0] + ":" + time.split(",")[1];
  }

  test() {
    return "AAAA";
  }

  formatAddDate(date, val, type){
    return moment(date).add(val, type).format('DD/MM/YYYY');

  }

  addDate(date, val, type){
    if (!(date instanceof moment)) date = moment(date);

    return date.add(val, type).format();

  }

  formatDate(date) {
    //date = date[0] + "-" + date[1] + "-" + date[2];
    if (date instanceof moment) return date.format('DD/MM/YYYY');
    return moment(date).format('DD/MM/YYYY');
  }

  formatDateTime(date) {
    //date = date[0] + "-" + date[1] + "-" + date[2] + " "  + date[3] + ":"  + date[2];

    return moment(date).format('DD/MM/YYYY HH:mm');
  }

  getFirstInNextMounth(date){
    if (!(date instanceof moment)) date = moment(date);

    return date.add(1, 'M').set('date', 1);
  }

  isPastDate(date){
    return moment(date).isBefore();
  }

  lessThan24HoursLeft(date){
    return moment(date) < moment().subtract(1, 'd');
  }

  formatReservationStatus(status){
    if (status == 0) return "Pending";
    else if (status == 1) return "Taken";
    else if (status == 2) return "Canceled";
    else return "Expired";
  }

  formatRatingBracket(rating) {
    if (rating < 1 || rating > 5) return "";
    let rounded = Math.round(rating*10)/10;

    return " ( " + rounded + " / 5 )";
  }

  isTimeForAppointment(appointmentDate) {
    appointmentDate = appointmentDate + '';
    //durationInMins = durationInMins + '';
    console.log(appointmentDate);
    //console.log(durationInMins);
    if (moment() > moment(appointmentDate).subtract(20,'m') && moment() < moment(appointmentDate).add('1','d')){//.add(durationInMins,'m')
      return true;}
    else return false;
  }

}

export default new UtilService();
