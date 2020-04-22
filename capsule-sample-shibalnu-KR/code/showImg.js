module.exports.function = function showImg (query) {
  const config = require('config');
  const fail = require('fail');
  const http = require('http');
  const console = require('console');
  // 설정한 property(capsule.properties)로부터 데이터를 가져옵니다.
  const baseURL = config.get("baseUrl");
  
  if(query.length === 0){
    throw fail.checkedError("No Result", "NoResult");  
  }
  
  let mode = query.mode.random;
  let response = null;
  let result = {};
  
  if(query.mode.images == true){
    url = baseURL + '15';
    
    // 외부 API로 부터 데이터 받음 (https://bixbydevelopers.com/dev/docs/reference/JavaScriptAPI/http)
    // cacheTime: cache 시간을 설정, returnHeaders: API에 대한 Response를 Header 형식으로 받음.
    response = http.getUrl(url, {format:"json", cacheTime: 0, returnHeaders:true});
    
    if(response.status == 404){
      throw fail.checkedError("No Result", "NoResult");  
    }
    
    objects = [];
    for(var key in response.parsed.message){
      objects.push({
        url: response.parsed.message[key],
        caption: "shiba"
      });
    }
    result.photos = objects;
    
  }else{
    url = baseURL + '1';
    response = http.getUrl(url, {format:"json", cacheTime: 0, returnHeaders:true});
    
    if(response.status == 404){
      throw fail.checkedError("No Result", "NoResult");  
    }
    
    result.photos = {url: response.parsed.message, caption: "shiba"};
  }
  
  console.log(result.photos);
  return result;
}
