const path = require('path');

module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'https://pharmacy-tim9.herokuapp.com'
            }
        }
    }
  }

console.log("Vue Config JS");