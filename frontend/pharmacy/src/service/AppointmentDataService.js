import axios from "axios";


const API_URL = "http://localhost:8080/api/appointments";

class MedicineDataService {
  getAllAppointments(id) {
    return axios.get(`${API_URL}`);
  }

  getFreeDermAppointments(id) {
    return axios.get(`${API_URL}/dermatologist/free`);
  }

}

export default new MedicineDataService();