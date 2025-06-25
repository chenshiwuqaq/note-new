package org.ash.Controller;

import org.ash.DTO.PasswordUpdateDTO;
import org.ash.DTO.UserInfoDTO;
import org.ash.Entity.User;
import org.ash.Service.SettingService;
import org.com.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/user")
public class SettingController {
    @Autowired
    private SettingService userService;

    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            User updatedUser = userService.updateUserInfo(userInfoDTO);
            if (updatedUser != null) {
                return Result.success(updatedUser);
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新失败: " + e.getMessage());
        }
    }

    @PostMapping("/password")
    public Result updatePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        try {
            User user = userService.updatePassword(passwordUpdateDTO);
            if (user != null) {
                return Result.success("密码更新成功");
            } else {
                return Result.error("密码更新失败");
            }
        } catch (Exception e) {
            return Result.error("密码更新失败: " + e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result getUserInfo(@RequestParam("account") Long account) {
        try {
            User user = userService.getUserInfo(account);
            if (user != null) {
                return Result.success(user);
            } else {
                return Result.error("未找到用户信息");
            }
        } catch (Exception e) {
            return Result.error("获取用户信息失败: " + e.getMessage());
        }
    }
}