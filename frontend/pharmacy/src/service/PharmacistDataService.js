import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl;
// const API_URL = "api";

class PharmacistDataService {
  SendPharmacist(idAdmina, promenljiva) {
    return axios({
      method: 'post',
      url: API_URL + "/users/register/pharmacist/" + idAdmina,
      data: promenljiva
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("error.config");
      console.log(error.config);
    });
  }
  
  getAllPharmacistAdmin(idAdminaApoteke) {
    return axios.get(API_URL + "/users/pharmacists/admin/" + idAdminaApoteke);
  }

  firePharmacist(adminId, farmaceut) {
    return axios({
      method: 'post',
      url: API_URL + "/users/pharmacist/fire/" + adminId,
      data: farmaceut
    });
  }
  
  searchPharmacistAdmin(idAdminaApoteke, search) {
    return axios.get(API_URL + "/users/pharmacists/admin/" + idAdminaApoteke + "/" + search)
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

  filterPharmacistAdmin(idAdminaApoteke, search, filterIme, filterPrez, filterBroj, 
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
      url: API_URL + "/users/pharm/filter/admin/" + idAdminaApoteke,
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
  
  getAllPharmacistPharmacy(idApoteke) {
    return axios.get(API_URL + "/users/pharmacists/pharmacy/" + idApoteke);
    // .catch(function (error) {
    //   if (error.response) {
    //     console.log(error.response.data);
    //   } else if (error.request) {
    //     console.log(error.request);
    //   }
    //   console.log("Error");
    //   console.log(error.config);
    // });
  }
  
  searchPharmacistPharmacy(idApoteke, search) {
    return axios.get(API_URL + "/users/pharmacists/pharmacy/" + idApoteke + "/" + search)
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

  filterPharmacistPharmacy(idApoteke, search, filterIme, filterPrez, filterBroj, 
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
    url: API_URL + "/users/pharm/filter/pharmacy/" + idApoteke,
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

getAllPharmacists() {
  return axios.get(API_URL + "/users/allPharmacists");
}

searchAllPharmacists(search) {
  return axios.get(API_URL + "/users/pharmacists/search/" + search)
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

filterAllPharmacists(search, filterIme, filterPrez, filterBroj, 
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
  url: API_URL + "/users/pharmacists/filter",
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

  getPharmacist(id){
    return axios.get(`${API_URL}/users/` + id);
  }

  editPersonalData(pharmacist){
    return axios({
      method: 'post',
      url: API_URL + "/users/edit/pharmacist",
      data: pharmacist
    });
  }

  getVisitedPharmacists(id){
    return axios.get(`${API_URL}/users/visitedDoctors/` + id);
  }
}

export default new PharmacistDataService();
