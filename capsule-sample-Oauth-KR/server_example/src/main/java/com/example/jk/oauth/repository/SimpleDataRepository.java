package com.example.jk.oauth.repository;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimpleDataRepository extends JpaRepository<SimpleData, Object> {
    SimpleData findByUser(User user);
    List<SimpleData> findAllByUser(User user);
}
