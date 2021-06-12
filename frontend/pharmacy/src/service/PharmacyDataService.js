import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl + "/pharmacies"
// const API_URL = "api/pharmacies";

class PharmacyDataService {
    getAllPharmacies() {
        return axios.get(`${API_URL}`);
    }
    searchPharmacies(searchParams) {
      console.log(searchParams);

      return axios({
        method: 'post',
        url: `${API_URL}/search`,
        data: JSON.parse(searchParams)
      })
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

  inquiriesPharmacy(idApoteke) {
    return axios.get(API_URL + "/inquiries/" + idApoteke)
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

  savePricelist(idAdminaApoteke, cenovnik) {
    return axios({
      method: 'post',
      url: API_URL + "/savePricelist/" + idAdminaApoteke,
      data: cenovnik
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

  getVisitedPharmacies(patientId){
    return axios.get(`${API_URL}/visitedPharmacies/`+patientId);
  }

  subscribeUnsubscribeToPharmacy(pharmacy, id){
    return axios({
      method: 'post',
      url: `${API_URL}/subscribeUnsubscribe/`+id ,
      data: pharmacy
    });
  }

  getSubscriptions(id){
    return axios.get(`${API_URL}/subscriptions/`+id);
  }
}


export default new PharmacyDataService();