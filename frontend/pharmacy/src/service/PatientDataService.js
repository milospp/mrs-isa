import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl;
// const API_URL = "api";

class PatientDataService {
  retrieveAllPatients(refreshData) {
    return axios({
      method: 'post',
      url: `${API_URL}/users/patients`,
      data: refreshData
    });
    //return axios.get(`${API_URL}/users/patients?pageNo=`+ pageNo +`&pageSize=`+ pageSize +`&sortBy=`+ sortBy +`&doctorId=`+ doctorId + `&asc=` + asc);
  }

  searchPatients(name, surname) {
    return axios.get(`${API_URL}/users/all-patients/search?name=` + name + `&surname=` + surname);
  }

  getPatient(id){
    return axios.get(`${API_URL}/users/patient/` + id);
  }

  getPatientAllergies(id){
    return axios.get(`${API_URL}/users/` + id + `/allergies`);
  }

  addPatientAllergy(medicine){
    return axios({
      method: 'post',
      url: `${API_URL}/users/id/allergies`,
      data: medicine
    });
  }


  deletePatientAllergy(medicine){
    return axios({
      method: 'delete',
      url: `${API_URL}/users/id/allergies`,
      data: medicine
    });
  }

  getPatientSubscriptions(id){
    return axios.get(`${API_URL}/users/` + id + `/subscriptions`);
  }

  unsubscribePatient(userId, pharmacyId){
    return axios.delete(`${API_URL}/users/` + userId + `/subscriptions/` + pharmacyId);
  }

  getPatientPenalities(id){
    return axios.get(`${API_URL}/users/` + id + `/penalties`);
  }
  
  getPatientPenalitiesCount(id){
    return axios.get(`${API_URL}/users/` + id + `/penalties/count`);
  }
  
  updatePatientInfo(patient) {
    return axios({
      method: 'post',
      url: `${API_URL}/users/` + patient.id + `/update`,
      data: patient
    });
  }

  SendPatient(newPatient) {
    return axios({
      method: 'post',
      url: `${API_URL}/users/register/patient`,
      data: newPatient
    });
  }

  getPatientReservations(id) {
    return axios.get(`${API_URL}/users/` + id + `/reservations`);
  }

  
  getPatientReservationsFilter(id,filter) {
    return axios({
      method: 'post',
      url: `${API_URL}/users/` + id + `/reservations/search`,
      data: JSON.parse(filter)
    });
  }

  cancelReservation(id) {
    return axios.put(`${API_URL}/users/reservations/` + id + `/cancel`);
  }

  rateDoctor(doctor, rateObj) {
    return axios({
      method: 'post',
      url: `${API_URL}/users/doctor/` + doctor.id + `/rating`,
      data: rateObj
    });
  }

  getUserRating(patient, doctor) {
    return axios.get(`${API_URL}/users/doctor/` + doctor.id + `/rating/user/` + patient.id);
  }

  activateUser(token){
    return axios.get(`${API_URL}/users/activatePatient?token=`+token);
  }

}


export default new PatientDataService();
