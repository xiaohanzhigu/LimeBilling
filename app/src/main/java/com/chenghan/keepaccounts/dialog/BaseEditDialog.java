package com.chenghan.keepaccounts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chenghan.keepaccounts.R;

public abstract class BaseEditDialog extends Dialog implements View.OnClickListener {
    public TextView titleTv, hintTv, noEmptyTv;
    public EditText inputEt;
    public Button cancelBtn, ensureBth;
    private OnEnsureListener onEnsureListener;

    public abstract void editView();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_edit_pwd_ensure) {
            String content = inputEt.getText().toString();

            if (TextUtils.isEmpty(content)) {
                noEmptyTv.setVisibility(View.VISIBLE);
                return;
            }

            onEnsureListener.onEnsureListener(content);
        }
        cancel();
    }

    public BaseEditDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);

        //设置背景透明
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        //初始化布局控件
        initView();
        //设置参数必须在show之后，不然没有效果
        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().setAttributes(params);
    }

    private void initView() {
        titleTv = findViewById(R.id.tv_setting_dialog_title);
        hintTv = findViewById(R.id.tv_setting_dialog_hint);
        inputEt = findViewById(R.id.et_edit_old_password);
        cancelBtn = findViewById(R.id.btn_edit_pwd_cancel);
        ensureBth = findViewById(R.id.btn_edit_pwd_ensure);
        noEmptyTv = findViewById(R.id.tv_edit_no_empty1);
        cancelBtn.setOnClickListener(this);
        ensureBth.setOnClickListener(this);
        editView();
    }

    public interface OnEnsureListener{
        void onEnsureListener(String editContent);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }
}
