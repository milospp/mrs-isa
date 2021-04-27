import axios from "axios";


const API_URL = "http://localhost:8080";

class PatientDataService {
  retrieveAllPatients(refreshData) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/patients`,
      data: refreshData
    });
    //return axios.get(`${API_URL}/api/users/patients?pageNo=`+ pageNo +`&pageSize=`+ pageSize +`&sortBy=`+ sortBy +`&doctorId=`+ doctorId + `&asc=` + asc);
  }

  searchPatients(name, surname) {
    return axios.get(`${API_URL}/api/users/all-patients/search?name=` + name + `&surname=` + surname);
  }

  getPatient(id){
    return axios.get(`${API_URL}/api/users/patient/` + id);
  }

  getPatientAllergies(id){
    return axios.get(`${API_URL}/api/users/` + id + `/allergies`);
  }

  addPatientAllergy(medicine){
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/id/allergies`,
      data: medicine
    });
  }


  deletePatientAllergy(medicine){
    return axios({
      method: 'delete',
      url: `${API_URL}/api/users/id/allergies`,
      data: medicine
    });
  }

  getPatientSubscriptions(id){
    return axios.get(`${API_URL}/api/users/` + id + `/subscriptions`);
  }

  unsubscribePatient(userId, pharmacyId){
    return axios.delete(`${API_URL}/api/users/` + userId + `/subscriptions/` + pharmacyId);
  }

  getPatientPenalities(id){
    return axios.get(`${API_URL}/api/users/` + id + `/penalties`);
  }
  
  getPatientPenalitiesCount(id){
    return axios.get(`${API_URL}/api/users/` + id + `/penalties/count`);
  }
  
  updatePatientInfo(patient) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/` + patient.id + `/update`,
      data: patient
    });
  }

  SendPatient(newPatient) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/register/patient`,
      data: newPatient
    }).then(response => {
      if (response.data) {
        alert("Successfully added patient!");
        return true;
      }
      alert("This e-mail is already taken!");
      return false;
    });
  }

  getPatientReservations(id) {
    return axios.get(`${API_URL}/api/users/` + id + `/reservations`);
  }

  cancelReservation(id) {
    return axios.put(`${API_URL}/api/users/reservations/` + id + `/cancel`);
  }

  rateDoctor(doctor, rateObj) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/users/doctor/` + doctor.id + `/rating`,
      data: rateObj
    });
  }

  getUserRating(patient, doctor) {
    return axios.get(`${API_URL}/api/users/doctor/` + doctor.id + `/rating/user/` + patient.id);
  }

}


export default new PatientDataService();
