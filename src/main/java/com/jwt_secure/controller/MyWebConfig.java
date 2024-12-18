package com.jwt_secure.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


    //  https://docs.spring.io/spring-framework/docs/5.0.0.RELEASE/spring-framework-reference/web.html#mvc-config-customize
@Configuration
@EnableWebMvc
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/")
////                .setCachePeriod(31556926);        // cache page to one year
                .setCachePeriod(10);                // cach page to 10 second
    }

    /*
        Maybe this CORS setting not wokring for my API becouse it`s setting for MVC
    
    */
    
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**").allowedOrigins("http://localhost:8383");
//        registry.addMapping("/**").allowedOrigins("http://localhost:8383")
//                .allowedMethods("POST", "OPTIONS", "GET", "HEAD", "DELETE", "PUT")
//                .allowedHeaders("Authorization")
//                .allowCredentials(true)
//                ;
////        registry.addMapping(" ").allowedMethods("OPTIONS").
//        
//    }
    
}
