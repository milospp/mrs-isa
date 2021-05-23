import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl + "/users"
// const API_URL = "api/users";

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