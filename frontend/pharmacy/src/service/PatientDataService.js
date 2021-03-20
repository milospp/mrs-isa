import axios from "axios";


const API_URL = "http://localhost:8080";

class PatientDataService {
  retrieveAllPatients() {
    console.log('saljem zahtev');
    return axios.get(`${API_URL}/patients`);
  }
}

export default new PatientDataService();

console.log('napravila PatientDataService');
