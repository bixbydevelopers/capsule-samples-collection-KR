package com.example.jk.oauth.controller;

import com.example.jk.oauth.Util.ResourceUtil;
import com.example.jk.oauth.entity.JsonResult;
import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.service.ISimpleDataService;
import com.example.jk.oauth.service.IUserService;
import com.example.jk.oauth.service.TokenResponseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/simpleData")
public class SimpleDataController {

    private final ISimpleDataService simpleDataService;
    private final IUserService userService;
    private final TokenResponseComponent tokenResponseComponent;
    private final ResourceUtil resourceUtil;

    @Autowired
    public SimpleDataController(ISimpleDataService simpleDataService, IUserService userService, TokenResponseComponent tokenResponseComponent, ResourceUtil resourceUtil) {
        this.simpleDataService = simpleDataService;
        this.userService = userService;
        this.tokenResponseComponent = tokenResponseComponent;
        this.resourceUtil = resourceUtil;
    }

    /**
     * SimpleData 저장하기 위한 method
     *
     * @param authorization access_token 이 포함된 header value, format 은 Bearer ACCESS_TOKEN 형식 (blank 포함)
     * @param message       SimpleData 에 저장하기 위한 parameter
     * @return              JsonResult - Json format 에 맞는 custom object.
     */
    @GetMapping(value = "/save")
    public JsonResult save (
            @RequestHeader(value = "Authorization") String authorization,
            @RequestParam(value = "message") String message) {
        try {
            User user = tokenResponseComponent.getUserByToken(authorization);
            simpleDataService.save(user, message);

            return JsonResult.success();
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }

    /**
     * SimpleData 갱신하기 위한 method
     *
     * @param authorization access_token 이 포함된 header value, format 은 Bearer ACCESS_TOKEN 형식 (blank 포함)
     * @param message       SimpleData 에 저장하기 위한 parameter
     * @return              JsonResult - Json format 에 맞는 custom object.
     */
    @GetMapping(value = "/update")
    public JsonResult update (@RequestHeader(value = "Authorization") String authorization,
                              @RequestParam(value = "message") String message) {
        try {
            User user = tokenResponseComponent.getUserByToken(authorization);
            simpleDataService.update(user, message);

            return JsonResult.success();
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }

    /**
     * client 에서 (Bixby capsule) 자원 (SimpleData)을 요청하는 method
     *
     * @param authorization access_token 이 포함된 header value, format 은 Bearer ACCESS_TOKEN 형식 (blank 포함)
     * @param response      redirect 를 위한 HttpServletResponse
     * @return              JsonResult - Json format 에 맞는 custom object.
     */
    @GetMapping(value = "/get")
    public JsonResult get(
            @RequestHeader(value = "Authorization") String authorization,
            HttpServletResponse response) {
        try {
            boolean result = tokenResponseComponent.checkToken(authorization);

            if (!result) {
                response.sendError(400, "invalid_token");
                return JsonResult.failure("invalid_token");
            }

            User user = userService.getByAccessToken(resourceUtil.extractToken(authorization));
            return JsonResult.success(resourceUtil.convert(simpleDataService.getByUser(user)));
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }

    /**
     *
     * @param authorization access_token 이 포함된 header value, format 은 Bearer ACCESS_TOKEN 형식 (blank 포함)
     * @param response      redirect 를 위한 HttpServletResponse
     * @return              JsonResult - Json format 에 맞는 custom object.
     */
    @GetMapping(value = "/list")
    public JsonResult list(@RequestHeader(value = "Authorization") String authorization,
                           HttpServletResponse response) {
        try {
            boolean result = tokenResponseComponent.checkToken(authorization);

            if (!result) {
                response.sendError(400, "invalid_token");
                return JsonResult.failure("invalid_token");
            }

            User user = userService.getByAccessToken(resourceUtil.extractToken(authorization));
            return JsonResult.success(resourceUtil.convert(simpleDataService.listByUser(user)));
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }
}
