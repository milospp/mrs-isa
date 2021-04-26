import axios from "axios";


const API_URL = "http://localhost:8080/api/medicines";

class MedicineDataService {
  getAllMedicines() {
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

  editMedicinePharmacyAdmin(idAdminaApoteke, lek) {
    return axios({
      method: 'post',
      url: API_URL + "/edit/pharmacyAdmin/" + idAdminaApoteke,
      data: lek
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

  deleteMedicinePharmacyAdmin(idAdminaApoteke, kodLeka) {
    return axios.get(API_URL + "/delete/pharmacyAdmin/" + idAdminaApoteke + "/" + kodLeka)
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

  sendMedicine(newMedicine){
    return axios({
      method: 'post',
      url: `${API_URL}/newMedicine`,
      data: newMedicine
      }).then(response => {
        if (response.data == 1) alert("This medicine code is taken!");
		if (response.data == 0) alert("Medicine has been successfully added!");
    });
  }

  getReservation(code){
    return axios.get(`${API_URL}/resevation/` + code);
  }

  giveMedicineToPatient(code){
    return axios.get(`${API_URL}/reservation/dispense/` + code);
  }

  rateMedicine(medicine, rateObj) {
    return axios({
      method: 'post',
      url: `${API_URL}/` + medicine.id + `/rating`,
      data: rateObj
    });
  }

  getUserRating(patientId, medicineId) {
    return axios.get(`${API_URL}/` + medicineId + `/rating/user/` + patientId);
  }

}

export default new MedicineDataService();