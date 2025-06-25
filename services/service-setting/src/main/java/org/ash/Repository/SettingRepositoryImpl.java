package org.ash.Repository;

import org.ash.DTO.UserInfoDTO;
import org.ash.Entity.User;
import org.ash.Mapper.SettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SettingRepositoryImpl  {
    @Autowired
    private SettingMapper settingMapper;

    public User findByAccount(Long account) {
        return settingMapper.selectByAccount(account);
    }

    public int updateUserInfo(UserInfoDTO userInfoDto) {
        return settingMapper.updateUserInfo(userInfoDto);
    }

    public int updatePassword(Long account, String password) {
        return settingMapper.updatePassword(account, password);
    }
}
