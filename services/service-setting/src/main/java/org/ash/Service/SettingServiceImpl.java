package org.ash.Service;

import org.ash.DTO.PasswordUpdateDTO;
import org.ash.DTO.UserInfoDTO;
import org.ash.Entity.User;
import org.ash.Repository.SettingRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SettingServiceImpl implements SettingService {
    private static final Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);

    @Autowired
    private SettingRepositoryImpl settingRepository;

    @Override
    public User updateUserInfo(UserInfoDTO userInfoDto) {
        int affectedRows = settingRepository.updateUserInfo(userInfoDto);
        if (affectedRows > 0) {
            return settingRepository.findByAccount(userInfoDto.getAccount());
        }
        return null;
    }

    public User updatePassword(PasswordUpdateDTO passwordUpdateDto) {
        User user = settingRepository.findByAccount(passwordUpdateDto.getAccount());
        if (user != null && user.getPassword().equals(generateMD5(passwordUpdateDto.getOldPassword()))) {
            String newPassword = generateMD5(passwordUpdateDto.getNewPassword());
            int affectedRows = settingRepository.updatePassword(passwordUpdateDto.getAccount(), newPassword);
            if (affectedRows > 0) {
                return settingRepository.findByAccount(passwordUpdateDto.getAccount());
            }
        }
        return null;
    }

    @Override
    public User getUserInfo(Long account) {
        User user = settingRepository.findByAccount(account);
        logger.info("查询用户信息 - 账号: {}, 结果: {}", account, user);
        if (user != null) {
            logger.info("用户详细信息 - 用户名: {}, 头像: {}, 密码: {}", 
                user.getUserName(), 
                user.getUserPic(), 
                user.getPassword());
        }
        return user;
    }

    private String generateMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
