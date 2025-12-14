package org.ash.demos.Mapper;


import org.apache.ibatis.annotations.*;
import org.ash.demos.Entity.TeamMember;

import java.util.List;

@Mapper
public interface TeamMemberMapper {

    // 添加团队成员（用于创建团队时添加管理员）
    @Insert("INSERT INTO team_member (team_id, user_id, account, user_name, user_pic, role, join_time, invite_status) " +
            "VALUES (#{teamId}, #{userId}, #{account}, #{userName}, #{userPic}, #{role}, #{joinTime}, #{inviteStatus})")
    int insertTeamMember(TeamMember teamMember);

    // 发送邀请（创建邀请记录）
    @Insert("INSERT INTO team_member (team_id, user_id, account, user_name, user_pic, role, invite_time, invite_status) " +
            "VALUES (#{teamId}, #{userId}, #{account}, #{userName}, #{userPic}, 'member', #{inviteTime}, 0)")
    int insertInvitation(TeamMember teamMember);

    // 接受邀请
    @Update("UPDATE team_member SET invite_status = 1, accept_time = #{acceptTime}, join_time = #{acceptTime} " +
            "WHERE team_id = #{teamId} AND user_id = #{userId} AND invite_status = 0")
    int acceptInvitation(@Param("teamId") Integer teamId, @Param("userId") Integer userId, @Param("acceptTime") String acceptTime);

    // 拒绝邀请
    @Update("UPDATE team_member SET invite_status = 2 WHERE team_id = #{teamId} AND user_id = #{userId} AND invite_status = 0")
    int rejectInvitation(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    // 查询团队成员列表（已加入的）
    @Select("SELECT tm.*, u.region, t.team_name " +
            "FROM team_member tm " +
            "LEFT JOIN user u ON tm.user_id = u.user_id " +
            "LEFT JOIN team t ON tm.team_id = t.team_id " +
            "WHERE tm.team_id = #{teamId} AND tm.invite_status = 1 " +
            "ORDER BY CASE tm.role WHEN 'admin' THEN 1 ELSE 2 END, tm.join_time")
    List<TeamMember> selectTeamMembers(Integer teamId);

    // 查询用户收到的邀请
    @Select("SELECT tm.*, t.team_name, t.team_desc, u.user_name as invite_user_name " +
            "FROM team_member tm " +
            "JOIN team t ON tm.team_id = t.team_id " +
            "JOIN user u ON t.create_user_id = u.user_id " +
            "WHERE tm.user_id = #{userId} AND tm.invite_status = 0 AND t.status = 1")
    List<TeamMember> selectInvitationsByUser(Integer userId);

    // 查询团队成员信息
    @Select("SELECT * FROM team_member WHERE team_id = #{teamId} AND user_id = #{userId}")
    TeamMember selectTeamMember(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    // 移除成员（管理员操作）
    @Delete("DELETE FROM team_member WHERE team_id = #{teamId} AND user_id = #{userId} AND role = 'member'")
    int removeMember(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    // 退出团队（成员自己操作）
    @Delete("DELETE FROM team_member WHERE team_id = #{teamId} AND user_id = #{userId}")
    int quitTeam(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    // 检查用户是否已在团队中
    @Select("SELECT COUNT(*) FROM team_member " +
            "WHERE team_id = #{teamId} AND user_id = #{userId} AND invite_status = 1")
    int checkUserInTeam(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    // 检查邀请是否已存在
    @Select("SELECT COUNT(*) FROM team_member " +
            "WHERE team_id = #{teamId} AND user_id = #{userId} AND invite_status = 0")
    int checkInvitationExists(@Param("teamId") Integer teamId, @Param("userId") Integer userId);
}