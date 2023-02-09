package com.chenghan.keepaccounts.presenter;

import android.content.Context;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.model.IDisburseIncomeModel;
import com.chenghan.keepaccounts.model.impl.DisburseIncomeModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.IEditView;

public class EditPresenter<T extends IEditView> extends BasePresenter {
    private IDisburseIncomeModel diModel;

    public void deleteAccount(DisburseIncome disburseIncome) {
        diModel.deleteAccount(disburseIncome);
    }

    public EditPresenter(Context context) {
        diModel = new DisburseIncomeModel(new keepAccountsDatabaseHelper(context));
    }

    public void showDate(int id) {
        DisburseIncome disburseIncome = diModel.getDisburseIncomeById(id);
        ((T)weakView.get()).refresh(disburseIncome);
    }
}
