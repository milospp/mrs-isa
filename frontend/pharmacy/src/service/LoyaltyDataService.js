import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl + "/loyaltyPrograms"

class LoyaltyDataService {

    sendLoyaltyProgram(loyaltyProgram){
        return axios({
            method: 'post',
            url: `${API_URL}/newUserCategory`,
            data: loyaltyProgram
        });
    }

    editLoyaltyProgram(loyaltyProgram){
        return axios({
            method: 'post',
            url: `${API_URL}/updateCategory`,
            data: loyaltyProgram
        });
    }

    getAllCategories(){
        return axios.get(`${API_URL}/`);
    }

    getExaminationPoints(){
        return axios.get(`${API_URL}/examinationPoints`);
    }

    setExaminationPoints(p){
        return axios({
            method: 'post',
            url: `${API_URL}/setExaminationPointsReward`,
            data: { points: parseInt(p) }
        });
    }
}

export default new LoyaltyDataService();