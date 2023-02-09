package com.chenghan.keepaccounts.presenter;

import android.content.Context;

import com.chenghan.keepaccounts.base.BasePresenter;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.bean.LabelType;
import com.chenghan.keepaccounts.model.IDisburseIncomeModel;
import com.chenghan.keepaccounts.model.impl.DisburseIncomeModel;
import com.chenghan.keepaccounts.model.ILabelTypeModel;
import com.chenghan.keepaccounts.model.impl.LabelTypeModel;
import com.chenghan.keepaccounts.db.keepAccountsDatabaseHelper;
import com.chenghan.keepaccounts.view.IAddView;
import com.chenghan.keepaccounts.view.IBaseAddFragView;

import java.util.List;

public class AddPresenter<T extends IAddView> extends BasePresenter {
    private IDisburseIncomeModel diModel;
    private ILabelTypeModel iLabelTypeModel;

    public void getLabelTypeByKind(int kind) {
        List<LabelType> list = iLabelTypeModel.getLabelTypeByKind(kind);
        ((IBaseAddFragView)weakView.get()).refresh(list);
    }

    public void addOrUpdateAccount(DisburseIncome disburseIncome) {
        if (disburseIncome.getId() == null) {
            diModel.addAccount(disburseIncome);
        } else {
            diModel.updateAccount(disburseIncome);
        }
    }

    public AddPresenter(Context context) {
        keepAccountsDatabaseHelper helper = new keepAccountsDatabaseHelper(context);
        diModel = new DisburseIncomeModel(helper);
        iLabelTypeModel = new LabelTypeModel(helper);
    }

    public LabelType getLabelByName(String name) {
        return iLabelTypeModel.getLabelByName(name);
    }
}
