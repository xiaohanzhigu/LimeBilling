package com.chenghan.keepaccounts.model.impl;

import android.database.sqlite.SQLiteOpenHelper;

import com.chenghan.keepaccounts.base.BaseModel;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.model.IUserModel;
import com.chenghan.keepaccounts.bean.User;


public class UserModel extends BaseModel<User> implements IUserModel {

    public UserModel(SQLiteOpenHelper helper) {
        super(helper);
    }

    @Override
    public User checkUser(String username) {
        User user = querySingle("select * from t_user where username = ? ", username);
        return user;
    }

    @Override
    public User getLoggedInUser() {
        int userId = ((User) Data.getData("user")).getId();
        User user = querySingle("select * from t_user where id = ?", userId);
        return user;
    }


    @Override
    public void addUser(User user) {
        String sql = "insert into t_user values(null, ?, ?, ?, null, null)";
        update(sql, user.getNickName(), user.getUsername(), user.getPassword());
    }

    @Override
    public boolean checkUsername(String username) {
        User user = querySingle("select * from t_user where username = ?", username);
        return user == null;
    }

    @Override
    public void editNickname(String nickname) {
        Integer userId = ((User) Data.getData("user")).getId();
        update("update t_user set nickname = ? where id = ?", nickname, userId);
    }

    @Override
    public void editWX(String wx) {
        Integer userId = ((User) Data.getData("user")).getId();
        update("update t_user set wx = ? where id = ?", wx, userId);
    }

    @Override
    public void editPhone(String phone) {
        Integer userId = ((User) Data.getData("user")).getId();
        update("update t_user set phone = ? where id = ?", phone, userId);
    }

    @Override
    public void editPassword(String password) {
        Integer userId = ((User) Data.getData("user")).getId();
        update("update t_user set password = ? where id = ?", password, userId);
    }
}
