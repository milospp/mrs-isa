import axios from "axios";


const API_URL = "http://localhost:8080/api/complaints";

class ComplaintDataService{

    sendComplaint(complaint){
        return axios({
            method: 'post',
            url: `${API_URL}/newComplaint`,
            data: complaint
        });
    }

    getAllComplaints(){
        return axios.get(`${API_URL}/`);
    }

    respondToComplaint(complaint){
        return axios({
            method: 'post',
            url: `${API_URL}/responseToComplaint`,
            data: complaint
        });
    }

    getPatientsComplaints(id){
        return axios.get(`${API_URL}/complaintsByPatient/`+id);
    }

    getAdminResponses(id){
        return axios.get(`${API_URL}/responsesBySysAdmin/`+id);
    }
}

export default new ComplaintDataService();


