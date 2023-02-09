package com.chenghan.keepaccounts.presenter;

import android.content.Context;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.bean.User;
import com.chenghan.keepaccounts.common.Data;
import com.chenghan.keepaccounts.model.IUserModel;
import com.chenghan.keepaccounts.model.impl.UserModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.IMyView;

public class MyPresenter<T extends IMyView> extends BasePresenter {
    private IUserModel iUserModel;

    public void editNickname(String nickname) {
        iUserModel.editNickname(nickname);
        User user = iUserModel.getLoggedInUser();
        Data.putData("user",user);
        ((T)weakView.get()).refresh();
    }

    public void editWX(String wx) {
        iUserModel.editWX(wx);
        Data.putData("user", iUserModel.getLoggedInUser());
        ((T)weakView.get()).refresh();
    }

    public void editPhone(String phone) {
        iUserModel.editPhone(phone);
        User user = iUserModel.getLoggedInUser();
        Data.putData("user",user);
        ((T)weakView.get()).refresh();
    }

    public void editPwd(String pwd) {
        iUserModel.editPassword(pwd);
        User user = iUserModel.getLoggedInUser();
        Data.putData("user",user);
        ((T)weakView.get()).refresh();
    }


    public MyPresenter(Context context) {
        iUserModel = new UserModel(new keepAccountsDatabaseHelper(context));
    }

}
