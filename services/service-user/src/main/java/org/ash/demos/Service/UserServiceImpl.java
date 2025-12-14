package org.ash.demos.Service;

import org.ash.demos.DTO.UserDTO;
import org.ash.demos.Entity.user;
import org.ash.demos.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.com.utils.ThreadLocalUtils;
import org.com.utils.JwtUtil;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final String CHARACTERS = "0123456789";
    private static final int ACCOUNT_LENGTH = 6;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String register(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String phoneNumber = userDTO.getPhoneNumber();

        // 分别检查邮箱和手机号（两个都要检查）
        if (email != null && userMapper.findEmailByEmail(email) != null) {
            return "邮箱或手机号已被注册";
        }
        if (phoneNumber != null && userMapper.findPhoneNumberByPhoneNumber(phoneNumber) != null) {
            return "邮箱或手机号已被注册";
        }

        // 3. 生成唯一账号
        String accountStr = generateUniqueAccount();
        // 4. 注册用户
        userMapper.register(
                accountStr,
                userDTO.getPassword(),
                userDTO.getUserName(),
                userDTO.getUserPic(),
                userDTO.getUserIdentify(),
                userDTO.getCreateTime(),
                userDTO.getUpdateTime(),
                email,
                phoneNumber
        );
        return accountStr;
    }

    private String generateUniqueAccount() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder account = new StringBuilder(ACCOUNT_LENGTH);

        while (true) {
            // 生成随机账号
            for (int i = 0; i < ACCOUNT_LENGTH; i++) {
                int index = secureRandom.nextInt(CHARACTERS.length());
                account.append(CHARACTERS.charAt(index));
            }
            String accountStr = account.toString();

            List<String> accounts = userMapper.searchAccount();
            if (!accounts.contains(accountStr)) {
                return accountStr; // 返回唯一账号
            }
            // 如果账号已存在，清空 StringBuilder 重新生成
            account.setLength(0);
        }
    }

    @Override
    public boolean updateAvatar(String user_Picture) {
        Map<String, Object> map = ThreadLocalUtils.get();
        return userMapper.updateAvatar(user_Picture, (String) map.get("username"));
    }

    //    @Override
//    public String login(String account, String password) {
//        String SearchPassword = userMapper.findPasswordByAccount(account);
//        if(password == null){
//            return "账户不存在";
//        }
//        else if(password.equals(SearchPassword)){
//            return JwtUtil.genToken(account);
//        }else {
//            return "密码错误";
//        }
//    }
    @Override
    public String login(String account, String password) {
        // 查询用户
        user user = userMapper.findUserByAccount(account);
        if (user == null) {
            return null; // 用户不存在
        }

        // 验证密码
        String dbPassword = userMapper.findPasswordByAccount(account);
        if (dbPassword != null && dbPassword.equals(password)) {
            // 登录成功，可以生成token或直接返回成功标识
            return "login_success_token_" + System.currentTimeMillis();
        }

        return null; // 密码错误
    }

    @Override
    public String getUserName(long account) {
        return userMapper.findUsername(account);
    }

    @Override
    public user findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }
}