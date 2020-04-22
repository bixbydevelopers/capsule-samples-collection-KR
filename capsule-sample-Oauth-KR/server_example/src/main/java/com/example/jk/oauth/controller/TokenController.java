package com.example.jk.oauth.controller;

import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.service.ITokenService;
import com.example.jk.oauth.service.IUserService;
import com.example.jk.oauth.Util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class TokenController {

    @Value("${oauth.url}")
    private String oauthURL;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final ITokenService tokenService;
    private final TokenUtil tokenUtil;
    private final IUserService userService;

    @Autowired
    public TokenController(ITokenService tokenService, TokenUtil tokenUtil, IUserService userService) {
        this.tokenService = tokenService;
        this.tokenUtil = tokenUtil;
        this.userService = userService;
    }

    /**
     * authorization end-point. 각 client 별 end-point 를 다른 path 로 설정 시 path 변경 가능
     *
     * @param clientId     OAuth 2.0 parameter 의 client_id. client 를 정의하기 위한 random string. (예제 기준 jklee)
     * @param state        client - OAuth server 간 통신 검증을 위한 상태 값
     * @param redirectURI  client 가 code 를 전달 받을 redirect_uri
     * @param responseType authorization_code 로 고정 (예제 기준)
     * @param scope        client 에서 해당 code 를 통해 발급 받을 token 의 계정에 대한 권한
     *                     ex : read,write 일 경우 해당 token으로 접근하는 계정은 resource server 에서 read 와 write 만 가능하다. 개발자 별 customizing 가능
     * @param model        html page 에 전달 시 사용
     * @return             해당 부분에선 login page 로 redirect 하기 위해 login 으로 주었음, 개발자 별로 customizing 가능
     */
    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam(value = "client_id") String clientId,
                          @RequestParam(value = "state") String state,
                          @RequestParam(value = "redirect_uri") String redirectURI,
                          @RequestParam(value = "response_type") String responseType,
                          @RequestParam(value = "scope") String scope, Model model) {

        // 원래대로 라면 client-id, response-type 등으로 인증
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("redirectURI", redirectURI);
        model.addAttribute("responseType", responseType);
        model.addAttribute("scope", scope);
        model.addAttribute("contextPath", contextPath);

        return "login";
    }

    /**
     * token end-point. 각 client 별 end-point 를 다른 path 로 설정 시 path 변경 가능하며,
     * refresh_token 을 통한 재발급 end-point 로 또한 쓰인다.
     *
     * @param clientId     OAuth 2.0 parameter 의 client_id. client 를 정의하기 위한 random string. (예제 기준 jklee)
     * @param grantType    OAuth 2.0 parameter 의 grant_type, (예제 기준 authorization_code / refresh_token)
     * @param code         grantType 이 authorization_code 일 경우 authorize end-point 를 통해 발급 받은 code
     * @param refreshToken grantType 이 refresh_token 일 경우 token end-point 를 통해 발급 받은 refresh_token
     * @param response     redirect 를 위한 HttpServletResponse
     * @throws IOException Exception 대비 try/catch 구문
     */
    @PostMapping(value = "/token")
    @ResponseBody
    public void token(@RequestParam(value = "client_id") String clientId,
                               @RequestParam(value = "grant_type") String grantType,
                               @RequestParam(value = "code", required = false) String code,
                               @RequestParam(value = "refresh_token", required = false) String refreshToken,
                               HttpServletResponse response) throws IOException {
        try {
            OAuthToken token;

            switch (grantType) {
                case "authorization_code":
                    token = tokenService.getTokenByCode(code);
                    tokenUtil.responseAsJson(response, tokenUtil.convert(token));
                    break;
                case "refresh_token":
                    token = tokenService.getTokenByRefresh(refreshToken);
                    // 변경 전 user 가져오고 갱신
                    String beforeToken = token.getAccessToken();
                    token = tokenService.update(token);
                    userService.updateByToken(beforeToken, token.getAccessToken());
                    tokenUtil.responseAsJson(response, tokenUtil.convert(token));
                    break;
                default:
                    response.sendError(400, "invalid_grant_type");
                    break;
            }
        } catch (Exception ex) {
            switch (grantType) {
                case "authorization_code":
                    response.sendError(401, "invalid_code");
                    break;
                case "refresh_token":
                    response.sendError(401, "invalid_refresh_code");
                    break;
                default:
                    response.sendError(500, "OAuth server Internal Server Error.");
                    break;
            }
        }
    }

    /**
     * OAuth 2.0 에는 없는 Spec, 내부 예제에서 code 발급 및 응답을 위한 method
     *
     * @param clientId     OAuth 2.0 parameter 의 client_id. client 를 정의하기 위한 random string. (예제 기준 jklee)
     * @param state        client - OAuth server 간 통신 검증을 위한 상태 값
     * @param redirectURI  client 가 code 를 전달 받을 redirect_uri
     * @param scope        client 에서 해당 code 를 통해 발급 받을 token 의 계정에 대한 권한
     * @param loginId      token - id link 를 위해 받는 parameter 로, 개발자 custom parameter
     * @param response     redirect 를 위한 HttpServletResponse
     * @throws IOException Exception 대비 try/catch 구문
     */
    @PostMapping(value="/code")
    @ResponseBody
    public void code (@RequestParam(value = "clientId") String clientId,
                      @RequestParam(value = "state") String state,
                      @RequestParam(value = "redirectURI") String redirectURI,
                      @RequestParam(value = "scope") String scope,
                      @RequestParam(value = "loginId") String loginId,
                      HttpServletResponse response) throws IOException {
        try {
            String code = UUID.randomUUID().toString().replace("-", "");

            OAuthToken token = tokenService.save(code);
            userService.updateByloginId(loginId, token.getAccessToken());

            response.sendRedirect(redirectURI + "?code=" + code + "&state=" + state);
        } catch (Exception ex) {
            response.sendError(400, "invalid code");
        }
    }
}
