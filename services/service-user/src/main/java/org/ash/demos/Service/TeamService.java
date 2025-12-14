package org.ash.demos.Service;

import org.ash.demos.Entity.Team;
import org.ash.demos.Entity.TeamMember;
import org.ash.demos.Entity.user;

import java.util.List;

public interface TeamService {
    // 团队管理
    Team createTeam(Team team, Integer currentUserId);
    List<Team> getUserTeams(Integer userId);
    Team getTeamInfo(Integer teamId);
    boolean updateTeam(Team team, Integer currentUserId);
    boolean deleteTeam(Integer teamId, Integer currentUserId);

    // 成员管理
    boolean inviteMember(Integer teamId, Integer inviteUserId, Integer currentUserId);
    boolean acceptInvitation(Integer teamId, Integer userId);
    boolean rejectInvitation(Integer teamId, Integer userId);
    List<TeamMember> getTeamMembers(Integer teamId);
    boolean removeMember(Integer teamId, Integer targetUserId, Integer currentUserId);
    boolean quitTeam(Integer teamId, Integer userId);

    // 查询
    List<TeamMember> getUserInvitations(Integer userId);
    boolean checkUserPermission(Integer teamId, Integer userId);

    List<user> searchUsersForInvite(String keyword, Integer currentUserId);
}
