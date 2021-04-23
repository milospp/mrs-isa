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
  var objekat = {"search" : search, "filterIme": filterIme, "filterPrez": filterIme, "filterBroj": filterBroj,
    "filterAdrD": filterAdrD, "filterAdrD": filterAdrG, "filterAdrU": filterAdrU, "filterAdrB": filterAdrB};
  return axios({
    method: 'post',
    url: API_URL + "/api/users/dermatologists/admin/" + idAdminaApoteke,
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
  var objekat = {"search" : search, "filterIme": filterIme, "filterPrez": filterIme, "filterBroj": filterBroj,
    "filterAdrD": filterAdrD, "filterAdrD": filterAdrG, "filterAdrU": filterAdrU, "filterAdrB": filterAdrB};
  return axios({
    method: 'post',
    url: API_URL + "/api/users/dermatologists/pharmacy/" + idApoteke,
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
      url: API_URL + "/api/users/edit/dermatologist",
      data: dermatologist
    });
  }
}

export default new DermatologistDataService();
