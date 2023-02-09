package com.chenghan.keepaccounts.fragment;

public class ExpendFragment extends BaseAddFragment {
    @Override
    public void initData() {
        presenter.getLabelTypeByKind(1);
    }
}
