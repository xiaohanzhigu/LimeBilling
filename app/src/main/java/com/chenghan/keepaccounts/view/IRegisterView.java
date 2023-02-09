package com.chenghan.keepaccounts.view;

import com.chenghan.keepaccounts.base.IBaseView;
import com.chenghan.keepaccounts.bean.User;


public interface IRegisterView extends IBaseView {
    void toLoginPage(User user);
}
