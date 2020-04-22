# Open API를 활용한 캡슐 만들기 2
# 공적 마스크 판매정보 

*** 인증키가 필요없는 Open API ***

1. Open API 찾아 보기
  . 공공데이터포털 회원가입 (https://www.data.go.kr/)
  . 공공데이터포털 상단메뉴 [데이터셋 > 오픈 API]를 통해 검색
  . 이번 샘플에서 만들고자 하는 공적 마스크 판매정보 관련 오픈 API를 찾아서 활용
  . 오픈 API 상세보기 화면을 통해 참고문서, 상세기능정보 등을 확인할 수 있다. 
  . 이 예제에서는 간단하게 주변 및 주소정보를 이용해 공적 마스크 판매처 조회를 활용한다. 

2. 참고문서 참고 
  . https://www.data.go.kr/dataset/15043025/openapi.do
  . https://app.swaggerhub.com/apis-docs/Promptech/public-mask-info/20200307-oas3#/StoreSale
  . 주소_좌표 기준 판매처별 공적 마스크 판매정보 제공 서비스.docx
  . 좌표(위_경도) 기준 공적 마스크 판매정보 제공 서비스.docx

3. 활용
  . 주소_좌표 기준 판매처별 공적 마스크 판매정보 제공 서비스.docx
    . 형식 : https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json
    . 요청 메시지 (Request Parameter)
      . address (주소) 
    . 응답 메시지 (Response Message)
      . address (주소), count (조회 수)
      . stores[] 
        . addr (판매처 주소), code (식별코드), create_at (데이터 생성일자), remain_stat (재고 상태), 
        . stock_at (입고시간), lat (위도), lng (경도), name (이름), type (판매처 유형)
    . 응답 메시지를 저장해서 활용해야 하기 때문에 이에 대한 Concept 을 만들고 저장해서 활용한다. 
  . 좌표(위_경도) 기준 공적 마스크 판매정보 제공 서비스.docx
    . 형식 : https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json
    . 요청 메시지 (Request Parameter)
      . lat (위도), lng (경도), m (반경(미터))
    . 응답 메시지 (Response Message)
      . count (조회 수)
      . stores[] 
        . code (식별코드), create_at (데이터 생성일자), remain_stat (재고 상태), stock_at (입고시간)
        . addr (주소), lat (위도), lng (경도), name (이름), type (판매처 유형)
    . 응답 메시지를 저장해서 활용해야 하기 때문에 이에 대한 Concept 을 만들고 저장해서 활용한다. 

4. js 코드 수정
  . Open API 에 Request Parameter 를 채워서 정보를 요청한다. 
  . 이 Open API 는 serviceKey 가 필요없다. 
  . 받아온 데이터(Response)를 갯수만큼 반복하면서 결과에 저장한다. 
  . 해당 결과로 적당한 views, layout 구성한다. Bixby Views 참고(https://github.com/bixbydevelopers/capsule-samples-collection/tree/master/bixby-views)
