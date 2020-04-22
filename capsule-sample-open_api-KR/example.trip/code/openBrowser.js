module.exports.function = function openBrowser (point) {
  var url = "https://map.kakao.com/link/map/"; 
  url = url + point.latitude + ',' + point.longitude;
  return url;
}
