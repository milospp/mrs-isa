import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl;

class UserDataService {
    getAllUsers(){
        return axios.get(`${API_URL}/users/all-users`);
    }
}

export default new UserDataService();