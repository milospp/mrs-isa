import axios from "axios";


const API_URL = "http://localhost:8080/api/users";

class PharmacyAdminDataService {
    SendPharmacyAdmin(newAdmin) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/pharmacyAdmin`,
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

    getOrder(idAdmina) {
        return axios.get("http://localhost:8080/api/medicines/allOrder/admin/" + idAdmina)
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
}


export default new PharmacyAdminDataService();