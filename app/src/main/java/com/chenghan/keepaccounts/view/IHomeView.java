package com.chenghan.keepaccounts.view;


import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.base.IBaseView;

import java.util.List;

public interface IHomeView extends IBaseView {
    void refresh(List<DisburseIncome> list);

}
