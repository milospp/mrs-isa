import axios from "axios";


const API_URL = "http://localhost:8080/api/pharmacies";

class PharmacyDataService {
    getAllPharmacies() {
        return axios.get(`${API_URL}/all-pharmacist`);
    }
    getPharmacy(id) {
      return axios.get(`${API_URL}/` + id);
    }
    SendPharmacy(newPharmacy) {
        return axios({
            method: 'post',
            url: `${API_URL}/api/pharmacies/register/pharmacy`,
            data: newPharmacy
        }).then(response => {
            if (response.data == false) alert("This pharmacy already exists!");
        });
    }
}


export default new PharmacyDataService();