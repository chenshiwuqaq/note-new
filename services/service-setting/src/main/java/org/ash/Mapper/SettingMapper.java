package org.ash.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ash.DTO.UserInfoDTO;
import org.ash.Entity.User;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SettingMapper {

    @Select("SELECT * FROM user WHERE account = #{account}")
    @Results({
        @Result(property = "id", column = "user_id"),
        @Result(property = "account", column = "account"),
        @Result(property = "password", column = "password"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "userPic", column = "user_pic"),
        @Result(property = "userIdentify", column = "user_identify"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time"),
        @Result(property = "remark", column = "remark"),
        @Result(property = "region", column = "region")
    })
    User selectByAccount(@Param("account") Long account);

    @Update("UPDATE user SET user_name = #{userName}, remark = #{remark}, region = #{region}, update_time = NOW() WHERE account = #{account}")
    int updateUserInfo(UserInfoDTO userInfoDto);

    @Update("UPDATE user SET password = #{password}, update_time = NOW() WHERE account = #{account}")
    int updatePassword(@Param("account") Long account, @Param("password") String password);
}
