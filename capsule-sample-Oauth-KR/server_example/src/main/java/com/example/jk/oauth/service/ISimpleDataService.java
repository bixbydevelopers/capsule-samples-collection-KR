package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ISimpleDataService {
    void save(User user, String message);

    void update(User user, String message);

    SimpleData getByUser(User user);

    List<SimpleData> listByUser(User user);
}
