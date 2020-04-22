# Open API를 활용한 캡슐 만들기 1
# 위치기반 관광정보 조회 

*** 인증키가 필요한 Open API ***
  . code\tourInfoLocation.js 
  . var key = secret.get("serviceKey");
    => Bixby Developer Center >>> Teams & Capsules >>> Config & Secrets >>> Secrets 에 Name(serviceKey), Value(인증키 - Open API 활용신청을 하면 받을 수 있다) 추가 필요 
    => https://bixbydevelopers.com/dev/docs/reference/ref-topics/capsule-config 참고 
  . 인증키의 경우 적용하여 http 호출해도 아래와 같은 에러가 발생하는 경우 UTF-8 에 대해 ASCII Encoding 이 필요할 수 있다. 
    . SERVICE KEY IS NOT REGISTERED ERROR
    . %2B (+), %2F (/), %3D (=) 등등

1. Open API 찾아 보기
  . 공공데이터포털 회원가입 (https://www.data.go.kr/)
  . 공공데이터포털 상단메뉴 [데이터셋 > 오픈 API]를 통해 검색
  . 이번 샘플에서 만들고자 하는 관광정보 관련 오픈 API를 찾아서 활용 신청 (국문 관광정보 서비스)
  . 오픈 API 상세보기 화면을 통해 인증키, 참고문서, 상세기능정보, 실행 등을 확인할 수 있다. 
  . 이 예제에서는 간단하게 주변의 관광정보만을 활용하기 때문에 “위치기반 관광정보조회”만을 활용한다. 
  . 상세보기 화면에서 “실행”을 통해 바로 결과 등을 확인해 볼 수 있다. 

2. 참고문서 참고 : TourAPI_Guide_(Korean)v3.4_20181120.zip
  . 한국관광공사_TourAPI활용신청방법_매뉴얼_v3.2(20180323).docx 
    . 회원 가입
    . 개발계정 활용 신청 (테스트 계정) 
    . 인증키(서비스키) 발급
    . 운영계정 활용 신청 
  . 한국관광공사_TourAPI활용매뉴얼(국문)_v3.4_20181120.docx
    . 서비스 개요
    . 인증키 활용 및 API호출 방법
    . TourAPI

3. [위치기반 관광정보 조회] 활용
  . 한국관광공사_TourAPI활용매뉴얼(국문)_v3.4_20181120.docx 
  . 12) [위치기반 관광정보 조회] 오퍼레이션 명세
    . 내용 : 내 주변 좌표를 기반으로 관광정보 목록을 조회하는 기능
    . 형식 : http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList
    . 요청 메시지 (Request Parameter)
      . numOfRows (한 페이지 결과 수), pageNo (페이지 번호), MobileOS (OS 구분), MobileApp (서비스명)
      . ServiceKey (인증키 (서비스키)), listYN (목록 구분), arrange (정렬 구분)
      . contentTypeId (관광타입 ID), mapX (X좌표), mapY (Y좌표), radius (거리 반경)
    . 응답 메시지 (Response Message)
      . resultCode (결과코드), numOfRows (한 페이지 결과 수), totalCount (전체 결과 수)
      . item[] 
        . addr1 (주소), addr2 (상세주소), areacode (지역코드), contentid (콘텐츠ID)
        . dist (거리), firstimage (대표이미지(원본)), firstimage2 (대표이미지(썸네일))
        . mapx (GPS X좌표), mapy (GPS Y좌표), tel (전화번호), title (제목)
    . 응답 메시지를 저장해서 활용해야 하기 때문에 이에 대한 Concept 을 만들고 저장해서 활용한다. 

4. js 코드 수정
  . Open API 에 Request Parameter 를 채워서 정보를 요청한다. 
  . url, serviceKey 정보는 config & secret 활용하여 설정 권장 (https://bixbydevelopers.com/dev/docs/reference/ref-topics/capsule-config)
  . 받아온 데이터(Response)를 갯수만큼 반복하면서 결과에 저장한다. 
  . 해당 결과로 적당한 views, layout 구성한다. 아래 Bixby Views 참고. (https://github.com/bixbydevelopers/capsule-samples-collection/tree/master/bixby-views)
