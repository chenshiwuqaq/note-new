package org.ash.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordUpdateDTO {
    private Long account;
    private String oldPassword;
    private String newPassword;
}
