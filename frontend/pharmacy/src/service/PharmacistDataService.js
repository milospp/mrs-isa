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
    return axios.get(API_URL + "/api/users/pharmacists/admin/" + idAdminaApoteke)
    .catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("Error");
      console.log(error.config);
  });
  }
  
  getAllPharmacistPharmacy(idApoteke) {
    return axios.get(API_URL + "/api/users/pharmacists/pharmacy/" + idApoteke)
    .catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("Error");
      console.log(error.config);
  });
  }
}

export default new PharmacistDataService();
