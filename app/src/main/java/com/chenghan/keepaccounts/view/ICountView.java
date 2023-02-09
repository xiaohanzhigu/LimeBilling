package com.chenghan.keepaccounts.view;

import com.chenghan.keepaccounts.base.IBaseView;
import com.chenghan.keepaccounts.bean.DisburseIncome;

import java.util.List;
import java.util.Map;

public interface ICountView extends IBaseView {
    void refresh(Map<String, Object> map);
}
