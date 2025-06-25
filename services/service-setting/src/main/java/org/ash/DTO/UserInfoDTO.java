package org.ash.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class UserInfoDTO {
    private Long account;
    private String userName;
    private String remark;
    private String region;
}
