package org.ash.demos.DTO;

public class UserDTO {
        private String userName;
        private String password;
        private String phoneNumber;
        private String email;
        private String userPic;
        private String userIdentify;
        private String createTime;
        private String updateTime;

        public UserDTO() {

        }
        public String getUserName() {
            return userName;
        }


        public String getPassword() {
            return password;
        }


        public String getEmail() { return email; }

        public String getPhoneNumber() { return phoneNumber; }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public void setEmail(String email) { this.email = email; }



    public UserDTO(String userName, String password, String userPic, String phoneNumber, String email, String userIdentify, String createTime, String updateTime) {
        this.userName = userName;
        this.password = password;
        this.userPic = userPic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userIdentify = userIdentify;
        this.createTime = createTime;
        this.updateTime = updateTime;
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
}
