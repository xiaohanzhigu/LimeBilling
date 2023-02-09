package com.chenghan.keepaccounts.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

public class EditPhoneDialog extends BaseEditDialog{
    public EditPhoneDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void editView() {
        titleTv.setText("绑定手机号");
        hintTv.setText("请输入手机号");
        inputEt.setHint("Phone");
    }
}
