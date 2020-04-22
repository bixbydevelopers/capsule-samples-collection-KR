package com.example.jk.oauth.entity;

import lombok.Data;

@Data
public class SimpleDataDTO {
    private String id;
    private String message;

    public SimpleDataDTO (SimpleData simpleData) {
        id = simpleData.getUser().getLoginId();
        message = simpleData.getMessage();
    }
}
