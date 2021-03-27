import axios from "axios";
import moment from "moment";


class UtilService {
  AddressToString(address) {
    return  address.state + ", " + address.city + ", " + address.street + " " + address.number;
  }

  test() {
    return "AAAA";
  }

  formatAddDate(date, val, type){
    return moment(date).add(val, type).format('DD/MM/YYYY');

  }

  addDate(date, val, type){
    return moment(date).add(val, type).format();

  }

  formatDate(date) {
    return moment(date).format('DD/MM/YYYY');
  }

  isPastDate(date){
    console.log(date)
    return moment(date).isBefore();
  }

}

export default new UtilService();
