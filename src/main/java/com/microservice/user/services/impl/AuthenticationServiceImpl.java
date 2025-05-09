package com.microservice.user.services.impl;

import com.microservice.user.dtos.UserEntityDTO.JWTTokenDTO;
import com.microservice.user.dtos.UserEntityDTO.UserAuthenticationData;
import com.microservice.user.services.AuthenticationService;
import com.microservice.user.services.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public JWTTokenDTO authenticate(UserAuthenticationData userAuthenticationData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthenticationData.email(), userAuthenticationData.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        return new JWTTokenDTO(token);
    }
}