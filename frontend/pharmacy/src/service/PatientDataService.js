import axios from "axios";


const API_URL = "http://localhost:8080";

class PatientDataService {
  retrieveAllPatients() {
    return axios.get(`${API_URL}/api/patients/all-patients`);
  }
  
  SendPatient(newPatient) {
    return axios({
      method: 'post',
      url: `${API_URL}/api/patients/register/patient`,
      data: newPatient
    });
  }
}

export default new PatientDataService();
