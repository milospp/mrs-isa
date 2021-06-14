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
        });
    }
}


export default new SysAdminDataService();