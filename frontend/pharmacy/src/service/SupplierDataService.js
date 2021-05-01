import axios from "axios";


const API_URL = "http://localhost:8080/api/users";

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
        return axios.get(`${API_URL}/api/users/` + id);
    }
    editPersonalData(supplier){
        return axios({
          method: 'post',
          url: API_URL + "/api/users/edit/supplier",
          data: supplier
        });
    }
}


export default new SupplierDataService();