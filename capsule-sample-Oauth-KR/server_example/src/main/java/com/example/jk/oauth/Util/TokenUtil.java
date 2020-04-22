package com.example.jk.oauth.Util;

import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.entity.OAuthTokenDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 각 Controller 및 Service 에서 사용하는 token 및 response 관련 utility method
 */
@Component
public class TokenUtil {

    /**
     * Custom Token Store 에서 쓰인 token 을 OAuth 2.0 token format 에 맞게 converting 하는 method
     *
     * @param oAuthToken Custom Token Store 에서 쓰이는 OAuth object
     * @return           OAuthTokenDTO. OAuth 2.0 spec 에 맞는 token object
     */
    public OAuthTokenDTO convert(OAuthToken oAuthToken) {
        OAuthTokenDTO oAuthTokenDTO = new OAuthTokenDTO();

        oAuthTokenDTO.setAccess_token(oAuthToken.getAccessToken());
        oAuthTokenDTO.setRefresh_token(oAuthToken.getAccessToken());
        oAuthTokenDTO.setCode(oAuthToken.getCode());
        oAuthTokenDTO.setToken_type("Bearer");
        oAuthTokenDTO.setExpires_in(oAuthToken.getExpiresIn());
        oAuthTokenDTO.setExpire(oAuthToken.getExpire());

        return oAuthTokenDTO;
    }

    /**
     * OAuth 2.0 spec 에 맞는 error Object maker
     *
     * @param error        OAuth 2.0 error value (ex: invalid_token)
     * @param errorMessage OAuth 2.0 detail error message. 개발자 custom
     * @return             OAuthTokenDTO
     */
    public OAuthTokenDTO convertError(String error, String errorMessage) {
        OAuthTokenDTO oAuthTokenDTO = new OAuthTokenDTO();
        oAuthTokenDTO.setError(error);
        oAuthTokenDTO.setError_description(errorMessage);

        return oAuthTokenDTO;
    }

    /**
     * 전달 받은 Object 를 Http 통신으로 전달하는 method
     *
     * @param response redirect 를 위한 HttpServletResponse
     * @param object   client response body Object
     * @throws IOException response IOException
     */
    public void responseAsJson (HttpServletResponse response, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String tokenJsonString = mapper.writeValueAsString(object);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(tokenJsonString);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
