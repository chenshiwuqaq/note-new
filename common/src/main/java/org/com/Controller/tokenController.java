package org.com.Controller;

import org.com.Entity.TokenDTO;
import org.com.Service.TokenServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("token")
public class tokenController {
    final
    TokenServiceImpl tokenService;

    public tokenController(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }
    @RequestMapping("getAccount")
    public String getAccountByToken(@RequestBody TokenDTO token){
        if (token == null){
            System.out.println("后端接收值为空");
            return "token为空";
        }
        return tokenService.getAccountByToken(token.getToken());
    }
}
