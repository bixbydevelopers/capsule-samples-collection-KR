package com.example.jk.oauth.repository;

import com.example.jk.oauth.entity.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TokenRepository extends JpaRepository<OAuthToken, String> {
    OAuthToken findByAccessTokenAndExpireAfter(String accessToken, Date date);
    OAuthToken findByCode(String code);
    OAuthToken findByRefreshToken(String refreshToken);
}
