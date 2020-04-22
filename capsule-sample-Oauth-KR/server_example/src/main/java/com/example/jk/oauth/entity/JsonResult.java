package com.example.jk.oauth.entity;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * JsonResponse format 에 대응하는 custom JsonResult Object
 */
@Data
public class JsonResult {
    private boolean success;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Object data;

    private String message;

    public static JsonResult success(Object data) {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static JsonResult success() {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        return result;
    }

    public static JsonResult failure(String message) {
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
