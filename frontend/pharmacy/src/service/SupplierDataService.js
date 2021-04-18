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
}


export default new SupplierDataService();