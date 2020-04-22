package com.example.jk.oauth.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OAuthTokenDTO {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private Date expire;
    private String code;
    private String error;
    private String error_description;
}
