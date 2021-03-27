import axios from "axios";


const API_URL = "http://localhost:8080";

class PatientDataService {
  retrieveAllPatients() {
    return axios.get(`${API_URL}/api/users/all-patients`);
  }

  getPatient(id){
    return axios.get(`${API_URL}/api/users/` + id);
  }

  getPatientAllergies(id){
    return axios.get(`${API_URL}/api/users/` + id + `/allergies`);
  }

  getPatientSubscriptions(id){
    return axios.get(`${API_URL}/api/users/` + id + `/subscriptions`);
  }

  unsubscribePatient(userId, pharmacyId){
    return axios.delete(`${API_URL}/api/users/` + userId + `/subscriptions/` + pharmacyId);
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
      alert("Email is not unique!");
      return false;
    });;
  }
}

export default new PatientDataService();
