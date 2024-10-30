package com.jwt_secure.config;

import com.jwt_secure.token.DecodeEncodeService;
import com.jwt_secure.token.RSAKeyPairGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TokenConfig {
    
    @Bean
    public DecodeEncodeService decodeEncodeService() {
        return new DecodeEncodeService();
    }
    
    @Bean
    public RSAKeyPairGenerator pairGenerator() {
        return new RSAKeyPairGenerator();
    }
    
    //--------------------------------------------------
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
    
}
