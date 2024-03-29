import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl + "/medicines"
// const API_URL = "api/medicines";

class MedicineDataService {
  getAllMedicines() {
    return axios.get(`${API_URL}`);
  }
  searchMedicines(searchParams) {
    console.log(searchParams);

    return axios({
      method: 'post',
      url: `${API_URL}/search`,
      data: JSON.parse(searchParams)
    })
  }

  getMedicineForPharmacyAdmin(idAdminaApoteke) {
    return axios.get(API_URL + "/pharmacyAdmin/" + idAdminaApoteke);
  }

  getPricelistForPharmacyAdmin(idAdminaApoteke) {
    return axios.get(API_URL + "/pricelist/" + idAdminaApoteke);
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

  makeActionPromotion(idAdminaApoteke, lek, procenat, novaCena, datum) {
    lek.oldPrice = lek.currentPrice;
    lek.currentPrice = novaCena;
    datum = datum.split("-");
    var konacan_datum = []
    var ind = 0;
    for (var p of datum) {konacan_datum[ind] = parseInt(p); ind++;}
    konacan_datum[3] = 0; konacan_datum[4] = 0;
    lek.endDate = konacan_datum;
    var jesteAkcija = true;
    if (procenat == 0) jesteAkcija = false;
    return axios({
      method: 'post',
      url: API_URL + "/makeActionPromotion/"+ idAdminaApoteke + "/" + jesteAkcija,
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

  deleteActionPromotion(idAdminaApoteke, kodLeka) {
    return axios.get(API_URL + "/deleteActionPromotion/" + idAdminaApoteke + "/" + kodLeka)
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

  addMedicinePharmacyAdmin(idAdminaApoteke, lek) {
    return axios({
      method: 'post',
      url: API_URL + "/add/pharmacyAdmin/" + idAdminaApoteke,
      data: lek
    });
  }

  getMedicineForPharmacy(idApoteke) {
    return axios.get(API_URL + "/pharmacy/" + idApoteke);
  }
  getAllMedicineForPharmacy(idApoteke) {
    return axios.get(API_URL + "/all/pharmacy/" + idApoteke);
  }

  
  getMedicineInPharmacies(medicineId) {
    return axios.get(API_URL + "/" + medicineId + "/pharmacies/");
  }

  reserveMedicine(reserveData, idDoktora){
    return axios({
      method: 'post',
      url: `${API_URL}/` + reserveData.medicineId + `/pharmacy/` + reserveData.pharmacyId + `/reserve/` + idDoktora,
      data: reserveData
    });
  }

  reserveMedicineAsPatient(reserveData){
    return axios({
      method: 'post',
      url: `${API_URL}/` + reserveData.medicineId + `/pharmacy/` + reserveData.pharmacyId + `/reserve/`,
      data: reserveData
    });
  }

  sendMedicine(newMedicine){
    return axios({
      method: 'post',
      url: `${API_URL}/newMedicine`,
      data: newMedicine
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

  getPricesOfMedicine(medicine){
    return axios.get(`${API_URL}/prices/` + medicine.id);
  }

  getMedicinesInStock(){
    return axios.get(`${API_URL}/medicinesInStock`);
  }

  getSuppliersMedicines(supplier_id){
    return axios.get(`${API_URL}/suppliersMedicines/`+supplier_id);
  }

  updateSuppliersMedicine(medicine){
    return axios({
      method: 'post',
      url: `${API_URL}/updateSuppliersMedicine`,
      data: medicine
    });
  }

  removeMedicineOfSupplier(medicine){
    return axios({
      method: 'post',
      url: `${API_URL}/removeSuppliersMedicine`,
      data: medicine
    });
  }

  addMedicineToSupplier(medicine, id){
    return axios({
      method: 'post',
      url: `${API_URL}/addSuppliersMedicine/`+id,
      data: medicine
    });
  }

  getPatientsPurchases(id){
    return axios.get(`${API_URL}/patientsPurchases/`+id);
  }

  eReserve(ePrescription){
    return axios({
      method: 'post',
      url: `${API_URL}/reserveFromEPrescription`,
      data: ePrescription
    });
  }
}

export default new MedicineDataService();