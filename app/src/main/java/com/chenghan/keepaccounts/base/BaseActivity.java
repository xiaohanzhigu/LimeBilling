package com.chenghan.keepaccounts.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chenghan.keepaccounts.common.StatusBarUtil;


public abstract class BaseActivity<T extends BasePresenter, V extends IBaseView> extends AppCompatActivity {
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        presenter = createPresenter();
        presenter.attachView((V)this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract T createPresenter();
}