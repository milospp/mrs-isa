import axios from "axios";


const API_URL = "http://localhost:8080/api/users";

class SysAdminDataService {
    SendSysAdmin(newSysAdmin) {
        return axios({
            method: 'post',
            url: `${API_URL}/register/systemAdmin`,
            data: newSysAdmin
        }).then(response => {
            if (response.data) {
				alert("System administrator has been successfully added!");
				return true;
			}
			alert("This e-mail is already taken!");
			return false;
        });
    }
}


export default new SysAdminDataService();