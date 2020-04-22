var http = require('http')
var console = require('console')
var config = require('config')
var secret = require('secret')
module.exports.function = function tourInfoLocation (point) {
  // < 위치기반 관광정보 조회 > 
  // Open API 에 Request Parameter 를 채워서 정보를 요청한다. 
  // Request Parameter 
  // - numOfRows (한 페이지 결과 수), pageNo (페이지 번호), MobileOS (OS 구분), MobileApp (서비스명), 
  //   ServiceKey (인증키 (서비스키)), listYN (목록 구분), arrange (정렬 구분), contentTypeId (관광타입 ID), 
  //   mapX (X좌표), mapY (Y좌표), radius (거리 반경)
  var url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList"; // config.get("locationbasedlist");
  var key = secret.get("serviceKey");
  const query = {
    numOfRows: "20", 
    pageNo: "1", 
    MobileOS: "ETC", 
    MobileApp: "AppTest", 
    ServiceKey: key, 
    listYN: "Y",
    arrange: "B", 
    contentTypeId: "15", 
    mapX: point.longitude, 
    mapY: point.latitude, 
    radius: "10000"
  }
  var options = {
    format: 'xmljs', 
    query: query, 
    cacheTime: 0
  };   
  var response = http.getUrl(url, options); 
  // console.log("response", response);   

  // 받아온 데이터(Response)를 갯수만큼 반복하면서 결과에 저장한다. 
  // Response
  // - resultCode (결과코드), numOfRows (한 페이지 결과 수), totalCount (전체 결과 수)
  // - item[] 
  //   addr1 (주소), addr2 (상세주소), areacode (지역코드), contentid (콘텐츠ID)
  //   dist (거리), firstimage (대표이미지(원본)), firstimage2 (대표이미지(썸네일))
  //   mapx (GPS X좌표), mapy (GPS Y좌표), tel (전화번호), title (제목)
  var tourinfo = []; 
  for (var i=0; i<options.query.numOfRows; i++) {
    var temp = {}; 
    var pointR = {}; 
    temp.addr1 = response.response.body.items.item[i].addr1; 
    temp.addr2 = response.response.body.items.item[i].addr2; 
    temp.contentid = response.response.body.items.item[i].contentid; 
    temp.dist = response.response.body.items.item[i].dist; 
    temp.firstimage = response.response.body.items.item[i].firstimage; 
    temp.firstimage2 = response.response.body.items.item[i].firstimage2; 
    pointR.longitude = response.response.body.items.item[i].mapx; 
    pointR.latitude = response.response.body.items.item[i].mapy; 
    temp.point = pointR; 
    temp.tel = response.response.body.items.item[i].tel; 
    temp.title = response.response.body.items.item[i].title; 

    tourinfo.push(temp); 
  }
  // console.log("tourinfo", tourinfo); 
  return tourinfo;   
}
