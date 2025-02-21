package org.com.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public TokenDTO() {
    }
}
