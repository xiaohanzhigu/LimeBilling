package com.chenghan.keepaccounts.presenter;


import android.content.Context;
import android.text.TextUtils;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.common.StringUtils;
import com.chenghan.keepaccounts.model.IUserModel;
import com.chenghan.keepaccounts.model.impl.UserModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.view.IRegisterView;

public class RegisterPresenter<T extends IRegisterView> extends BasePresenter {
    private IUserModel iUserModel;

    public void registerUser(String username, String password, String confirmPassword) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ((T)weakView.get()).showMessage("账号或密码不能为空");
            return;
        }

        if (!password.equals(confirmPassword)) {
            ((T)weakView.get()).showMessage("两次密码不一致");
            return;
        }

        if (!iUserModel.checkUsername(username)) {
            ((T)weakView.get()).showMessage("该账号已存在");
            return;
        }
        String nickname = StringUtils.getRandomNickname(10);

        String tempPassword = password;

        password = StringUtils.MD5(password);
        User user = new User(nickname, username, password);
        iUserModel.addUser(user);
        user.setPassword(tempPassword);
        ((T)weakView.get()).toLoginPage(user);
    }

    public RegisterPresenter(Context context) {
        iUserModel = new UserModel(new keepAccountsDatabaseHelper(context));
    }
}
