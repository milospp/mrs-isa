import axios from "axios";


const API_URL = "http://localhost:8080";

class DermatologistDataService {
  getAllDermatologistAdmin(idAdminaApoteke) {
    return axios.get(API_URL + "/api/users/dermatologists/admin/" + idAdminaApoteke)
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

  searchDermatologistAdmin(idAdminaApoteke, search) {
    return axios.get(API_URL + "/api/users/dermatologists/admin/" + idAdminaApoteke + "/" + search)
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
  
  getAllDermatologistsPharmacy(idApoteke) {
    return axios.get(API_URL + "/api/users/dermatologists/pharmacy/" + idApoteke)
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

  searchDermatologistPharmacy(idApoteke, search) {
    return axios.get(API_URL + "/api/users/dermatologists/pharmacy/" + idApoteke + "/" + search)
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

  getDermatologist(id){
    return axios.get(`${API_URL}/api/users/` + id);
  }

  SendDermatologist(newDermatologist) {
    return axios({
        method: 'post',
        url: `${API_URL}/api/users/register/dermatologist`,
        data: newDermatologist
    }).then(response => {
        if (response.data == false) alert("This e-mail is taken!");
    });
}
}

export default new DermatologistDataService();
