package org.ash.demos.Service;

import org.ash.demos.DTO.UserDTO;
import org.ash.demos.Mapper.UserMapper;
import org.ash.demos.utils.JwtUtil;
import org.ash.demos.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    private static final String CHARACTERS = "0123456789";
    private static final int ACCOUNT_LENGTH = 6;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public String register(UserDTO userDTO) {
        String accountStr = generateUniqueAccount();
        userMapper.register(
                accountStr,
                userDTO.getPassword(),
                userDTO.getUserName(),
                userDTO.getUserPic(),
                userDTO.getUserIdentify(),
                userDTO.getCreateTime(),
                userDTO.getUpdateTime()
        );
        return "注册成功";
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
        Map<String,Object> map = ThreadLocalUtils.get();
        return userMapper.updateAvatar(user_Picture,(String) map.get("username"));
    }

    @Override
    public String login(String account, String password) {
        String SearchPassword = userMapper.findPasswordByAccount(account);
        if(password == null){
            return "账户不存在";
        }
        else if(password.equals(SearchPassword)){
            Map<String,Object> claims=new HashMap<>();
            claims.put("account",account);
            String username=userMapper.findUsernameByAccount(account);
            claims.put("username",username);
            return JwtUtil.GenToken(claims);
        }else {
            return "密码错误";
        }
    }

}