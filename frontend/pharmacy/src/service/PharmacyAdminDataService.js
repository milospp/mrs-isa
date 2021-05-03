import axios from "axios";


const API_URL = "http://localhost:8080/api";

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

    getOrders(idAdmina) {
        return axios.get(API_URL + "/orders/" + idAdmina)
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

    addOrder(idAdminaApoteke, lekovi, pocetak, kraj) {
      return axios({
        method: 'post',
        url: API_URL + "/orders/add/" + idAdminaApoteke,
        data: {"medicines": lekovi, "startDate": pocetak + "T06:00", "endDate": kraj + "T06:00"}
      }).catch(function (error) {
        if (error.response) {
            console.log(error.response.data);
        } else if (error.request) {
            console.log(error.request);
        }
        console.log("Error");
        console.log(error.config);
    });
    }

    deleteOrder(narudzbina) {
      return axios({
          method: 'post',
          url: API_URL + "/orders/delete",
          data: narudzbina
      }).catch(function (error) {
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