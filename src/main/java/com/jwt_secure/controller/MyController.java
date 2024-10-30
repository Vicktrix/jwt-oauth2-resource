package com.jwt_secure.controller;

import com.jwt_secure.model.LoginDto;
import com.jwt_secure.model.RegisterDto;
import com.jwt_secure.service.AppUserService;
import com.jwt_secure.service.TokenService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    
    private AppUserService appUserService;
    private TokenService tokenService;
    
    public MyController(AppUserService appUserService, TokenService tokenService) {
        this.appUserService = appUserService;
        this.tokenService = tokenService;
    }
    @GetMapping("/")
    public String home() {
        return "Home page";
    }
    @GetMapping("/store")
    public String store() {
        return "Store page";
    }
    @GetMapping("/admin/home")
    public String adminHome() {
        return "Admin Home page";
    }
    @GetMapping("/user/home")
    public String userHome() {
        return "User Home page";
    }
    @GetMapping("/profile")
    public ResponseEntity<Object> profile(Authentication auth) {
        Map<String, Object> response = new HashMap<>();
        response.put("userName", auth.getName());
        response.put("Authorities", auth.getAuthorities());
        response.put("AppUser", appUserService.getAppUserByName(auth.getName()).get());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> userRegister(@RequestBody RegisterDto registerDto) {
        return mapUserDetailsToResponse(appUserService.saveUserToDB(registerDto));
    }
    @GetMapping("/login")
    public String userLogin() {
        return "User Home page";
    }
    @PostMapping("/login")
    public ResponseEntity<Object> userLogining(@RequestBody LoginDto login) {
        return mapUserDetailsToResponse(appUserService.loginUserFromDB(login));

    }
    private ResponseEntity<Object> mapUserDetailsToResponse(Optional<UserDetails> user) {
        if(user.isEmpty())
            return ResponseEntity.badRequest().body(" User not registred in DB ");
        String token = tokenService.generateTokenFromUserDetail(user.get());
        Map<String, Object> response = new HashMap<>();
        response.put("user", user.get());
        response.put("scope", user.get().getAuthorities().toString());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }    
}
