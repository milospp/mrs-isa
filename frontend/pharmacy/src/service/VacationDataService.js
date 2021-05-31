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

    getVacationsForPharmacy(idAdminaApoteke){
        return axios.get(API_URL + "/" + idAdminaApoteke);
    }

    saveVacationApproval(idAdminaApoteke, zahtev, potvrda, zastoNe) {
        if (potvrda == "Accept") {zahtev.status = "ACCEPTED";}
        else {
            zahtev.status = "DENIED";
            zahtev.whyNot = zastoNe;
        }
        return axios({
            method: 'post',
            url: API_URL + "/" + idAdminaApoteke,
            data: zahtev
        }).catch(function (error) {
            if (error.response) {
            console.log(error.response.data);
            } else if (error.request) {
            console.log(error.request);
            }
            console.log("Error");
            console.log(error.config);
        });
    }
}

export default new VacationDataService();

