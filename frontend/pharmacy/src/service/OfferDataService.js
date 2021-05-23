import axios from "axios";
import config from "@/config";

const API_URL = config.apiUrl + "/offers"
// const API_URL = "api/offers";

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

    getOffersOfSupplier(supplierId){
        return axios.get(`${API_URL}/offersOfSupplier/${supplierId}`);
    }

    searchOffers(searchParams) {
      console.log(searchParams);

      return axios({
        method: 'post',
        url: `${API_URL}/search`,
        data: searchParams
      })
    }
}

export default new OfferDataService();