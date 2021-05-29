import axios from "axios";


const API_URL = "http://localhost:8080/api/vacations";

class VacationDataService{

    sendRequest(vacation){
        return axios({
            method: 'post',
            url: `${API_URL}/saveRequest`,
            data: vacation
        });
    }

    getVacationsForDoctorAndPharmacy(doctorId, pharmacyId){
        return axios.get(`${API_URL}/`+doctorId+'/'+pharmacyId);
    }
}

export default new VacationDataService();

