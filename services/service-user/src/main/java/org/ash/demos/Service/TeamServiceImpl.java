package org.ash.demos.Service;

import org.ash.demos.Entity.Team;
import org.ash.demos.Entity.TeamMember;
import org.ash.demos.Entity.user;
import org.ash.demos.Mapper.TeamMapper;
import org.ash.demos.Mapper.TeamMemberMapper;
import org.ash.demos.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamMemberMapper teamMemberMapper;

    @Autowired
    private UserMapper userMapper;

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    @Transactional
    public Team createTeam(Team team, Integer currentUserId) {
        // 设置创建信息
        team.setCreateUserId(currentUserId);
        String currentTime = getCurrentTime();
        team.setCreateTime(currentTime);
        team.setUpdateTime(currentTime);

        // 插入团队
        teamMapper.insertTeam(team);

        // 获取当前用户信息
        user currentUser = userMapper.selectUserById(currentUserId);

        // 将创建者添加为管理员
        TeamMember adminMember = new TeamMember();
        adminMember.setTeamId(team.getTeamId());
        adminMember.setUserId(currentUserId);
        adminMember.setAccount(currentUser.getAccount());
        adminMember.setUserName(currentUser.getUserName());
        adminMember.setUserPic(currentUser.getUserPic());
        adminMember.setRole("admin");
        adminMember.setJoinTime(currentTime);
        adminMember.setInviteStatus(1);

        teamMemberMapper.insertTeamMember(adminMember);

        return team;
    }

    @Override
    public List<Team> getUserTeams(Integer userId) {
        return teamMapper.selectTeamsByMember(userId);
    }

    @Override
    public Team getTeamInfo(Integer teamId) {
        return teamMapper.selectTeamById(teamId);
    }

    @Override
    public boolean updateTeam(Team team, Integer currentUserId) {
        // 检查权限
        int isAdmin = teamMapper.checkUserIsAdmin(team.getTeamId(), currentUserId);
        if (isAdmin == 0) {
            return false;
        }

        team.setUpdateTime(getCurrentTime());
        return teamMapper.updateTeam(team) > 0;
    }

    @Override
    public boolean deleteTeam(Integer teamId, Integer currentUserId) {
        // 检查权限
        int isAdmin = teamMapper.checkUserIsAdmin(teamId, currentUserId);
        if (isAdmin == 0) {
            return false;
        }

        return teamMapper.deleteTeam(teamId, getCurrentTime()) > 0;
    }

    @Override
    public boolean inviteMember(Integer teamId, Integer inviteUserId, Integer currentUserId) {
        // 检查权限
        int isAdmin = teamMapper.checkUserIsAdmin(teamId, currentUserId);
        if (isAdmin == 0) {
            return false;
        }

        // 检查用户是否已在团队中
        int alreadyInTeam = teamMemberMapper.checkUserInTeam(teamId, inviteUserId);
        if (alreadyInTeam > 0) {
            return false;
        }

        // 检查是否已有邀请
        int invitationExists = teamMemberMapper.checkInvitationExists(teamId, inviteUserId);
        if (invitationExists > 0) {
            return false;
        }

        // 获取被邀请用户信息
        user inviteUser = userMapper.selectUserById(inviteUserId);
        if (inviteUser == null) {
            return false;
        }

        // 创建邀请
        TeamMember invitation = new TeamMember();
        invitation.setTeamId(teamId);
        invitation.setUserId(inviteUserId);
        invitation.setAccount(inviteUser.getAccount());
        invitation.setUserName(inviteUser.getUserName());
        invitation.setUserPic(inviteUser.getUserPic());
        invitation.setInviteTime(getCurrentTime());

        return teamMemberMapper.insertInvitation(invitation) > 0;
    }

    @Override
    public boolean acceptInvitation(Integer teamId, Integer userId) {
        return teamMemberMapper.acceptInvitation(teamId, userId, getCurrentTime()) > 0;
    }

    @Override
    public boolean rejectInvitation(Integer teamId, Integer userId) {
        return teamMemberMapper.rejectInvitation(teamId, userId) > 0;
    }

    @Override
    public List<TeamMember> getTeamMembers(Integer teamId) {
        return teamMemberMapper.selectTeamMembers(teamId);
    }

    @Override
    public boolean removeMember(Integer teamId, Integer targetUserId, Integer currentUserId) {
        // 检查权限
        int isAdmin = teamMapper.checkUserIsAdmin(teamId, currentUserId);
        if (isAdmin == 0) {
            return false;
        }

        // 不能移除自己
        if (targetUserId.equals(currentUserId)) {
            return false;
        }

        return teamMemberMapper.removeMember(teamId, targetUserId) > 0;
    }

    @Override
    public boolean quitTeam(Integer teamId, Integer userId) {
        // 检查是否是管理员
        int isAdmin = teamMapper.checkUserIsAdmin(teamId, userId);
        if (isAdmin > 0) {
            return false; // 管理员不能直接退出，需要先转让管理权或解散团队
        }

        return teamMemberMapper.quitTeam(teamId, userId) > 0;
    }

    @Override
    public List<TeamMember> getUserInvitations(Integer userId) {
        return teamMemberMapper.selectInvitationsByUser(userId);
    }

    @Override
    public boolean checkUserPermission(Integer teamId, Integer userId) {
        return teamMemberMapper.checkUserInTeam(teamId, userId) > 0;
    }

    @Override
    public List<user> searchUsersForInvite(String keyword, Integer currentUserId) {
        // 搜索用户（排除自己）
        // return userMapper.searchUsersByKeyword(keyword, currentUserId);
        return new ArrayList<>();
    }

    /**
     * 检查是否可以邀请用户
     */
    private boolean canInviteUser(Integer teamId, Integer inviteUserId, Integer currentUserId) {
        // 1. 检查当前用户是否是管理员
        int isAdmin = teamMapper.checkUserIsAdmin(teamId, currentUserId);
        if (isAdmin == 0) {
            return false;
        }

        // 2. 检查被邀请用户是否存在
        user inviteUser = userMapper.selectUserById(inviteUserId);
        if (inviteUser == null) {
            return false;
        }

        // 3. 不能邀请自己
        if (inviteUserId.equals(currentUserId)) {
            return false;
        }

        // 4. 检查是否已在团队中
        int alreadyInTeam = teamMemberMapper.checkUserInTeam(teamId, inviteUserId);
        if (alreadyInTeam > 0) {
            return false;
        }

        // 5. 检查是否已有待处理的邀请
        int invitationExists = teamMemberMapper.checkInvitationExists(teamId, inviteUserId);
        if (invitationExists > 0) {
            return false;
        }

        return true;
    }
}
