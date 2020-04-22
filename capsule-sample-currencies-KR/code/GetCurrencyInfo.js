let config = require('config');

function makeURL(dateTime, base, target){  
  // 설정한 property(capsule.properties)로부터 데이터를 가져옴
  const baseURL = config.get("baseUrl");

  let url = '';
  
  if(!dateTime){
    url = baseURL + 'latest?';
  }else{
    baseDate = dateTime.date.year + '-' + dateTime.date.month + '-' + dateTime.date.day;
    url = baseURL + baseDate + '?';
  }
  
  if(!target){
    url += 'base=' + base;
  }else{
    url += 'base=' + base + '&symbols=' + target.currencySymbol;
  }
  
  return url;
}

module.exports.function = function GetCurrencyInfo (value, dateTime, baseCurrency, targetCurrency, $vivContext) {
  //다른 경로의 파일을 가져옴 
  const curCode = require("./data/currencyCode.js");
  const nationData = require("./data/nationData.js");
  const fail = require('fail');
  const http = require('http');
  const console = require('console');
  
  let url = '';
  let base = null;
  let target = null;

  if(!baseCurrency){
    
    let local = $vivContext.locale.split("-")[1];
    
    for(let i = 0; i < curCode.length; ++i){
      if(curCode[i].code == local){
        base = curCode[i].symbol;
        break;
      }
    }

    if(base == null){
      throw fail.checkedError("No Local", "NoLocal");
    }
    
    if(!targetCurrency){
      url = makeURL(dateTime, base, target);
    }else{
      url = makeURL(dateTime, base, targetCurrency);
    }
    
  }else{
    base = baseCurrency.currencySymbol;
    url = makeURL(dateTime, base, targetCurrency);
  }

  //외부 API와 http 통신 (https://bixbydevelopers.com/dev/docs/reference/JavaScriptAPI/http)
  // returnHeaders: API에 대한 Response를 Header 형식으로 받음
  let response = http.getUrl(url, {format:"json", returnHeaders:true});
  
  if(response.status != 200){
    throw fail.checkedError("No Information", "NoInfo");
  }
     
  let result = [];
  let date = response.parsed.date;
  
  if(!value){
    value = 1;
  }
  
  for(let val in response.parsed.rates){
    if(base != val){
      result.push({
        baseSymbol: {
          currencySymbol: base
        },
        currencySymbol: val,
        nation: nationData[val],
        value: response.parsed.rates[val] * value,
        date: date,
        baseValue: value
      });
    }
  }
  
  console.log(result);
  return result;
}
