var config = {
    apiUrl: "https://localhost:8080/api"
}

if (window.location.hostname == "pharmacy-tim9.herokuapp.com"){
    config = {
        apiUrl: "https://pharmacy-tim9.herokuapp.com/api"
    }
}


export default config;