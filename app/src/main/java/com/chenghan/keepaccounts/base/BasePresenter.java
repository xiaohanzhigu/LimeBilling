package com.chenghan.keepaccounts.base;

import java.lang.ref.WeakReference;

public class BasePresenter<T extends IBaseView> {
    public WeakReference<T> weakView;

    /**
     * 绑定view
     * @param view
     */
    public void attachView(T view) {
        weakView = new WeakReference<>(view);
    }

    /**
     * 解绑
     */
    public void detachView() {
        if (weakView != null) {
            weakView.clear();
            weakView = null;
        }
    }
}
