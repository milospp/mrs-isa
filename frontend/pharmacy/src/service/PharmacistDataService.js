import axios from "axios";


const API_URL = "http://localhost:8080";

class PharmacistDataService {
  retrieveAllPharmacists() {
    return axios.get(`${API_URL}/all-pharmacist`);
  }
  SendPharmacist(promenljiva) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/register/pharmacist`,
      data: promenljiva
    }).then(response => {
      if (response.data == 0) {
        alert("Successfully added pharmacist!");
        return true;
      }
      alert("Email is not unique!");
      return false;
    });
  }
}

export default new PharmacistDataService();
