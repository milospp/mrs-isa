import axios from "axios";


const API_URL = "http://localhost:8080/api/users";

class PharmacyAdminDataService {
    SendPharmacyAdmin(newAdmin) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/pharmacyAdmin`,
            data: newAdmin
        }).then(response => {
            if (response.data == false) alert("This e-mail is taken!");
        });
    }
}


export default new PharmacyAdminDataService();