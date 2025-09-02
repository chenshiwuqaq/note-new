package org.ash.demos.Controller;

import org.ash.demos.DTO.LoginDTO;
import org.ash.demos.DTO.UserDTO;
import org.ash.demos.Service.UserServiceImpl;
import org.com.Entity.Result;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    @Autowired

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        String result=userService.login(loginDTO.getAccountDTO(),loginDTO.getPasswordDTO());
        if ("密码错误".equals(result)){
            return Result.error("密码错误");
        }else if ("账户不存在".equals(result)){
            return Result.error("账户不存在");
        }else {
            return Result.success(result);
        }
    }
    @PostMapping("/register")
    public Result registerUser(@RequestBody UserDTO userDTO){
        if ("注册成功".equals(userService.register(userDTO))){
            return Result.success("注册成功");
        }else {
            return Result.error("注册失败");
        }
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String user_PictureDTO){
        if(userService.updateAvatar(user_PictureDTO)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }
    @PostMapping("/getUserName")
    public Result getUserName(@RequestParam("account") long account){
        return Result.success(userService.getUserName(account));
    }
}