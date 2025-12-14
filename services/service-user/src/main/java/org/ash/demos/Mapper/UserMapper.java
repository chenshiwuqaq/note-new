
package org.ash.demos.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.demos.Entity.user;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserMapper {
    @Select("select * from user where account=#{account}")
    user findUserByAccount(String account);
    @Insert("insert INTO user(account,password,user_name,user_pic,user_identify,create_time,update_time,email,phone_number) values" +
            "(#{account}, #{password} ,#{username},#{user_pic},#{user_identify},#{create_time},#{update_time},#{email},#{phone_number})")
    void register(@Param("account") String account, @Param("password") String password, @Param("username") String username,
                  @Param("user_pic") String user_pic, @Param("user_identify") String user_identify, @Param("create_time") String create_time,
                  @Param("update_time") String update_time,@Param("email") String email,@Param("phone_number") String phone_number);
    //查询邮箱是否已注册
    @Select("select email from user where email = #{email}")
    String findEmailByEmail(String email);
    //查询手机号是否已注册
    @Select("select phone_number from user where phone_number = #{phone_number}")
    String findPhoneNumberByPhoneNumber(String phone_number);
    @Select("select account from user")
    List<String> searchAccount();
    @Select("select password from user where account = #{account}")
    String findPasswordByAccount(@Param("account")String account);
    @Select("select user_name from user where account = #{account}")
    String findUsernameByAccount(@Param("account")String account);
    @Update("update user set user_pic=#{userPic} where account = #{account}")
    boolean updateAvatar(@Param("userPic")String userPic,@Param("account")String account);
    @Select("select user_name from user where account = #{account}")
    String findUsername(@Param("account")long account);
    // 根据主键查询用户信息
    @Select("select * from user where user_id = #{userId}")
    user selectUserById(Integer userId);
}
