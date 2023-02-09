package com.chenghan.keepaccounts.presenter;

import android.content.Context;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.model.IDisburseIncomeModel;
import com.chenghan.keepaccounts.model.impl.DisburseIncomeModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.IHomeView;

public class MainPresenter<T extends IHomeView> extends BasePresenter {
    private IDisburseIncomeModel diModel;


    public MainPresenter(Context context) {
        diModel = new DisburseIncomeModel(new keepAccountsDatabaseHelper(context));
    }
}
