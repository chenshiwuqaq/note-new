package org.ash.demos.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "team_member")
@Entity
public class TeamMember {
    @Id
    @Column(name = "id")
    private Integer id;             // 主键ID
    @Column(name = "team_id")
    private Integer teamId;         // 团队ID
    @Column(name = "user_id")
    private Integer userId;         // 用户ID
    @Column(name = "account")
    private String account;           // 用户账号
    @Column(name = "user_name")
    private String userName;        // 用户昵称
    @Column(name = "user_pic")
    private String userPic;         // 用户头像
    @Column(name = "role")
    private String role;           // 角色（admin/member）
    @Column(name = "join_time")
    private String joinTime;       // 加入时间
    @Column(name = "invite_status")
    private Integer inviteStatus;   // 邀请状态（0待接受 1已加入 2已拒绝）
    @Column(name = "invite_time")
    private String inviteTime;     // 邀请时间
    @Column(name = "accept_time")
    private String acceptTime;     // 接受邀请时间

    // 非数据库字段
    private String teamName;       // 团队名称
    private String region;         // 用户地区
    private Boolean isCurrentUser; // 是否是当前用户

    /* ---------- 构造方法 ---------- */
    public TeamMember() {
    }

    public TeamMember(Integer id, Integer teamId, Integer userId, String account,
                      String userName, String userPic, String role, String joinTime,
                      Integer inviteStatus, String inviteTime, String acceptTime,
                      String teamName, String region, Boolean isCurrentUser) {
        this.id = id;
        this.teamId = teamId;
        this.userId = userId;
        this.account = account;
        this.userName = userName;
        this.userPic = userPic;
        this.role = role;
        this.joinTime = joinTime;
        this.inviteStatus = inviteStatus;
        this.inviteTime = inviteTime;
        this.acceptTime = acceptTime;
        this.teamName = teamName;
        this.region = region;
        this.isCurrentUser = isCurrentUser;
    }

    /* ---------- Getter & Setter ---------- */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Integer inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(String inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getIsCurrentUser() {
        return isCurrentUser;
    }

    public void setIsCurrentUser(Boolean currentUser) {
        isCurrentUser = currentUser;
    }

    /* ---------- toString ---------- */
    @Override
    public String toString() {
        return "TeamMember{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", userId=" + userId +
                ", account=" + account +
                ", userName='" + userName + '\'' +
                ", userPic='" + userPic + '\'' +
                ", role='" + role + '\'' +
                ", joinTime='" + joinTime + '\'' +
                ", inviteStatus=" + inviteStatus +
                ", inviteTime='" + inviteTime + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", teamName='" + teamName + '\'' +
                ", region='" + region + '\'' +
                ", isCurrentUser=" + isCurrentUser +
                '}';

    }
}
