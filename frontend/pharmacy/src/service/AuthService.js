import axios from "axios";


const API_URL = "http://localhost:8080";

class AuthService {
	login(credentials){
		return axios({
		  method: 'post',
		  url: `${API_URL}/api/auth/login`,
		  data: credentials
		}).then(response => {
			if (response.data.accessToken) {
			  localStorage.setItem('userToken', JSON.stringify(response.data));
			  axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.accessToken}`;
			  this.setCurrentUser();
			  alert("Login successful!");
			  //window.location.href = "/";
			  return response.data;
			};
			//response.data.redirect = "/login";
			return response.data;
		})
		
	}
	
	logout(){
		axios.defaults.headers.common['Authorization'] = null;
		localStorage.removeItem('userToken');
		localStorage.removeItem('currentUser');
	}
	
	setCurrentUser(){
		return axios.get(`${API_URL}/api/auth/getLoggedIn`).then(response => {
			localStorage.setItem('currentUser', JSON.stringify(response.data));
			return response.data;
		})
	}

	getCurrentUser(){
		let user = localStorage.getItem('currentUser');
		return user;
	}
}


export default new AuthService();
