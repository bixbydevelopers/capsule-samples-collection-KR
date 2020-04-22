package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.OAuthToken;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.transaction.Transactional;

@Transactional
public interface ITokenService {
    OAuthToken save(String code);

    OAuthToken update(OAuthToken token);

    OAuthToken getTokenByCode(String code);

    OAuthToken getTokenByAccess(String accessToken);

    OAuthToken getTokenByRefresh(String refreshToken);
}
