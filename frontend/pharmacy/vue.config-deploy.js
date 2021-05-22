const path = require('path');

// module.exports = {
//     devServer: {
//         proxy: {
//             '/api': {
//                 target: 'http://localhost:8080'
//             }
//         }
//     }
// }

module.exports = {
    devServer: {
      proxy: 'https://pharmacy-tim9.herokuapp.com'
    }
  }

console.log("Vue Config JS");