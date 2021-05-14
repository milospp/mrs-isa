import axios from "axios";

const API_URL = "http://localhost:8080/api/offers";

class OfferDataService{
    sendOffer(offer){
        return axios({
            method: 'post',
            url: API_URL + "/newOffer",
            data: offer
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

export default new OfferDataService();