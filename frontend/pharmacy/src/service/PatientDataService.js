import axios from "axios";


const API_URL = "http://localhost:8080";

class PatientDataService {
  retrieveAllPatients() {
    return axios.get(`${API_URL}/all-patients`);
  }
}

export default new PatientDataService();
