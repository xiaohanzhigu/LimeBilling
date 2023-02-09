package com.chenghan.keepaccounts.view;

import com.chenghan.keepaccounts.base.IBaseView;
import com.chenghan.keepaccounts.bean.LabelType;

import java.util.List;

public interface IBaseAddFragView extends IBaseView {
    void refresh(List<LabelType> list);
}
