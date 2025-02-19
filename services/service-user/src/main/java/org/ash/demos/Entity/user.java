package org.ash.demos.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "user")
@Entity
public class user {
    @Id
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "account")
    private String account;
    @Column(name = "password")
    private String password;
    @Column(name = "user_pic")
    private String userPic;
    public user() {

    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public user(int id, String userName, String account, String password , String userPic) {
        this.id = id;
        this.userName = userName;
        this.account = account;
        this.password = password;
        this.userPic = userPic;
    }
}
