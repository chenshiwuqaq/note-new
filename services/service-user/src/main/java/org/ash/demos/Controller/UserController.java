package org.ash.demos.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.ash.demos.DTO.LoginDTO;
import org.ash.demos.DTO.UserDTO;
import org.ash.demos.Entity.user;
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
//    public Result login(@RequestBody LoginDTO loginDTO){
//        String result=userService.login(loginDTO.getAccountDTO(),loginDTO.getPasswordDTO());
//        if ("密码错误".equals(result)){
//            return Result.error("密码错误");
//        }else if ("账户不存在".equals(result)){
//            return Result.error("账户不存在");
//        }else {
//            return Result.success(result);
//        }
//    }
    public Result<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            String token = userService.login(loginDTO.getAccountDTO(), loginDTO.getPasswordDTO());
            if (token != null && !token.isEmpty()) {
                // 获取用户信息
                user user = userService.findUserByAccount(loginDTO.getAccountDTO());
                if (user != null) {
                    // 存储用户信息到Session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("userAccount", user.getAccount());
                    session.setAttribute("userName", user.getUserName());
                    session.setAttribute("userPic", user.getUserPic());

                    // 设置Session过期时间（30分钟）
                    session.setMaxInactiveInterval(30 * 60);

                    // 可以记录日志
                    System.out.println("用户登录成功，Session ID: " + session.getId() +
                            ", 用户ID: " + user.getId() +
                            ", 账号: " + user.getAccount());
                }
                return Result.success(token);
            }
            return Result.error("登录失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("登录异常: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public Result registerUser(@RequestBody UserDTO userDTO){
        String result=userService.register(userDTO);
        if("邮箱或手机号已被注册".equals(result)){
            return Result.error("邮箱或手机号已被注册");
        }else{
            return Result.success("注册成功",result);
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

    // 登出接口
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Result.success("登出成功");
    }

    // 检查登录状态
    @GetMapping("/checkLogin")
    public Result<Boolean> checkLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            return Result.success(true);
        }
        return Result.success(true);
    }
    // 获取当前用户信息
    @GetMapping("/current")
    public Result<user> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userObj = session.getAttribute("user");
            if (userObj instanceof user) {
                return Result.success((user) userObj);
            }
        }
        return Result.error("用户未登录");
    }
}