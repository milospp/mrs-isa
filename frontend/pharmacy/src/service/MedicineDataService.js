import axios from "axios";


const API_URL = "http://localhost:8080/api/medicines";

class MedicineDataService {
  getAllMedicines(id) {
    return axios.get(`${API_URL}`);
  }

  getMedicineForPharmacyAdmin(idAdminaApoteke) {
    return axios.get(API_URL + "/pharmacyAdmin/" + idAdminaApoteke)
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
  getMedicineForPharmacy(idApoteke) {
    return axios.get(API_URL + "/pharmacy/" + idApoteke)
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

  reserveMedicine(reserveData){
    return axios({
      method: 'post',
      url: `${API_URL}/` + reserveData.medicineId + `/pharmacy/` + reserveData.pharmacyId + `/reserve`,
      data: reserveData
    });
  }
}

export default new MedicineDataService();