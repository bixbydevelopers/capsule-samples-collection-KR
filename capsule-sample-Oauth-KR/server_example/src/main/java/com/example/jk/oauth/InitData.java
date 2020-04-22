package com.example.jk.oauth;

import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitData implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void run(ApplicationArguments args) {
        User user = new User();
        user.setLoginId("jklee");
        user.setPassword(passwordEncoder.encode("password"));

        if (userRepository.findByLoginId("jklee") == null) {
            userRepository.save(user);
        }
    }
}
