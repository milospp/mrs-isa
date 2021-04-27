import axios from "axios";


const API_URL = "http://localhost:8080/api/pharmacies";

class PharmacyDataService {
    getAllPharmacies() {
        return axios.get(`${API_URL}`);
    }
    getPharmacy(id) {
      return axios.get(`${API_URL}/` + id);
    }

    getAvailablePharmacies(){
        return axios.get(`${API_URL}/available`);
    }

    getDoctorsJobs(id){
        // returns pharmacy where the doctor work
        return axios.get(`${API_URL}/working/` + id);
    }

    SendPharmacy(newPharmacy) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/pharmacy`,
            data: newPharmacy
        }).then(response => {
			if (response.data) {
				alert("Pharmacy successfully added!");
				return true;
			}
			alert("This pharmacy already exists!");
			return false;
        });
    }
    getPharmacyByIDAdmin(idAdminaApoteke) {
      return axios.get(API_URL + "/admin/" + idAdminaApoteke);
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
    setPharmacy(apoteka) {
      return axios({
          method: 'post',
          url: API_URL + "/setPharmacyInfo",
          data: apoteka});
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

  ratePharmacy(pharmacy, rateObj) {
    return axios({
      method: 'post',
      url: `${API_URL}/` + pharmacy.id + `/rating`,
      data: rateObj
    });
  }

  getUserRating(patientId, pharmacyId) {
    return axios.get(`${API_URL}/` + pharmacyId + `/rating/user/` + patientId);
  }

  canUserRate(patientId, pharmacyId) {
    return axios.get(`${API_URL}/` + pharmacyId + `/rating/user/` + patientId + `/can-rate`);
  }
}


export default new PharmacyDataService();