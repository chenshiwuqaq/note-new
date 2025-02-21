package org.com.Service;

import org.com.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

    @Override
    public String getAccountByToken(String token) {
        return JwtUtil.verifyToken(token);
    }
}
