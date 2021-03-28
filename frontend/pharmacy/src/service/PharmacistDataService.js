import axios from "axios";


const API_URL = "http://localhost:8080";

class PharmacistDataService {
  SendPharmacist(promenljiva) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/register/pharmacist`,
      data: promenljiva
    }).then(response => {
      alert(response.data);
      if (response.data == 0) return true; 
      alert("Email is not unique!");
      return false;
    });
  }

  getAllPharmacistAdmin(idAdminaApoteke) {
    alert( `${API_URL}/api/users/pharmacists/admin` + `${idAdminaApoteke}` + idAdminaApoteke);
    return axios({
      method: 'get',
      url: `${API_URL}/api/users/pharmacists/admin`,
      data: `${idAdminaApoteke}`    //idAdminaApoteke
    });
    // return axios.get(`${API_URL}/api/users/pharmacists/admin/${idAdminaApoteke}`);
    // return axios.get(`${API_URL}/api/users/pharmacists/admin/` + {idAdminaApoteke});
  }
  
  getAllPharmacistPharmacy(idApoteke) {
    return axios.get(`${API_URL}/api/users/all-pharmacists/apoteka/${idApoteke}`);
  }
}

export default new PharmacistDataService();
