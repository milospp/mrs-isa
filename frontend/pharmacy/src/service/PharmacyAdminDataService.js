import axios from "axios";


const API_URL = "api";

class PharmacyAdminDataService {
    SendPharmacyAdmin(newAdmin) {
        return axios({
            method: 'post',
            url: `${API_URL}/users/register/pharmacyAdmin`,
            data: newAdmin
        }).then(response => {
            if (response.data) {
				alert("Pharmacy administrator successfully added!");
				return true;
			}
			alert("This e-mail is already taken!");
			return false;
        });
    }
}


export default new PharmacyAdminDataService();