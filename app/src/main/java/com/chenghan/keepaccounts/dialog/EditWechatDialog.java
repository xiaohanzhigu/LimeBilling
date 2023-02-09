package com.chenghan.keepaccounts.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

public class EditWechatDialog extends BaseEditDialog{
    public EditWechatDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void editView() {
        titleTv.setText("绑定微信");
        hintTv.setText("请输入微信号");
        inputEt.setHint("WechatNumber");
    }
}
