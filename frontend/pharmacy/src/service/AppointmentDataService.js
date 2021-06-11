import axios from "axios";
import config from "@/config";
import { returnOrUpdate } from "ol/extent";

const API_URL = config.apiUrl + "/appointments"
// const API_URL = "api/appointments";

class AppointmentDataService {
  getAllAppointments(id) {
    return axios.get(`${API_URL}`);
  }

  getAllUpcomingAppointments(id) {
    return axios.get(`${API_URL}/patient-upcoming/` + id);
  }

  
  getAllUserPastAppointments(id) {
    return axios.get(`${API_URL}/patient-past/` + id);
  }

  searchAllUserPastAppointments(id, searchParams) {
    return axios({
      method: 'post',
      url: `${API_URL}/patient-past/` + id + `/search`,
      data: JSON.parse(searchParams)
    })
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

  bookCustomAppointment(datetime, doctorId, patientId, pharmacyId, price, durationInMins){
    return axios({
      method: 'post',
      url: `${API_URL}/book`,
      data: {
        "dateTime": datetime, 
        "doctorId": doctorId, 
        "patientId": patientId, 
        "pharmacyId": pharmacyId, 
        "price": price,
        "durationInMins": durationInMins
      }
    });
  }

  getAvailableEmployes(timeRequest) {
    return axios({
      method: 'post',
      url: `${API_URL}/free-derm`,
      data: timeRequest
    });
  }

  bookCounseling(appointmentRequest) {
    return axios({
      method: 'post',
      url: `${API_URL}/derm-examination`,
      data: appointmentRequest
    });
  }

  getDermAppFromPharmacy(dermId, pharmacyId) {
    return axios.get(`${API_URL}/` + `calendar/derm/`+dermId+`/pharmacy/`+pharmacyId);
  }

  getPharmAppForCalendar(pharmId) {
    return axios.get(`${API_URL}/` + `calendar/pharm/`+pharmId);
  }

  getDermAppFromPharmacyFree(dermId, pharmacyId) {
    return axios.get(`${API_URL}/` + `calendar/free-derm/`+dermId+`/pharmacy/`+pharmacyId);
  }

  getPharmAppForCalendarFree(pharmId) {
    return axios.get(`${API_URL}/` + `calendar/free-pharm/`+pharmId);
  }

  makeAppointmentPAdmin(pocetak, trajanje, cena, dermatolog, idApoteke) {
    //2021-05-28T16:32  
    pocetak = pocetak.split('T');
    var vreme = pocetak[0].split('-');
    var brojac = 0;
    var konacnoVreme = [];
    for (var i of vreme) { konacnoVreme[brojac] = parseInt(i); brojac++; }
    
    vreme = pocetak[1].split(':');
    for (var i of vreme) { konacnoVreme[brojac] = parseInt(i); brojac++; }

    var pregled = { "startTime": konacnoVreme, "durationInMins": trajanje, "price": cena,
      "doctor": dermatolog, "pharmacy": {"id" : idApoteke} }; 
    return axios({
      method: 'post',
      url: API_URL + "/add",
      data: pregled
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("error.config");
      console.log(error.config);
    });
  }

  getAppointmentApoteka(idApoteke) {
    return axios.get(API_URL + "/allForPharmacy/" + idApoteke)
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

  deleteAppointmentApoteka(pregled) {
    var brojac = 0;
    for (var i of pregled.startTime) { pregled.startTime[brojac] = parseInt(i); brojac++; }
    return axios({
      method: 'post',
      url: API_URL + "/delete",
      data: pregled
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("error.config");
      console.log(error.config);
    });
  }

  editAppointmentApoteka(pregled) {
    //2021-05-28T16:32  
    pregled.startTime = pregled.startTime.split('T');
    var vreme = pregled.startTime[0].split('-');
    var brojac = 0;
    var konacnoVreme = [];
    for (var i of vreme) { konacnoVreme[brojac] = parseInt(i); brojac++; }
    
    vreme = pregled.startTime[1].split(':');
    for (var i of vreme) { konacnoVreme[brojac] = parseInt(i); brojac++; }
    pregled.startTime = konacnoVreme;
    return axios({
      method: 'post',
      url: API_URL + "/edit",
      data: pregled
    }).catch(function (error) {
      if (error.response) {
        console.log(error.response.data);
      } else if (error.request) {
        console.log(error.request);
      }
      console.log("error.config");
      console.log(error.config);
    });
  }

}



export default new AppointmentDataService();