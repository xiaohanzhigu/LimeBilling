package com.chenghan.keepaccounts.bean;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String nickName;
    private String username;
    private String password;
    private String wx;
    private String phone;

    public User() {

    }

    public User(Integer id) {
        this.id = id;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String nickName, String username, String password) {
        this.nickName = nickName;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
