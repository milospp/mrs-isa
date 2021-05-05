import axios from "axios";


const API_URL = "http://localhost:8080/api";

class OrderDataService {
    getOrders(idAdmina) {
        return axios.get(API_URL + "/orders/" + idAdmina)
        .catch(function (error) {
          if (error.response) {
            console.log(error.response.data);
          } else if (error.request) {
            console.log(error.request);
          }
          console.log("Error");
          console.log(error.config);
      });
    }

    addOrder(idAdminaApoteke, lekovi, kraj) {
      return axios({
        method: 'post',
        url: API_URL + "/orders/add/" + idAdminaApoteke,
        data: {"medicines": lekovi, "endDate": kraj}
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

    deleteOrder(narudzbina) {
      return axios({
          method: 'post',
          url: API_URL + "/orders/delete",
          data: narudzbina
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

  findOneOrder(order) {
    return axios({
        method: 'post',
        url: API_URL + "/orders/findOne",
        data: order
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

editOrder(order) {
  return axios({
      method: 'post',
      url: API_URL + "/orders/edit",
      data: order
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


export default new OrderDataService();