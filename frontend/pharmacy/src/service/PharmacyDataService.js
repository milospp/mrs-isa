import axios from "axios";


const API_URL = "http://localhost:8080";

class PharmacyDataService {
    getAllPharmacies() {
        return axios.get(`${API_URL}/api/pharmacies/all-pharmacist`);
    }
    getOnePharmacy(id){
        return axios.get(`${API_URL}/api/pharmacies/pharmacy-${id}`);
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