package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * OAuth 2.0 Custom Token Store
 */
@Service
public class TokenService implements ITokenService {

    private static final int expires_in = 30*24*60*60;

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * OAuth Token 을 저장하는 부분. authorize end-point 에서 발급 받은 code 로 token 을 생성한다.
     *
     * @param code authorize end-point 에서 발급 받은 code.
     *             token 과 같이 저장 후에 token end-point 에서 요청 시 code 로 찾기 위함
     * @return     OAuthToken
     */
    @Override
    public OAuthToken save(String code) {
        OAuthToken token = new OAuthToken();

        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expires_in);

        token.setCode(code);
        token.setCreatedDate(new Date());
        token.setExpire(calendar.getTime());
        token.setExpiresIn(expires_in);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        tokenRepository.save(token);

        return token;
    }

    /**
     * OAuth Token 을 갱신하는 부분. token end-point 에서 발급 token 을 갱신한다.
     *
     * @param token 갱신이 필요한 OAuthToken
     * @return      갱신된 OAuthToken
     */
    @Override
    public OAuthToken update(OAuthToken token) {
        String accessToken = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expires_in);

        token.setExpire(calendar.getTime());
        token.setExpiresIn(expires_in);
        token.setAccessToken(accessToken);

        return token;
    }

    /**
     * authorize end-point 에서 발급 받은 code 를 통해 token get
     *
     * @param code authorize end-point 에서 발급 받은 code
     * @return     OAuthToken
     */
    @Override
    public OAuthToken getTokenByCode(String code) {
        return tokenRepository.findByCode(code);
    }

    /**
     * token end-point 에서 발급 받은 access_token 을 통해 token get
     * 내부에서 쓰는 Jpa method 의 두번째 parameter 는 token expire date
     *
     * @param accessToken token end-point 에서 발급 받은 access_token
     * @return            OAuthToken
     */
    @Override
    public OAuthToken getTokenByAccess(String accessToken) {
        return tokenRepository.findByAccessTokenAndExpireAfter(accessToken, new Date());
    }

    /**
     * token end-point 에서 발급 받은 refresh_token 을 통해 token get, 재발급을 위해 찾는 method
     *
     * @param refreshToken token end-point 에서 발급 받은 refresh_token
     * @return             OAuthToken
     */
    @Override
    public OAuthToken getTokenByRefresh(String refreshToken){
        return tokenRepository.findByRefreshToken(refreshToken);
    }
}
