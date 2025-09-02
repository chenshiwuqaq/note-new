package org.com.Controller;

import org.com.Entity.CustomTokenException;
import org.com.Entity.TokenDTO;
import org.com.Service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("token")
public class tokenController {
    @Autowired
    final
    TokenServiceImpl tokenService;

    public tokenController(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }
    @RequestMapping("getAccount")
    public ResponseEntity<?> getAccountByToken(@RequestBody TokenDTO token) {
        try {
            String account = tokenService.getAccountByToken(token.getToken());
            return ResponseEntity.ok( account);
        } catch (CustomTokenException ex) {
            throw ex;
        }
    }
    @RestControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(org.com.Entity.CustomTokenException.class)
        public ResponseEntity<Map<String, Object>> handleTokenException(
                org.com.Entity.CustomTokenException ex) {

            Map<String, Object> body = new HashMap<>();
            body.put("code", ex.getErrorCode());
            body.put("message", ex.getMessage());
            body.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }
}
