import axios from "axios";


const API_URL = "http://localhost:8080/api/appointments";

class MedicineDataService {
  getAllAppointments(id) {
    return axios.get(`${API_URL}`);
  }

  getAllUpcomingAppointments(id) {
    return axios.get(`${API_URL}/patient-upcoming/` + id);
  }

  
  getAllUserPastAppointments(id) {
    return axios.get(`${API_URL}/patient-past/` + id);
  }

  getFreeDermAppointments(id) {
    return axios.get(`${API_URL}/dermatologist/free`);
  }

  cancelAppointment(id){
    return;
  }

}

export default new MedicineDataService();