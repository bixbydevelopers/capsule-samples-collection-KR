var http = require('http');
var fail = require('fail');
var console = require('console');
module.exports.function = function geoSearch(point) {
  // < 좌표 위 경도 기준 공적 마스크 판매정보 제공 서비스 > 
  // Open API 에 Request Parameter 를 채워서 정보를 요청한다. 
  // Request Parameter 
  // lat (위도), lng (경도), m (반경(미터))
  var url = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json";
  const query = {
    lat: point.point.latitude,
    lng: point.point.longitude,
    m: 5000
  };
  var options = {
    format: 'json', 
    query: query, 
    cacheTime: 0
  }; 
  const response = http.getUrl(url, options);
  // console.log("response", response); 

  // 받아온 데이터(Response)를 갯수만큼 반복하면서 결과에 저장한다. 
  // Response
  // - count (조회 수)
  // - stores[] 
  //   code (식별코드), create_at (데이터 생성일자), remain_stat (재고 상태), stock_at (입고시간)
  //   addr (주소), lat (위도), lng (경도), name (이름), type (판매처 유형)
  if (response) {
    if (response.count == 0) {
      throw fail.checkedError("NoResult", "NoResult");
    } else {
      let sellerResult = [];
      for (let i = 0; i < response.count; ++i) {
        var sellerObj = {
          sellerName: response.stores[i].name,
          sellerAddr: response.stores[i].addr,
          remainMask: response.stores[i].remain_stat,
          maskWhen: response.stores[i].stock_at,
          resultPoint: {
            latitude: response.stores[i].lat,
            longitude: response.stores[i].lng
          },
          isNearFlag: true
        };
        sellerResult.push(sellerObj);
      } 
    }
  }
  // console.log("sellerResult", sellerResult); 

  return sellerResult;
}