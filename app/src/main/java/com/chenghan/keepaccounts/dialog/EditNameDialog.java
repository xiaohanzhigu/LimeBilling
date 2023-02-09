package com.chenghan.keepaccounts.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

public class EditNameDialog extends BaseEditDialog {
    public EditNameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void editView() {
        titleTv.setText("修改姓名");
        hintTv.setText("请输入新的姓名");
        inputEt.setHint("Name");
    }


}
