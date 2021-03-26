import axios from "axios";


const API_URL = "http://localhost:8080/api/medicines";

class MedicineDataService {
  getAllMedicines(id) {
    return axios.get(`${API_URL}`);
  }

}

export default new MedicineDataService();