package com.zh.testbottonnav.net;

public class User {
    private Integer id;
    private String userName;
    private String password;
    private String avatar;
    private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar = avatar; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
