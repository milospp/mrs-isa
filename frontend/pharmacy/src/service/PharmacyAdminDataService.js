import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl;
// const API_URL = "api";

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

    getPA(idAdminaApoteke) {
        return axios.get(API_URL + "/users/getPharmacyAdmin/" + idAdminaApoteke);
    }
    editPA(admin, ime, prezime, mejl, brFona, adresaD, adresaG, adresaU, adresaB){
        admin.name = ime;
        admin.surname = prezime;
        admin.phoneNumber = brFona;
        admin.email = mejl;
        admin.address.state = adresaD; 
        admin.address.city = adresaG; 
        admin.address.street = adresaU; 
        admin.address.number = adresaB; 
        return axios({
          method: 'post',
          url: API_URL + "/users/edit/pharmacyAdmin",
          data: admin
        });
      }
}


export default new PharmacyAdminDataService();