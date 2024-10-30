package com.jwt_secure.controller;

import com.jwt_secure.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    
    private TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @PostMapping("/token")
    public String token(Authentication auth) {
        System.out.println("AUTH  is null? "+auth);
        System.out.println("Name inside token + "+auth.getName());
        String token = tokenService.generateToken(auth);
        System.out.println("Token granted "+token);
        System.out.println("Authorities granted "+auth.getAuthorities());
        System.out.println("Credentials granted "+auth.getCredentials());
        return token;
    }
}
