package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;

import javax.transaction.Transactional;

@Transactional
public interface IUserService {
    void saveOrUpdate(String id, String password, String accessToken);

    boolean checkPassword(String id, String password);

    User get(String id);

    User getByAccessToken(String accessToken);

    void updateByToken(String beforeToken, String afterToken);

    void updateByloginId(String loginId, String afterToken);
}
