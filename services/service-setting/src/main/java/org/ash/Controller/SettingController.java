package org.ash.Controller;

import org.ash.DTO.CityDTO;
import org.ash.DTO.PasswordUpdateDTO;
import org.ash.DTO.UserInfoDTO;
import org.ash.DTO.UserInfoWithCitiesDTO;
import org.ash.Entity.User;
import org.ash.Service.CityService;
import org.ash.Service.SettingService;
import org.com.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SettingController {
    @Autowired
    private SettingService userService;
    @Autowired
    private CityService cityService;
    /**
     * 城市搜索接口
     * @param keyword 搜索关键词
     * @return 匹配的城市列表
     */
    @GetMapping("/search")
    public Result searchCities(@RequestParam String keyword) {
        try {
            List<CityDTO> cities = cityService.searchCities(keyword);
            return Result.success(cities);
        } catch (Exception e) {
            return Result.error("城市搜索失败: " + e.getMessage());
        }
    }
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
            List<CityDTO> cityDTOList = cityService.selectAllCities();

            if (user != null) {
                // 创建复合 DTO 对象并设置数据
                UserInfoWithCitiesDTO responseData = new UserInfoWithCitiesDTO();
                responseData.setUser(user);
                responseData.setCities(cityDTOList);

                // 将复合 DTO 作为 data 返回
                return Result.success(responseData);
            } else {
                return Result.error("未找到用户信息");
            }
        } catch (Exception e) {
            return Result.error("获取用户信息失败: " + e.getMessage());
        }
    }
}