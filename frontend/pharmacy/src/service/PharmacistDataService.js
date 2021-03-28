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
      alert(response.data);
      if (response.data == 0) return true; 
      else if (response.data == 1) alert("Username is not unique!");
      else if (response.data == 2) alert("Email is not unique!");
      else alert("Username or email is not unique!");
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
