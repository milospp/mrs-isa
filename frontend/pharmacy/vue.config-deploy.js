const path = require('path');

module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080'
            }
        }
    }
  }

console.log("Vue Config JS");