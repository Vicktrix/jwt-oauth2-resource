package com.jwt_secure.config;

import com.jwt_secure.service.AppUserService;
import com.jwt_secure.token.DecodeEncodeService;
import com.jwt_secure.token.RSAKeyPairGenerator;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPublicKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private DecodeEncodeService decodeEncodeService;
    private RSAKeyPairGenerator keyPairGenerator;
    private PasswordEncoder encoder;

    public SecurityConfig(DecodeEncodeService decodeEncodeService, RSAKeyPairGenerator keyPairGenerator, PasswordEncoder encoder) {
        this.decodeEncodeService = decodeEncodeService;
        this.keyPairGenerator = keyPairGenerator;
        this.encoder = encoder;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/store/**", "/account", 
                        "/register", "/login", "/token").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(auth -> auth.jwt(withDefaults()))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))    
            .build();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey)keyPairGenerator.getPublicKey()).build();
    }
    
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder((RSAPublicKey)keyPairGenerator.getPublicKey())
                .privateKey(keyPairGenerator.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AppUserService userService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(encoder);
        return new ProviderManager(provider);
    }
}
