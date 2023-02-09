package com.chenghan.keepaccounts.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenghan.keepaccounts.R;


public class IncomeFragment extends BaseAddFragment {
    private OnChangeListener onChangeListener;

    @Override
    public void initData() {
        presenter.getLabelTypeByKind(0);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }
}