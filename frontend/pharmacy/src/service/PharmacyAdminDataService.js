import axios from "axios";


const API_URL = "http://localhost:8080/api/users";

class PharmacyAdminDataService {
    retrieveAllPharmacists() {
        return axios.get(`${API_URL}/all-pharmacist`);
    }
    SendPharmacy(newPharmacy) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/pharmacy`,
            data: newPharmacy
        }).then(response => {
            if (response.data == false) alert("This pharmacy already exists!");
        });
    }
}


export default new PharmacyDataService();