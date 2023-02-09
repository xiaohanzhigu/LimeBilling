package com.chenghan.keepaccounts.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


public abstract class BaseFragment<T extends BasePresenter, V extends IBaseView> extends Fragment {
    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView((V)this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    protected abstract T createPresenter();
}