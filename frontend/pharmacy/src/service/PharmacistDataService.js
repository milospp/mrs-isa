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

  getAllPharmacistAdmin(idAdminaApoteke) {
    return axios.get(`${API_URL}/api/users/all-pharmacist/Admin/${idAdminaApoteke}`);
  }
  
  getAllPharmacistPharmacy(idApoteke) {
    return axios.get(`${API_URL}/api/users/all-pharmacist/Apoteka/${idApoteke}`);
  }
}

export default new PharmacistDataService();
