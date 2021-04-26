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

  bookAppointment(appointmentId, userId) {
    return axios({
      method: 'post',
      url: `${API_URL}/` + appointmentId + `/book`,
      data: {"id": userId}
    })
  }

  cancelAppointment(id){
    return axios.post(`${API_URL}/` + id + `/cancel`);
  }

  getAppointmentInfo(id){
    return axios.get(`${API_URL}/` + id);
  }

  postAppointmentInfo(appointment){
    return axios({
      method: 'post',
      url: `${API_URL}/` + appointment.id + `/done`,
      data: appointment
    });
  }

}

export default new MedicineDataService();