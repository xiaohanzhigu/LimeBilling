package com.chenghan.keepaccounts.model;


import com.chenghan.keepaccounts.bean.User;

public interface IUserModel {
    User checkUser (String username);

    User getLoggedInUser();

    void addUser(User user);

    boolean checkUsername(String username);

    void editNickname(String nickname);

    void editWX(String wx);

    void editPhone(String phone);

    void editPassword(String password);
}
