package com.example.babble.modals;


//model class for doctors which will show needers
public class UsersModal {
    String dp,username,userid, lastMessage,email;

    public UsersModal(String dp, String username, String userid, String email) {
        this.dp = dp;
        this.username = username;
        this.userid = userid;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsersModal(){}



    public String getDp() {
        return dp;
    }




    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUserid(String key) {
        return userid;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
