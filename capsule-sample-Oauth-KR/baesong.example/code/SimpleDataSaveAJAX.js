module.exports.function = function simpleDataSaveAJAX (message) {
  var http = require('http')
  
  var result = http.oauthGetUrl('https://URL_PATH/oauthEx/simpleData/save?message=' + message, {
    format:'json',
    headers:{
      'Content-Type': 'application/json;charset=utf-8'
    }
  });
  
  return result
}
