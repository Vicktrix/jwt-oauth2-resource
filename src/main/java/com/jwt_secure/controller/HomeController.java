package com.jwt_secure.controller;

import com.jwt_secure.token.RSAKeyPairGenerator;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    private RSAKeyPairGenerator keys;

    public HomeController(RSAKeyPairGenerator keys) {
        this.keys = keys;
    }

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
}
