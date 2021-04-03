import axios from "axios";


const API_URL = "http://localhost:8080/api/users";

class SupplierDataService {
    SendSupplier(newSupplier) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/supplier`,
            data: newSupplier
        }).then(response => {
            if (response.data == false) alert("This e-mail is taken!");
        });
    }
}


export default new SupplierDataService();