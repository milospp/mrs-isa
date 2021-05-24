var config = {
    apiUrl: "http://localhost:8080/api"
}

if (window.location.hostname == "pharmacy9.herokuapp.com"){
    config = {
        apiUrl: "https://pharmacy-tim9.herokuapp.com/api"
    }
}

console.log("config");

export default config;