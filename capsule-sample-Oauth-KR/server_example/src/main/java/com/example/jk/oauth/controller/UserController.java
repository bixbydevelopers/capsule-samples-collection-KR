package com.example.jk.oauth.controller;

import com.example.jk.oauth.Util.ResourceUtil;
import com.example.jk.oauth.entity.JsonResult;
import com.example.jk.oauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
public class UserController {

    private final IUserService userService;
    private final ResourceUtil resourceUtil;

    @Autowired
    public UserController(IUserService userService, ResourceUtil resourceUtil) {
        this.userService = userService;
        this.resourceUtil = resourceUtil;
    }

    /**
     * 다른 계정을 저장하기 위한 method
     *
     * @param id loginId
     * @param password login password
     * @return JsonResult - Json format 에 맞는 custom object.
     *
     * default value : id: jklee / password: password
     */
    @GetMapping(value="/save")
    public JsonResult save(@RequestParam(value="id") String id, @RequestParam(value="password") String password) {
        try {
            userService.saveOrUpdate(id, password, null);
            return JsonResult.success();
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }

    /**
     * access_token 으로 계정을 가져오기 위한 method, 예제는 계정 - access_token 이 relation 이 걸려 있어서 가능하다.
     * 개발자가 custom 할 경우 각자의 logic 에 따라 구축 요망
     *
     * @param authorization access_token 이 포함된 header value, format 은 Bearer ACCESS_TOKEN 형식 (blank 포함)
    * @return JsonResult - Json format 에 맞는 custom object
     */
    @GetMapping(value = "/getByAccessToken")
    public JsonResult getByAccessToken(@RequestHeader(value = "Authorization") String authorization) {
        try {
            return JsonResult.success(userService.getByAccessToken(resourceUtil.extractToken(authorization)));
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }


}
