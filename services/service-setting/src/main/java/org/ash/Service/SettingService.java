package org.ash.Service;

import org.ash.DTO.UserInfoDTO;
import org.ash.DTO.PasswordUpdateDTO;
import org.ash.Entity.User;

public interface SettingService {

    User updateUserInfo(UserInfoDTO userInfoDto);
    User updatePassword(PasswordUpdateDTO passwordUpdateDto);
    User getUserInfo(Long account);
}

