package com.chenghan.keepaccounts.view;

import com.chenghan.keepaccounts.base.IBaseView;
import com.chenghan.keepaccounts.bean.DisburseIncome;

public interface IEditView extends IBaseView {
    void refresh(DisburseIncome disburseIncome);
}
