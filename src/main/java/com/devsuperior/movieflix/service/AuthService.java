package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.config.AuthConfig;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.exceptions.UnauthorizedException;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {

    private final AuthConfig authConfig;
   final
   UserRepository userRepository;

    public AuthService(UserRepository userRepository, AuthConfig authConfig) {
        this.userRepository = userRepository;
        this.authConfig = authConfig;
    }

    @Transactional
    public User authenticated(){
        String username = authConfig.getName();
        System.out.println( "username: " + username);

        User user = userRepository.findByEmail(username)
               .orElseThrow(() -> new UnauthorizedException("usuário inválido"));

        return user;
    }
}
