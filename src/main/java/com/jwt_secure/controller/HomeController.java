package com.jwt_secure.controller;

import com.jwt_secure.service.TokenService;
import com.jwt_secure.token.RSAKeyPairGenerator;
import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class HomeController {
    
    private RSAKeyPairGenerator keys;
//    private TokenService tokenService;

    public HomeController(RSAKeyPairGenerator keys) {
        this.keys = keys;
    }

//    public HomeController(RSAKeyPairGenerator keys, TokenService tokenService) {
//        this.keys = keys;
//        this.tokenService = tokenService;
//    }
    
    
    
    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "Hello in JWT!";
    }
    @GetMapping("/info")
    @ResponseBody
    public String info(Principal principal) {
        return "Principal is "+principal.getName()+"!";
    }
    @GetMapping("/key/private")
    @ResponseBody
    public String privateKey() {
        return "Private Key = "+keys.getPrivateKeyBase64();
    }
    @GetMapping("/key/public")
    @ResponseBody
    public String publicKey() {
        return "Private Key = "+keys.getPublicKeyBase64();
    }
    
//    @GetMapping("/login")
    public String logIn() {
        return "login.html";
    }
    @GetMapping("/log")
    public String log() {
        return "log.html";
    }
    
//    @PostMapping("/token")
//    public String token(Authentication auth) {
//        System.out.println("Name inside token + "+auth.getName());
//        String token = tokenService.generateToken(auth);
//        System.out.println("Token granted "+token);
//        System.out.println("Authorities granted "+auth.getAuthorities());
//        System.out.println("Credentials granted "+auth.getCredentials());
//        return token;
//    }
}
