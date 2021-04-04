import axios from "axios";
import moment from "moment";


class UtilService {
  AddressToString(address) {
    return address.street + " " + address.number + ", " + address.city + ", " + address.state;
  }

  FormatTime(time) {
    return time.split(":")[0] + ":" + time.split(":")[1];
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
    if (date instanceof moment) return date.format('DD/MM/YYYY');
    return moment(date).format('DD/MM/YYYY');
  }

  formatDateTime(date) {
    
    return moment(date).format('DD/MM/YYYY hh:mm');
  }

  getFirstInNextMounth(date){
    if (!(date instanceof moment)) date = moment(date);

    return date.add(1, 'M').set('date', 1);
  }

  isPastDate(date){
    return moment(date).isBefore();
  }

}

export default new UtilService();
