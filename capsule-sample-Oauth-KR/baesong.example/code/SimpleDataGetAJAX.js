module.exports.function = function simpleDataAJAX () {
  var http = require('http')
  // var console = require('console')
  
  var result = http.oauthGetUrl('https://URL_PATH/oauthEx/simpleData/list', {
    format:'json',
    headers:{
      'Content-Type': 'application/json;charset=utf-8'
    }
  });
  
  return result
}

