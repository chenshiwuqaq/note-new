package org.ash.demos.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "team")
@Entity
public class Team {
    @Id
    @Column(name = "team_id")
    private Integer teamId;          // 团队ID
    @Column(name = "team_name")
    private String teamName;        // 团队名称
    @Column(name = "team_desc")
    private String teamDesc;        // 团队描述
    @Column(name = "team_user_id")
    private Integer createUserId;   // 创建者ID
    @Column(name = "create_time")
    private String createTime;      // 创建时间
    @Column(name = "update_time")
    private String updateTime;      // 更新时间
    @Column(name = "status")
    private Integer status;         // 状态（1正常 0解散）

    // 非数据库字段（用于关联查询）
    private String createUserName;  // 创建者昵称
    private Integer memberCount;    // 成员数量


    /* ---------- 构造方法 ---------- */
    public Team() {
    }

    public Team(Integer teamId, String teamName, String teamDesc,
                Integer createUserId, String createTime, String updateTime,
                Integer status, String createUserName, Integer memberCount) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDesc = teamDesc;
        this.createUserId = createUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.createUserName = createUserName;
        this.memberCount = memberCount;
    }

    /* ---------- Getter & Setter ---------- */
    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    /* ---------- toString ---------- */
    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamDesc='" + teamDesc + '\'' +
                ", createUserId=" + createUserId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status=" + status +
                ", createUserName='" + createUserName + '\'' +
                ", memberCount=" + memberCount +
                '}';
    }
}
