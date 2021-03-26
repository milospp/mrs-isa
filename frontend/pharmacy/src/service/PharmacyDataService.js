import axios from "axios";


const API_URL = "http://localhost:8080/api/pharmacies";

class PharmacyDataService {
  getPharmacy(id) {
    return axios.get(`${API_URL}/` + id);
  }

}

export default new PharmacyDataService();
