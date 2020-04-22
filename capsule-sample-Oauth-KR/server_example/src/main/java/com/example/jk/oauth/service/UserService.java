package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void saveOrUpdate(String id, String password, String accessToken) {
        User user = userRepository.findByLoginId(id);

        if (user == null) {
            user = new User();
            user.setLoginId(id);
        }

        if (StringUtils.hasText(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        if (StringUtils.hasText(accessToken)) {
            user.setAccessToken(accessToken);
        }

        userRepository.save(user);
    }

    @Override
    public boolean checkPassword(String id, String password) {
        User user = userRepository.findByLoginId(id);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User get(String id) {
        return userRepository.findByLoginId(id);
    }

    @Override
    public User getByAccessToken(String accessToken) {
        return userRepository.findByAccessToken(accessToken);
    }

    @Override
    public void updateByToken(String beforeToken, String afterToken) {
        User user = userRepository.findByAccessToken(beforeToken);

        user.setAccessToken(afterToken);
        userRepository.save(user);
    }

    @Override
    public void updateByloginId(String loginId, String afterToken) {
        User user = userRepository.findByLoginId(loginId);

        user.setAccessToken(afterToken);
        userRepository.save(user);
    }
}
