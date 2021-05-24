import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl + "/users"
// const API_URL = "api/users";

class SupplierDataService {
    SendSupplier(newSupplier) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/supplier`,
            data: newSupplier
        }).then(response => {
            if (response.data) {
				alert("Supplier has been successfully added!");
				return true;
			}
			alert("This e-mail is already taken!");
			return false;
        });
    }

    getSupplier(id){
        return axios.get(`${API_URL}/` + id);
    }
    editPersonalData(supplier){
        return axios({
          method: 'post',
          url: API_URL + "/edit/supplier",
          data: supplier
        });
    }
}


export default new SupplierDataService();