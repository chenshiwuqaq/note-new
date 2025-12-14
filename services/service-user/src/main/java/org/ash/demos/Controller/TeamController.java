package org.ash.demos.Controller;


import jakarta.servlet.http.HttpSession;
import org.ash.demos.Entity.Team;
import org.ash.demos.Entity.TeamMember;
import org.ash.demos.Entity.user;
import org.ash.demos.Service.TeamService;
import org.ash.demos.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * 从Session中获取当前用户ID
     * 注意：需要在登录接口中设置Session，如：
     * session.setAttribute("userId", user.getId());
     * session.setAttribute("userAccount", user.getAccount());
     */
    private Integer getCurrentUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userIdObj = session.getAttribute("userId");
            if (userIdObj instanceof Integer) {
                return (Integer) userIdObj;
            } else if (userIdObj instanceof Long) {
                return ((Long) userIdObj).intValue();
            } else if (userIdObj != null) {
                try {
                    return Integer.parseInt(userIdObj.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 统一的登录检查方法
     */
    private Result<Void> checkLogin(HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        if (userId == null) {
            return Result.error("用户未登录");
        }
        return Result.success();
    }

    /**
     * 获取当前用户ID，如果未登录则返回错误
     */
    private Result<Integer> getCurrentUserIdOrError(HttpServletRequest request) {
        Integer userId = getCurrentUserId(request);
        if (userId == null) {
            return Result.error("用户未登录");
        }
        return Result.success(userId);
    }

    // 1. 创建团队
    @PostMapping("/create")
    public Result<Team> createTeam(@RequestBody Team team, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        Team createdTeam = teamService.createTeam(team, userIdResult.getData());
        return Result.success(createdTeam);
    }

    // 2. 获取用户加入的团队列表（左侧菜单）
    @GetMapping("/list")
    public Result<List<Team>> getUserTeams(HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        List<Team> teams = teamService.getUserTeams(userIdResult.getData());
        return Result.success(teams);
    }

    // 3. 获取团队详情
    @GetMapping("/{teamId}")
    public Result<Team> getTeamInfo(@PathVariable Integer teamId, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        // 检查用户是否有权限访问该团队
        boolean hasPermission = teamService.checkUserPermission(teamId, userIdResult.getData());
        if (!hasPermission) {
            return Result.error("无权访问该团队");
        }

        Team team = teamService.getTeamInfo(teamId);
        return Result.success(team);
    }

    // 4. 更新团队信息（仅管理员）
    @PutMapping("/update")
    public Result<Void> updateTeam(@RequestBody Team team, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.updateTeam(team, userIdResult.getData());
        if (success) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败，可能您不是管理员");
    }

    // 5. 解散团队（仅管理员）
    @DeleteMapping("/{teamId}")
    public Result<Void> deleteTeam(@PathVariable Integer teamId, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.deleteTeam(teamId, userIdResult.getData());
        if (success) {
            return Result.success("团队解散成功");
        }
        return Result.error("解散失败，可能您不是管理员");
    }

    // 6. 邀请成员加入团队（仅管理员）
    @PostMapping("/{teamId}/invite")
    public Result<Void> inviteMember(@PathVariable Integer teamId,
                                     @RequestParam Integer inviteUserId,
                                     HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.inviteMember(teamId, inviteUserId, userIdResult.getData());
        if (success) {
            return Result.success("邀请已发送");
        }
        return Result.error("邀请失败，可能用户已在团队中或您不是管理员");
    }

    // 7. 获取团队成员列表（点击团队展开）
    @GetMapping("/{teamId}/members")
    public Result<List<TeamMember>> getTeamMembers(@PathVariable Integer teamId, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        // 检查用户是否有权限访问该团队
        boolean hasPermission = teamService.checkUserPermission(teamId, userIdResult.getData());
        if (!hasPermission) {
            return Result.error("无权访问该团队");
        }

        List<TeamMember> members = teamService.getTeamMembers(teamId);

        // 标记当前用户
        Integer currentUserId = userIdResult.getData();
        if (currentUserId != null) {
            for (TeamMember member : members) {
                if (member.getUserId().equals(currentUserId)) {
                    member.setIsCurrentUser(true);
                    break;
                }
            }
        }

        return Result.success(members);
    }

    // 8. 获取用户收到的邀请列表
    @GetMapping("/invitations")
    public Result<List<TeamMember>> getUserInvitations(HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        List<TeamMember> invitations = teamService.getUserInvitations(userIdResult.getData());
        return Result.success(invitations);
    }

    // 9. 接受邀请
    @PostMapping("/{teamId}/accept")
    public Result<Void> acceptInvitation(@PathVariable Integer teamId, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.acceptInvitation(teamId, userIdResult.getData());
        if (success) {
            return Result.success("已加入团队");
        }
        return Result.error("操作失败，可能邀请已过期");
    }

    // 10. 拒绝邀请
    @PostMapping("/{teamId}/reject")
    public Result<Void> rejectInvitation(@PathVariable Integer teamId, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.rejectInvitation(teamId, userIdResult.getData());
        if (success) {
            return Result.success("已拒绝邀请");
        }
        return Result.error("操作失败，可能邀请已过期");
    }

    // 11. 移除成员（仅管理员）
    @DeleteMapping("/{teamId}/member/{targetUserId}")
    public Result<Void> removeMember(@PathVariable Integer teamId,
                                     @PathVariable Integer targetUserId,
                                     HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.removeMember(teamId, targetUserId, userIdResult.getData());
        if (success) {
            return Result.success("成员已移除");
        }
        return Result.error("移除失败，可能您不是管理员或试图移除自己");
    }

    // 12. 退出团队
    @PostMapping("/{teamId}/quit")
    public Result<Void> quitTeam(@PathVariable Integer teamId, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        boolean success = teamService.quitTeam(teamId, userIdResult.getData());
        if (success) {
            return Result.success("已退出团队");
        }
        return Result.error("退出失败，管理员请先转让管理权或解散团队");
    }

    // 13. 搜索用户（用于邀请）
    @GetMapping("/search/user")
    public Result<List<user>> searchUser(@RequestParam String keyword, HttpServletRequest request) {
        Result<Integer> userIdResult = getCurrentUserIdOrError(request);
        if (!userIdResult.isSuccess()) {
            return Result.error(userIdResult.getMessage());
        }

        // 这里可以调用UserService来搜索用户
        // List<User> users = userService.searchUsers(keyword);
        // return Result.success(users);

        return Result.success(null);
    }
}