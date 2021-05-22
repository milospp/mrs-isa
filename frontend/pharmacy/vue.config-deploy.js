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
      proxy: '{API_URL}'
    }
  }

console.log("Vue Config JS");