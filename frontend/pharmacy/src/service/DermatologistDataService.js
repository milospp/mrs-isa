import axios from "axios";

import config from "@/config";

const API_URL = config.apiUrl
// const API_URL = "api";

class DermatologistDataService {
  getAllDermatologistAdmin(idAdminaApoteke) {
    return axios.get(API_URL + "/users/dermatologists/admin/" + idAdminaApoteke);
  }

  getDermAdminHire(idAdminaApoteke) {
    return axios.get(API_URL + "/users/derm/hire/admin/" + idAdminaApoteke);
  }

  hireDermatologist(adminId, dermatolog, startTime, endTime) {
    var objekat = {"registerData": dermatolog, "startHour": startTime, "endHour": endTime}
    return axios({
      method: 'post',
      url: API_URL + "/users/dematologist/hire/" + adminId,
      data: objekat
    });
  }

  searchDermatologistAdmin(idAdminaApoteke, search) {
    return axios.get(API_URL + "/users/dermatologists/admin/" + idAdminaApoteke + "/" + search)
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

  filterDermatologistAdmin(idAdminaApoteke, search, filterIme, filterPrez, filterBroj, 
    filterAdrD, filterAdrG, filterAdrU, filterAdrB) {
  if (search.length == 0) search = "-";
  if (filterIme.length == 0) filterIme = "-";
  if (filterPrez.length == 0) filterPrez = "-";
  if (filterBroj.length == 0) filterBroj = "-";
  if (filterAdrD.length == 0) filterAdrD = "-";
  if (filterAdrG.length == 0) filterAdrG = "-";
  if (filterAdrU.length == 0) filterAdrU = "-";
  if (filterAdrB.length == 0) filterAdrB = "-";
  var objekat = {"search" : search, "filterIme": filterIme, "filterPrez": filterPrez, "filterBroj": filterBroj,
    "filterAdrD": filterAdrD, "filterAdrG": filterAdrG, "filterAdrU": filterAdrU, "filterAdrB": filterAdrB};
  return axios({
    method: 'post',
    url: API_URL + "/users/derm/filter/admin/" + idAdminaApoteke,
    data: objekat
  }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("Error");
      console.log(error.config);
  });
  }

  filterDermatologistPharmacy(idApoteke, search, filterIme, filterPrez, filterBroj, 
    filterAdrD, filterAdrG, filterAdrU, filterAdrB) {
  if (search.length == 0) search = "-";
  if (filterIme.length == 0) filterIme = "-";
  if (filterPrez.length == 0) filterPrez = "-";
  if (filterBroj.length == 0) filterBroj = "-";
  if (filterAdrD.length == 0) filterAdrD = "-";
  if (filterAdrG.length == 0) filterAdrG = "-";
  if (filterAdrU.length == 0) filterAdrU = "-";
  if (filterAdrB.length == 0) filterAdrB = "-";
  var objekat = {"search" : search, "filterIme": filterIme, "filterPrez": filterPrez, "filterBroj": filterBroj,
    "filterAdrD": filterAdrD, "filterAdrG": filterAdrG, "filterAdrU": filterAdrU, "filterAdrB": filterAdrB};
  return axios({
    method: 'post',
    url: API_URL + "/users/derm/filter/pharmacy/" + idApoteke,
    data: objekat
  }).catch(function (error) {
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
    return axios.get(API_URL + "/users/dermatologists/pharmacy/" + idApoteke);
  }

  fireDermatologist(adminId, dematolog) {
    return axios({
      method: 'post',
      url: API_URL + "/users/dermatologist/fire/" + adminId,
      data: dematolog
    }).catch(function (error) {
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
    return axios.get(API_URL + "/users/dermatologists/pharmacy/" + idApoteke + "/" + search)
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
		if (response.data) {
			alert("Dermatologist has been successfully added!");
			return true;
		  }
		  alert("This e-mail is already taken!");
		  return false;
    });
  }
  editPersonalData(dermatologist){
    return axios({
      method: 'post',
      url: API_URL + "/users/edit/dermatologist",
      data: dermatologist
    });
  }
}

export default new DermatologistDataService();
