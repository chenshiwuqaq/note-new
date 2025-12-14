package org.ash.demos.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.demos.Entity.Team;

import java.util.List;

@Mapper
public interface TeamMapper {

    // 创建团队
    @Insert("INSERT INTO team (team_name, team_desc, create_user_id, create_time, update_time, status) " +
            "VALUES (#{teamName}, #{teamDesc}, #{createUserId}, #{createTime}, #{updateTime}, 1)")
    @Options(useGeneratedKeys = true, keyProperty = "teamId")
    int insertTeam(Team team);

    // 根据ID查询团队
    @Select("SELECT * FROM team WHERE team_id = #{teamId} AND status = 1")
    Team selectTeamById(Integer teamId);

    // 查询用户创建的团队
    @Select("SELECT t.*, u.user_name as create_user_name " +
            "FROM team t LEFT JOIN user u ON t.create_user_id = u.user_id " +
            "WHERE t.create_user_id = #{userId} AND t.status = 1")
    List<Team> selectTeamsByCreator(Integer userId);

    // 查询用户加入的团队（包含用户角色）
    @Select("SELECT t.*, tm.role, u.user_name as create_user_name, " +
            "(SELECT COUNT(*) FROM team_member WHERE team_id = t.team_id AND invite_status = 1) as member_count " +
            "FROM team t " +
            "JOIN team_member tm ON t.team_id = tm.team_id " +
            "LEFT JOIN user u ON t.create_user_id = u.user_id " +
            "WHERE tm.user_id = #{userId} AND tm.invite_status = 1 AND t.status = 1 " +
            "ORDER BY tm.join_time DESC")
    List<Team> selectTeamsByMember(Integer userId);

    // 更新团队信息
    @Update("UPDATE team SET team_name = #{teamName}, team_desc = #{teamDesc}, update_time = #{updateTime} " +
            "WHERE team_id = #{teamId}")
    int updateTeam(Team team);

    // 解散团队
    @Update("UPDATE team SET status = 0, update_time = #{updateTime} WHERE team_id = #{teamId}")
    int deleteTeam(Integer teamId, String updateTime);

    // 检查用户是否是团队管理员
    @Select("SELECT COUNT(*) FROM team " +
            "WHERE team_id = #{teamId} AND create_user_id = #{userId} AND status = 1")
    int checkUserIsAdmin(@Param("teamId") Integer teamId, @Param("userId") Integer userId);
}
