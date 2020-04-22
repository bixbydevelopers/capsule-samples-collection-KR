package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.repository.SimpleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SimpleData CRU 관련 Service 입니다.
 *
 * 보시면 다 아실터이니 자세한 설명은 생략하겠습니다.
 */
@Service
public class SimpleDataService implements ISimpleDataService {

    private final SimpleDataRepository simpleDataRepository;

    @Autowired
    public SimpleDataService(SimpleDataRepository simpleDataRepository) {
        this.simpleDataRepository = simpleDataRepository;
    }

    @Override
    public void save (User user, String message) {
        SimpleData simpleData = new SimpleData();
        simpleData.setMessage(message);
        simpleData.setUser(user);
        simpleDataRepository.save(simpleData);
    }

    @Override
    public void update(User user, String message) {
        SimpleData simpleData = simpleDataRepository.findByUser(user);

        simpleData.setMessage(message);
        simpleDataRepository.save(simpleData);
    }

    @Override
    public SimpleData getByUser(User user) {
        return simpleDataRepository.findByUser(user);
    }

    @Override
    public List<SimpleData> listByUser(User user) {
        return simpleDataRepository.findAllByUser(user);
    }
}
