var http = require('http');
var fail = require('fail');
var console = require('console');
module.exports.function = function addrSearch(regionName) {
  // < 주소 기준 동네별 공적 마스크 판매정보 제공 서비스 > 
  // Open API 에 Request Parameter 를 채워서 정보를 요청한다. 
  // Request Parameter 
  // - address (주소) 
  var response;
  if (regionName.unstructuredAddress != undefined) {
    var url = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json";
    var query = {
      address: regionName.unstructuredAddress
    }
    var options = {
      format: 'json', 
      query: query, 
      cacheTime: 0
    }; 
    response = http.getUrl(url, options);
    // console.log("response", response); 
  } 

  // 받아온 데이터(Response)를 갯수만큼 반복하면서 결과에 저장한다. 
  // Response
  // - address (주소), count (조회 수)
  // - stores[] 
  //   addr (판매처 주소), code (식별코드), create_at (데이터 생성일자), remain_stat (재고 상태), 
  //   stock_at (입고시간), lat (위도), lng (경도), name (이름), type (판매처 유형)
  if (response) {
    if (response.count == 0) {
      throw fail.checkedError("NoResult", "NoResult");
    } else {
      let sellerResult = [];
      for (let i = 0; i < response.count; ++i) {
        sellerResult[i] = {
          sellerName: response.stores[i].name,
          sellerAddr: response.stores[i].addr,
          remainMask: response.stores[i].remain_stat,
          maskWhen: response.stores[i].stock_at,
          resultPoint: {
            latitude: response.stores[i].lat,
            longitude: response.stores[i].lng
          }
        }
        sellerResult[i].isNearFlag = false; // 좌표 기반인지 여부 
      } 
    }
  }
  // console.log("sellerResult", sellerResult); 

  return sellerResult;
}