function makeAddress(element){
  let tmp = element;
  tmp.location = element.location.levelOneDiv + " " + element.location.levelTwoDiv + " " + element.location.place;
  return tmp;
}

module.exports.function = function findStore (location, place) {
  const fail = require('fail');
  const console = require('console');
  //다른 경로의 파일을 가져옵니다.
  let fakeData = require("./data/StoreData.js");

  if(!location && !place){
    throw fail.checkedError("No Result", "NoResult");  
  }
   
  console.log(location);
  console.log(place);
  
  let result = [];
  
  fakeData.forEach(function (element){
    if(!location){
      if(element.location.place == place){
        result.push(makeAddress(element));
      }
    }else{
      if(!location.levelOne){
        if(!location.subLocalityOne){
          if(location.locality.name == element.location.levelOneDiv)
            result.push(makeAddress(element));
        }else{
          if(location.locality.name == element.location.levelOneDiv && location.subLocalityOne.name == element.location.levelTwoDiv)
            result.push(makeAddress(element));     
        }
      }else{
        if(!location.locality){
          if(location.levelOne.name == element.location.levelOneDiv)
            result.push(makeAddress(element));
        }else{
          if(location.levelOne.name == element.location.levelOneDiv && location.locality.name == element.location.levelTwoDiv)
            result.push(makeAddress(element));
        }
      } 
    }
  });
  
  console.log(result);

  return result;
}
