import axios from "axios";


const API_URL = "http://localhost:8080";

class PharmacistDataService {
  SendPharmacist(idAdmina, promenljiva) {
    return axios({
      method: 'post',
      url: API_URL + "/api/users/register/pharmacist/" + idAdmina,
      data: promenljiva
    }).then(response => {
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

  
  searchPharmacistAdmin(idAdminaApoteke, search) {
    return axios.get(API_URL + "/api/users/pharmacists/admin/" + idAdminaApoteke + "/" + search)
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

  filterPharmacistAdmin(idAdminaApoteke, filterIme, filterPrez, filterBroj, 
      filterAdrD, filterAdrG, filterAdrU, filterAdrB) {
    if (filterIme.length == 0) filterIme = "nema";
    if (filterPrez.length == 0) filterPrez = "nema";
    if (filterBroj.length == 0) filterBroj = "nema";
    if (filterAdrD.length == 0) filterAdrD = "nema";
    if (filterAdrG.length == 0) filterAdrG = "nema";
    if (filterAdrU.length == 0) filterAdrU = "nema";
    if (filterAdrB.length == 0) filterAdrB = "nema";
    return axios.get(API_URL + "/api/users/pharmacists/admin/" + idAdminaApoteke + "/" + filterIme + "/" + filterPrez
        + "/" + filterBroj + "/" + filterAdrD + "/" + filterAdrG + "/" + filterAdrU + "/" + filterAdrB)
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
  
  searchPharmacistPharmacy(idApoteke, search) {
    return axios.get(API_URL + "/api/users/pharmacists/pharmacy/" + idApoteke + "/" + search)
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

  filterPharmacistPharmacy(idApoteke, filterIme, filterPrez, filterBroj, 
    filterAdrD, filterAdrG, filterAdrU, filterAdrB) {
    if (filterIme.length == 0) filterIme = "nema";
    if (filterPrez.length == 0) filterPrez = "nema";
    if (filterBroj.length == 0) filterBroj = "nema";
    if (filterAdrD.length == 0) filterAdrD = "nema";
    if (filterAdrG.length == 0) filterAdrG = "nema";
    if (filterAdrU.length == 0) filterAdrU = "nema";
    if (filterAdrB.length == 0) filterAdrB = "nema";
    return axios.get(API_URL + "/api/users/pharmacists/pharmacy/" + idApoteke + "/" + filterIme + "/" + filterPrez
        + "/" + filterBroj + "/" + filterAdrD + "/" + filterAdrG + "/" + filterAdrU + "/" + filterAdrB)
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

  getPharmacist(id){
    return axios.get(`${API_URL}/api/users/` + id);
  }
}

export default new PharmacistDataService();
