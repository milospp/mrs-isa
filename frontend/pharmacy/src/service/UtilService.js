import axios from "axios";



class UtilService {
  AddressToString(address) {
    return  address.state + ", " + address.city + ", " + address.street + " " + address.number;
  }

  test() {
    return "AAAA";
  }

}

export default new UtilService();
