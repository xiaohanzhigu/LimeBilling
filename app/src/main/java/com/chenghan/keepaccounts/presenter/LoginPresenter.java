package com.chenghan.keepaccounts.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.common.StringUtils;
import com.chenghan.keepaccounts.model.IUserModel;
import com.chenghan.keepaccounts.model.impl.UserModel;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.ILoginView;


public class LoginPresenter<T extends ILoginView> extends BasePresenter {
    private IUserModel iUserModel ;

    public LoginPresenter(Context context) {
        iUserModel= new UserModel(new keepAccountsDatabaseHelper(context));
    }

    public void login(String username, String password, boolean isAutoLogin) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ((T)weakView.get()).showMessage("账号或密码不能为空");
            return;
        }

        User user = iUserModel.checkUser(username);
        if (user == null) {
            ((T)weakView.get()).showMessage("账号不存在");
            return;
        }

        //如果是自动登录进来的不用进行MD5加密
       if (!isAutoLogin) {
           password = StringUtils.MD5(password);
       }
        if (!password.equals(user.getPassword())) {
            ((T)weakView.get()).showMessage("密码错误");
            return;
        }
        user.setPassword(password);
        Data.putData("user", user);//将当前登录的用户放入Date
        ((T)weakView.get()).login();
    }
}
