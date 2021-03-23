import axios from "axios";


const API_URL = "http://localhost:8080";

class PharmacistDataService {
  retrieveAllPharmacists() {
    return axios.get(`${API_URL}/all-pharmacist`);
  }
  checkUsername(us) {
    alert(us);
    return axios.get(`${API_URL}/api/users/is-available-username/${us}`);
  }
  checkEmail(em) {
    alert(em);
    alert(`${API_URL}/api/users/is-available-email/${em}`);
    return axios.get(`${API_URL}/api/users/is-available-email/${em}`);
  }
  SendPharmacist(promenljiva) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/register/pharmacist`,
      data: promenljiva
    }).then(response => {
      if (response.data == 0) return true; 
      else if (response.data == 1) alert("Username is not unique!");
      else if (response.data == 2) alert("Email is not unique!");
      else alert("Username and email are not unique!");
      return false;
    });
  }
}

export default new PharmacistDataService();
