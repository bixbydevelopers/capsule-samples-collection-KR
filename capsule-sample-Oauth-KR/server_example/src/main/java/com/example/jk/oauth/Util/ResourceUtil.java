package com.example.jk.oauth.Util;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.SimpleDataDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 각 Controller 및 Service 에서 사용하는 resource 관련 utility method
 */
@Component
public class ResourceUtil {

    /**
     * Authorization format 에서 access_token 을 추출하는 method
     *
     * @param authorization access_token 이 포함된 header value String 으로 전달 받음,
     *                      format 은 Bearer ACCESS_TOKEN 형식 (blank 포함)
     * @return              String 으로 추출된 access_token 만을 return한다.
     */
    public String extractToken (String authorization) {
        return authorization.replaceAll("Bearer ", "");
    }

    /**
     * SimpleData 를 client(Bixby capsule) 의 format에 맞게 재구성하는 부분
     *
     * @param simpleData Resource Server (분리를 했다면) 에서 전달 받은 Original Data
     * @return           SimpleDataDto
     */
    public SimpleDataDTO convert (SimpleData simpleData) {
        return new SimpleDataDTO(simpleData);
    }

    /**
     * convert 의 list 형태
     *
     * @param simpleData Resource Server (분리를 했다면) 에서 전달 받은 Original Data
     * @return           List(SimpleDataDto)
     */
    public List<SimpleDataDTO> convert (List<SimpleData> simpleData) {

        List<SimpleDataDTO> result = new ArrayList<>();

        for (SimpleData tempData : simpleData) {
            result.add(new SimpleDataDTO(tempData));
        }

        return result;
    }
}
