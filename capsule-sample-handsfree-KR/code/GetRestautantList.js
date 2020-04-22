module.exports.function = function getRestautantList(self, location) {
  const dummyData = require("./data/restaurants.js");
  const searchLocation = require("./GetLocation.js");
  const console = require('console');

  var fail = require('fail');
  console.log(self);
  console.log(typeof location);
  console.log(typeof searchLocation);

  var serachlocation = searchLocation();
  console.log(serachlocation);
  let result = [];
  let result2 = "";
  
  //GetLocation.js 에 있는 location 정보를 사용자가 선택 또는 입력한 정보와 비교한 후 맞을 경우 result2 에 해당 값을 부여한다.
  for (let i = 0; i < serachlocation.length; i++) {
    if (serachlocation[i] == location) {
      result2 = serachlocation[i];
    }
  }
  // result2의 값이 없을 경우 GetRestaurantList.model.bxb에서 선언한 에러를 사용자(view에)에게 띄어준다.
  if (result2 == "") {
    throw fail.checkedError("Given Location is Not Supported", "NotSupportedLocation");
  }

  for (let i = 0; i < dummyData.length; i++) {
    if (self.nameInfo != undefined) {
      if (self.nameInfo.nickName) {
        dummyData[i].username = self.nameInfo.nickName;
      } else {
        dummyData[i].username = self.nameInfo.structuredName;
      }
    } else {
      dummyData[i].username = '사용자';
    }
    if (dummyData[i].location == location) {
      result.push(dummyData[i]);
    }
  }
  console.log(result);
  return result;
}