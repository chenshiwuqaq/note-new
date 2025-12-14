package org.ash.demos.Service;

import org.ash.demos.DTO.UserDTO;
import org.ash.demos.Entity.user;

public interface UserService{
    String register(UserDTO userDTO);
    boolean updateAvatar(String user_Picture);
    String login(String account,String password);
    String getUserName(long account);
    user findUserByAccount(String account);
}