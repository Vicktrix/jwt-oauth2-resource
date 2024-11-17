package com.jwt_secure.controller;

import com.jwt_secure.token.RSAKeyPairGenerator;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*      This MVC controller 
        all request must starts with URL /resources
        all REST request with same URL will be override by MVC
        all MVC resources maps in WebMvcConfigurer(impl. MyWebConfig)
            and located "classpath:/static/" 
*/
@Controller
@RequestMapping("/resources")
public class HomeController {
    
    private RSAKeyPairGenerator keys;

    public HomeController(RSAKeyPairGenerator keys) {
        this.keys = keys;
    }

    @GetMapping("/store2")
    @ResponseBody
    public String store() {
        return "Store page 2!";
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
    
    @GetMapping("/log")
    public String log() {
        return "log.html";
    }
}
