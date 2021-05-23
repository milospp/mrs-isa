const express = require('express');
const port = process.env.PORT || 8081;
const app = express();

// app.use(express.static(__dirname + "/dist/"));
// app.get(/.*/, function(req, res) {
//     res.sendFile(__dirname + "/dist/index.html");
// });

// var request = require('request');
// app.get('/api', function(req,res) {
//   //modify the url in any way you want
//   var newurl = 'http://localhost:8080';
//   request(newurl).pipe(res);
// });

// if(process.env.NODE_ENV === 'production') {
    app.use(express.static(__dirname + '/dist/'));

    app.get(/.*/, (req, res) => res.sendFile(__dirname + '/dist/index.html'));

// }


app.listen(port);

console.log("Server started...")