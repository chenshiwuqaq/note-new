package org.ash.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "user")
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @JsonProperty("id")
    private int id;
    
    @Column(name = "account")
    @JsonProperty("account")
    private Long account;
    
    @Column(name = "password")
    @JsonProperty("password")
    private String password;
    
    @Column(name = "user_name")
    @JsonProperty("userName")
    private String userName;
    
    @Column(name = "user_pic")
    @JsonProperty("userPic")
    private String userPic;
    
    @Column(name = "user_identify")
    @JsonProperty("userIdentify")
    private String userIdentify;
    
    @Column(name = "create_time")
    @JsonProperty("createTime")
    private String createTime;
    
    @Column(name = "update_time")
    @JsonProperty("updateTime")
    private String updateTime;
    
    @Column(name = "remark")
    @JsonProperty("remark")
    private String remark;
    
    @Column(name = "region")
    @JsonProperty("region")
    private String region;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUserIdentify() {
        return userIdentify;
    }

    public void setUserIdentify(String userIdentify) {
        this.userIdentify = userIdentify;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public User(int id, String userName, Long account, String password , String userPic) {
        this.id = id;
        this.userName = userName;
        this.account = account;
        this.password = password;
        this.userPic = userPic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account=" + account +
                ", userName='" + userName + '\'' +
                ", userPic='" + userPic + '\'' +
                ", userIdentify='" + userIdentify + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", remark='" + remark + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
