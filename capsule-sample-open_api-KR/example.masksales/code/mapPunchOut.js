var console = require('console');
module.exports.function = function mapPunchOut (resultPoint, sellerName) {
  // geo:37.554998,126.970577?q=서울역
  var result = "geo:" + resultPoint.latitude + "," + resultPoint.longitude + "?q=" + sellerName;
  // console.log("result", result); 
  return result;
}